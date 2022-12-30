

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import Apputiles.Utilies;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonLibrery1 extends Utilies {
	
	static WebDriver driver;
	
	@Given("^open chrome browser with \"([^\"]*)\" url$")
	public void open_chrome_browser_with_url(String url) throws Throwable {
		System.setProperty("webdriver.chromedriver.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(url);	
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//li[@data-cy='account']")).click();
	}

	@Then("^system should display homepage of site$")
	public void system_should_display_homepage_of_site() throws Throwable {
		driver.getTitle();
//		Thread.sleep(2000);
//		System.out.println(title);
	}

	@Then("^select \"([^\"]*)\" trip$")
	public void select_trip(String arg1) throws Throwable {
		WebElement oneway = driver.findElement(By.xpath("//li[text()='OneWay']"));
		{
			oneway.click();
		}
		
		
	}

	@Then("^select from city as \"([^\"]*)\"$")
	public void select_from_city_as(String cityname) throws Throwable {
		
//		Utilies.findElement("//span[text()='From']	| //span[contains(text(),'City, Property name or Location')]").click();
		
		driver.findElement(By.xpath("//span[text()='From']	| //span[contains(text(),'City, Property name or Location')]")).click();
		driver.findElement(By.xpath("//input[@placeholder='From'] | //input[@placeholder='Enter city/ Hotel/ Area/ Building']")).sendKeys(cityname);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		
		java.util.List<WebElement> sugg_ul, sugg_li;
		sugg_ul = driver.findElements(By.xpath("//div[contains(@class,'react-autosuggest__section-container--first')]//ul"));
		for(int i=0; i<sugg_ul.size(); i++) 
		{	
			sugg_li = sugg_ul.get(i).findElements(By.xpath("//div[contains(@class,'section-container--first')]//li//p[1]"));
			for(WebElement ele:sugg_li) 
			{
				if(ele.getText().contains(cityname)) 
				{
//					System.out.println(ele.getText());
					ele.click();
					break;
				}
			}			
		}
	
	}

	@Then("^select to city as \"([^\"]*)\"$")
	public void select_to_city_as(String to) throws Throwable {
		try {
//			driver.findElement(By.xpath("//span[text()='To'] | //input[@placeholder='To']")).click();
			System.out.println("try running");
//			driver.findElement(By.xpath("//span[text()='From']	| //span[contains(text(),'City, Property name or Location')]")).click();
			driver.findElement(By.xpath("//input[@placeholder='To'] | //input[@placeholder='Enter city/ Hotel/ Area/ Building']")).sendKeys(to);

//			WebElement sugg = driver.findElement(By.xpath("//p[contains(text(),'SUGGESTIONS ')]"));
//			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			Thread.sleep(3000);
			
			java.util.List<WebElement> sugg_ul, sugg_li;
			sugg_ul = driver.findElements(By.xpath("//div[contains(@class,'react-autosuggest__section-container--first')]//ul"));
			for(int i=0; i<sugg_ul.size(); i++) 
			{	
				sugg_li = sugg_ul.get(i).findElements(By.xpath("//div[contains(@class,'section-container--first')]//li//p[1]"));
				for(WebElement ele:sugg_li) 
				{
					if(ele.getText().contains(to)) 
					{
//						System.out.println(ele.getText());
						ele.click();
						break;
					}
				}			
			}
		
		} catch (Exception e) {
			driver.findElement(By.xpath("//span[text()='To'] | //input[@placeholder='To']")).click();
//			Utilies.waitForClickablility(element, 30);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys(to);
			System.out.println("catch running");
			// Selecting From Suggestion List
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

			java.util.List<WebElement> sugg_ul1, sugg_li1;

			sugg_ul1 = driver.findElements(By.xpath("//div[contains(@class,'react-autosuggest__section-container--first')]//ul"));
			for(int i=0; i<sugg_ul1.size(); i++) 
			{	
				sugg_li1 = sugg_ul1.get(i).findElements(By.xpath("//div[contains(@class,'section-container--first')]//li//p[1]"));
				for(WebElement ele:sugg_li1) 
				{
					if(ele.getText().contains(to)) 
					{
						System.out.println(ele.getText());
						ele.click();
						break;
					}
				}			
			}
		}
	}
	
	@Then("^departure date as \"([^\"]*)\"$")
	public void departure_date_as(String flydate) throws Throwable {
		
		try {
			
			String[] temp = flydate.split("-");//28-dec-2023
			String date = temp[0];
			String month = temp[1];
			String year = temp[2];

			String mmyy = month+" "+year;
			String mmyy1 = month+""+year;
			System.out.println(mmyy);
//			System.out.println(mmyy1);

			String calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
//			System.out.println(calyear);
			
			boolean res =  StringUtils.containsWhitespace(calyear);
//			System.out.println(res);
			
			if(res) {
//				System.out.println("if running");
				while(!calyear.equalsIgnoreCase(mmyy))
				{		
					driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
			}else {
//				System.out.println("else running");
				while(!calyear.equalsIgnoreCase(mmyy1))
				{
				driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
				calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
				
			}

				//table
				WebElement table = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']"));
				java.util.List<WebElement> rows,cols;
				
				// row
				rows =  table.findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Week']"));//
				for(int i=0; i<rows.size(); i++) 
				{
					try {
						Thread.sleep(3000);
						//coloum
						cols = rows.get(i).findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='dateInnerCell']//p[1] | //div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Day']"));
						//System.out.println(cols);
						for(WebElement element : cols) 
							if(element.getText().equals(date)) 
							{
//								System.out.println(element.getText());
								element.click();
								Thread.sleep(2000);
								break;
							}

					} catch (StaleElementReferenceException e) {
						System.out.println(".");
					}
				}
				
		} 
		catch (Exception e) {
			WebElement ele = driver.findElement(By.xpath("//span[text()='TRAVEL DATE'] | //span[text()='DEPARTURE Date & time'] |//span[text()='DEPARTURE']"));

			if(ele.isDisplayed()) {
				try {
					ele.click();
				} catch (Exception e1) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", ele);
				}
			}

			String[] temp = flydate.split("-");//28-dec-2023
			String date = temp[0];
			String month = temp[1];
			String year = temp[2];

			String mmyy = month+" "+year;
			String mmyy1 = month+""+year;
//			System.out.println(mmyy);
//			System.out.println(mmyy1);

			String calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
//			System.out.println(calyear);

			boolean res =  StringUtils.containsWhitespace(calyear);
//			System.out.println(res);

			if(res) {
				//				System.out.println("if running");
				while(!calyear.equalsIgnoreCase(mmyy))
				{		
					driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
			}else {
				//				System.out.println("else running");
				while(!calyear.equalsIgnoreCase(mmyy1))
				{
					driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}

			}

			//table
			WebElement table = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']"));
			java.util.List<WebElement> rows,cols;

			// row
			rows =  table.findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Week']"));//
			for(int i=0; i<rows.size(); i++) 
			{
				try {
					Thread.sleep(3000);
					//coloum
					cols = rows.get(i).findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='dateInnerCell']//p[1] | //div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Day']"));
					//System.out.println(cols);
					for(WebElement element : cols) 
						if(element.getText().equals(date)) 
						{
							System.out.println(element.getText());
							element.click();
							Thread.sleep(2000);
							break;
						}

				} catch (StaleElementReferenceException e2) {
					System.out.println(".");
				}
			}
				
		}
		
		
		
	}

	@Then("^click on traveller$")
	public void click_on_traveller() throws Throwable {
//		driver.findElement(By.xpath("//span[@class='appendRight10']")).click();
//		driver.findElement(By.xpath("//span[contains(text(),'Travellers & CLASS')]")).click();
		driver.findElement(By.xpath("//label[@for='travellers']")).click();

	}

	@Then("^select \"([^\"]*)\" adults from list$")
	public void select_adults_from_list(String adult) throws Throwable {
		try {
			if(driver.findElement(By.xpath("(//div[@class='gstSlct'])[2]")).isDisplayed()) 
			{
				driver.findElement(By.xpath("(//div[@class='gstSlct'])[2]")).click();		
				List<WebElement> ad_ul,ad_li;
				ad_ul = driver.findElements(By.xpath("//div[@class='appendBottom20']/ul[1] | (//ul[@class='guestCounter font12 darkText'])[1] | //ul[@class='gstSlct__list']"));
				for(int u=0; u<ad_ul.size(); u++) 
				{
					ad_li = ad_ul.get(u).findElements(By.tagName("li"));
					for(WebElement ele:ad_li) 
					{
						if(ele.getText().contains(adult)) 
						{
							System.out.println(ele);
							ele.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {	
			List<WebElement> ad_ul,ad_li;
			ad_ul = driver.findElements(By.xpath("//div[@class='appendBottom20']/ul[1] | (//ul[@class='guestCounter font12 darkText'])[1] | //ul[@class='passengerList font12 darkText']  |//ul[@class='gstSlct__list']"));
			for(int u=0; u<ad_ul.size(); u++) 
			{
				ad_li = ad_ul.get(u).findElements(By.tagName("li"));
				for(WebElement ele:ad_li) 
				{
					if(ele.getText().contains(adult)) 
					{
						ele.click();
						break;
					}
				}
			}
		}
			
		
	}

	@Then("^select \"([^\"]*)\" childrens from list$")
	public void select_childrens_from_list(String children) throws Throwable {
	    List<WebElement> ch_ul ,ch_li;
		ch_ul = driver.findElements(By.xpath("//div[contains(@class,'childCounter')] | //ul[@class='gstSlct__list']"));

		for(int uc=0; uc<ch_ul.size(); uc++)
		{
			ch_li = ch_ul.get(uc).findElements(By.tagName("li"));
			for(WebElement ele:ch_li) 
			{
				if(ele.getText().contains(children)) 
				{
					ele.click();
					break;
				}
			}
		}
	}

	@Then("^select \"([^\"]*)\" infants from list$")
	public void select_infants_from_list(String infants) throws Throwable {
		List<WebElement> if_ul ,if_li;
		if_ul = driver.findElements(By.xpath("//div[contains(@class,'infantCounter')]"));
		for(int ic=0; ic<if_ul.size(); ic++) 
		{
			if_li = if_ul.get(ic).findElements(By.tagName("li"));
			for(WebElement ele:if_li) 
			{
				if(ele.getText().contains(infants)) 
				{
					ele.click();
					break;
				}
			}
		}
	}
	
	@Then("^click on traveller & class$")
	public void click_on_traveller_class() throws Throwable {
		driver.findElement(By.xpath("//span[@class='appendRight10'] | //span[text()='Class']")).click();
	}
		
	@Then("^Select adult from the list \"([^\"]*)\"$")
	public void select_adult_from_the_list(String adult) throws Throwable {

//		WebElement res = driver.findElement(By.xpath("(//div[@class='gstSlct'])[2]"));		
		
	try {
		if(driver.findElement(By.xpath("(//div[@class='gstSlct'])[2]")).isDisplayed()) 
		{
			driver.findElement(By.xpath("(//div[@class='gstSlct'])[2]")).click();		
			List<WebElement> ad_ul,ad_li;
			ad_ul = driver.findElements(By.xpath("//div[@class='appendBottom20']/ul[1] | (//ul[@class='guestCounter font12 darkText'])[1] | //ul[@class='gstSlct__list']"));
			for(int u=0; u<ad_ul.size(); u++) 
			{
				ad_li = ad_ul.get(u).findElements(By.tagName("li"));
				for(WebElement ele:ad_li) 
				{
					if(ele.getText().contains(adult)) 
					{
						System.out.println(ele);
						ele.click();
						break;
					}
				}
			}
		}
	} catch (Exception e) {	
		List<WebElement> ad_ul,ad_li;
		ad_ul = driver.findElements(By.xpath("//div[@class='appendBottom20']/ul[1] | (//ul[@class='guestCounter font12 darkText'])[1] | //ul[@class='passengerList font12 darkText']  |//ul[@class='gstSlct__list']"));
		for(int u=0; u<ad_ul.size(); u++) 
		{
			ad_li = ad_ul.get(u).findElements(By.tagName("li"));
			for(WebElement ele:ad_li) 
			{
				if(ele.getText().contains(adult)) 
				{
					ele.click();
					break;
				}
			}
		}
	}
		
	}

	@Then("^Select children from the list \"([^\"]*)\"$")
	public void select_children_from_the_list(String children) throws Throwable {
		
		
			List<WebElement> ch_ul ,ch_li;
			ch_ul = driver.findElements(By.xpath("//div[contains(@class,'childCounter')] | //ul[@class='gstSlct__list']"));

			for(int uc=0; uc<ch_ul.size(); uc++)
			{
				ch_li = ch_ul.get(uc).findElements(By.tagName("li"));
				for(WebElement ele:ch_li) 
				{
					if(ele.getText().contains(children)) 
					{
						ele.click();
						break;
					}
				}
			}
	
	}

	@Then("^Select infants from the list \"([^\"]*)\"$")
	public void select_infants_from_the_list(String infants) throws Throwable {
		List<WebElement> if_ul ,if_li;
		if_ul = driver.findElements(By.xpath("//div[contains(@class,'infantCounter')]"));
		for(int ic=0; ic<if_ul.size(); ic++) 
		{
			if_li = if_ul.get(ic).findElements(By.tagName("li"));
			for(WebElement ele:if_li) 
			{
				if(ele.getText().contains(infants)) 
				{
					ele.click();
					break;
				}
			}
		}
	}

	@Then("^select \"([^\"]*)\" class from list$")
	
	public void select_class_from_list(String cls) throws Throwable {
		
		try {
			List<WebElement> cl_ul, cl_li;
			cl_ul = driver.findElements(By.xpath("//ul[contains(@class,'classSelect')] | //ul[@class='travelForPopup']"));
			for(int cl=0; cl<cl_ul.size(); cl++) 
			{
				cl_li = cl_ul.get(cl).findElements(By.tagName("li"));
				for(WebElement ele:cl_li) 
				{
					if(ele.getText().equalsIgnoreCase(cls)) 
					{
						ele.click();
						break;
					}
				}
			}
			
		} catch (Exception e) {
//			if(driver.findElement(By.xpath("//span[text()='Class']")).isDisplayed()){
//			WebElement ele1 = driver.findElement(By.xpath("//span[text()='Class']"));
			
			
			 driver.findElement(By.xpath("//span[text()='Class']")).click();
			
			List<WebElement> cl_ul, cl_li;
			cl_ul = driver.findElements(By.xpath("//ul[contains(@class,'classSelect')] | //ul[@class='travelForPopup']"));
			for(int cl=0; cl<cl_ul.size(); cl++) 
			{
				cl_li = cl_ul.get(cl).findElements(By.tagName("li"));
				for(WebElement ele:cl_li) 
				{
					if(ele.getText().equalsIgnoreCase(cls)) 
					{
						ele.click();
						break;
					}
				}
			}
			}
	}
	
	@Then("click on Apply button")	
	public void click_on_button() throws Throwable {
		WebElement app = driver.findElement(By.xpath("//button[text()='APPLY'] | //button[text()='Apply']"));
		try {
			app.click();
		} catch (NoSuchElementException e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click", app);
			
		}
	}
	
	@When("click on Search button")
	public void click_button() throws Throwable {
//		WebElement srch = driver.findElement(By.xpath("//a[text()='Search'] | //button[text()='Search']"));;
//		 driver.findElement(By.xpath("//a[text()='Search'] | //button[text()='Search']")).click();;
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Search'] | //button[text()='Search']")));
		WebElement srch = driver.findElement(By.xpath("//a[text()='Search'] | //button[text()='Search']"));

		
		try {
			srch.click();
		} catch (NoSuchElementException e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", srch);
			
		}
	}
	
	@Then("^system should display list of flights$")
	public void system_should_display_list_of_flights() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='bgProperties icon20 overlayCrossIcon']")));
	}
	
	@Then("^system should display one pop-up$")
	public void system_should_display_one_pop_up() throws Throwable {
//		WebElement popup = driver.findElement(By.xpath("//div[@class='commonOverlay ']"));
//		Utilies.waitForVisilility(popup, 5);
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='commonOverlay '] | //p[contains(text(),'additional information')] | //div[@class='commonOverlay ']")));
	}

	@Then("^close that pop-up$")
	public void close_that_pop_up() throws Throwable {
//		driver.findElement(By.xpath("//span[@class='bgProperties icon20 overlayCrossIcon'] | //img[@class='primoCloseIcon']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='bgProperties icon20 overlayCrossIcon'] | //img[@class='primoCloseIcon']")));
		
		driver.findElement(By.xpath("//span[@class='bgProperties icon20 overlayCrossIcon'] | //img[@class='primoCloseIcon']")).click();

	
	}
	
	@Then("^system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs$")
	
	public void system_should_display_list_of_filghtes_hotels_homestays_trains_Buses_Cabs() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='appendRight8'])[1] | //span[text()='Activities'] | //div[contains(@id,'Listing_hotel')] | //div[@class='bus-card'] | //span[text()='Activities'] |//div[contains(@class,'cabListingTileWrapper blackText')] |//div[contains(@class,'single-train-detail')]")));
	
	}

	@Then("^select anyone from list & click on View price$")
	public void select_anyone_from_list_click_on_View_price() throws Throwable {
		driver.findElement(By.xpath("(//span[@class='appendRight8'])[1] | (//div[@id='Listing_hotel_1'])[1] | (//div[contains(@class,'card ')])[1] | (//a[@data-test-id='select-seats'])[1]")).click();
//		WebElement ele = driver.findElement(By.xpath("(//span[@class='appendRight8'])[1] | (//div[@id='Listing_hotel_1'])[1]"));
	}

	@Then("^click on Book Now$")
	public void click_on_Book_Now() throws Throwable {
		
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Book Now'])[1] | (//button[text()='BOOK NOW'])[1] | (//span[text()='BOOK NOW'])[1] | (//span[text()='BOOK SEATS'])[1]")));
	
		
		WebElement ele = driver.findElement(By.xpath("(//button[text()='Book Now'])[1] | (//button[text()='BOOK NOW'])[1] |(//span[text()='BOOK NOW'])[1] | (//span[text()='BOOK SEATS'])[1]"));
		
		try {
			ele.click();
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", ele);
		}
		
	
	}

	@Then("^system should open new tab$")
	public void system_should_open_new_tab() throws Throwable {
	    List<String> browserTab = Lists.newArrayList(driver.getWindowHandles());
	    driver.switchTo().window(browserTab.get(1));
	    
	}

	@Then("^system should display Traveller Details page$")
	public void system_should_display_Traveller_Details_page() throws Throwable {
//	    WebElement detailspage = driver.findElement(By.xpath("//h2[text()='Traveller Details']| //h2[text()='Customer Information'] | //p[text()='Confirm Traveller information'] | //h3[text()='Enter Traveller Details']"));
	    
	    WebDriverWait wait = new WebDriverWait(driver, 20);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Traveller Details']| //h2[text()='Customer Information'] | //p[text()='Confirm Traveller information'] | //h3[text()='Enter Traveller Details']")));
	
	}
	
	@Then("^scroll upto \"([^\"]*)\"$")
	public void scroll_upto(String element) throws Throwable {
		
		
		WebElement scroll = driver.findElement(By.xpath("//h3[text()='"+element+"']| //h2[text()='\"+element+\"'] | //span[text()='"+element+"'] | //button[text()='"+element+"'] |//p[contains(text(),'"+element+"')]"));
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView()",scroll);
			Thread.sleep(3000);
			
			
		} catch (NoSuchElementException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
			Thread.sleep(3000);
		}
		
		
	}

	@Then("^click on add new adult$")
	public void click_on_add_new_adult() throws Throwable {
		
		
		WebElement addinf = driver.findElement(By.xpath("//button[normalize-space()='+ ADD NEW ADULT'] | //span[normalize-space()='Add Traveller']"));
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(addinf));
		
		if(addinf.isDisplayed()) 
		{
			addinf.click();
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		wait.until(ExpectedConditions.visibilityOf(addinf));
	}

	@Then("^send first name as \"([^\"]*)\"$")
	public void send_first_name_as(String f_name) throws Throwable {
		driver.findElement(By.xpath("(//input[@placeholder='First & Middle Name'])[1]| //input[@placeholder='Enter first name'] |(//input[@class='formInput'])[1] | //input[@placeholder='Enter your full name'] |//input[@id='fname'] |(//input[@placeholder='Enter Traveller Name'])[1]")).sendKeys(f_name);
	}

	@Then("^send last name as \"([^\"]*)\"$")
	public void send_last_name_as(String l_name) throws Throwable {
		driver.findElement(By.xpath("(//input[@placeholder='Last Name'])[1] | //input[@placeholder='Enter last name']")).sendKeys(l_name);
	}

	@Then("^gender as \"([^\"]*)\"$")
	public void gender_as(String gender) throws Throwable {

		WebElement m = driver.findElement(By.xpath("(//label[@tabindex='0'])[1]"));
		String ml = driver.findElement(By.xpath("(//span[@class='selectTabText'])[1]")).getText();
		if(ml.equalsIgnoreCase(gender)) 
		{
			if(!m.isSelected()) 
			{
				m.click();
			}
		}
		WebElement f = driver.findElement(By.xpath("(//label[@tabindex='1'])"));
		String fm = driver.findElement(By.xpath("(//span[@class='selectTabText'])[2]")).getText();
		if(fm.equalsIgnoreCase(gender)) 
		{
			if(!f.isSelected()) 
			{
				f.click();
			}
		}	
	}
	
	@Then("^send mobile no as \"([^\"]*)\"$")
	public void send_mobile_no_as(String mobailno) throws Throwable {
		driver.findElement(By.xpath("//input[contains(@placeholder,'Mobile')]| (//input[@class='formInput'])[2] | //input[contains(@placeholder,'Mobile')] | //input[contains(@placeholder,'mobile')] | //input[@name='phone']| //input[@placeholder='Enter telephone number'] |//input[contains(@id,'mobileNumber')]")).sendKeys(mobailno);
	}

	@Then("^send mail as \"([^\"]*)\"$")
	public void send_mail_as(String mail) throws Throwable {
		driver.findElement(By.xpath("//input[contains(@placeholder,'Email')] | //input[@placeholder='Enter email id'] | (//input[@class='formInput'])[3] | //input[contains(@id,'contactEmail')]")).sendKeys(mail);
	}
	
	@Then("^click on continue$")
	public void click_on_continue() throws Throwable {
		driver.findElement(By.xpath("//button[text()='Continue']")).sendKeys(Keys.ENTER);
	}
	
	@Then("^click on confirm$")
	public void click_on_confirm() throws Throwable {
		driver.findElement(By.xpath("//button[text()='CONFIRM']")).click();
	}

	@Then("^click on yes,please$")
	public void click_on_yes_please() throws Throwable {
		
		WebElement ele = driver.findElement(By.xpath("//button[text()='Yes, Please']"));
		
		try {
			ele.click();
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", ele);
		}
	}
	
	@When("^click on skip add on$")
	public void click_on_skip_add_on() throws Throwable {
	     WebElement ele =  driver.findElement(By.xpath("//span[text()='Skip to add-ons']"));
	     Actions ac = new Actions(driver);
	     ac.moveToElement(ele).click().build().perform();
	     
	}
		
	@Then("^system should display complete your booking page$")
	public void system_should_display_complete_your_booking_page() throws Throwable {
//		driver.findElement(By.xpath("//h2[text()='Complete your booking']"));
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Complete your booking'] | //h3[text()='Enter Traveller Details']")));	
	}

	@When("^click on proceed to pay$")
	public void click_on_proceed_to_pay() throws Throwable {
		
		WebElement ele = driver.findElement(By.xpath("//button[text()='Proceed to pay']| //button[contains(text(),'Proceed')] | //span[text()='Pay & Book Now']| //a[contains(text(),'Pay')] | //span[text()='Continue to Book Now']"));
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		try {
		     ele.click();
		  } 
		catch (Exception e) {
		     JavascriptExecutor executor = (JavascriptExecutor) driver;
		     executor.executeScript("arguments[0].click();", ele);
		  }
		
//		Actions ac = new Actions(driver);
//		try {
//			ac.moveToElement(ele).doubleClick().build().perform();
//			
//		} catch (Exception e) {
//			ac.moveToElement(ele).doubleClick().build().perform();
//		}
		
	}

	@Then("^system should display payment options page$")
	public void system_should_display_payment_options_page() throws Throwable {
//	   driver.findElement(By.xpath("//span[text()='Payment options']"));
	   
	   WebDriverWait wait = new WebDriverWait(driver, 20);
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Payment options']")));

	}
	
	@When("^click on googlepay$")
	public void click_on_googlepay() throws Throwable {
		WebElement ele = driver.findElement(By.xpath("//span[text()='GooglePay']"));
		
		Actions ac = new Actions(driver);
		ac.moveToElement(ele).click().build().perform();
	}

	@When("^enter upi id as \"([^\"]*)\"$")
	public void enter_upi_id_as(String UPIID) throws Throwable {
		driver.findElement(By.xpath("//input[@id='inputVpa']")).sendKeys(UPIID);
	}

	@When("^click on verify and pay$")
	public void click_on_verify_and_pay() throws Throwable {
		driver.findElement(By.xpath("//button[normalize-space()='verify & pay']")).click();
	}

	@Then("^user should able to book pay$")
	public void user_should_able_to_book_pay() throws Throwable {
		driver.findElement(By.xpath("//p[contains(@class,'red-text font11 append-top5')]"));
	}
	
	@Then("^take screenshot named as \"([^\"]*)\"$")
	public void take_screenshot_named_as(String name) throws Throwable {
		
//		WebDriverWait wait = new WebDriverWait(driver, 20);
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='customModalContent']")));
//	
		File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(f, new File("Screenshots\\"+name+".jpg"));
			
	}
		

	
	//---------------------------------------------------Hotels ---------------------------------------------------------//
	
	@When("^click on hotel$")
	public void click_on_hotel() throws Throwable {
		WebElement hotels = driver.findElement(By.xpath("(//a[contains(@href,'hotels')])[1]"));
		hotels.click();
	}

	@Then("^system should display hotels homepage$")
	public void system_should_display_hotels_homepage() throws Throwable {
		driver.findElement(By.xpath("//span[contains(text(),'UPTO 5 ROOMS')]"));
	}

	@Then("^select \"([^\"]*)\" rooms from list$")
	public void select_rooms_from_list(String rooms) throws Throwable {
		List<WebElement> no_rooms;
		
		driver.findElement(By.xpath("(//div[@class='gstSlct'])[1]")).click();
		no_rooms = driver.findElements(By.xpath("//ul[@class='gstSlct__list']//li"));
		
		for(WebElement ele:no_rooms) 
		{
			if(ele.getText().equalsIgnoreCase(rooms)) 
			{
				ele.click();
				break;
			}
		}
	}

	@Then("^chackout date as \"([^\"]*)\"$")
	public void chackout_date_as(String flydate) throws Throwable {
		String[] temp = flydate.split("-");//28-dec-2023
		String date = temp[0];
		String month = temp[1];
		String year = temp[2];

		String mmyy = month+" "+year;
		String mmyy1 = month+""+year;
		System.out.println(mmyy);
		System.out.println(mmyy1);

		String calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
		System.out.println(calyear);
		
		boolean res =  StringUtils.containsWhitespace(calyear);
		System.out.println(res);
		
		if(res) {
//			System.out.println("if running");
			while(!calyear.equalsIgnoreCase(mmyy))
			{		
				driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
				calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
			}
		}else {
//			System.out.println("else running");
			while(!calyear.equalsIgnoreCase(mmyy1))
			{
			driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
			calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
			}
			
		}

			//table
			WebElement table = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']"));
			java.util.List<WebElement> rows,cols;
			
			// row
			rows =  table.findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Week']"));//
			for(int i=0; i<rows.size(); i++) 
			{
				try {
					Thread.sleep(3000);
					//coloum
					cols = rows.get(i).findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='dateInnerCell']//p[1] | //div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Day']"));
					//System.out.println(cols);
					for(WebElement element : cols) 
						if(element.getText().equals(date)) 
						{
							System.out.println(element.getText());
							element.click();
//							Thread.sleep(2000);
							break;
						}

				} catch (StaleElementReferenceException e) {
					System.out.println(".");
				}
			}
			
	}

	@Then("^select room$")
	public void select_room() throws Throwable {
	   WebElement ele = driver.findElement(By.xpath("(//p[contains(text(),'SELECT')])[1] | (//span[contains(text(),'Select ')])[1]"));
	   
	   Actions ac = new Actions(driver);
	   ac.moveToElement(ele).click().build().perform();
	}

	@Then("^user should able to book hotel room$")
	public void user_should_able_to_book_hotel_room() throws Throwable {
	    driver.findElement(By.xpath("//p[contains(text(),'Sold Out')]"));
	    
	    ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,300)");
	    
	    
	    
	}
		
	//------------------------------------------------ Homestay --------------------------------------------------------//
	
	@When("^click on homestay$")
	public void click_on_homestay() throws Throwable {
		driver.findElement(By.xpath("(//a[contains(@href,'homestays')])[1]")).click();
	}

	@Then("^system should display homestay homepage$")
	public void system_should_display_homestay_homepage() throws Throwable {
		driver.findElement(By.linkText("Homestay - Villas, Apartments & more."));
	}

	@Then("^select \"([^\"]*)\" from travelling from$")
	public void select_from_travelling_from(String travel_For) throws Throwable {
		driver.findElement(By.xpath("//span[text()='Travelling For']")).click();
		List<WebElement> tf_ul, tf_li;
		tf_ul = driver.findElements(By.xpath("//ul[@class='travelForPopup']"));
		
		for(int i=0; i<tf_ul.size(); i++) {
			try {
				tf_li = tf_ul.get(i).findElements(By.tagName("li"));
				for(WebElement ele:tf_li) {
					if(ele.getText().equalsIgnoreCase(travel_For)) {
						ele.click();
					}
				}
			} catch (StaleElementReferenceException e) {
				System.out.println(".");
			}		
		}
	}
	
	//-------------------------------------------- Trains---------------------------------------------------------------//
	
	@When("^click on train$")
	public void click_on_train() throws Throwable {
		driver.findElement(By.xpath("(//a[contains(@href,'railways')])[1]")).click();
	}

	@Then("^system should display train homepage$")
	public void system_should_display_train_homepage() throws Throwable {
		driver.findElement(By.xpath("(//span[text()='Trains'])[1]"));
	}
	
	@Then("^select as \"([^\"]*)\" in get free cancellation$")
	public void select_as_in_get_free_cancellation(String cancellation) throws Throwable {
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[contains(text(),'Yes')])[1]")));
		
		
		List<WebElement> canl_ul, canl_li, data;
		canl_ul = driver.findElements(By.xpath("//ul[@class='getFc__list']"));
		for(int i=0; i<canl_ul.size(); i++) 
		{
			canl_li = canl_ul.get(i).findElements(By.tagName("li"));
//			System.out.println(canl_li.size());
			
			for(int j=0; j<canl_li.size(); j++) {
				data = canl_li.get(j).findElements(By.tagName("label"));
//				System.out.println(data.size());
				for(WebElement ele:data) {
//					System.out.println(ele.getText());
					if(ele.getText().contains(cancellation)) {
						ele.click();
						break;
					}
				}
			}
			
		}
		
	}

	@Then("^send age as \"([^\"]*)\"$")
	public void send_age_as(String age) throws Throwable {
		driver.findElement(By.xpath("//input[@id='age']")).sendKeys(age);
	}
	
	@Then("^select gender as \"([^\"]*)\" from list$")
	public void select_gender_as_from_list(String gender) throws Throwable {
		driver.findElement(By.xpath("//span[text()='Select']")).click();
		Thread.sleep(3000);
		List<WebElement> gen_ul, gen_li;
		gen_ul = driver.findElements(By.xpath("(//ul[contains(@class,'quotaBox  makeAbsolute textLeft font14 darkGreyText')])[2]"));
		for(int i=0; i<gen_ul.size(); i++) 
		{
			gen_li = gen_ul.get(i).findElements(By.xpath("//span[contains(@class,'appendRight30')]"));
//			System.out.println(gen_li.size());
			for(WebElement ele:gen_li) {
				if(ele.getText().equalsIgnoreCase(gender)) 
				{
					ele.click();
					break;
				}
			}
		}
	}
	
	@Then("^select berth as \"([^\"]*)\" from list$")
	public void select_berth_as_from_list(String berth) throws Throwable {
		driver.findElement(By.xpath("(//span[contains(@class,'arrow arrow-down-wide')])[4]")).click();
		List<WebElement> berth_ul, berth_li;
		berth_ul = driver.findElements(By.xpath("(//ul[contains(@class,'quotaBox  makeAbsolute textLeft font14 darkGreyText')])[4]"));
		for(int i=0; i<berth_ul.size(); i++) 
		{
			berth_li = berth_ul.get(i).findElements(By.tagName("li"));
//			System.out.println(berth_li.size());
			for(WebElement ele:berth_li) {
//				System.out.println(ele.getText());
				if(ele.getText().equalsIgnoreCase(berth)) {
					ele.click();
					break;
				}
			}
		}
	}

	@Then("^click on add$")
	public void click_on_add() throws Throwable {
		driver.findElement(By.xpath("//button[text()='Add']")).click();
	}

	@Then("^send irctc id name as \"([^\"]*)\"$")
	public void send_irctc_id_name_as(String irctcid) throws Throwable {
		driver.findElement(By.xpath("//a[text()='CHANGE']")).click();
		driver.findElement(By.xpath("//input[@id='IRCTCUserName']")).sendKeys(irctcid);
		driver.findElement(By.xpath("//span[text()='Submit']")).click();
	}	
	

	//--------------------------------------------- Bus -----------------------------------------------------------------//
	
	@When("^click on buses$")
	public void click_on_buses() throws Throwable {
		driver.findElement(By.xpath("(//a[contains(@href,'bus-tickets')])[1]")).click();
	}

	@Then("^system should display buses homepage$")
	public void system_should_display_buses_homepage() throws Throwable {
		 driver.findElement(By.xpath("(//a[contains(text(),'Bus Ticket')])[1]"));
	}
	
	@Then("^select any seat$")
	public void select_any_seat() throws Throwable {
//		driver.findElement(By.xpath("(//img[@class='seat-icon'])[2]")).click();
//		WebElement ele = driver.findElement(By.xpath("(//img[@class='seat-icon'])[2]"));
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//img[@class='seat-icon'])[2]")));
		
		WebElement ele = driver.findElement(By.xpath("(//img[@class='seat-icon'])[2]"));
		ele.click();
		
	}

	@Then("^select trip insurance as \"([^\"]*)\"$")
	public void select_trip_insurance_as(String arg1) throws Throwable {
		driver.findElement(By.xpath("(//span[@class='sc-kPVwWT hCyTgB'])[2]")).click();
	}
	
	@Then("^gender as \"([^\"]*)\" from list$")
	public void gender_as_from_list(String arg1) throws Throwable {
		driver.findElement(By.xpath("//span[contains(@class,'blackArrowIcon sprite appendLeft10 ')]")).click();
		
		WebElement male = driver.findElement(By.xpath("//span[contains(@class,'trdic-male-2x')]"));
		
		if(male.isDisplayed()) {
			male.click();
		}
		
	}

	//---------------------------------------------Cab ------------------------------------------------------------------//
	
	@When("^click on cab$")
	public void click_on_cab() throws Throwable {
		driver.findElement(By.xpath("(//a[contains(@href,'cabs')])[1]")).click();
	}

	@Then("^system should display cab homepage$")
	public void system_should_display_cab_homepage() throws Throwable {
		driver.findElement(By.linkText("Cab Booking"));
	}

	@Then("^pickup time as  as \"([^\"]*)\"$")
	public void pickup_time_as_as(String time) throws Throwable {
		try {
			if(driver.findElement(By.xpath("//span[contains(text(),'PICKUP-TIME')]")).isDisplayed())
			{
				System.out.println("try running ");
				driver.findElement(By.xpath("(//span[contains(@class,'lbl_input latoBold appendBottom10')])[3]")).click();

				List<WebElement> bookingTimes, tli;
				bookingTimes = driver.findElements(By.xpath("//ul[contains(@class,'timeDropDown blackText')]"));
				for(int i=0; i<bookingTimes.size(); i++) 
				{ 
					tli = bookingTimes.get(i).findElements(By.tagName("li"));
					for(WebElement ele : tli){
						try {
							if(ele.getText().equalsIgnoreCase(time)){
								try{
									System.out.println(ele.getText());
									ele.click();
									break;
								} 
								catch (Exception e){
									JavascriptExecutor executor = (JavascriptExecutor) driver;
									executor.executeScript("arguments[0].click();", ele);
									break;
								}
							}
						} catch (Exception e) {
							System.out.println(".");
						}
					}
				}
			}
		} catch (Exception e) {

				System.out.println("else try running ");
//				int ti=Integer.parseInt(time) ;
				List<WebElement> bookingTimes, tli;
				bookingTimes = driver.findElements(By.xpath("//ul[contains(@class,'timeDropDown blackText')]//li | //ul[@class='timeList']"));
				for(int i=0; i<bookingTimes.size(); i++) {
					tli = bookingTimes.get(i).findElements(By.tagName("li"));
//					System.out.println(tli.size());
					for(WebElement ele:tli) {
						if(ele.getText().contains(time)) {
							try{
								System.out.println(ele.getText());
								ele.click();
								break;
							} 
							catch (Exception er){
								JavascriptExecutor executor = (JavascriptExecutor) driver;
								executor.executeScript("arguments[0].click();", ele);
								break;
							}
						}
					}
					}
				}
	}
		
	@Then("^send pick-up addreess as \"([^\"]*)\"$")
	public void send_pick_up_addreess_as(String pickup) throws Throwable {
		WebElement ele = driver.findElement(By.xpath("(//input[@class='inputBox '])[1]"));
		try {
			ele.click();
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", ele);
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@placeholder='Address']")).sendKeys(pickup);

		WebElement suggest = driver.findElement(By.xpath("//p[text()='SEARCH RESULTS']"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(suggest));

		driver.findElement(By.xpath("(//span[contains(@class,'sr_iata grayText latoBold')])[1]")).click();
	}

	@Then("^select gender as \"([^\"]*)\" from options$")
	public void select_gender_as_from_options(String gender) throws Throwable {
		String malegen = driver.findElement(By.xpath("//span[text()='Male']")).getText();
		String femalegen = driver.findElement(By.xpath("//span[text()='Female']")).getText();

		WebElement mradio = 	driver.findElement(By.xpath("(//div[@class='radioUnchecked'])[1]"));
		WebElement fradio = 	driver.findElement(By.xpath("(//div[@class='radioUnchecked'])[2]"));


		if(malegen.equalsIgnoreCase(gender)) {
			if(!mradio.isSelected()) {
				mradio.click();
			}
		}

		if(femalegen.equalsIgnoreCase(gender)) {
			if(!fradio.isSelected()) {
				fradio.click();
			}
		}
	}
	
	
	
	//---------------------------------------- charterflight-------------------------------------------------------------//
	
	
	@When("^click on charterflight$")
	public void click_on_charterflight() throws Throwable {
//		driver.findElement(By.xpath("(//a[contains(@href,'charter')])[1]")).click();
		
		
//		Utilies.findElement(driver, "(//a[contains(@href,'charter')])[1]");
	}
	
	
	

	@Then("^system should display charterflight homepage$")
	public void system_should_display_charterflight_homepage() throws Throwable {
		driver.findElement(By.xpath("//h1[text()='Book Charter Planes in India']"));
	}
	
	@Then("^click on Get call back$")
	public void click_on_Get_call_back() throws Throwable {
		WebElement ele = driver.findElement(By.xpath("//span[text()='GET A CALLBACK']"));
		
		try {
			ele.click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
		     executor.executeScript("arguments[0].click();", ele);
		}
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='customModalContent']")));
	
	
	}
	
	
	
	
	@When("click on Go to homepage")
	public void goToHomepage() throws Throwable {
		driver.findElement(By.xpath("//button[text()='GO TO HOME PAGE']")).click();
	}
	
	//-------------------------------------- Activities------------------------------------------------------------------//
	
	@When("^click on Activities$")
	public void click_on_Activities() throws Throwable {
		driver.findElement(By.xpath("(//a[contains(@href,'activities')])[1]")).click();
	}

	
	@Then("^system should display Activities homepage$")
	public void system_should_display_Activities_homepage() throws Throwable {
		driver.findElement(By.xpath("//span[@class='appendBottom10 font14 capText']"));
	}
	

	@Then("^enter destinstion as \"([^\"]*)\"$")
	public void enter_destinstion_as(String location) throws Throwable {
		driver.findElement(By.xpath("//span[@class='appendBottom10 font14 capText']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//input[contains(@placeholder,'Activities, Tours or Destination')])[2]")).sendKeys(location);
		
		List<WebElement> sugg_ul, sugg_li;
		sugg_ul = driver.findElements(By.xpath("(//div[@id='react-autowhatever-1']//ul)[1]"));
		for(int i=0; i<sugg_ul.size(); i++) {
			sugg_li = sugg_ul.get(i).findElements(By.xpath("(//div[@id='react-autowhatever-1']//ul)[1]//b"));
			for(WebElement ele:sugg_li) {
				System.out.println(ele.getText());
				if(ele.getText().contains(location)){
					//					System.out.println(ele);
					ele.click();
					break;
				}
			}
		}
	}
	
	@When("^Click on any \"([^\"]*)\"$")
	public void click_on_any(String hotspot) throws Throwable {
		List<WebElement> ul, li;
		ul = driver.findElements(By.xpath("//ul[@class='tags-list']"));
		for(int i=0; i<ul.size(); i++) {
			li = ul.get(i).findElements(By.xpath("//ul[@class='tags-list']//li//p"));
			for(WebElement ele:li) {
				try {
					if(ele.getText().toLowerCase().contains(hotspot)) 
					{
						ele.click();
						break;
					}				
				} catch (Exception e) {				
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", ele);
					break;
				}				
			}
		}
	}

	@Then("^System should display list of activity packs$")
	public void system_should_display_list_of_activity_packs() throws Throwable {
	   WebDriverWait wait = new WebDriverWait(driver, 20);
	   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'listing-card-container')]")));		
	}

	@When("^Click on any pack$")
	public void click_on_any_pack() throws Throwable {
		driver.findElement(By.xpath("(//div[contains(@class,'listing-card-container')])[1]")).click();
	}

	@Then("^add \"([^\"]*)\" person$")
	public void add_person(int person) throws Throwable {
		WebElement plus;
		plus = driver.findElement(By.xpath("(//div[contains(@class,'invent-unit-q-change-box')])[1]//button[2]"));


		for(int i=1; i<person; i++) {
			try {
				plus.click();
				break;
			} catch (Exception e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", plus);
			}
		}
	}

	@Then("^select \"([^\"]*)\" as Hotel pickup details$")
	public void select_as_Hotel_pickup_details(String opt) throws Throwable {
		
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Hotel Pickup Details')]")));
//		
		try {
			System.out.println("try running");
			List<WebElement> form_ul, form_li;
			
			form_ul = driver.findElements(By.xpath("//ul[@class='FormRowCol select-tab']"));	
			for(int i=0; i<form_ul.size(); i++) {
				form_li = form_ul.get(i).findElements(By.tagName("li"));
				for(WebElement ele:form_li) {
					System.out.println(ele.getText());
					if(ele.getText().toLowerCase().contains(opt)) 
					{
						
						try {
							System.out.println(ele.getText());
							ele.click();
							break;
						} catch (Exception e) {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].Click()", ele);
							break;
						}
						
					}
				}			
			}
		} catch (Exception e) {
			driver.findElement(By.xpath("//textarea[@class='tvlrInput']")).sendKeys(opt);
		}
	}
	
	@Then("^click on procced$")
	public void click_on_procced() throws Throwable {
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   driver.findElement(By.xpath("//button[text()='Save & Proceed']")).click();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
//	public static void clickElement(String xpath) throws Throwable {
//		System.out.println(xpath);
//		driver.findElement(By.xpath(xpath)).click();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}