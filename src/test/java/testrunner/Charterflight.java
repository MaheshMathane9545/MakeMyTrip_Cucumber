package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/charterflight.feature",
				dryRun=false, 
				glue= {"stepDefination"},
				plugin={"com.cucumber.listener.ExtentCucumberFormatter:reports/charterflight.html"})
public class Charterflight {

}
