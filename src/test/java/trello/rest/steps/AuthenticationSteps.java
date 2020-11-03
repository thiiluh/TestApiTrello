package trello.rest.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import trello.rest.utilities.AuthenticationUtilities;
import trello.rest.utilities.RestAssuredExtension;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.HashMap;

public class AuthenticationSteps extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    @Given("I have a valid Key")
    public void iHaveAValidKey() {
    }

    @Then("I must receive a status code {int}")
    public void iMustReceiveAStatusCode(int status) {
        assertThat(response.getStatusCode(), equalTo(status));
    }

    @When("I make an http get call with valid Key to get an authentication page")
    public void iMakeAnHttpGetCallToGetAnAuthenticationPage() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("expiration", "never");
        queryParams.put("scope", "read,write,account");
        queryParams.put("name", "Server%20Token");
        response = AuthenticationUtilities.GetPageAuthentication(queryParams);
    }

    @Given("I have a invalid Key")
    public void iHaveAInvalidKey() {
    }

    @And("Must contain the message {string}")
    public void mustContentTheMessage(String message) {
        assertThat(response.getBody().asString(), equalTo(message));
    }

    @When("I make an http get call with invalid Key to get an authentication page")
    public void iMakeAnHttpGetCallWithInvalidKeyToGetAnAuthenticationPage() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", "123456789");
        queryParams.put("expiration", "never");
        queryParams.put("scope", "read,write,account");
        queryParams.put("name", "Server%20Token");
        response = AuthenticationUtilities.GetPageAuthentication(queryParams);
    }

    @Given("I have a valid token")
    public void iHaveAValidToken() {
    }

    @When("I make an http get call with valid token to validation token")
    public void iMakeAnHttpGetCallWithValidTokenToValidationToken() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        response = AuthenticationUtilities.GetTokenValidation(queryParams, TOKEN);
    }

    @And("Must contain Json Object")
    public void mustContainJsonObject() {
        assertThat(response.getBody().jsonPath().get("id"), notNullValue());
    }

    @Given("I have a invalid token")
    public void iHaveAInvalidToken() {
    }

    @When("I make an http get call with invalid token to validation token")
    public void iMakeAnHttpGetCallWithInvalidTokenToValidationToken() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        response = AuthenticationUtilities.GetTokenValidation(queryParams, "1234567890");
    }
}
