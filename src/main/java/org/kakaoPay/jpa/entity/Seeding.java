package org.kakaoPay.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Entity
@Table(name="SEEDTABLE")
public class Seeding {
    /**
     * 토큰 값 3자리
     */
    @NonNull
    @Id
    @Column(name="TOKEN", length=3, nullable=false)
    private String token;

    /**
     * 대화방 식별 값
     */
    @NonNull
    @Column(name="ROOMID", nullable=false)
    private String roomId;

    /**
     * 사용자 식별 값
     */
    @NonNull
    @Column(name="USERID",nullable = false)
    private long userId;

    /**
     * 뿌린 금액
     */
    @NonNull
    @Column(name="AMOUNT", nullable = false)
    private Long amount;

    /**
     * 받을 인원수
     */
    @NonNull
    @Column(name="PEOPLES", nullable = false)
    private int peoples;

    /**
     * 토큰 생성 시간
     */
    @Column(name="CREATE_DATE", nullable = false)
    @Setter
    private LocalDateTime createDate = LocalDateTime.now();

    /**
     * 토큰 만료 여부
     * @param minutes 분
     * @return {@code true} 만
     */
    public boolean isExpired(int minutes){
        return createDate.isBefore(LocalDateTime.now().minusMinutes(minutes));
    }

    @ToString.Exclude
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="TOKEN")
    @OrderBy("SEQ ASC")
    private List<PickUp> pickups;

    public List<PickUp> getPickups(){
        if(Objects.isNull(pickups)){
            pickups=new ArrayList<>();
        }
        return pickups;
    }
}
