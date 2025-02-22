package com.example.teamcity.api;


import com.example.teamcity.api.enums.ErrorMessage;
import com.example.teamcity.api.models.build.Project;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class BaseApiTest extends BaseTest {

    protected void validateCreateProjectResponse(Project createProjectRequest, Project createProjectResponse) {
        softAssert.assertEquals(createProjectRequest.getId(), createProjectResponse.getId(), "Incorrect  Project Id");
        softAssert.assertEquals(createProjectRequest.getName(), createProjectResponse.getName(), "Incorrect Project name");
    }

    protected void validateErrorResponse(Response response, ErrorMessage errorMessage, int httpStatus) {
        softAssert.assertEquals(response.getStatusCode(), httpStatus);
        softAssert.assertTrue(response.asString().contains(errorMessage.getMessage()));
    }


}
