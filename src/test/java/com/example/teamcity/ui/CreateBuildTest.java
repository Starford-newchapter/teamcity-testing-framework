package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.ui.enums.ErrorMessage;
import com.example.teamcity.ui.enums.RunnerType;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import com.example.teamcity.ui.pages.build.BuildConfigurationPage;
import com.example.teamcity.ui.pages.build.BuildStepsPage;
import com.example.teamcity.ui.pages.build.CommandLineBuildStepConfigurationPage;
import com.example.teamcity.ui.pages.build.CreateBuildStepPage;
import org.testng.annotations.Test;

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
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        loginAs(testData.getUser());

        //Взаимодействие с UI
        CreateBuildPage createBuildPage = CreateBuildPage.open(testData.getProject().getId());
        createBuildPage
                .createForm(GIT_URL)
                .setUpBuildType("");

        //Проверяем сообщение об ошибке
        createBuildPage
                .buildNameErrorMessage
                .shouldHave(Condition.exactText(ErrorMessage.EMPTY_BUILD_NAME.getGetMessage()));

    }


    @Test(description = "User should be able to create build step", groups = {"Regression"})
    public void userCreatesBuildStep() {
        CommandLineBuildStepConfigurationPage commandLineBuildStepConfigurationPage = new CommandLineBuildStepConfigurationPage();
        BuildStepsPage buildStepsPage = new BuildStepsPage();

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

        //корректность отправки данных с UI на API
        var createdBuild = superUserCheckedRequest.<BuildType>getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softAssert.assertTrue(createdBuild.getSteps().getCount() > 0, "Build step is not created");

        //проверка состояния UI
        //корректность считывания данных и отображение данных на UI)
        var foundBuildSteps = buildStepsPage
                .getBuildStepElements()
                .stream()
                .anyMatch(buildStep -> buildStep.getStepName().text().equals(RunnerType.COMMAND_LINE.getType()));

        softAssert.assertTrue(foundBuildSteps);
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
