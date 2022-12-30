package stepDefination;

//import java.io.File;
import java.util.List;
//import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

//import javax.xml.xpath.XPath;
//import Apputiles.*;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
//import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.google.common.collect.Lists;
//import com.google.common.io.Files;

import Apputiles.Utilies;
//import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonLibrery1 extends Utilies {
	
	static WebDriver driver;
	
	@Given("^open chrome browser with \"([^\"]*)\" url$")
	public void openBrowser(String url) throws Throwable {
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
	public void homepage() throws Throwable {
		findElement(driver, "xpath", "(//a[contains(@href,'flights')])[1]");

	}

	@Then("^select \"([^\"]*)\" trip$")
	public void trip(String arg1) throws Throwable {
		
		clickAction(driver, "xpath", "//li[text()='OneWay']");
		
	}

	@Then("^select from city as \"([^\"]*)\"$")
	public void fromCity(String cityname) throws Throwable {
		
		clickAction(driver, "xpath", "//span[text()='From']	| //span[contains(text(),'City, Property name or Location')]");
		
		sendValue(driver, "//input[@placeholder='From'] | //input[@placeholder='Enter city/ Hotel/ Area/ Building']", cityname);
		
		
		java.util.List<WebElement> sugg_ul, sugg_li;
		sugg_ul = driver.findElements(By.xpath("//div[contains(@class,'react-autosuggest__section-container--first')]//ul"));
		for(int i=0; i<sugg_ul.size(); i++) 
		{	
			sugg_li = sugg_ul.get(i).findElements(By.xpath("//div[contains(@class,'section-container--first')]//li//p[1]"));
			for(WebElement ele:sugg_li) 
			{
				if(ele.getText().contains(cityname)) 
				{
					ele.click();
					break;
				}
			}			
		}
	
	}

	@Then("^select to city as \"([^\"]*)\"$")
	public void toCity(String to) throws Throwable {
		try {
			System.out.println("try running");
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			sendValue(driver, "//input[@placeholder='To'] | //input[@placeholder='Enter city/ Hotel/ Area/ Building']", to);
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
						ele.click();
						break;
					}
				}			
			}
		
		} catch (Exception e) {
			Utilies.clickAction(driver, "xpath", "//span[text()='To'] | //input[@placeholder='To']");
			
			
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			Utilies.sendValue(driver, "//input[@placeholder='To']", to);
			
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
	public void departureDate(String flydate) throws Throwable {
		
		try {
			
			String[] temp = flydate.split("-");//28-dec-2023
			String date = temp[0];
			String month = temp[1];
			String year = temp[2];

			String mmyy = month+" "+year;
			String mmyy1 = month+""+year;
			System.out.println(mmyy);

			String calyear = findElement(driver, "xpath", "(//div[@class='DayPicker-Caption'])[1]//div").getText();
//			System.out.println(calyear);
			
			boolean res =  StringUtils.containsWhitespace(calyear);
//			System.out.println(res);
			
			if(res) {
//				System.out.println("if running");
				while(!calyear.equalsIgnoreCase(mmyy))
				{		
					Utilies.clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
			}else {
//				System.out.println("else running");
				while(!calyear.equalsIgnoreCase(mmyy1))
				{
					Utilies.clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");

				calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
				
			}

				//table
				WebElement table = findElement(driver, "xpath", "//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']");
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
			WebElement ele = findElement(driver, "xpath", "//span[text()='TRAVEL DATE'] | //span[text()='DEPARTURE Date & time'] |//span[text()='DEPARTURE']");
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
//			
			String calyear = findElement(driver, "xpath", "(//div[@class='DayPicker-Caption'])[1]//div").getText();
//			System.out.println(calyear);

			boolean res =  StringUtils.containsWhitespace(calyear);
//			System.out.println(res);

			if(res) {
				//				System.out.println("if running");
				while(!calyear.equalsIgnoreCase(mmyy))
				{		
					clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");
					calyear = findElement(driver, "xpath", "(//div[@class='DayPicker-Caption'])[1]//div").getText();
				}
			}else {
				//				System.out.println("else running");
				while(!calyear.equalsIgnoreCase(mmyy1))
				{
					clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");
					calyear = findElement(driver, "xpath", "(//div[@class='DayPicker-Caption'])[1]//div").getText();
				}
			}

			//table
			WebElement table = findElement(driver, "xpath", "//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']");
			
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
			driver.navigate().refresh();
				
		}
		
		
		
	}

	@Then("^click on traveller$")
	public void traveller() throws Throwable {
		Utilies.clickAction(driver, "xpath","//label[@for='travellers']" );
	}

	@Then("^select \"([^\"]*)\" adults from list$")
	public void adults(String adult) throws Throwable {
		try {
			if(findElement(driver, "xpath", "(//div[@class='gstSlct'])[2]").isDisplayed())
			{
				Utilies.clickAction(driver, "xpath", "(//div[@class='gstSlct'])[2]");
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
	public void childrens(String children) throws Throwable {
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
	public void infants(String infants) throws Throwable {
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
	public void travellerClass() throws Throwable {
		clickAction(driver, "xpath", "//span[@class='appendRight10'] | //span[text()='Class']");
	}
		
	@Then("^select \"([^\"]*)\" class from list$")
	public void selectClass(String cls) throws Throwable {
		
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

//			 driver.findElement(By.xpath("//span[text()='Class']")).click();
			 clickAction(driver, "xpath", "//span[text()='Class']");
			
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
	public void applyButton() throws Throwable {
		
		clickAction(driver, "xpath", "//button[text()='APPLY'] | //button[text()='Apply']");
	
	}
	
	@When("click on Search button")
	public void serchButton() throws Throwable {
		
		waitForElement(driver, 20, "//a[text()='Search'] | //button[text()='Search']");
		clickAction(driver, "xpath", "//a[text()='Search'] | //button[text()='Search']");
		
	}
	
	@Then("^system should display list of flights$")
	public void listFlights() throws Throwable {
		
		waitForElement(driver, 20, "//span[@class='bgProperties icon20 overlayCrossIcon']");
	}
	
	@Then("^system should display one pop-up$")
	public void popUp() throws Throwable {
		
		waitForElement(driver, 20, "//div[@class='commonOverlay '] | //p[contains(text(),'additional information')] | //div[@class='commonOverlay ']");
		}

	@Then("^close that pop-up$")
	public void closePopUp() throws Throwable {
		
		waitForElement(driver, 20, "//span[@class='bgProperties icon20 overlayCrossIcon'] | //img[@class='primoCloseIcon']");
		clickAction(driver, "xpath", "//span[@class='bgProperties icon20 overlayCrossIcon'] | //img[@class='primoCloseIcon']");

	
	}
	
	@Then("^system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs$")	
	public void filghtes_hotels_homestays_trains_Buses_Cabs() throws Throwable {
		
		waitForElement(driver, 20, "(//span[@class='appendRight8'])[1] | //span[text()='Activities'] | //div[contains(@id,'Listing_hotel')] | //div[@class='bus-card'] | //span[text()='Activities'] |//div[contains(@class,'cabListingTileWrapper blackText')] |//div[contains(@class,'single-train-detail')]");
		
	}

	@Then("^select anyone from list & click on View price$")
	public void selectFromList() throws Throwable {
		
		waitForElement(driver, 20, "(//span[@class='appendRight8'])[1] | (//div[@id='Listing_hotel_1'])[1] | (//div[contains(@class,'card ')])[1] | (//a[@data-test-id='select-seats'])[1]");
		clickAction(driver, "xpath", "(//span[@class='appendRight8'])[1] | (//div[@id='Listing_hotel_1'])[1] | (//div[contains(@class,'card ')])[1] | (//a[@data-test-id='select-seats'])[1]");
	}

	@Then("^click on Book Now$")
	public void bookNow() throws Throwable {
		
		waitForElement(driver, 20, "(//button[text()='Book Now'])[1] | (//button[text()='BOOK NOW'])[1] | (//span[text()='BOOK NOW'])[1] | (//span[text()='BOOK SEATS'])[1]");
		clickAction(driver, "xpath", "(//button[text()='Book Now'])[1] | (//button[text()='BOOK NOW'])[1] |(//span[text()='BOOK NOW'])[1] | (//span[text()='BOOK SEATS'])[1]");
		
	}

	@Then("^system should open new tab$")
	public void newTab() throws Throwable {
		changeTab(driver, 1);
	}

	@Then("^system should display Traveller Details page$")
	public void travellerDetailspage() throws Throwable {
		
		waitForElement(driver, 20, "//h2[text()='Traveller Details']| //h2[text()='Customer Information'] | //p[text()='Confirm Traveller information'] | //h3[text()='Enter Traveller Details']");
	}
	
	@Then("^scroll upto \"([^\"]*)\"$")
	public void scrollUpto(String element) throws Throwable {
		
		scrollDown(driver, element);
		
	}

	@Then("^click on add new adult$")
	public void addNewAdult() throws Throwable {
		
		waitForElement(driver, 20, "//button[normalize-space()='+ ADD NEW ADULT'] | //span[normalize-space()='Add Traveller']");

		if(driver.findElement(By.xpath("//button[normalize-space()='+ ADD NEW ADULT'] | //span[normalize-space()='Add Traveller']")).isDisplayed()) 
		{
			clickAction(driver, "xpath", "//button[normalize-space()='+ ADD NEW ADULT'] | //span[normalize-space()='Add Traveller']");
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Then("^send first name as \"([^\"]*)\"$")
	public void firstName(String f_name) throws Throwable {
		
		sendValue(driver, "(//input[@placeholder='First & Middle Name'])[1]| //input[@placeholder='Enter first name'] |(//input[@class='formInput'])[1] | //input[@placeholder='Enter your full name'] |//input[@id='fname'] |(//input[@placeholder='Enter Traveller Name'])[1]",f_name);
	}

	@Then("^send last name as \"([^\"]*)\"$")
	public void lastName(String l_name) throws Throwable {
		
		sendValue(driver, "(//input[@placeholder='Last Name'])[1] | //input[@placeholder='Enter last name']",l_name);
	}

	@Then("^gender as \"([^\"]*)\"$")
	public void gender(String gender) throws Throwable {
		
		WebElement m = findElement(driver, "xpath", "(//label[@tabindex='0'])[1]");
		String ml = findElement(driver, "xpath", "(//span[@class='selectTabText'])[1]").getText();
		if(ml.equalsIgnoreCase(gender)) 
		{
			if(!m.isSelected()) 
			{
				m.click();
			}
		}
		WebElement f = findElement(driver, "xpath", "(//label[@tabindex='1'])");
		String fm = findElement(driver, "xpath", "(//span[@class='selectTabText'])[2]").getText();
		if(fm.equalsIgnoreCase(gender)) 
		{
			if(!f.isSelected()) 
			{
				f.click();
			}
		}	
	}
	
	@Then("^send mobile no as \"([^\"]*)\"$")
	public void mobileNo(String mobailno) throws Throwable {
		
		sendValue(driver, "//input[contains(@placeholder,'Mobile')]| (//input[@class='formInput'])[2] | //input[contains(@placeholder,'Mobile')] | //input[contains(@placeholder,'mobile')] | //input[@name='phone']| //input[@placeholder='Enter telephone number'] |//input[contains(@id,'mobileNumber')]",mobailno);
	}

	@Then("^send mail as \"([^\"]*)\"$")
	public void mail(String mail) throws Throwable {
		sendValue(driver, "//input[contains(@placeholder,'Email')] | //input[@placeholder='Enter email id'] | (//input[@class='formInput'])[3] | //input[contains(@id,'contactEmail')]", mail);
	}
	
	@Then("^click on continue$")
	public void continueButton() throws Throwable {
		
		waitForElement(driver, 20, "//button[text()='Continue']");
		clickAction(driver, "xpath", "//button[text()='Continue']");

	}
	
	@Then("^click on confirm$")
	public void confirm() throws Throwable {
		clickAction(driver, "xpath", "//button[text()='CONFIRM']");
	}

	@Then("^click on yes,please$")
	public void yesPlease() throws Throwable {
		
		waitForElement(driver, 20, "//button[text()='Yes, Please']");
		clickAction(driver, "xpath", "//button[text()='Yes, Please']");
		
	}
	
	@When("^click on skip add on$")
	public void skipAddOn() throws Throwable {
		
		waitForElement(driver, 20, "//span[text()='Skip to add-ons']");
		clickAction(driver, "xpath", "//span[text()='Skip to add-ons']");
     
	}
		
	@Then("^system should display complete your booking page$")
	public void completeYourBookingPage() throws Throwable {

		waitForElement(driver, 20, "//h2[text()='Complete your booking'] | //h3[text()='Enter Traveller Details']");
			
	}

	@When("^click on proceed to pay$")
	public void proceedToPay() throws Throwable {
		
		try {
			waitForElement(driver, 40, "//button[text()='Proceed to pay']| //button[contains(text(),'Proceed')] | //span[text()='Pay & Book Now']| //a[contains(text(),'Pay')] | //span[text()='Continue to Book Now']");
			clickAction(driver, "xpath", "//button[text()='Proceed to pay']| //button[contains(text(),'Proceed')] | //span[text()='Pay & Book Now']| //a[contains(text(),'Pay')] | //span[text()='Continue to Book Now']");
		} catch (Exception e) {
			waitForClickable(driver, 40, "//button[text()='Proceed to pay']| //button[contains(text(),'Proceed')] | //span[text()='Pay & Book Now']| //a[contains(text(),'Pay')] | //span[text()='Continue to Book Now']");
			clickAction(driver, "xpath", "//button[text()='Proceed to pay']| //button[contains(text(),'Proceed')] | //span[text()='Pay & Book Now']| //a[contains(text(),'Pay')] | //span[text()='Continue to Book Now']");
		}
	
	}

	@Then("^system should display payment options page$")
	public void paymentOptionsPage() throws Throwable {
		
		waitForElement(driver, 20, "//span[text()='Payment options']");
	}
	
	@When("^click on googlepay$")
	public void Googlepay() throws Throwable {
		
		waitForElement(driver, 20, "//span[text()='GooglePay']");
		moveToElementAndClick(driver, "//span[text()='GooglePay']");
		
	}

	@When("^enter upi id as \"([^\"]*)\"$")
	public void upiId(String UPIID) throws Throwable {
		
		sendValue(driver, "//input[@id='inputVpa']", UPIID);
	}

	@When("^click on verify and pay$")
	public void verify_N_Pay() throws Throwable {
		clickAction(driver, "xpath", "//button[normalize-space()='verify & pay']");
	}

	@Then("^user should able to book pay$")
	public void ableToPay() throws Throwable {
		findElement(driver, "xpath", "//p[contains(@class,'red-text font11 append-top5')]");
	}
	
	@Then("^take screenshot named as \"([^\"]*)\"$")
	public void takeScreenshot(String name) throws Throwable {
		
		takeScreenshot(driver, name);
	}
	
	@Then("^close the browser$")
	public void closeBrowser() throws Throwable {
	    
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.quit();
	}


	
	//---------------------------------------------------Hotels ---------------------------------------------------------//
	
	@When("^click on hotel$")
	public void hotelClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'hotels')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'hotels')])[1]");
	}

	@Then("^system should display hotels homepage$")
	public void hotelsHomepage() throws Throwable {
		waitForElement(driver, 30, "//span[contains(text(),'UPTO 5 ROOMS')]");
	}

	@Then("^select \"([^\"]*)\" rooms from list$")
	public void selectRooms(String rooms) throws Throwable {
		waitForElement(driver, 40, "(//div[@class='gstSlct'])[1]");
		
		List<WebElement> no_rooms;
		
		clickAction(driver, "xpath", "(//div[@class='gstSlct'])[1]");
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
	public void chackoutDate(String flydate) throws Throwable {
		String[] temp = flydate.split("-");//28-dec-2023
		String date = temp[0];
		String month = temp[1];
		String year = temp[2];

		String mmyy = month+" "+year;
		String mmyy1 = month+""+year;
		System.out.println(mmyy);
		System.out.println(mmyy1);
		
		String calyear = findElement(driver, "xpath", "(//div[@class='DayPicker-Caption'])[1]//div").getText();
		
		boolean res =  StringUtils.containsWhitespace(calyear);
//		System.out.println(res);
		
		if(res) {
//			System.out.println("if running");
			while(!calyear.equalsIgnoreCase(mmyy))
			{		
				clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");
				calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
			}
		}else {
//			System.out.println("else running");
			while(!calyear.equalsIgnoreCase(mmyy1))
			{
				clickAction(driver, "xpath", "(//span[@tabindex=0])[2]");
				calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
			}
			
		}

			//table
			WebElement table = findElement(driver, "xpath", "//div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Body']");
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
							break;
						}

				} catch (StaleElementReferenceException e) {
					System.out.println(".");
				}
			}
			
	}

	@Then("^select room$")
	public void select_room() throws Throwable {
		waitForElement(driver, 60, "(//p[contains(text(),'SELECT')])[1] | (//span[contains(text(),'Select ')])[1]");
		moveToElementAndClick(driver, "(//p[contains(text(),'SELECT')])[1] | (//span[contains(text(),'Select ')])[1]");
	   
	}

	@Then("^user should able to book hotel room$")
	public void bookHotelRoom() throws Throwable {
		findElement(driver, "xpath", "//p[contains(text(),'Sold Out')]");
	   ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,300)");
	    
	    
	    
	}
		
	//------------------------------------------------ Homestay --------------------------------------------------------//
	
	@When("^click on homestay$")
	public void homestayClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'homestays')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'homestays')])[1]");
	}

	@Then("^system should display homestay homepage$")
	public void homestayHomepage() throws Throwable {
		waitForElement(driver, 30, "//a[text()='Homestay - Villas, Apartments & more.']");
	}

	@Then("^select \"([^\"]*)\" from travelling from$")
	public void travellingFor(String travel_For) throws Throwable {
		
		waitForElement(driver, 30, "//span[text()='Travelling For']");
		clickAction(driver, "xpath", "//span[text()='Travelling For']");

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
	public void trainClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'railways')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'railways')])[1]");
	}

	@Then("^system should display train homepage$")
	public void trainHomepage() throws Throwable {
		waitForElement(driver, 30, "(//span[text()='Trains'])[1]");
	}
	
	@Then("^select as \"([^\"]*)\" in get free cancellation$")
	public void freeCancellation(String cancellation) throws Throwable {
		
		waitForElement(driver, 30, "(//label[contains(text(),'Yes')])[1]");
		
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
	public void age(String age) throws Throwable {
		waitForElement(driver, 30, "//input[@id='age']");
		sendValue(driver, "//input[@id='age']", age);
	}
	
	@Then("^select gender as \"([^\"]*)\" from list$")
	public void gender1(String gender) throws Throwable {
		waitForElement(driver, 30, "//span[text()='Select']");
		clickAction(driver, "xpath", "//span[text()='Select']");
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
	public void selectBerth(String berth) throws Throwable {
		
		waitForElement(driver, 30, "(//span[contains(@class,'arrow arrow-down-wide')])[4]");
		clickAction(driver, "xpath", "(//span[contains(@class,'arrow arrow-down-wide')])[4]");
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
	public void add() throws Throwable {
		waitForElement(driver, 30, "//button[text()='Add']");
		clickAction(driver, "xpath", "//button[text()='Add']");
	}

	@Then("^send irctc id name as \"([^\"]*)\"$")
	public void irctcId(String irctcid) throws Throwable {
		waitForElement(driver, 30, "//a[text()='CHANGE']");
		clickAction(driver, "xpath","//a[text()='CHANGE']" );
		sendValue(driver, "//input[@id='IRCTCUserName']", irctcid);
		clickAction(driver, "xpath", "//span[text()='Submit']");
	}	
	

	//--------------------------------------------- Bus -----------------------------------------------------------------//
	
	@When("^click on buses$")
	public void busesClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'bus-tickets')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'bus-tickets')])[1]");
	}

	@Then("^system should display buses homepage$")
	public void busesHomepage() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(text(),'Bus Ticket')])[1]");
	}
	
	@Then("^select any seat$")
	public void selectSeat() throws Throwable {
		waitForElement(driver, 30, "(//img[@class='seat-icon'])[2]");
		clickAction(driver, "xpath", "(//img[@class='seat-icon'])[2]");
		
	}

	@Then("^select trip insurance as \"([^\"]*)\"$")
	public void insurance(String arg1) throws Throwable {
		waitForElement(driver, 30, "(//span[@class='sc-kPVwWT hCyTgB'])[2]");
		clickAction(driver, "xpath", "(//span[@class='sc-kPVwWT hCyTgB'])[2]");
	}
	
	@Then("^gender as \"([^\"]*)\" from list$")
	public void gender2(String arg1) throws Throwable {
		waitForElement(driver, 30, "//span[contains(@class,'blackArrowIcon sprite appendLeft10 ')]");
		clickAction(driver, "xpath", "//span[contains(@class,'blackArrowIcon sprite appendLeft10 ')]");
		
		WebElement male = findElement(driver, "xpath", "//span[contains(@class,'trdic-male-2x')]");
		
		if(male.isDisplayed()) {
			male.click();
		}
		
	}

	//---------------------------------------------Cab ------------------------------------------------------------------//
	
	@When("^click on cab$")
	public void cabClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'cabs')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'cabs')])[1]");
	}

	@Then("^system should display cab homepage$")
	public void cabHomepage() throws Throwable {
		waitForElement(driver, 30, "//li[text()='Hourly Rentals']");
	}

	@Then("^pickup time as  as \"([^\"]*)\"$")
	public void pickupTime(String time) throws Throwable {
		try {
			if(findElement(driver, "xpath", "//span[contains(text(),'PICKUP-TIME')]").isDisplayed())
			{
				System.out.println("try running ");
				clickAction(driver, "xpath", "(//span[contains(@class,'lbl_input latoBold appendBottom10')])[3]");

				List<WebElement> bookingTimes, tli;
				bookingTimes = driver.findElements(By.xpath("//ul[contains(@class,'timeDropDown blackText')]"));
				for(int i=0; i<bookingTimes.size(); i++) 
				{ 
					tli = bookingTimes.get(i).findElements(By.tagName("li"));
					for(WebElement ele : tli){
						try {
							if(ele.getText().equalsIgnoreCase(time)){
								try{
//									System.out.println(ele.getText());
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
//								System.out.println(ele.getText());
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
	public void pickupAddreess(String pickup) throws Throwable {
		
		waitForElement(driver, 30, "(//input[@class='inputBox '])[1]");
		clickAction(driver, "xpath", "(//input[@class='inputBox '])[1]");
		
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		sendValue(driver, "//input[@placeholder='Address']", pickup);
		
		waitForElement(driver, 30, "//p[text()='SEARCH RESULTS']");

		clickAction(driver, "xpath", "(//span[contains(@class,'sr_iata grayText latoBold')])[1]");
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
	public void charterflightClick() throws Throwable {
		waitForElement(driver, 30, "(//a[contains(@href,'charter')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'charter')])[1]");
	}


	@Then("^system should display charterflight homepage$")
	public void charterflightHomepage() throws Throwable {
		clickAction(driver, "xpath", "//h1[text()='Book Charter Planes in India']");
	}
	
	@Then("^click on Get call back$")
	public void GetCallBack() throws Throwable {
		
		waitForElement(driver, 30, "//span[text()='GET A CALLBACK']");
		clickAction(driver, "xpath", "//span[text()='GET A CALLBACK']");
		waitForElement(driver, 30, "//div[@class='customModalContent']");

	}
	
	
	
	
	@When("click on Go to homepage")
	public void goToHomepage() throws Throwable {
		driver.findElement(By.xpath("//button[text()='GO TO HOME PAGE']")).click();
	}
	
	//-------------------------------------- Activities------------------------------------------------------------------//
	
	@When("^click on Activities$")
	public void ActivitiesClick() throws Throwable {
		
		waitForElement(driver, 30, "(//a[contains(@href,'activities')])[1]");
		clickAction(driver, "xpath", "(//a[contains(@href,'activities')])[1]");
	}

	
	@Then("^system should display Activities homepage$")
	public void activitiesHomepage() throws Throwable {
		
		waitForElement(driver, 30, "//span[@class='appendBottom10 font14 capText']");
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
	public void pack() throws Throwable {
		waitForElement(driver, 30, "(//div[contains(@class,'listing-card-container')])[1]");
		clickAction(driver, "xpath", "(//div[contains(@class,'listing-card-container')])[1]");
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
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}