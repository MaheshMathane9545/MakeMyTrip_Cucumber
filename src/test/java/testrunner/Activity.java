package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/activity.feature",
				dryRun=false, 
				glue= {"stepDefination"},
				plugin={"com.cucumber.listener.ExtentCucumberFormatter:reports/activity.html"})
public class Activity {

}
