package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildRunElements extends  BasePageElement{
    private  SelenideElement buildStatus;


    public BuildRunElements(SelenideElement element) {
        //this.buildStatus=
        super(element);
    }



}
