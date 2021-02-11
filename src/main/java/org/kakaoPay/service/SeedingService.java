package org.kakaoPay.service;

import org.kakaoPay.exception.SelfPickUpException;
import org.kakaoPay.jpa.entity.Seeding;

public interface SeedingService {
    /**
     * 뿌리기
     * @param roomId    대화방 식별값
     * @param userId    사용자 식별값
     * @param amount    뿌릴 금액
     * @param peoples   뿌릴 인원수
     * @return {@Link Seeding} 뿌리기 entity
     */

    Seeding sprinkle(String roomId, long userId, long amount, int peoples);

    /**
     * 조회
     * @param token     토큰값
     * @param userId    사용자 식별값
     * @return {@Link Seeding} 뿌리기 entity
     */

    Seeding search(String token, long userId) throws Exception;

    /**
     * 줍기
     * @param token     토큰값
     * @param roomId    대화방 식별값
     * @param userId    사용자 식별값
     * @return 받은 금액
     */

    long pickUp(String token, String roomId, long userId) throws SelfPickUpException, Exception;
}
