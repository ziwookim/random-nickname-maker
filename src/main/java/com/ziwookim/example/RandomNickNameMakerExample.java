package com.ziwookim.example;

import com.ziwookim.random_nickname_maker.NumberOfPhrase;
import com.ziwookim.random_nickname_maker.RandomNickNameMaker;

public class RandomNickNameMakerExample {

    public static void main(String[] args) {
        String defaultNickName = RandomNickNameMaker.builder()
                .callNickNameMaker();
        /**
         * default 설정 값
         * 2개 어구
         * .numberOfPhrase(NumberOfPhrase.PHRASE_2)
         *
         * 공백 값 포함
         * .isIncludedBlank(false)
         *
         * 공백 값 미포함 최대 글자 길이 값
         * .maxLength(-1)
         */

        System.out.println("default 설정 값으로 만들어진 랜덤 닉네임 입니다... nickName: " + defaultNickName);

        String optionalNickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                .isIncludedBlank(false)
                .maxLength(10)
                .callNickNameMaker();

        System.out.println("3개 어구 + 공백 문자 포함 X + 공백 미포함 최대 길이 10자 이내 설정 값으로 만들어진 랜덤 닉네임 입니다... nickName: " + optionalNickName);

        String onePhraseNickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_1)
                .maxLength(1)
                .callNickNameMaker();

        System.out.println("1개 어구 (단어로만 구성된) 설정 값으로 만들어진 랜덤 닉네임 입니다... nickName: " + onePhraseNickName);
    }
}
