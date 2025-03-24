package com.example.teamcity.ui.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RunnerType {
    COMMAND_LINE("Command Line"),
    GRADLE("Gradle"),
    MAVEN("Maven");

    private final String type;
}
