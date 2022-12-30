package Apputiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class Utilies {
	
//	WebDriver driver;
	
	public static void clickAction(WebDriver driver, String LocatorType, String LocatorValue) {
//		System.out.println(LocatorValue);
//		driver.findElement(By.xpath(LocatorValue)).click();
		
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			try {
				driver.findElement(By.xpath(LocatorValue)).click();
				
			}catch (Exception e) {
				driver.findElement(By.xpath(LocatorValue)).sendKeys(Keys.ENTER);
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				js.executeScript("arguments[0].click()", driver.findElement(By.xpath(LocatorValue)));
			}
			
			
			
		}
		else if(LocatorType.equalsIgnoreCase("linktext")) 
		{
			driver.findElement(By.linkText(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		else {
			System.out.println("Unable to execute click action");
		}
		

	}
	
	public static void waitForClickable(WebDriver driver, int sec, String LocatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, sec);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LocatorValue)));
	}
	
	public static void waitForElement(WebDriver driver, int sec, String LocatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, sec);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
	
	public static void sendValue(WebDriver driver, String LocatorValue, String value ) {
		
		driver.findElement(By.xpath(LocatorValue)).sendKeys(value);
		
	}
	
	public static WebElement findElement(WebDriver driver, String LocatorType, String LocatorValue) {
		
		return driver.findElement(By.xpath(LocatorValue));
		
		
	}
	
	public static void changeTab(WebDriver driver, int tab) {
		 List<String> browserTab = Lists.newArrayList(driver.getWindowHandles());
		 driver.switchTo().window(browserTab.get(tab));
	}

	public static void scrollDown(WebDriver driver, String element) throws Throwable {
		WebElement scroll = driver.findElement(By.xpath("//h3[text()='"+element+"']| //h2[text()='"+element+"'] | //span[text()='"+element+"'] | //button[text()='"+element+"'] |//p[contains(text(),'"+element+"')]"));
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView()",scroll);
			Thread.sleep(3000);
			
			
		} catch (NoSuchElementException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
			Thread.sleep(3000);
		}
	}
	
	public static void moveToElementAndClick(WebDriver driver, String LocatorValue) {
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath(LocatorValue))).click().build().perform();
	}
	
	public static void takeScreenshot(WebDriver driver, String name) throws Throwable {
		File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(f, new File("Screenshots\\"+name+".jpg"));
	}
	
	//	public static void findElement(WebDriver driver, )
	
	
	
	
	
	
	

	
	
	

	
	
	
	
}
