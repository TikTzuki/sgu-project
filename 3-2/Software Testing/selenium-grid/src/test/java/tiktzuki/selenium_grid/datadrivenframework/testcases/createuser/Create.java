package tiktzuki.selenium_grid.datadrivenframework.testcases.createuser;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty9.util.security.Constraint;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tiktzuki.selenium_grid.datadrivenframework.pages.HomePage;
import tiktzuki.selenium_grid.datadrivenframework.pages.LoginPage;
import tiktzuki.selenium_grid.datadrivenframework.pages.ModifyUserPage;
import tiktzuki.selenium_grid.utils.Constant;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class Create {

	public WebDriver driver;
	String username;
	String phoneNumber;
	String email;
	String password;
	String bl1,bl2,bl3;
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setup(String os, String browser, String url, String node) throws MalformedURLException {
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}
	
	public Create(String username, String phoneNumber, String email, String password, String bl1, String bl2, String bl3) {
		this.username=username;
		this.phoneNumber=phoneNumber;
		this.email=email;
		this.password=password;
		this.bl1=bl1;
		this.bl2=bl2;
		this.bl3=bl3;
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		LoginPage.txtUsername(driver).sendKeys(Constant.Default_Username);
		LoginPage.txtPassword(driver).sendKeys(Constant.Default_Password);
		Thread.sleep(2000);
		LoginPage.btnLogin(driver).click();
	}

	@Test(priority = 2)
	public void goToCreate() {
		Actions builder = new Actions(driver);
		builder.moveToElement(HomePage.createUserNavItem(driver)).perform();
		HomePage.createUserNavItem(driver).click();
	}
	
	@Test(priority = 3)
	public void CreateUser() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[1]/input")).sendKeys(username);
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[2]/input")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[3]/input")).sendKeys(email);
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[4]/input")).sendKeys(password);
		if(bl1.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[5]/div/input")).click();
		if(bl2.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[6]/div/input")).click();
		if(bl3.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-add-user/form/div[7]/div/input")).click();
		ModifyUserPage.btnSubmit(driver).click();
		Thread.sleep(2000);
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
