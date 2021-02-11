package org.kakaoPay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.kakaoPay.exception.*;
import org.kakaoPay.jpa.entity.PickUp;
import org.kakaoPay.jpa.entity.Seeding;
import org.kakaoPay.jpa.repository.PickUpRepository;
import org.kakaoPay.jpa.repository.SeedingRepository;
import org.kakaoPay.security.TokenGenerator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeedingServiceImpl implements SeedingService{

    private final TokenGenerator tokenGenerator;
    private final SeedingRepository seedingRepository;
    private final PickUpRepository pickupRepository;
    @Transactional
    @Override
    public Seeding sprinkle(String roomId, long userId, long amount, int count) {
        String token = tokenGenerator.generate(3);
        Seeding seeding = new Seeding(token, roomId, userId, amount, count);
        seedingRepository.save(seeding);
        long[] divide = divide(seeding.getAmount(), seeding.getPeoples());
        for (int i = 0; i < divide.length; i++) {
            PickUp pickup = new PickUp(seeding, i + 1, divide[i]);
            System.out.println(pickup.toString());
            pickupRepository.save(pickup);
        }
        return seeding;
    }

    @Override
    public Seeding search(String token, long userId) throws Exception{

        LocalDateTime createDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusDays(7);
        Seeding seeding = seedingRepository.findByTokenAndCreateDateGreaterThan(token, createDate);

        log.debug("seeding = {}",seeding);

        if(Objects.isNull(seeding)){
            throw new Exception("뿌리기 토큰을 찾을 수 없습니다.");
        }

        if(seeding.getUserId() != userId){
            throw new Exception("너가 뿌린거 아니니까 조회할 수 없다.");
        }

        return seeding;
    }

    @Transactional
    @Override
    public long pickUp(String token, String roomId, long userId) throws Exception {

        Seeding seeding = seedingRepository.findByToken(token);
        if(Objects.isNull(seeding)){ // 뿌리기 없음
            throw new SeedingNotFoundException();
        }

        if(seeding.isExpired(10)){
            // 만료된 토큰
            throw new SeedingExpiredException();
        }

        if(!seeding.getRoomId().equals(roomId)){
            // 다른 방 사용자
            throw new NotBelongRoomException();
        }

        if(seeding.getUserId() == userId ){
            // 뿌린사람은 주울수 없음
            throw new SelfPickUpException();
        }

        if(seeding.getPickups().stream()
                    .filter(PickUp::isReceived)
                    .anyMatch(it -> it.getUserId() == userId)){
            // 한번만 받을 수 있다.
            throw new DuplicatePickupException();
        }

        List<PickUp> notPickUpYet = seeding.getPickups().stream()
                                            .filter(PickUp::isNotReceived)
                                            .collect(Collectors.toList());
        System.out.println(notPickUpYet.toString());
        PickUp pickup = notPickUpYet.get(0);

        pickup.pickup(userId);
        return pickup.getAmount();

    }



    private long[] divide(long amount, int peoples){
        long[] array = new long[peoples];
        long max = RandomUtils.nextLong(amount/peoples, amount/peoples*2);
        for(int i=0;i<peoples-1;i++){
            array[i] = RandomUtils.nextLong(1, Math.min(max,amount));
            amount -= array[i];
        }
        array[peoples-1] = amount;
        return array;
    }
}
