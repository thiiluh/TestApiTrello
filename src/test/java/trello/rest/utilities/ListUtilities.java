package trello.rest.utilities;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ListUtilities extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    public static ResponseOptions<Response> CreateListBoard(String boardId, String listName) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("name", listName);
        queryParams.put("idBoard", boardId);
        response = restAssuredExtension.PostWithQueryParams("lists", queryParams);

        return response;
    }

    public static ResponseOptions<Response> GetCardsInList(String listId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = restAssuredExtension.GetWithQueryParameter("lists/" + listId + "/cards", queryParams);

        return response;
    }

    public static void DeleteAllCardsInList(List<String> idsOfCards) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        while (!idsOfCards.isEmpty()) {
            response = CardUtilities.DeleteCard(queryParams, idsOfCards.get(0));
            assertThat(response.getStatusCode(), equalTo(200));
            idsOfCards.remove(0);
        }
    }
}
