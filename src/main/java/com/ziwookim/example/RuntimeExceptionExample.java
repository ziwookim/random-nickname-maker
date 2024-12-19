package com.ziwookim.example;

import com.ziwookim.random_nickname_maker.RandomNickNameMaker;

public class RuntimeExceptionExample {

    public static void main(String[] args) {

        /*
        * "./nickname_dictionary.csv" 파일이 존재하지 않는 경우 발생합니다.
        */

        try{
            String nickName = RandomNickNameMaker.builder().callNickNameMaker();

        } catch (RuntimeException e) {

            System.err.println("RuntimeException 발성... message: " + e.getMessage());
        }
    }
}
