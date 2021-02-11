package org.kakaoPay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class PickUpDto {
    // 받은 사람
    private long userId;
    // 받은 금액
    private long amount;
}
