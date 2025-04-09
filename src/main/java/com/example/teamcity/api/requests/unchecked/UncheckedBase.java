package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.SearchInterface;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedBase extends Request implements CrudInterface, SearchInterface {

    public UncheckedBase(RequestSpecification specification, Endpoint endpoint) {
        super(specification, endpoint);
    }


    @Override
    @Step("Отправка запроса на создание сущности")
    public Response create(BaseModel model) {
        return RestAssured
                .given()
                .spec(specification)
                .body(model)
                .post(endpoint.getUrl());
    }

    @Override
    @Step("Отправка запроса на получение данных о сущности")
    public Response read(String locator) {
        return RestAssured
                .given()
                .spec(specification)
                .get(endpoint.getUrl() + "/" + locator);
    }

    @Override
    @Step("Отправка запроса на обновление данных сущности")
    public Response update(String locator, BaseModel model) {
        return RestAssured
                .given()
                .spec(specification)
                .body(model)
                .put(endpoint.getUrl() + "/" + locator);
    }

    @Override
    @Step("Отправка запроса на удаление  сущности")
    public Response delete(String locator) {
        return RestAssured
                .given()
                .spec(specification)
                .delete(endpoint.getUrl() + "/" + locator);
    }

    @Override
    public Response search(String locator) {
        return RestAssured
                .given()
                .spec(specification)
                .get(endpoint.getUrl() + endpoint.getSearchParam() + locator);
    }
}
