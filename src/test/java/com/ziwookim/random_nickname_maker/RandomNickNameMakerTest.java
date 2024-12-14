package com.ziwookim.random_nickname_maker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomNickNameMakerTest {

    @DisplayName("랜덤 닉네임 생성 테스트")
    @Test
    void 랜덤_닉네임_생성() {

        assertNotEquals(RandomNickNameMaker.builder()
                .callNickNameMaker(), RandomNickNameMaker.builder()
                .callNickNameMaker());
    }

    @DisplayName("닉네임 생성 길이 제한 테스트")
    @Test
    void 랜덤_닉네임_생성_길이_제한() {

        String nickName = RandomNickNameMaker.builder()
                .maxLength(13)
                .callNickNameMaker();

        assertTrue(nickName.replace(" ", "").length() <= 13);
    }

    @DisplayName("닉네임 음절 설정 테스트")
    @Test
    void 닉네임_음절_설정() {
        String nickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                .callNickNameMaker();

        String nickName2 = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_1)
                .callNickNameMaker();

        assertEquals(3, nickName.split(" ").length);
        assertEquals(1, nickName2.split(" ").length);
    }

    @DisplayName("공백 문자 제거 테스트")
    @Test
    void 공백_문자_제거() {
        String nickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                .isIncludedBlank(false)
                .callNickNameMaker();

        String nickName2 = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_2)
                .isIncludedBlank(false)
                .callNickNameMaker();

        assertFalse(nickName.contains(" "));
        assertFalse(nickName2.contains(" "));
    }

    @DisplayName("글자수_제한_테스트")
    @Test
    void 글자수_제한() {

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            RandomNickNameMaker.builder()
                    .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                    .maxLength(7)
                    .isIncludedBlank(false)
                    .callNickNameMaker();
        });

        assertEquals("닉네임 최대 글자수 값을 늘려 주세요.", thrown.getMessage());
    }

    @DisplayName("다중 조건 테스트")
    @Test
    void 다중_조건() {
        String nickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_1)
                .maxLength(1)
                .callNickNameMaker();

        String nickName2 = RandomNickNameMaker.builder()
                .isIncludedBlank(false)
                .callNickNameMaker();

        String nickName3 = RandomNickNameMaker.builder()
                .maxLength(10)
                .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                .callNickNameMaker();

        assertTrue(!nickName.contains(" ") && nickName.length() <= 1);
        assertFalse(nickName2.contains(" "));
        assertTrue(3 == nickName3.split(" ").length && nickName3.replace(" ", "").length() <= 10);
    }
}