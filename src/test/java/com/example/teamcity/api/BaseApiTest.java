package com.example.teamcity.api;


import com.example.teamcity.api.models.build.Project;
import io.restassured.response.Response;

public class BaseApiTest extends BaseTest {

    protected void validateCreateProjectResponse(Project createProjectRequest, Project createProjectResponse) {
        if (createProjectRequest.getId() == null) {
            softAssert.assertNotNull(createProjectResponse.getId());
        } else {
            softAssert.assertEquals(createProjectRequest.getId(), createProjectResponse.getId());
        }
        softAssert.assertEquals(createProjectRequest.getName(), createProjectResponse.getName());
    }

    protected void validateErrorResponse(Response response, String errorMessage, int httpStatus) {
        softAssert.assertEquals(response.getStatusCode(), httpStatus);
        softAssert.assertTrue(response.asString().contains(errorMessage));
    }


}
