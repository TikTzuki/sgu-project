package tiktzuki.selenium_grid.datadrivenframework.testcases.modifyuser;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tiktzuki.selenium_grid.datadrivenframework.actions.GetAlert;
import tiktzuki.selenium_grid.datadrivenframework.actions.Login;
import tiktzuki.selenium_grid.datadrivenframework.pages.HomePage;
import tiktzuki.selenium_grid.datadrivenframework.pages.ManagerUserPage;
import tiktzuki.selenium_grid.datadrivenframework.pages.ModifyUserPage;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class TestModifyUser {
	public WebDriver driver;
	public String username;
	public String phoneNumber;
	public String email;
	public String password;
	public boolean isActive;
	public boolean canRead;
	public boolean canWrite;
	
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setUp(String os, String browser, String url, String node) throws MalformedURLException {
		// Setup driver
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}

	public TestModifyUser(String username,String phoneNumber, String email, String password, String isActive, String canRead,
			String canWrite) {
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.isActive = Boolean.valueOf(isActive);
		this.canRead = Boolean.valueOf(canRead);
		this.canWrite = Boolean.valueOf(canWrite);
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		Login.Execute(driver);
	}
	
	@Test(priority = 2)
	public void goToModifyUser() throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.moveToElement(HomePage.manageUserNavItem(driver)).perform();
		HomePage.manageUserNavItem(driver).click();
		Thread.sleep(1000);
		ManagerUserPage.btnModifyByUsername(driver, this.username).click();
	}
	
	@Test(priority = 3)
	public void goToUser() throws InterruptedException {
		Thread.sleep(2000);
		ModifyUserPage.txtPhoneNumber(driver).clear();;
		ModifyUserPage.txtPhoneNumber(driver).sendKeys(this.phoneNumber);
		ModifyUserPage.txtEmail(driver).clear();
		ModifyUserPage.txtEmail(driver).sendKeys(this.email);
		ModifyUserPage.txtPassword(driver).clear();
		ModifyUserPage.txtPassword(driver).sendKeys(this.password);
		if(this.isActive && !ModifyUserPage.cbActive(driver).isSelected() || !this.isActive && ModifyUserPage.cbActive(driver).isSelected()) {
			ModifyUserPage.cbActive(driver).click();
		}
		if(this.canRead && !ModifyUserPage.cbRead(driver).isSelected() || !this.canRead&& ModifyUserPage.cbRead(driver).isSelected()) {
			ModifyUserPage.cbRead(driver).click();
		}
		if(this.canWrite && !ModifyUserPage.cbWrite(driver).isSelected() || !this.canWrite && ModifyUserPage.cbWrite(driver).isSelected()) {
			ModifyUserPage.cbWrite(driver).click();
		}
		Thread.sleep(4000);
		ModifyUserPage.btnSubmit(driver).click();
		Thread.sleep(4000);
		String result = GetAlert.GetAlert(driver).getText();
		assertEquals(result, "modify success");
	}
	
	@AfterClass
	public void closeBrowser() {
		this.driver.quit();
	}
}
