package com.example.teamcity.ui;

import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.ui.enums.RunnerType;
import com.example.teamcity.ui.pages.build.CommandLineBuildStepConfigurationPage;
import com.example.teamcity.ui.pages.build.CreateBuildStepPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

public class CreateBuildStepsTest extends BaseUiTest {


    @Test(description = "User should be able to create build step", groups = {"Regression"})
    public void userCreatesBuildStep() {

        CommandLineBuildStepConfigurationPage commandLineBuildStepConfigurationPage = new CommandLineBuildStepConfigurationPage();

        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        superUserCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());
        loginAs(testData.getUser());

        //Взаимодействие с UI
        CreateBuildStepPage.open(testData.getBuildType().getId())
                .selectRunnerType(RunnerType.COMMAND_LINE);

        commandLineBuildStepConfigurationPage
                .inputScript("echo 'Hello World!'")
                .clickSaveButton();




        /*// проверка состояния API
        //корректность отправки данных с UI на API
        var createdBuild = superUserCheckedRequest.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softAssert.assertNotNull(createdBuild);

        //проверка состояния UI
        //корректность считывания данных и отображение данных на UI)
        BuildConfigurationPage.open(createdBuild.getId())
                .buildNameTitle.shouldHave(Condition.exactText(testData.getBuildType().getName()));*/

    }
}
