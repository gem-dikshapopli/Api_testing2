package runner;

import cucumber.api.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources"
        ,glue = "stepDefinition"
        ,publish = true
)
public class TestRunner {
}

