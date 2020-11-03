# Test Api Trello

## Environment: 
* Get your "key" and "token" from the [trello.com/app-key](https://trello.com/app-key) with scope read, write, and account
* Configure TOKEN and KEY in the file [Environment.java](https://github.com/thiiluh/TestApiTrello/blob/main/src/test/java/trello/rest/core/Environment.java)
* Example link to obtain token with scope for read, write, and account:    
        https://trello.com/1/authorize?expiration=never&scope=read&response_type=token&name=Server%20Token&key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

### Framework Contents:
- Cucumber [link](https://cucumber.io/)
- REST Assured [link](https://rest-assured.io/)
- Maven Project

### Initial Setup: 
- Install and configure JAVA 10
- Install and configure Maven (apache-maven-3.6.3)
- Simply run  ***mvn test*** command to run your test cases
