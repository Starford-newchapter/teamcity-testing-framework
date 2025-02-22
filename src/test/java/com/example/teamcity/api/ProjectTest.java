package com.example.teamcity.api;

import com.example.teamcity.api.enums.ErrorMessage;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User should be able to create project",
            dataProvider = "copyAllAssociatedSettings", groups = {"Positive", "CRUD"})
    public void successCreateProjectTest(boolean copyAllAssociatedSettings) {
        superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        var createProjectRequest = testData.getProject();
        createProjectRequest.setCopyAllAssociatedSetting(copyAllAssociatedSettings);

        var createProjectResponse = userCheckRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);
        validateCreateProjectResponse(createProjectRequest, createProjectResponse);
    }

    @Test(description = "User cannot to create project without name", groups = {"Negative", "CRUD"})
    public void createProjectWithoutNameTest() {
        superUserUnCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userUncheckedRequests = new UncheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        var createProjectRequest = testData.getProject();
        createProjectRequest.setName(null);

        var createProjectResponse = userUncheckedRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);
        validateErrorResponse(createProjectResponse, ErrorMessage.EMPTY_PROJECT_NAME, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(description = "User should be able to create project without Id", groups = {"Positive", "CRUD"})
    public void createProjectWithoutIdTest() {
        superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        var createProjectRequest = testData.getProject();
        createProjectRequest.setId(null);

        var createProjectResponse = userCheckRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);
        validateCreateProjectResponse(createProjectRequest,createProjectResponse);
    }

    @Test(description = "User cannot to create project with exists name", groups = {"Negative", "CRUD"})
    public void createProjectWithExistsNameTest() {
        superUserUnCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userUncheckedRequests = new UncheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        var createProjectRequest = testData.getProject();

        userUncheckedRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);

        var createProjectWithExistsNameResponse = userUncheckedRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);
        validateErrorResponse(createProjectWithExistsNameResponse, ErrorMessage.PROJECT_NAME_EXISTS, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(description = "User cannot to create project with exists id", groups = {"Negative", "CRUD"})
    public void createProjectWithExistsIdTest() {
        var projectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getBuildType().getId());


        superUserUnCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userUncheckedRequests = new UncheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        var createProjectRequest = testData.getProject();

        userUncheckedRequests.<Project>getRequest(PROJECTS).create(createProjectRequest);

        var createProjectWithExistsIdRequest = generate(Project.class);
        createProjectWithExistsIdRequest.setId(createProjectRequest.getId());

        var createProjectWithExistsIdResponse = userUncheckedRequests.<Project>getRequest(PROJECTS).create(createProjectWithExistsIdRequest);
        validateErrorResponse(createProjectWithExistsIdResponse, ErrorMessage.PROJECT_ID_EXISTS, HttpStatus.SC_BAD_REQUEST);
    }

    @DataProvider(name = "copyAllAssociatedSettings")
    public Object[] getCopyAllAssociatedSettingsValues() {
        return new Object[]{
                true,
                false
        };
    }
}
