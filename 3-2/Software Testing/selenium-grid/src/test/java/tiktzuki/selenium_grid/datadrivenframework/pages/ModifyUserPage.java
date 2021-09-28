package tiktzuki.selenium_grid.datadrivenframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ModifyUserPage {

	public static WebElement txtUsername(WebDriver driver) {
		WebElement txtUsername = null;
		try {
			txtUsername = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[1]/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txtUsername;
	}


	public static WebElement txtPhoneNumber(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[2]/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}

	public static WebElement txtEmail(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[3]/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}

	public static WebElement txtPassword(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[4]/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}

	public static WebElement cbActive(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[5]/div/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}

	public static WebElement cbRead(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[6]/div/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}

	public static WebElement cbWrite(WebDriver driver) {
		WebElement txt= null;
		try {
			txt = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[7]/div/input"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return txt;
	}
	
	public static WebElement btnSubmit(WebDriver driver) {
		WebElement btn= null;
		try {
			btn = driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/button"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return btn;
	}
}
