package trello.rest.utilities;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import trello.rest.steps.CardSteps;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class CardUtilities extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    public static ResponseOptions<Response> CreateCard(Map<String, String> queryParams, Map<String, String> body) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PostWithBodyAndQueryParams("cards", queryParams, body);

        return response;
    }

    public static ResponseOptions<Response> UpdateCard(Map<String, String> queryParams, Map<String, String> body, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PutWithBodyAndPathParams("cards/" + cardId, queryParams, body);

        return response;
    }

    public static ResponseOptions<Response> UpdateCardInteger(Map<String, String> queryParams, Map<String, Integer> body, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PutWithBodyAndPathParamsInteger("cards/" + cardId, queryParams, body);

        return response;
    }

    public static ResponseOptions<Response> AddSticker(HashMap<String, String> queryParams, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PostWithQueryParams("cards/" + cardId + "/stickers", queryParams);

        return response;
    }

    public static ResponseOptions<Response> AddLabel(HashMap<String, String> queryParams, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PostWithQueryParams("cards/" + cardId + "/idLabels", queryParams);

        return response;
    }

    public static ResponseOptions<Response> DeleteLabel() {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = restAssuredExtension.DeleteWithQueryParams("cards/" + CardSteps.cardId + "/idLabels/" + CardSteps.labelId, queryParams);

        return response;
    }

    public static ResponseOptions<Response> AddComment(HashMap<String, String> queryParams, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PostWithQueryParams("cards/" + cardId + "/actions/comments", queryParams);

        return response;
    }

    public static ResponseOptions<Response> UpdateComment(HashMap<String, String> queryParams, String cardId, String commentId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PutWithQueryParams("cards/" + cardId + "/actions/" + commentId + "/comments", queryParams);

        return response;
    }

    public static ResponseOptions<Response> DeleteComment(HashMap<String, String> queryParams, String cardId, String commentId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.DeleteWithQueryParams("cards/" + cardId + "/actions/" + commentId + "/comments", queryParams);

        return response;
    }

    public static ResponseOptions<Response> DeleteCard(HashMap<String, String> queryParams, String cardId) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.DeleteWithQueryParams("cards/" + cardId, queryParams);

        return response;
    }

    public static void CreateMultipleCards(int numberOfCards) {
        for (Integer i = 0; i < numberOfCards; i++){
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("key", KEY);
            queryParams.put("token", TOKEN);
            queryParams.put("idList", CardSteps.listId);
            HashMap<String, String> body = new HashMap<>();
            body.put("name", "Test Multiple Cards "+i.toString());
            response = CreateCard(queryParams, body);
            assertThat(response.getStatusCode(), equalTo(200));
        }

    }

}
