package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class ProjectAndBuildElement extends BasePageElement {

    private SelenideElement name;

    public ProjectAndBuildElement(SelenideElement element) {
        super(element);
        this.name = $("span[class*='ProjectsTreeItem__name']");

    }
}
