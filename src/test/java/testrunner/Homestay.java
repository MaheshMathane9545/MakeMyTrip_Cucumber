package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/homestaybooking.feature",
				dryRun=false, 
				glue= {"stepDefination"},
				plugin={"com.cucumber.listener.ExtentCucumberFormatter:reports/homestaybooking.html"})

public class Homestay {

}
