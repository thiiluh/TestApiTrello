package trello.rest.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.Assert;
import trello.rest.utilities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class CardSteps extends RestAssuredExtension {


    public static ResponseOptions<Response> response;
    public static String boardId = "";
    public static String listId = "";
    public static String cardId;
    public static String labelId;
    public static String commentId;

    @Given("I have a board with List")
    public void iHaveABoardWithList() {
        if (boardId.isEmpty()) {
            response = BoardUtilities.CreateBoard("Board_Sensedia");
            assertThat(response.getStatusCode(), equalTo(200));
            boardId = response.getBody().jsonPath().get("id").toString();
        }
        if (listId.isEmpty()) {
            response = ListUtilities.CreateListBoard(boardId, "List_Sensedia");
            assertThat(response.getStatusCode(), equalTo(200));
            listId = response.getBody().jsonPath().get("id").toString();
        }
    }

    @When("I make an http post call to create a card")
    public void iMakeAnHttpPostCallToCreateACard() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Thiagoteste");
        body.put("desc", "Teste 123 :smile:");
        body.put("pos", "0");
        response = CardUtilities.CreateCard(queryParams, body);

    }

    @Then("I must get a status code {int} and a json object with card details")
    public void iMustGetAResponseWithStatusCodeAndAJsonObjectWithCardDetails(int status) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get("desc"), equalTo("Teste 123 :smile:"));
    }

    @When("I make an http post call without {string} query parameter to create a card")
    public void iMakeAHttpPostCallWithoutQueryParameterToCreateACard(String parameter) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        queryParams.remove(parameter);
        HashMap<String, String> body = new HashMap<>();
        response = CardUtilities.CreateCard(queryParams, body);
    }

    @Then("I must get a status code {int} and the message {string}")
    public void iMustGetAStatusCodeAndTheMessage(int status, String message) {
        assertThat(response.getBody().asString(), equalTo(message));
        assertThat(response.getStatusCode(), equalTo(status));
    }

    @Given("I have a card created")
    public void iHaveACardCreated() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Thiagoteste");
        body.put("desc", "Teste 123 :smile:");
        body.put("pos", "0");
        response = CardUtilities.CreateCard(queryParams, body);
        cardId = response.getBody().jsonPath().get("id").toString();
        assertThat(response.getStatusCode(), equalTo(200));
    }

    @When("I make an http put call to edit {string} card to {string}")
    public void iMakeAHttpPutCallToEditCardTo(String key, String value) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        HashMap<String, String> body = new HashMap<>();
        body.put(key, value);
        response = CardUtilities.UpdateCard(queryParams, body, cardId);
    }

    @Then("I must get a status code {int} and the card {string} must be {string}")
    public void iMustGetAStatusCodeAndTheCardMustBe(int status, String key, String value) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get(key), equalTo(value));
    }

    @When("I make an http put call without {string} query parameter to edit a card")
    public void iMakeAHttpPutCallWithoutQueryParameterToEditACard(String parameter) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        queryParams.remove(parameter);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Update card invalid");
        response = CardUtilities.UpdateCard(queryParams, body, cardId);
    }

    @When("I make an http put call to edit {string} card to {int}")
    public void iMakeAHttpPutCallToEditCardTo(String key, int value) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        HashMap<String, Integer> body = new HashMap<>();
        body.put(key, value);
        response = CardUtilities.UpdateCardInteger(queryParams, body, cardId);
    }

    @Then("I must get a status code {int} and the card {string} must be true")
    public void iMustGetAStatusCodeAndTheCardMustBeTrue(int status, String key) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get(key), equalTo(true));
    }

    @And("The list not must contain the card")
    public void theListNotMustContentTheCard() {
        response = ListUtilities.GetCardsInList(listId);
        Assert.assertTrue(!response.getBody().asString().contains(cardId));
    }

    @Then("I must get a status code {int} and the card {string} must be false")
    public void iMustGetAStatusCodeAndTheCardMustBeFalse(int status, String key) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get(key), equalTo(false));
    }

    @And("The list must contain the card")
    public void theListMustContentTheCard() {
        response = ListUtilities.GetCardsInList(listId);
        Assert.assertTrue(response.getBody().asString().contains(cardId));
    }

    @When("I make an http post call to add a sticker with {string}: {string}, {string}: {int}, {string}: {double} and {string}: {int}")
    public void iMakeAHttpPostCallToAddAStickerWithAnd(String imageKey, String imageValue, String topKey, Integer topValue, String leftKey, Double leftValue, String zIndexKey, Integer zIndexValue) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put(imageKey, imageValue);
        queryParams.put("token", TOKEN);
        queryParams.put(topKey, topValue.toString());
        queryParams.put(leftKey, leftValue.toString());
        queryParams.put(zIndexKey, zIndexValue.toString());
        response = CardUtilities.AddSticker(queryParams, cardId);
    }


    @And("The fields must be {string} = {string}, {string} = {int}, {string} = {double} and {string} = {int}")
    public void theFieldsMustBeAnd(String imageKey, String imageValue, String topKey, Integer topValue, String leftKey, Double leftValue, String zIndexKey, Integer zIndexValue) {
        assertThat(response.getBody().jsonPath().get(imageKey), equalTo(imageValue));
        assertThat(response.getBody().jsonPath().get(topKey), equalTo(topValue));
        assertThat(response.getBody().jsonPath().get(leftKey), equalTo(leftValue.floatValue()));
        assertThat(response.getBody().jsonPath().get(zIndexKey), equalTo(zIndexValue));
    }

    @When("I make an http post call to add a sticker with {string} = {string}")
    public void iMakeAHttpPostCallToAddAStickerWith(String key, String value) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("image", "check");
        queryParams.put("top", "0");
        queryParams.put("left", "99.110236220472444");
        queryParams.put("zIndex", "1");
        queryParams.put("rotate", "1");
        queryParams.replace(key, value);
        response = CardUtilities.AddSticker(queryParams, cardId);
    }

    @When("I make an http post call to create a new {string} {string} label with {string} {string}")
    public void iMakeAHttpPostCallToCreateANewLabelWith(String colorValue, String colorKey, String nameKey, String nameValue) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put(colorKey, colorValue);
        queryParams.put(nameKey, nameValue);
        queryParams.put("idBoard", boardId);
        response = LabelUtilities.CreateLabelWithParameter(queryParams);
    }

    @And("The fields must be {string} = {string}, {string} = {string}")
    public void theFieldsMustBe(String nameKey, String nameValue, String colorKey, String colorValue) {
        assertThat(response.getBody().jsonPath().get(nameKey), equalTo(nameValue));
        assertThat(response.getBody().jsonPath().get(colorKey), equalTo(colorValue));
    }

    @When("I make an http post call without {string} query parameter to create a label")
    public void iMakeAHttpPostCallWithoutQueryParameterToCreateALabel(String parameter) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("color", "blue");
        queryParams.put("name", "Label Teste without parameter");
        queryParams.put("idBoard", boardId);
        queryParams.remove(parameter);
        response = LabelUtilities.CreateLabelWithParameter(queryParams);
    }

    @Given("I have a board")
    public void iHaveABoard() {
        response = BoardUtilities.GetBoard(boardId);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody().jsonPath().get("id"), equalTo(boardId));
    }

    @When("I make an http delete call")
    public void iMakeAHttpDeleteCall() {
        response = BoardUtilities.DeleteBoard(boardId);
    }

    @Then("I must get a status code {int}")
    public void iMustGetAStatusCode(int status) {
        assertThat(response.getStatusCode(), equalTo(status));
    }

    @When("I make an http post call without idBoard query parameter to create a label")
    public void iMakeAHttpPostCallWithoutIdBoardQueryParameterToCreateALabel() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("color", "blue");
        queryParams.put("name", "Label Teste without parameter");
        response = LabelUtilities.CreateLabelWithParameter(queryParams);
    }

    @Then("I must get a status code {int} with obj Json")
    public void iMustGetAStatusCodeWithObjJson(int status) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get("message"), equalTo("Invalid id"));
        assertThat(response.getBody().jsonPath().get("error"), equalTo("ERROR"));
    }

    @Given("I have a {string} label")
    public void iHaveALabel(String color) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("color", color);
        queryParams.put("name", "Label " + color);
        queryParams.put("idBoard", boardId);
        response = LabelUtilities.CreateLabelWithParameter(queryParams);
        assertThat(response.getStatusCode(), equalTo(200));
        labelId = response.getBody().jsonPath().get("id").toString();
    }

    @When("I make an http post call to add the {string} label on a card")
    public void iMakeAHttpPostCallToAddTheLabelOnACard(String color) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("value", labelId);
        response = CardUtilities.AddLabel(queryParams, cardId);
    }

    @And("Must contain list of labels attributed to this card with the {string} label ID")
    public void mustContentListOfLabelsAttributedToThisCardWithTheLabelID(String color) {
        Assert.assertTrue(response.getBody().asString().contains(labelId));
    }

    @Given("I created a card")
    public void iCreatedACard() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("idList", listId);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Test Same Label");
        body.put("desc", "Test 123 Test Same Label :smile:");
        response = CardUtilities.CreateCard(queryParams, body);
        cardId = response.getBody().jsonPath().get("id");
        assertThat(response.statusCode(), equalTo(200));
    }

    @And("I created a label in the card")
    public void iCreatedALabelInTheCard() {
        response = LabelUtilities.CreateLabel();
        assertThat(response.statusCode(), equalTo(200));
        labelId = response.getBody().jsonPath().get("id").toString();
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("value", labelId);
        response = CardUtilities.AddLabel(queryParams, cardId);
        assertThat(response.statusCode(), equalTo(200));
    }

    @When("I make an http post call to add the same label on a card")
    public void iMakeAHttpPostCallToAddTheSameLabelOnACard() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("value", labelId);
        response = CardUtilities.AddLabel(queryParams, cardId);
    }

    @Then("I must get a status code {int} with message: {string}")
    public void iMustGetAStatusCodeWithMessage(int status, String message) {
        assertThat(response.statusCode(), equalTo(status));
        assertThat(response.getBody().asString(), equalTo(message));
    }

    @And("I created a label")
    public void iCreatedALabel() {
        response = LabelUtilities.CreateLabel();
        assertThat(response.statusCode(), equalTo(200));
        labelId = response.getBody().jsonPath().get("id").toString();
    }

    @When("I make an http post call without {string} query parameter to Add label on the card")
    public void iMakeAHttpPostCallWithoutParameterQueryParameterToAddLabelOnTheCard(String parameter) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("value", labelId);
        queryParams.remove(parameter);
        response = CardUtilities.AddLabel(queryParams, cardId);
    }

    @When("I make an http delete call to remove the same label on a card")
    public void iMakeAHttpDeleteCallToRemoveTheSameLabelOnACard() {
        response = CardUtilities.DeleteLabel();
    }

    @Then("I must get a status code {int} with obj Json value: null")
    public void iMustGetAStatusCodeWithObjJsonValueNull(int status) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get("_value"), equalTo(null));
    }

    @When("I make an http post call to Add a new comment to a Card with text = {string}")
    public void iMakeAHttpPostCallToAddANewCommentToACardWithText(String commentText) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("text", commentText);
        response = CardUtilities.AddComment(queryParams, cardId);
    }

    @Then("I must get a status code {int} with obj Json content text = {string}")
    public void iMustGetAStatusCodeWithObjJsonContentText(int status, String commentText) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().jsonPath().get("id"), notNullValue());
        assertThat(response.getBody().jsonPath().get("data.text"), equalTo(commentText));
    }

    @And("I created a comment with text = {string}")
    public void iCreatedACommentWithText(String commentText) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("text", commentText);
        response = CardUtilities.AddComment(queryParams, cardId);
        commentId = response.getBody().jsonPath().get("id").toString();
    }

    @When("I make an http put call to update the comment with text = {string}")
    public void iMakeAHttpPutCallToUpdateTheCommentWithText(String commentText) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.put("text", commentText);
        response = CardUtilities.UpdateComment(queryParams, cardId, commentId);
    }

    @When("I make an http delete call to remove the comment")
    public void iMakeAHttpDeleteCallToRemoveTheComment() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = CardUtilities.DeleteComment(queryParams, cardId, commentId);
    }

    @When("I make an http delete call to delete a card")
    public void iMakeAnHttpDeleteCallToDeleteACard() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        response = CardUtilities.DeleteCard(queryParams, cardId);
    }

    @Then("I must get a status code {int} with an empty json object")
    public void iMustGetAStatusCodeWithAnEmptyJsonObject(int status) {
        HashMap<String, String> hashMapEmpty = new HashMap<>();
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getBody().jsonPath().get("limits"), equalTo(hashMapEmpty));
    }

    @Given("I have a list with {int} or more cards")
    public void iHaveAListWithOrMoreCards(int numberOfCards) {
        CardUtilities.CreateMultipleCards(numberOfCards);
        response = ListUtilities.GetCardsInList(listId);
    }

    @When("I make http delete calls until I run out of cards")
    public void iMakeHttpDeleteCallsUntilIRunOutOfCards() {
        List<String> idsOfCards = new ArrayList<>(response.getBody().jsonPath().get("id"));
        ListUtilities.DeleteAllCardsInList(idsOfCards);
    }

    @Then("I query the list")
    public void iQueryTheList() {
        response = ListUtilities.GetCardsInList(listId);
    }

    @And("Receive an empty array stating that there is no card in the list")
    public void receiveAnEmptyArrayStatingThatThereIsNoCardInTheList() {
        Assert.assertTrue(response.getBody().jsonPath().getList("").isEmpty());
    }

    @When("I make an http delete call to delete a card without {string} query parameter")
    public void iMakeAnHttpDeleteCallToDeleteACardWithoutQueryParameter(String parameter) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", KEY);
        queryParams.put("token", TOKEN);
        queryParams.remove(parameter);
        response = CardUtilities.DeleteCard(queryParams, cardId);
    }
}
