package trello.rest.core;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/trello/rest/features/", plugin = {"json:target/test-report.json", "pretty", "html:target/cucumber-reports.html"}, glue = {"trello.rest.steps"}, monochrome = true, dryRun = false)
public class TestRunner {

}
