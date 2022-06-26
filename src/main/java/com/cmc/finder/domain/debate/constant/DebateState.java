package com.cmc.finder.domain.debate.constant;

public enum DebateState {
    PROCEEDING, COMPLETE;

    public static DebateState from(String state) {

        return DebateState.valueOf(state.toUpperCase());
    }

}
