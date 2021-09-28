package tiktzuki.selenium_grid.datadrivenframework.actions;

import org.openqa.selenium.WebDriver;

import tiktzuki.selenium_grid.datadrivenframework.pages.LoginPage;
import tiktzuki.selenium_grid.utils.Constant;

public class Login {
	public static void Execute(WebDriver driver) throws InterruptedException{
		LoginPage.txtUsername(driver).sendKeys(Constant.Default_Username);
		LoginPage.txtPassword(driver).sendKeys(Constant.Default_Password);
		Thread.sleep(2000);
		LoginPage.btnLogin(driver).click();
	}
}
