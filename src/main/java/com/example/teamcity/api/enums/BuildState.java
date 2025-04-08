package com.example.teamcity.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BuildState {
    QUEUED("queued"),
    RUNNING("running"),
    FINISHED("finished");

    private final String state;

}
