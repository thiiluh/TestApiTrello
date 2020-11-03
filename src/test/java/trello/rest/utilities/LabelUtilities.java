package trello.rest.utilities;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import trello.rest.steps.CardSteps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LabelUtilities extends RestAssuredExtension {

    public static ResponseOptions<Response> response;

    public static ResponseOptions<Response> CreateLabelWithParameter(Map<String, String> queryParams) {
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        response = restAssuredExtension.PostWithQueryParams("labels", queryParams);

        return response;
    }

    public static ResponseOptions<Response> CreateLabel() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("color", "blue");
        queryParams.put("name", strDate);
        queryParams.put("idBoard", CardSteps.boardId);
        response = restAssuredExtension.PostWithQueryParams("labels", queryParams);

        return response;
    }
}
