package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.agent.Agents;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class SetupAgentTest extends BaseApiTest {

    @Test(groups = {"Setup"})
    public void setupAgentTest() {

        var agents = superUserCheckedRequest.<Agents>getRequest(Endpoint.AGENTS).read("?locator=authorized:any");
        superUserCheckedRequest = new CheckedRequests(Specifications.superUserAuthSpec(ContentType.TEXT));


        if (!agents.getAgent().isEmpty()) {
            superUserCheckedRequest.getRequest(Endpoint.AGENTS).update("/id:" + agents.getAgent().get(0).getId() + "/authorized",
                    "true");
        } else {
            System.out.println("Teamcity Agent not authorized");
        }
    }
}
