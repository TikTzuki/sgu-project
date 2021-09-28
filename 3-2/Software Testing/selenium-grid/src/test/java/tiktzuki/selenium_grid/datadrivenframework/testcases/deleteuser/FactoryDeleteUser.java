package tiktzuki.selenium_grid.datadrivenframework.testcases.deleteuser;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import tiktzuki.selenium_grid.utils.Constant;
import tiktzuki.selenium_grid.utils.ExcelUtils;

public class FactoryDeleteUser {
	
	@Factory(dataProvider = "dataDeleteUser")
	public Object[] createInstances(String username) {
		return new Object[] {new TestDeleteUser(username)};
	}
	
	@DataProvider(name = "dataDeleteUser")
	public String[][] getExcelData(){
		// Setup data for test
		ExcelUtils excelUtil = new ExcelUtils(Constant.Path_TestData+Constant.File_TestData_DELETE_USER, "Sheet1");
		String[][] excelData = null;
		int totalRows = excelUtil.getRowCount();
		int totalCols = excelUtil.getColCont();
		excelData = new String[totalRows-1][totalCols-1];
		
		int startRow = 1;
		int startCol = 1;
		
		for(int i = startRow; i < totalRows; i++) {
			for(int j = startCol; j < totalCols; j++) {
				try {
					excelData[i-1][j-1] = excelUtil.getCellData(i, j).toString();
					System.out.print("\t"+excelData[i-1][j-1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println();
		}
		return excelData;
	}
}
