package trello.rest.utilities;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import java.util.HashMap;

public class BoardUtilities extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    public static ResponseOptions<Response> CreateBoard(String nameBoard) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("name", nameBoard);
        response = restAssuredExtension.PostWithQueryParams("boards", queryParams);

        return response;
    }

    public static ResponseOptions<Response> DeleteBoard(String boardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = restAssuredExtension.DeleteWithQueryParams("boards/" + boardId, queryParams);

        return response;
    }

    public static ResponseOptions<Response> GetBoard(String boardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = restAssuredExtension.GetWithQueryParameter("boards/" + boardId, queryParams);

        return response;
    }
}
