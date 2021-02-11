package org.kakaoPay.security;

public interface TokenGenerator {

    /**
     * @param length 생성 길이
     * @return 생성된 토큰 값
     * */
    String generate(int length);
}
