package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildStepElement extends BasePageElement {
    private SelenideElement stepName;

    public BuildStepElement(SelenideElement element) {
        super(element);
        this.stepName = find("td[class*='highlight stepName']");
    }
}
