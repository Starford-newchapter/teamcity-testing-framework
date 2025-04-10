package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.ui.pages.ProjectsPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

public class SearchProjectTest extends BaseUiTest {

    @Test(description = "User should be able search for project", groups = {"Regression"})
    public void userSearchProject() {
        //подготовка окружения
        superUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());
        loginAs(testData.getUser());

        //Взаимодействие с UI

        ProjectsPage.open()
                .selectProject(testData.getProject().getName())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));
    }

}
