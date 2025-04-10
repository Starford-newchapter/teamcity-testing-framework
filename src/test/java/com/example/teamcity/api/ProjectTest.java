package com.example.teamcity.api;

import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User should be able to search project by name", groups = {"Positive", "Search"})
    public void userCreatesBuildTypeTest() {
        superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON));

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var foundedProject = userCheckRequests.<Project>getRequest(PROJECTS).search("name:" + testData.getProject().getName());

        softAssert.assertEquals(foundedProject.extract().path("project[0].name"),
                createdProject.getName(),
                "Project was not found");
    }
}
