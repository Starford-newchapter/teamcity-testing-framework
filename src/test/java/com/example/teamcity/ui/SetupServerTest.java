package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.setUp.FirstStartPage;
import org.testng.annotations.Test;

public class SetupServerTest extends BaseUiTest {

    @Test(groups = {"Setup"})
    public void setupTeamCityServerTest() {
        FirstStartPage.open().setupFirstStart();
    }
}
