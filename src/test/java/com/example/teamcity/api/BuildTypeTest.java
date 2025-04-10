package com.example.teamcity.api;

import com.example.teamcity.api.enums.BuildState;
import com.example.teamcity.api.enums.BuildStatus;
import com.example.teamcity.api.enums.Roles;
import com.example.teamcity.api.enums.Scope;
import com.example.teamcity.api.models.build.Build;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.build.Property;
import com.example.teamcity.api.models.build.Steps;
import com.example.teamcity.api.models.user.Role;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.teamcity.api.enums.Endpoint.BUILDS;
import static com.example.teamcity.api.enums.Endpoint.BUILD_QUEUE;
import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("/id:" + testData.getBuildType().getId());
        softAssert.assertEquals(createdBuildType.getName(), testData.getBuildType().getName(), "BuildType name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Collections.singletonList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckedRequest.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        new UncheckedBase(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().spec(ValidationResponseSpecifications
                        .checkBuildTypeWithIdAlreadyExist(testData.getBuildType().getId()));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {


        step("Create project", () -> {
            superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        });


        step("Create user with role PROJECT_ADMIN", () -> {
            testData.getUser().getRoles().setRole(Collections.singletonList(Role.builder()
                    .roleId(Roles.PROJECT_ADMIN.name())
                    .scope(Scope.P.getValue() + ":" + testData.getProject().getId())
                    .build()));
            superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        });

        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON));

        step("Create buildType for project by user  with role PROJECT_ADMIN", () -> {
            userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        });

        step("Check buildType was created successfully", () -> {
            var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("/id:" + testData.getBuildType().getId());
            softAssert.assertEquals(createdBuildType.getName(), testData.getBuildType().getName(), "BuildType name is not correct");
        });

    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        var testData2 = generate();

        step("Create user1 with role PROJECT_ADMIN in project1", () -> {
            superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
            testData.getUser().getRoles().setRole(Collections.singletonList(Role.builder()
                    .roleId(Roles.PROJECT_ADMIN.name())
                    .scope(Scope.P.getValue() + ":" + testData.getProject().getId())
                    .build()));
            superUserCheckedRequest.getRequest(USERS).create(testData.getUser());

        });


        step("Create user2 with role PROJECT_ADMIN in project2", () -> {
            superUserCheckedRequest.getRequest(PROJECTS).create(testData2.getProject());
            testData2.getUser().getRoles().setRole(Collections.singletonList(Role.builder()
                    .roleId(Roles.PROJECT_ADMIN.name())
                    .scope(Scope.P.getValue() + ":" + testData2.getProject().getId())
                    .build()));
            superUserCheckedRequest.getRequest(USERS).create(testData2.getUser());
        });
        new UncheckedRequests(Specifications.authorizedSpec(testData2.getUser(), ContentType.JSON));


        step("Create buildType for project1 by user2 and check that buildType was not created with forbidden code", () -> {
            new UncheckedRequests(Specifications.authorizedSpec(testData2.getUser(), ContentType.JSON))
                    .getRequest(BUILD_TYPES).create(testData.getBuildType())
                    .then().spec(ValidationResponseSpecifications
                            .checkUserCannotCreateBuildWithOtherProject(testData.getProject().getId()));
        });
    }

    @Test(description = "User should be able to run build  with step", groups = {"Positive", "CRUD"})
    public void userCreatesAndRunBuildTypeWithStepTest() {
        step("Create project", () -> {
            superUserCheckedRequest.getRequest(USERS).create(testData.getUser());
        });

        step("Create project", () -> {
            superUserCheckedRequest.getRequest(PROJECTS).create(testData.getProject());
        });

        var buildType = testData.getBuildType();
        buildType.setSteps(generate(Steps.class, List.of(
                generate(Property.class, "script.content", "echo 'Hello World!'"),
                generate(Property.class, "use.custom.script", "true"))));

        step("Create build with step CommandLine", () -> {
            superUserCheckedRequest.getRequest(BUILD_TYPES).create(buildType);
        });

        var userCheckRequests = new CheckedBase<>(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON), BUILD_QUEUE);


        var createdBuildRun = (Build) userCheckRequests.create(Build.builder()
                .buildType(buildType)
                .build());

        softAssert.assertEquals(createdBuildRun.getState(), BuildState.QUEUED.getState());

        var buildResult = waitUntilBuildIsFinished(createdBuildRun);
        softAssert.assertEquals(buildResult.getStatus(), BuildStatus.SUCCESS.name());
        softAssert.assertEquals(buildResult.getBuildType().getId(), buildType.getId());

    }

    @Step("Wait until build is finished")
    private Build waitUntilBuildIsFinished(Build build) {
        var atomicBuild = new AtomicReference<>(build);
        var checkedBuildRequest = new CheckedBase<>(Specifications.authorizedSpec(testData.getUser(), ContentType.JSON), BUILDS);
        Awaitility.await()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofSeconds(3))
                .until(() -> {
                    atomicBuild.set((Build) checkedBuildRequest.read("/id:" + atomicBuild.get().getId()));
                    return BuildState.FINISHED.getState().equals(atomicBuild.get().getState());
                });
        return atomicBuild.get();
    }
}