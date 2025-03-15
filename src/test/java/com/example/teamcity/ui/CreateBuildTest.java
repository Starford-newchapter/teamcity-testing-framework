package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import org.testng.annotations.Test;

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

        //проверка состояния UI
        //корректность считывания данных и отображение данных на UI)
        BuildConfigurationPage.open(createdBuild.getId())
                .buildNameTitle.shouldHave(Condition.exactText(testData.getBuildType().getName()));

    }
}
