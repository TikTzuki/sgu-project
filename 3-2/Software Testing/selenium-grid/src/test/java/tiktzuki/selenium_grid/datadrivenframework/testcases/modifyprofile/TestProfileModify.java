package tiktzuki.selenium_grid.datadrivenframework.testcases.modifyprofile;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tiktzuki.selenium_grid.datadrivenframework.actions.Login;
import tiktzuki.selenium_grid.datadrivenframework.pages.HomePage;
import tiktzuki.selenium_grid.datadrivenframework.pages.ProfileModifyPage;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class TestProfileModify {
	public WebDriver driver;
	
	public String phoneNumber;
	public String email;
	public String password;
	
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setUp(String os, String browser, String url, String node) throws MalformedURLException {
		// Setup driver
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}
	
	public TestProfileModify(String phoneNumber, String email, String password) {
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		Login.Execute(driver);
	}
	
	@Test(priority = 2)
	public void goToProfile() throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.moveToElement(HomePage.profileNavItem(driver)).perform();
		HomePage.profileNavItem(driver).click();
		Thread.sleep(1000);
	}
	
	@Test(priority = 3)
	public void modifyProfile() throws InterruptedException {
		ProfileModifyPage.txtPhoneNumber(driver).clear();
		ProfileModifyPage.txtPhoneNumber(driver).sendKeys(this.phoneNumber);
		ProfileModifyPage.txtEmail(driver).clear();
		ProfileModifyPage.txtEmail(driver).sendKeys(this.email);
		ProfileModifyPage.txtPassword(driver).clear();
		ProfileModifyPage.txtPassword(driver).sendKeys(this.password);
		ProfileModifyPage.btnShowPassowrd(driver).click();
		ProfileModifyPage.btnSubmit(driver).click();
		Thread.sleep(5000);
	}
	
	@Test(priority = 4)
	public void checkUserList() {
		
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
