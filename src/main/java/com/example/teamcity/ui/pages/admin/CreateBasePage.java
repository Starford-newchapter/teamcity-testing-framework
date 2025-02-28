package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement urlInput = $("#url");
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement proceedButton = $(Selectors.byAttribute("value", "Proceed"));

    protected void createBaseForm(String url) {
        urlInput.val(url);
        proceedButton.click();
    }
}
