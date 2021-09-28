package tiktzuki.selenium_grid.datadrivenframework.testcases.deleteuser;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import tiktzuki.selenium_grid.datadrivenframework.pages.ManagerUserPage;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class TestDeleteUser {
	public WebDriver driver;
	public String username;
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setup(String os, String browser, String url, String node) throws MalformedURLException {
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}
	
	public TestDeleteUser(String username) {
		this.username = username;
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		Login.Execute(driver);
	}
	
	@Test(priority = 2)
	public void gotoManageUser() throws InterruptedException {
		Actions builder = new Actions(driver);
		Thread.sleep(1000);
		builder.moveToElement(HomePage.manageUserNavItem(driver)).perform();
		HomePage.manageUserNavItem(driver).click();
		Thread.sleep(1000);
		assertEquals(ManagerUserPage.title(driver).getText(), "User manager");
	}
	
	@Test(priority = 3)
	public void deleteUser() throws InterruptedException {
		ManagerUserPage.btnDeleteByUsername(driver, this.username).click();
		Thread.sleep(2000);
		List<WebElement> cells = ManagerUserPage.colAt(driver, 1);
		for(WebElement cell: cells) {
			System.out.println(cell.getText());
			assertNotEquals(cell.getText(), this.username);
		}
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
