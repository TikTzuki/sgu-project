package tiktzuki.selenium_grid.datadrivenframework.testcases.registeruser;

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

public class RegisterUser {
public WebDriver driver;
public String userName;
public String PassWord;
public String ConfirmPassWord;
public String Email;
public String PhoneNumber;
public String bl1;
public String bl2;
public String bl3;

	
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setup(String os, String browser, String url, String node) throws MalformedURLException {
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}
	
	public RegisterUser(String userName, String PassWord, String ConfirmPassWord, String Email, String PhoneNumber, String bl1, String bl2, String bl3) {
		this.userName=userName;
		this.PassWord=PassWord;
		this.ConfirmPassWord=ConfirmPassWord;
		this.Email=Email;
		this.PhoneNumber=PhoneNumber;
		this.bl1=bl1;
		this.bl2=bl2;
		this.bl3=bl3;
		System.out.print(userName+" "+PassWord+" "+ConfirmPassWord+" "+Email+" "+PhoneNumber);
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/button")).click();
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(userName);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(PassWord);
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[3]/input")).sendKeys(ConfirmPassWord);
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[4]/input")).sendKeys(Email);
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[5]/input")).sendKeys(PhoneNumber);
		if(bl1.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[6]/div/input")).click();
		if(bl2.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[7]/div/input")).click();
		if(bl3.equals("true"))
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/div[8]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-register/div/button")).click();
;		
	}
	
	@Test(priority = 2)
	public void gotoManageUser() throws InterruptedException {
		Thread.sleep(5000);
		Actions builder = new Actions(driver);
		builder.moveToElement(HomePage.manageUserNavItem(driver)).perform();
		Thread.sleep(2000);
		HomePage.manageUserNavItem(driver).click();
		Thread.sleep(1000);
	}
	
	@Test(priority = 3)
	public void deleteUser() throws InterruptedException {
		ManagerUserPage.btnDeleteByUsername(driver, "tiktzuki").click();
//		if(btnDelete!=null) {
//			System.out.println(btnDelete.getText());
//		}
//		btnDelete.click();
		Thread.sleep(2000);
		List<WebElement> cells = ManagerUserPage.colAt(driver, 1);
		for(WebElement cell: cells) {
			System.out.println(cell.getText());
		}
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
