package com.example.teamcity.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {


    public static ResponseSpecification checkBuildTypeWithIdAlreadyExist(String buildTypeId) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder.expectBody(Matchers
                .containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(buildTypeId)));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification checkUserCannotCreateBuildWithOtherProject(String projectId) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_FORBIDDEN);
        responseSpecBuilder.expectBody(Matchers
                .containsString("You do not have enough permissions to edit project with id: %s".formatted(projectId)));
        return responseSpecBuilder.build();
    }
}