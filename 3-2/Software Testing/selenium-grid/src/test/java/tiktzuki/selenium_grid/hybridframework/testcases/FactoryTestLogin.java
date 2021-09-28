package tiktzuki.selenium_grid.hybridframework.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import tiktzuki.selenium_grid.utils.Action;
import tiktzuki.selenium_grid.utils.Constant;
import tiktzuki.selenium_grid.utils.ExcelUtils;
import tiktzuki.selenium_grid.utils.ObjectReader;

public class FactoryTestLogin {
	final String OBJECT_FILE = "src//test//java//tiktzuki//selenium_grid//hybridframework//objects//login-page-object.properties";
	final String TEST_DATA_FILE = "src//test//java//tiktzuki//selenium_grid//hybridframework//testdata//Login_User - Integration.xlsx";
	@Factory(dataProvider="dataTestLogin")
	public Object[] createInstances(String testcase, List<Action> actions) {
		ObjectReader object = new ObjectReader();
		Properties allWebObjects = object.getObjectRepository(OBJECT_FILE);
		return new Object[] {new TestcaseExecutor(testcase, actions, allWebObjects)};
	}
	
	@DataProvider(name = "dataTestLogin")
	public Object[][] dataMethod(){
		Object[][] arrayObjects = createData();
		return arrayObjects;
	}

	private Object[][] createData() {
		// Setup data for test
		ExcelUtils excelUtil = new ExcelUtils(TEST_DATA_FILE, "Sheet1");
		List<Object[]> excelData = new ArrayList<Object[]>();
		int totalRows = excelUtil.getRowCount();
		int startRow = 1;
		List<Action> actions = new ArrayList<Action>();
		String testcaseName = "";
		for(int i = startRow; i <= totalRows; i++) {
			if(excelUtil.getCellData(i, 0) != null || i == totalRows) {
				if(!actions.isEmpty()) {
					excelData.add(new Object[] {testcaseName, actions});
				}
				actions = new ArrayList<Action>();
				testcaseName = String.valueOf(excelUtil.getCellData(i, 0));
			} else {
				actions.add(new Action(
						String.valueOf(excelUtil.getCellData(i, 1)) , 
						String.valueOf(excelUtil.getCellData(i, 2)),
						String.valueOf(excelUtil.getCellData(i, 3)),
						String.valueOf(excelUtil.getCellData(i, 4)) ));
			}
		}
		Object[][] converExcelData = new Object[excelData.size()][2];
		for(int i = 0; i< excelData.size(); i++) {
			converExcelData[i] = excelData.get(i); 
		}
		return converExcelData;
	}
}
