package tiktzuki.selenium_grid.hybridframework.operation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UIOperation {	
	WebDriver driver;
	public UIOperation(WebDriver driver) {
		this.driver = driver;
	}
	
	public void perform(Properties webObjectRepository, String operation, String objectName, String objectType, String value) throws NumberFormatException, InterruptedException {
		switch (operation.toUpperCase()) {
		case "NAVIGATE" :
			driver.navigate().to(value);
			break;
		case "CLICK":
			driver.findElement(getObject(webObjectRepository, objectName, objectType)).click();
			break;
		case "SENDKEYS":
			driver.findElement(getObject(webObjectRepository, objectName, objectType)).sendKeys(value);
			break;
		case "GET_ALERT":
			driver.switchTo().alert();
			break;
		case "ACCEPT_ALERT":
			driver.switchTo().alert().accept();
			break;
		case "DIMISS_ALERT":
			driver.switchTo().alert().dismiss();
			break;		
		case "ASSERT_ALERT_TEXT":
			Thread.sleep(4000);
			String alertMessage = driver.switchTo().alert().getText();
			assertEquals(alertMessage, value);
			break;
		case "ASSERT_DISPLAY":
			assertEquals( driver.findElement(getObject(webObjectRepository, objectName, objectType)).isDisplayed(), Boolean.parseBoolean(value) );
			break;
		case "ASSERT_TEXT":
			assertEquals(driver.findElement(getObject(webObjectRepository, objectName, objectType)).getText(), value);
			break;
		case "THREAD_SLEEP":
			Thread.sleep(Long.valueOf(value));
			break;
		default:
			Logger.getLogger(getClass().getSimpleName()).info("Cannot find operation!!!");
			assertTrue(false);
			break;
		}
	}

	private By getObject(Properties webObjectRepository, String objectName, String objectType) {
		switch (objectType.toUpperCase()) {
		case "XPATH":
			return By.xpath(webObjectRepository.getProperty(objectName));
		case "CLASSNAME":
			return By.className(webObjectRepository.getProperty(objectName));
		case "NAME":
			return By.name(webObjectRepository.getProperty(objectName));
		case "CSS":
			return By.cssSelector(webObjectRepository.getProperty(objectName));
		case "LINK":
			return By.linkText(webObjectRepository.getProperty(objectName));
		case "PARTIALLINK":
			return By.partialLinkText(webObjectRepository.getProperty(objectName));
		default:
			Logger.getLogger(getClass().getSimpleName()).info("Wrong object type");
			return null;
		}
	}
	
}
