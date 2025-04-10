package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.build.BuildType;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.api.models.build.Property;
import com.example.teamcity.api.models.build.Steps;
import com.example.teamcity.ui.enums.ErrorMessage;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import com.example.teamcity.ui.pages.build.BuildConfigurationPage;
import com.example.teamcity.ui.pages.build.BuildPage;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

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
        var createdBuild = superUserCheckedRequest.<BuildType>getRequest(BUILD_TYPES).read("/name:" + testData.getBuildType().getName());
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

        softAssert.assertEquals(createBuildPage
                .buildNameErrorMessage.text(), ErrorMessage.EMPTY_BUILD_NAME.getGetMessage());
    }


    @Test(description = "User should be able to run build with step", groups = {"Regression"})
    public void userRunBuildWithStep() {
        final String commandLineText = "'Hello World!'";

        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        var buildType = testData.getBuildType();
        buildType.setSteps(generate(Steps.class, List.of(
                generate(Property.class, "script.content", "echo " + commandLineText),
                generate(Property.class, "use.custom.script", "true"))));
        superUserCheckedRequest.getRequest(BUILD_TYPES).create(buildType);

        //Взаимодействие с UI
        loginAs(testData.getUser());

        var buildLogs = BuildPage.open(buildType.getId())
                .clickRunBuild()
                .clickBuildElement(1)
                .clickBuildLog()
                .searchLog(commandLineText);

        step("Проверка наличия лога в билде", () -> {
            buildLogs.filter(Condition.text(commandLineText))
                    .forEach(element -> {
                        softAssert.assertTrue(element.getText().contains(commandLineText));
                    });
        });
    }

}