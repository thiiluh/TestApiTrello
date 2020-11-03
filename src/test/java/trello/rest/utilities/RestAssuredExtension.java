package trello.rest.utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import trello.rest.core.Environment;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestAssuredExtension implements Environment {

    public static RequestSpecification Request;

    public RestAssuredExtension() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URL);
        builder.setContentType(CONTENT_TYPE);
        var requestSpec = builder.build();
        Request = RestAssured.given().spec(requestSpec);
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.responseSpecification = resBuilder.build();
    }

    public static ResponseOptions<Response> GetWithQueryParameter(String url, Map<String, String> queryParams) {

        Request.queryParams(queryParams);
        try {
            return Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> PostWithBodyAndQueryParams(String url, Map<String, String> queryParams, Map<String, String> body) {
        Request.queryParams(queryParams);
        Request.body(body);
        return Request.post(url);
    }

    public static ResponseOptions<Response> PostWithQueryParams(String url, Map<String, String> queryParams) {
        Request.queryParams(queryParams);
        return Request.post(url);
    }

    public static ResponseOptions<Response> DeleteWithQueryParams(String url, Map<String, String> queryParams) {
        Request.queryParams(queryParams);
        return Request.delete(url);
    }

    public static ResponseOptions<Response> PutWithBodyAndPathParams(String url, Map<String, String> queryParams, Map<String, String> body) {
        Request.queryParams(queryParams);
        Request.body(body);
        return Request.put(url);
    }

    public static ResponseOptions<Response> PutWithBodyAndPathParamsInteger(String url, Map<String, String> queryParams, Map<String, Integer> body) {
        Request.queryParams(queryParams);
        Request.body(body);
        return Request.put(url);
    }

    public static ResponseOptions<Response> PutWithQueryParams(String url, Map<String, String> queryParams) {
        Request.queryParams(queryParams);
        return Request.put(url);
    }
}