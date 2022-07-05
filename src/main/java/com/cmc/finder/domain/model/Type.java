package com.cmc.finder.domain.model;

import lombok.Getter;

@Getter
public enum Type {

    QUESTION("question"), DEBATE("debate"), COMMUNITY("community");

    private String value;

    Type(String value) {
        this.value = value;
    }
}
