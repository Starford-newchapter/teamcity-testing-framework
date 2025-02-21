package com.example.teamcity.api.models;

import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.user.User;
import lombok.Data;

@Data
public class TestData {
    private Project project;
    private User user;
    private BuildType buildType;
}
