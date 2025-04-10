package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.user.User;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

public class Specifications {

    private static Specifications specification;

    private Specifications() {
    }

    ;

    public static Specifications getSpec() {
        if (specification == null) {
            specification = new Specifications();
        }
        return specification;
    }


    private static RequestSpecBuilder requestSpecBuilder(ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setAccept(contentType);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        requestSpecBuilder.addFilter(new SwaggerCoverageRestAssured(
                new FileSystemOutputWriter(
                        Paths.get("target/" + OUTPUT_DIRECTORY)
                )
        ));
        requestSpecBuilder.addFilter(new AllureRestAssured());
        return requestSpecBuilder;
    }


    public static RequestSpecification superUserAuthSpec(ContentType contentType) {
        var requestBuilder = requestSpecBuilder(contentType);
        requestBuilder.setBaseUri("http://%s:%s@%s/httpAuth".formatted("", Config.getProperty("superUserToken"), Config.getProperty("host")));
        return requestBuilder.build();
    }

    public static RequestSpecification unauthorizedSpec(ContentType contentType) {
        return requestSpecBuilder(contentType).build();

    }

    public static RequestSpecification authorizedSpec(User user, ContentType contentType) {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(user.getUsername());
        basicAuthScheme.setPassword(user.getPassword());

        return requestSpecBuilder(contentType)
                .setAuth(basicAuthScheme)
                .build();
    }

    public RequestSpecification mockSpec(ContentType contentType) {
        return requestSpecBuilder(contentType)
                .setBaseUri("http://localhost:8086")
                .build();
    }

}
