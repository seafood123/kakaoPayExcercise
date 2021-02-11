package org.kakaoPay.exception;

public class NotBelongRoomException extends Exception {
    public NotBelongRoomException(){
        super("방에 속하지 않은 사용자입니다.");
    }
}
