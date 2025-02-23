package com.example.teamcity.api.models.build;

import com.example.teamcity.api.annotations.Parameterizable;
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
public class Property extends BaseModel {
    @Parameterizable
    private String name="script.content";
    @Parameterizable
    private String value="echo Hello World!";
}
