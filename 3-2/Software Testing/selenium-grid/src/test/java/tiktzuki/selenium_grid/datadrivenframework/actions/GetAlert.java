package tiktzuki.selenium_grid.datadrivenframework.actions;

import java.sql.Driver;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetAlert {
	public static Alert GetAlert(WebDriver driver) {
		if(isAlertPresent(driver)) {
			Alert alert = (Alert) driver.switchTo().alert();
			System.out.println(alert.getText());
			return alert;
		}
		return null;
	}

	public static boolean isAlertPresent(WebDriver driver){
	      try{
	          driver.switchTo().alert();
	          return true;
	      }catch(NoAlertPresentException ex){
	          return false;
	      }
	}
}
