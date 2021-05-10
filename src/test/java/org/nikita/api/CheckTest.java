package org.nikita.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nikita.api.model.ResponseIpAddress;
import org.nikita.api.step.CheckEndpoint;

@RunWith(SerenityRunner.class)
public class CheckTest {
    private final static String BASE_PATH = "check";
    private final static String GOOGLE_IP = "216.58.215.78";
    private final static String PRIVATE_IP = "192.168.0.1";
    private final static String INVALID_IP = "216.0.0.300";
    private final static String INVALID_IP_ERROR_MESSAGE = "The ip address must be a valid IPv4 or IPv6 address (e.g. 8.8.8.8 or 2001:4860:4860::8888).";

    @Steps
    private CheckEndpoint checkEndpoint;

    /**
     * AIPDB-1.1
     */
    @Test
    public void testCheckIpAddressDataSuccessfullyWithPublicIp() {
        checkEndpoint.setRequestSpecification(given());

        ResponseIpAddress responseIpAddress = checkEndpoint.get(GOOGLE_IP, 200);

        checkEndpoint.responseShouldHaveIp(responseIpAddress, GOOGLE_IP);
        checkEndpoint.responseIpShouldBePublic(responseIpAddress);
    }

    /**
     * AIPDB-1.2
     */
    @Test
    public void testCheckIpAddressDataSuccessfullyWithPrivateIp() {
        checkEndpoint.setRequestSpecification(given());

        ResponseIpAddress responseIpAddress = checkEndpoint.get(PRIVATE_IP, 200);

        checkEndpoint.responseShouldHaveIp(responseIpAddress, PRIVATE_IP);
        checkEndpoint.responseIpShouldNotBePublic(responseIpAddress);
    }

    /**
     * AIPDB-1.3
     */
    @Test
    public void testCheckIpAddressDataUnsuccessfullyWithInvalidIp() {
        checkEndpoint.setRequestSpecification(given());

        ResponseIpAddress responseIpAddress = checkEndpoint.get(INVALID_IP, 422);

        checkEndpoint.responseShouldHaveErrors(responseIpAddress);
        checkEndpoint.responseErrorShouldHaveMessage(responseIpAddress, INVALID_IP_ERROR_MESSAGE);
    }

    private RequestSpecification given() {
        return RestAssured
            .given()
            .spec(BaseRequestSpecification.given())
            .basePath(BASE_PATH);
    }
}
