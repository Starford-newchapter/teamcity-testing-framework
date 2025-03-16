package com.example.teamcity.ui.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    EMPTY_BUILD_NAME("Build configuration name must not be empty"),
    EMPTY_REPOSITORY_URL("URL must not be empty");

    private final String getMessage;

}
