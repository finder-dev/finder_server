package com.cmc.finder.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_USER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "비밀번호를 잘못 입력하셨습니다."),
    DUPLICATE_EMAIL(400, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(400, "이미 사용 중인 닉네임입니다."),
    DUPLICATE_KEYWORD(400, "중복된 키워드가 포함되어 있습니다."),

    INVALID_FILTER_TYPE(401, "잘못된 필터 타입 입니다.(orderBy : VIEWCOUNT)"),
    INVALID_USER_TYPE(401, "잘못된 회원 타입 입니다.(userType : KAKAO)"),
    INVALID_MBTI_TYPE(401, "잘못된 MBTI 타입 입니다.(mbti : INFP)"),
    NOT_EXISTS_AUTHORIZATION(401, "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰 입니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료됐습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),

    // 회원
    USER_NOT_EXISTS(400, "해당 회원은 존재하지 않습니다."),

    // 토큰
    TOKEN_NOT_FOUND(400, "token을 불러오지 못했습니다."),
    DOES_NOT_GET_USER_INFO(400, "회원 정보를 불러오지 못했습니다."),

    // 상품 이미지
    ITEM_IMAGE_NOT_EXISTS(400, "해당 아이템의 이미지를 찾을 수 없습니다."),

    // 메일
    FAILED_TO_SEND_MAIL(401, "메일 전송에 실패했습니다."),

    // 인증 코드
    AUTH_CODE_NOT_FOUND(400, "해당 인증 코드를 찾을 수 없습니다."),
    AUTH_CODE_NOT_EQUAL(400, "인증 코드가 일치하지 않습니다. "),

    // 질문
    QUESTION_NOT_EXISTS(400, "해당 질문을 찾을 수 없습니다."),
    QUESTION_IMAGE_NOT_EXISTS(400, "해당 질문 이미지를 찾을 수 없습니다."),
    CURIOUS_NOT_EXISTS(401,"해당 궁금해요를 찾을 수 없습니다." ),
    QUESTION_IMAGE_MAX(400, "최대 10개까지 사진을 추가하실 수 있습니다."),
    QUESETION_FAVORITE_NOT_EXISTS(401, "해당 질문에 대한 즐겨찾기를 찾을 수 없습니다."),
    QUESTION_USER_BE_NOT_WRITER(401, "해당 질문에 대한 작성자가 아닙니다."),

    // 답변
    ANSWER_IMAGE_MAX(400, "최대 10개까지 사진을 추가하실 수 있습니다."),
    ANSWER_NOT_EXISTS(400, "해당 답변을 찾을 수 없습니다."),
    HELPFUL_NOT_EXISTS(401, "해당 도움이 됐어요를 찾을 수 없습니다.");


    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}

