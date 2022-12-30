package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/cab.feature",
				dryRun=false, 
				glue= {"stepDefination"},
				plugin={"com.cucumber.listener.ExtentCucumberFormatter:reports/cabbooking.html"})
public class cab {

}
