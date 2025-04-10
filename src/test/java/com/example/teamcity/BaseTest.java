package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssert softAssert;
    protected CheckedRequests superUserCheckedRequest = new CheckedRequests(Specifications.superUserAuthSpec(ContentType.JSON));
    protected UncheckedRequests superUserUncheckedRequest = new UncheckedRequests(Specifications.superUserAuthSpec(ContentType.JSON));
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softAssert = new SoftAssert();
        testData = generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softAssert.assertAll();
        TestDataStorage.getTestDataStorage().deleteCreatedEntities();

    }
}
