package com.ziwookim.example;

import com.ziwookim.random_nickname_maker.NumberOfPhrase;
import com.ziwookim.random_nickname_maker.RandomNickNameMaker;

public class IllegalArgumentExceptionExample {

    public static void main(String[] args) {

        /*
         * 지정된 글자수가 너무 짧아 랜덤 닉네임을 생성할 수 없는 경우 예외가 발생합니다.
         */

        try {
            String onePhraseNickName = RandomNickNameMaker.builder()
                    .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                    .maxLength(3)
                    .callNickNameMaker();
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException 발생... message: " + e.getMessage());
        }
    }
}
