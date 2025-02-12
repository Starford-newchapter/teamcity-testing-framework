package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Specifications {

    private static Specifications specification;

    private Specifications() {
    }

    public static Specifications getSpecification() {
        if (specification == null) {
            specification = new Specifications();
        }
        return specification;
    }

    private RequestSpecBuilder requestSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        return requestSpecBuilder;
    }

    public RequestSpecification unauthorizedSpec() {
        return requestSpecBuilder().build();

    }

    public RequestSpecification authorizedSpec(User user) {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(user.getUser());
        basicAuthScheme.setPassword(user.getPassword());

        return requestSpecBuilder()
                .setAuth(basicAuthScheme)
                .build();
    }

}
