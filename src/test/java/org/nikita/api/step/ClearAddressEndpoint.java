package org.nikita.api.step;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.nikita.api.model.ResponseClearAddress;
import org.nikita.api.model.ResponseIpAddress;

public class ClearAddressEndpoint {
    private RequestSpecification requestSpecification;

    public ClearAddressEndpoint() {
        requestSpecification = RestAssured.given();
    }

    @Step("Deletes ip address data with response status {1}")
    public ResponseClearAddress delete(String ip, int expectedStatus) {
        return RestAssured
            .given()
            .param("ipAddress", ip)
            .delete()
            .then()
            .statusCode(expectedStatus)
            .extract()
            .as(ResponseClearAddress.class);
    }

    @Step("Response should not have deleted addresses")
    public void responseShouldNotHaveDeletedAddresses(ResponseClearAddress responseClearAddress) {
        Assert.assertEquals(0, responseClearAddress.getData().getNumReportsDeleted());
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }
}
