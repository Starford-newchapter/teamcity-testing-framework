package com.example.teamcity.api.models.build;

import com.example.teamcity.api.annotations.Optional;
import com.example.teamcity.api.annotations.Parameterizable;
import com.example.teamcity.api.annotations.Random;
import com.example.teamcity.api.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel {
    @Random
    @Parameterizable
    private String id;
    @Random
    private String name;
    private Project project;
    @Optional
    private Steps steps;
}