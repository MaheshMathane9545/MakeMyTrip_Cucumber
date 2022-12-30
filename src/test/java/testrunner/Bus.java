package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="featureFiles/busticketbooking.feature",
				dryRun=false,
				glue="stepDefination",
				plugin= {"com.cucumber.listener.ExtentCucumberFormatter:reports/busbooking.html"}			
		)

public class Bus {

}
