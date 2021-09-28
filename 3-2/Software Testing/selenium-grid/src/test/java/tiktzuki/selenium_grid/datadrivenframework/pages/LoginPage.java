package tiktzuki.selenium_grid.datadrivenframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	public static WebElement txtUsername(WebDriver driver) {
		return driver.findElement(By.id("username"));
	}
	
	public static WebElement txtPassword(WebDriver driver) {
		return driver.findElement(By.id("password"));
	}
	
	public static WebElement btnLogin(WebDriver driver) {
		WebElement el = driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-login/div/button"));
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(el));
		return el; 
	}
}
