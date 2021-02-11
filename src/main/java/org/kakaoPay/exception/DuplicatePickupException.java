package org.kakaoPay.exception;

public class DuplicatePickupException extends Exception {
    public DuplicatePickupException(){
        super("이미 뿌리기를 받은 사용자입니다.");
    }
}
