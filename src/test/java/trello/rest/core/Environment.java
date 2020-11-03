package trello.rest.core;

import io.restassured.http.ContentType;

public interface Environment {
    public static final String BASE_URL = "https://api.trello.com/1/";
    public static final ContentType CONTENT_TYPE = ContentType.JSON;
    public static final Long MAX_TIMEOUT = 50000L;
    public static final String KEY = "";
    public static final String TOKEN = "";
}
