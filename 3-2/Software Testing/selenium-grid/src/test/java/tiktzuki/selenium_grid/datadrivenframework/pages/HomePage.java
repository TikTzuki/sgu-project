package tiktzuki.selenium_grid.datadrivenframework.pages;

import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	WebDriver driver;

	public void perform(Properties p, String operation, String objectName, String objectType, String value) {
		switch (operation.toUpperCase()) {
		case "CLICK":
			driver.findElement(getObject(p, objectName, objectType)).click();
			break;
		default:
			break;
		}
	}

	public static WebElement manageUserNavItem(WebDriver driver) {
		WebElement el = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[1]/app-navbar/nav/ul/li[2]/a"));
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(el));
		wait.until(ExpectedConditions.elementToBeClickable(el));
		return el;
	}
	
	public static WebElement createUserNavItem(WebDriver driver) {
		WebElement el = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[1]/app-navbar/nav/ul/li[3]/a"));
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(el));
		return el;
	}
	
	public static WebElement profileNavItem(WebDriver driver) {
		WebElement el = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[1]/app-navbar/nav/ul/li[4]/a"));
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(el));
		return el;
	}
	
	private By getObject(Properties p, String objectName, String objectType) {
		switch (objectType.toUpperCase()) {
		case "XPATH":
			return By.xpath(p.getProperty(objectName));
		case "CLASSNAME":
			return By.className(p.getProperty(objectName));
		case "NAME":
			return By.name(p.getProperty(objectName));
		case "CSS":
			return By.cssSelector(p.getProperty(objectName));
		case "LINK":
			return By.linkText(p.getProperty(objectName));
		case "PARTIALLINK":
			return By.partialLinkText(p.getProperty(objectName));
		default:
			Logger.getLogger(getClass().getSimpleName()).info("Wrong object type");
			return null;
		}
	}
}
