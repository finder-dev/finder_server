package com.cmc.finder.domain.model;

import lombok.Getter;

@Getter
public enum ServiceType {

    QUESTION("question"), DEBATE("debate"), COMMUNITY("community"),
    QUESTION_ANSWER("question answer"), DEBATE_ANSWER("debate answer"), COMMUNITY_ANSWER("community answer"),
    MESSAGE("message");

    private String value;

    ServiceType(String value) {
        this.value = value;
    }

}
