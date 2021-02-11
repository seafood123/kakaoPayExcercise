package org.kakaoPay.exception;

public class SelfPickUpException extends Exception {
    public SelfPickUpException(){
        super("뿌린 사용자는 주울 수 없습니다.");
    }
}
