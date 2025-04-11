package com.example.teamcity.api;


import com.example.teamcity.api.enums.BuildState;
import com.example.teamcity.api.enums.BuildStatus;
import com.example.teamcity.api.models.build.Build;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.common.WireMock;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_QUEUE;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

@Feature("Start build")
public class StartBuildTest extends BaseApiTest {

    @BeforeMethod(alwaysRun = true)
    public void setupWireMockServer() {
        var fakeBuild = Build.builder()
                .state("finished")
                .status("SUCCESS")
                .build();

        WireMock.setupServer(post(BUILD_QUEUE.getUrl()), HttpStatus.SC_OK, fakeBuild);
    }



    @Test(description = "User should be able to start build (with WireMock)", groups = {"Regression"}, alwaysRun = true)
    public void userStartsBuildWithWireMockTest() {
        var checkedBuildQueueRequest = new CheckedBase<>(Specifications.getSpec()
                .mockSpec(ContentType.JSON), BUILD_QUEUE);

        var build = (Build) checkedBuildQueueRequest.create(Build.builder()
                .buildType(testData.getBuildType())
                .build());

        softAssert.assertEquals(build.getState(), BuildState.FINISHED.getState());
        softAssert.assertEquals(build.getStatus(), BuildStatus.SUCCESS.name());
    }

    @AfterMethod(alwaysRun = true)
    public void stopWireMockServer() {
        WireMock.stopServer();
    }

}

