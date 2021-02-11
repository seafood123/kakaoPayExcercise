package org.kakaoPay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class SeedingDto {
    // 뿌린 시각
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss")
    private final LocalDateTime datetime;

    // 뿌린 금액
    private final long amount;

    // 받기 완료된 금액
    private final long pickUpAmount;

    // 받기 완료 된 정보
    private final List<PickUpDto> pickUpList;
}
