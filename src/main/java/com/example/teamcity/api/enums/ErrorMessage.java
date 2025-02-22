package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    EMPTY_PROJECT_NAME("Project name cannot be empty"),
    EMPTY_PROJECT_ID("No project specified. Either 'id', 'internalId' or 'locator' attribute should be present"),
    PROJECT_NAME_EXISTS("Project with this name already exists"),
    PROJECT_ID_EXISTS("is already used by another project");

    private final String message;
}
