package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.user.User;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Specifications {

    private static Specifications specification;


    private static RequestSpecBuilder requestSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        return requestSpecBuilder;
    }


    public static RequestSpecification adminAuthSpec() {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(Config.getProperty("adminUserName"));
        basicAuthScheme.setPassword(Config.getProperty("adminPassword"));
        return requestSpecBuilder()
                .setAuth(basicAuthScheme)
                .build();

    }

    public static RequestSpecification unauthorizedSpec() {
        return requestSpecBuilder().build();

    }

    public static RequestSpecification authorizedSpec(User user) {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(user.getUsername());
        basicAuthScheme.setPassword(user.getPassword());

        return requestSpecBuilder()
                .setAuth(basicAuthScheme)
                .build();
    }

}
