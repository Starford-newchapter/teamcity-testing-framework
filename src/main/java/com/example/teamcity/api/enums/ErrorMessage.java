package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    PROJECT_NAME_CANNOT_BE_EMPTY("Project name cannot be empty"),
    PROJECT_NAME_ALREADY_EXISTS("Project with this name already exists"),
    PROJECT_ID_ALREADY_USED("Project ID \"%s\" is already used by another project");

    private final String message;
}
