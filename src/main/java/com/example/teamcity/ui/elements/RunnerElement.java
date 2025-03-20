package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;


@Getter
public class RunnerElement extends BasePageElement {

    private SelenideElement role;
    private SelenideElement dataTest;

    public RunnerElement(SelenideElement element) {
        super(element);
        this.role = find("tr[role='presentation']");
        this.dataTest = find("tr[data-test*='runner-item']");
    }
}
