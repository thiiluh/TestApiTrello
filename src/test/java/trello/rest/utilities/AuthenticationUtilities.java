package trello.rest.utilities;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import java.util.Map;

public class AuthenticationUtilities extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    public static ResponseOptions<Response> GetPageAuthentication(Map<String, String> queryParams) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.GetWithQueryParameter("authorize", queryParams);

        return response;
    }

    public static ResponseOptions<Response> GetTokenValidation(Map<String, String> queryParams, String token) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.GetWithQueryParameter("tokens/" + token, queryParams);

        return response;
    }
}
