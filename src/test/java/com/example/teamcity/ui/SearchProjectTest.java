package com.example.teamcity.ui;

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
        var foundedProject = ProjectsPage.open()
                .searchProject(testData.getProject().getName())
                .getProjectsAndBuilds().stream()
                .filter(project -> project.getName().text().equals(testData.getProject().getName()))
                .findAny()
                .map(project -> project.getName().text());

        softAssert.assertEquals(foundedProject,testData.getProject().getName());

    }

}
