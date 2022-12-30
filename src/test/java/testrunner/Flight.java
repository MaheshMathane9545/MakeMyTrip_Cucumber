package testrunner;

import org.junit.runner.RunWith;

import com.aventstack.extentreports.gherkin.model.Feature;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/filght.feature",
				dryRun=false, 
				glue= {"stepDefination"},
				plugin={"com.cucumber.listener.ExtentCucumberFormatter:reports/FlightBooking.html"})
public class Flight{

}
