package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class, "?locator="),
    USERS("/app/rest/users", User.class, "?locator="),
    PROJECTS("/app/rest/projects", Project.class, "?locator=");

    private final String url;
    private final Class<? extends BaseModel> modelClass;
    private final String searchParam;
}
