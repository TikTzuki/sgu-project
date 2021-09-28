package tiktzuki.selenium_grid.hybridframework.testcases;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tiktzuki.selenium_grid.hybridframework.operation.UIOperation;
import tiktzuki.selenium_grid.utils.Action;
import tiktzuki.selenium_grid.utils.SetupDriver;

public class TestcaseExecutor{
	private WebDriver driver;
	String testcaseName;
	List<Action> actions;
	Properties allObjects;
	
	@BeforeClass
	@Parameters({"os", "browser", "url", "node"})
	public void setup(
			@Optional("windows") String os,
			@Optional("chrome") String browser,
			@Optional("http://localhost:4200") String url,
			@Optional("http://192.168.56.1:48009") String node) throws MalformedURLException {
		SetupDriver setupTestDriver = new SetupDriver(os, browser, url, node);
		driver = setupTestDriver.getDriver();
	}

	public TestcaseExecutor(String testcase, List<Action> actions, Properties allObjects) {
		this.testcaseName = testcase;
		this.actions = actions;
		this.allObjects = allObjects; 
	}
	
	@Test
	public void executeTest() throws NumberFormatException, InterruptedException {
		UIOperation operation = new UIOperation(driver);
		for(Action action: actions){
			operation.perform(allObjects, action.keyword, action.object, action.objectType, action.value);
		};
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
