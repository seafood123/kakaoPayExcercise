package org.kakaoPay.exception;

public class SeedingNotFoundException extends NotFoundException {
    public SeedingNotFoundException(){
        super("Not Found Seeding Token");
    }
}
