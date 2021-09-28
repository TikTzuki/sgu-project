package tiktzuki.selenium_grid.datadrivenframework.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManagerUserPage {
	
	public static WebElement title(WebDriver driver) {
		return driver.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-user-list/div/h1"));
	}
	
	public static List<WebElement> tblManagerUserRow(WebDriver driver){
		return driver.findElements(By.xpath("/html/body/app-root/div/div[2]/div[2]/app-user-list/div/div[2]/table/tbody/tr"));
	}
	
	public static WebElement rowTblManageUserByUsername(WebDriver driver, String username){
		List<WebElement> rows = ManagerUserPage.tblManagerUserRow(driver);
		for(WebElement row: rows) {
			List<WebElement> cell = row.findElements(By.tagName("td"));
			if(cell.get(0).getText().equals(username)) {
				return row;
			}
		}
		return null;
	}
	
	public static WebElement btnDeleteByUsername(WebDriver driver, String username) {
		WebElement row = rowTblManageUserByUsername(driver, username);
		if(row==null) {
			return null;
		}
		WebElement btnDelete = row.findElement(By.xpath("./td[4]/button[2]"));
//		WebElement btnDelete = row.findElement(By.xpath("/htmtr[1]/td[4]/button[2]"));
		return btnDelete;
	}
	
	public static WebElement btnModifyByUsername(WebDriver driver, String username) {
		WebElement row = rowTblManageUserByUsername(driver, username);
		if(row==null) {
			return null;
		}
		WebElement btnModify = row.findElement(By.xpath("./td[4]/button[1]"));
		return btnModify;
	}
	
	public static List<WebElement> colAt(WebDriver driver, int index){
		List<WebElement> rows = tblManagerUserRow(driver);
		System.out.println("row count:" + rows.size());
		List<WebElement> cells = new ArrayList<WebElement>();
		for(WebElement row: rows) {
			cells.add(row.findElement(By.xpath("./td["+index+"]")));
		}
		return cells;
	}
}
