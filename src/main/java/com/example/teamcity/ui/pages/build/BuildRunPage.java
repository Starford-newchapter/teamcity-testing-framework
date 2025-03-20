package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.enums.RunnerType;
import com.example.teamcity.ui.pages.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BuildRunPage extends BasePage {

    private final String XPATH_LOG_MESSAGE = "(//*[contains(text(),'%s')])[1]";


    public final SelenideElement buildLogTab = $("span[data-tab-title='Build Log']");


    public BuildRunPage openLogMessage(RunnerType runnerType) {
        $(By.xpath(String.format(XPATH_LOG_MESSAGE, runnerType.getType()))).click();
        return this;
    }

    public BuildRunPage checkBuildLog(String message) {

        $(By.xpath(String.format(XPATH_LOG_MESSAGE, message))).
                should(Condition.appear, BASE_WAITING).
                shouldHave(Condition.exactText(message));
        return this;
    }

}
