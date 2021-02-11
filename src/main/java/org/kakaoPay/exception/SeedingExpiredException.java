package org.kakaoPay.exception;

public class SeedingExpiredException extends Exception {
    public SeedingExpiredException(){
        super("토큰 유효 시간이 지났습니다.");
    }
}
