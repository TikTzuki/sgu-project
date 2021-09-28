package tiktzuki.selenium_grid.datadrivenframework.testcases.login;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tiktzuki.selenium_grid.datadrivenframework.actions.Login;
import tiktzuki.selenium_grid.datadrivenframework.pages.HomePage;
import tiktzuki.selenium_grid.datadrivenframework.pages.LoginPage;
import tiktzuki.selenium_grid.datadrivenframework.pages.ManagerUserPage;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class TestLogin {
	public WebDriver driver;
	String username;
	String password;
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setup(String os, String browser, String url, String node) throws MalformedURLException {
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}
	
	public TestLogin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		LoginPage.txtUsername(driver).sendKeys(this.username);
		LoginPage.txtPassword(driver).sendKeys(this.password);
		Thread.sleep(2000);
		LoginPage.btnLogin(driver).click();
	}
	
	@Test(priority = 2)
	public void gotoManageUser() throws InterruptedException {
		String heder=driver.findElement(By.xpath("/html/body/app-root/div/div[1]/app-header/div/div/h4")).getText();
		assertEquals(heder, username);
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
