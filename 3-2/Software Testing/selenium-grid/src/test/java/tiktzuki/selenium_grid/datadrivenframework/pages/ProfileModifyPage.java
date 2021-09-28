package tiktzuki.selenium_grid.datadrivenframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfileModifyPage {
	
	public static WebElement txtPhoneNumber(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-profile/form/div[2]/input"));
	}
	
	public static WebElement txtEmail(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-profile/form/div[3]/input"));
	}
	
	public static WebElement txtPassword(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-profile/form/div[4]/input"));
	}
	
	public static WebElement btnShowPassowrd(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-profile/form/div[4]/div[2]/span/i"));
	}
	
	public static WebElement btnSubmit(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-profile/form/button"));
	}
}
