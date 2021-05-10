package org.nikita.api.step;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.nikita.api.model.ResponseIpAddress;

public class CheckEndpoint {
    private RequestSpecification requestSpecification;

    public CheckEndpoint() {
        requestSpecification = RestAssured.given();
    }

    @Step("Gets ip address data with response status {1}")
    public ResponseIpAddress get(String ip, int expectedStatus) {
        return RestAssured
            .given()
            .spec(requestSpecification.given())
            .param("ipAddress", ip)
            .get()
            .then()
            .statusCode(expectedStatus)
            .extract()
            .as(ResponseIpAddress.class);
    }

    @Step("Response should have ip {1}")
    public void responseShouldHaveIp(ResponseIpAddress responseIpAddress, String ip) {
        Assert.assertEquals(ip, responseIpAddress.getData().getIpAddress());
    }

    @Step("Response ip should be public")
    public void responseIpShouldBePublic(ResponseIpAddress responseIpAddress) {
        Assert.assertTrue(responseIpAddress.getData().isPublic());
    }

    @Step("Response ip should not be public")
    public void responseIpShouldNotBePublic(ResponseIpAddress responseIpAddress) {
        Assert.assertFalse(responseIpAddress.getData().isPublic());
    }

    @Step("Response has errors")
    public void responseShouldHaveErrors(ResponseIpAddress responseIpAddress) {
        Assert.assertTrue(responseIpAddress.getErrors().length > 0);
    }

    @Step("Response error have message: \"{1}\"")
    public void responseErrorShouldHaveMessage(ResponseIpAddress responseIpAddress, String message) {
        Assert.assertEquals(message, responseIpAddress.getErrors()[0].getDetail());
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }
}
