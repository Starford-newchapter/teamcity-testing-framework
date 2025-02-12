package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {

    private final RequestSpecification specification;
    private final Endpoint endpoint;

    public Request(RequestSpecification specification, Endpoint endpoint) {
        this.specification = specification;
        this.endpoint = endpoint;
    }
}
