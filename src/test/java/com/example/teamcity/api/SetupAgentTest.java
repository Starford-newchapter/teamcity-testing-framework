package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.agent.Agent;
import com.example.teamcity.api.models.agent.Agents;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.qameta.allure.Allure.step;

public class SetupAgentTest extends BaseApiTest {

    @Test(groups = {"Setup"})
    public void setupAgentTest() {
        var agentId = getUnauthorizedAgent().getId();

        step("Авторизация агента", () -> {
            superUserCheckedRequest = new CheckedRequests(Specifications.superUserAuthSpec(ContentType.TEXT));
            superUserCheckedRequest.getRequest(Endpoint.AGENTS)
                    .update("/id:" + agentId + "/authorized",
                            "true");
        });
    }



    @Step("Ожидаем появление неавторизованного агента")
    private Agent getUnauthorizedAgent() {
        var agents = new AtomicReference<List<Agent>>();
        Awaitility.await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    agents.set(superUserCheckedRequest.<Agents>getRequest(Endpoint.AGENTS)
                            .read("?locator=authorized:false")
                            .getAgent());
                    return !agents.get()
                            .isEmpty();
                });
        return agents.get()
                .get(0);
    }
}
