package trello.rest.core;


import io.cucumber.java.Before;

import trello.rest.utilities.RestAssuredExtension;

public class TestInitialize implements Environment{

    @Before
    public void TestSetup(){
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();

        if (TOKEN.isEmpty() || KEY.isEmpty()) {
            throw new IllegalStateException("Preencher Token e Key no arquivo Environment");
        }
    }
}
