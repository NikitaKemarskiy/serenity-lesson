package org.nikita.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nikita.api.model.ResponseClearAddress;
import org.nikita.api.step.ClearAddressEndpoint;

@RunWith(SerenityRunner.class)
public class ClearAddressTest {
    private final static String BASE_PATH = "clear-address";
    private final static String GOOGLE_IP = "216.58.215.78";

    @Steps
    private ClearAddressEndpoint clearAddressEndpoint;

    /**
     * AIPDB-1.4
     */
    @Test
    public void testClearIpAddressRecordSuccessfullyWithPublicIp() {
        clearAddressEndpoint.setRequestSpecification(given());

        ResponseClearAddress responseClearAddress = clearAddressEndpoint.delete(GOOGLE_IP, 200);

        clearAddressEndpoint.responseShouldNotHaveDeletedAddresses(responseClearAddress);
    }

    private RequestSpecification given() {
        return RestAssured
            .given()
            .spec(BaseRequestSpecification.given())
            .basePath(BASE_PATH);
    }
}
