package uk.gov.hmcts.reform.adoption.smoke;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class SmokeFT {

    @Test
    public void testHealthEndpoint() {
        String testUrl = "http://ccd-data-store-api-aat.service.core-compute-aat.internal";
        Assert.assertTrue(true);
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured
            .given()
            .baseUri(testUrl)
            .get("/")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
    }

    @Test
    public void dummyFT() {
        String testUrl = "http://ccd-data-store-api-aat.service.core-compute-aat.internal";
        SerenityRest.given()
            .get(testUrl);
        Assert.assertTrue(true);
    }
}
