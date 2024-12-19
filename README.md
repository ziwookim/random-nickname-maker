# 🎲 random-nickname-maker (랜덤 닉네임 생성기)


## 🌟 기능 개요
게임이나 각종 커뮤니티 내에서 부여되는 랜덤 닉네임을 생성하는 오픈 소스 입니다.

---

## 🧭 설치 방법

<b>Gradle</b>
````groovy
repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ziwookim:random-nickname-maker:1.0.13'
}
````

###
<b>Maven</b>
````xml
<repositories>
    <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.ziwookim</groupId>
    <artifactId>random-nickname-maker</artifactId>
    <version>1.0.13</version>
</dependency>
````

###

## 🗺️ 사용 방법

### 1. 프로젝트 내 ["/.nickname_dictionary.csv"]([nickname_dictionary.csv](https://prod-files-secure.s3.us-west-2.amazonaws.com/66fe14c5-e3ed-4f06-b0ef-d16353703ce7/2594a773-0d6c-4657-a7d3-e0a049f7afea/nickname_dictionary.csv)) 파일 양식으로 작성된 단어 사전 파일을 저장합니다.
  | partOfSpeech | Word |
  | --- | --- |
  | adverb | 밝게 |
  | adjective | 평화로운 |
  | noun | 감자 |

🧩 <b>품사(partOfSpeech)</b>와 <b>단어(word)</b>로 구성된 양식에 따라 닉네임에 사용될 단어 리스트를 변경할 수 있습니다.

### 2.  익명 닉네임 설정 값 지정
- **RandomNickNameMaker.builder()**
  - .numberOfPhrase: 닉네임에 생성에 사용될 어구 개수 지정 최소 1개 ~ 3개 지정 가능.
    - `NumberOfPhrase.PHRASE_1`
    - `NumberOfPhrase.PHRASE_2` (default)
    - `NumberOfPhrase.PHRASE_3`

  - .maxLength: 최대 글자 수 지정 (단, 공백 문자를 제외한 최대 글자 수를 뜻하며, 지정한 글자수 이하의 닉네임 생성 불가시 예외 발생)
    - default: `-1` (제한 없음)
  - .isIncludedBlank: 공백 문자 포함 여부
    - default: `true` (공백 값 포함)
- **.callNickNameMaker();**
  
###

## 🧪 샘플 코드
```java
    /* 디폴트 설정 값으로 익명 닉네임 생성 */

    String defaultNickName = RandomNickNameMaker.builder()
            .callNickNameMaker();

    System.out.println(defaultNickName);
        
````
````java
        /* 설정 값 지정하여 익명 닉네임 생성 */
        String nickName = RandomNickNameMaker.builder()
                .numberOfPhrase(NumberOfPhrase.PHRASE_3)
                .maxLength(10)
                .isIncludedBlank(true)
                .callNickNameMaker();

        System.out.println(nickName);
````