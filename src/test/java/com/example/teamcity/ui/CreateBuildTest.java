package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.build.Property;
import com.example.teamcity.ui.enums.ErrorMessage;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import com.example.teamcity.ui.pages.build.BuildConfigurationPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

public class CreateBuildTest extends BaseUiTest {

    @Test(description = "User should be able to create build", groups = {"Regression"})
    public void userCreatesBuild() {

        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        loginAs(testData.getUser());

        //Взаимодействие с UI
        CreateBuildPage.open(testData.getProject().getId())
                .createForm(GIT_URL)
                .setUpBuildType(testData.getBuildType().getName());


        // проверка состояния API
        //корректность отправки данных с UI на API
        var createdBuild = superUserCheckedRequest.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softAssert.assertNotNull(createdBuild);

        //проверка состояния UIc
        //корректность считывания данных и отображение данных на UI)
        BuildConfigurationPage.open(createdBuild.getId())
                .buildNameTitle.shouldHave(Condition.exactText(testData.getBuildType().getName()));

    }

    @Test(description = "User cannot create a build without name", groups = {"Regression"})
    public void userCreatesBuildWithoutName() {
        //подготовка окружения
        loginAs(testData.getUser());
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        //Взаимодействие с UI
        CreateBuildPage createBuildPage = CreateBuildPage.open(testData.getProject().getId());
        createBuildPage
                .createForm(GIT_URL)
                .setUpBuildType("");

        softAssert.assertEquals(createBuildPage.buildNameErrorMessage.text()
                , ErrorMessage.EMPTY_BUILD_NAME.getGetMessage()
        );


    }

    //TODO
    @Test(description = "User should be able to create build step", groups = {"Regression"})
    public void userCreatesBuildStep() {

        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        testData.getBuildType().getSteps().getStep().get(0).setProperties((new ArrayList<>(Arrays.asList(
                new Property("script.content", "echo 'Hello World!'"),
                new Property("teamcity.step.mode", "default"),
                new Property("use.custom.script", "true")))));

        superUserCheckedRequest.getRequest(BUILD_TYPES).create(testData.getBuildType());

        //loginAs(testData.getUser());

        //корректность отправки данных с UI на API
        var createdBuild = superUserCheckedRequest.<BuildType>getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softAssert.assertTrue(createdBuild.getSteps().getCount() > 0, "Build step is not created");

    }

   /* @Test(description = "User should be able to run build with step", groups = {"Regression"},enabled = false)
    public void userRunBuildWithStep() {
        CommandLineBuildStepConfigurationPage commandLineBuildStepConfigurationPage = new CommandLineBuildStepConfigurationPage();
        BuildStepsPage buildStepsPage = new BuildStepsPage();
        BuildRunPage buildRunPage = new BuildRunPage();


        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        superUserCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());
        loginAs(testData.getUser());

        //Взаимодействие с UI
        CreateBuildStepPage.open(testData.getBuildType().getId())
                .selectRunnerType(RunnerType.COMMAND_LINE);

        commandLineBuildStepConfigurationPage
                .sendScript("echo 'Hello World!'")
                .clickSaveButton();
        buildStepsPage.runBuild();

        BuildPage.open(testData.getBuildType().getId())
                .buildStatus.click();

        buildRunPage
                .buildLogTab
                .click();

        buildRunPage
                .searchLog("Hello World!")
                .checkLogMessage("Hello World!");
    }*/
}
