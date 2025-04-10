package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.agent.Agents;
import com.example.teamcity.api.models.build.Build;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class, "?locator="),
    BUILDS("/app/rest/builds", Build.class, null),
    BUILD_QUEUE("/app/rest/buildQueue", Build.class, null),
    USERS("/app/rest/users", User.class, "?locator="),
    PROJECTS("/app/rest/projects", Project.class, "?locator="),
    AGENTS("/app/rest/agents", Agents.class, "?locator=");

    private final String url;
    private final Class<? extends BaseModel> modelClass;
    private final String searchParam;
}
