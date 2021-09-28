package tiktzuki.selenium_grid.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public XSSFWorkbook workBook;
	public XSSFSheet sheet;
	public XSSFCell cell;
	public XSSFRow row;
	Logger Log = Logger.getLogger(getClass().getSimpleName());

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	public ExcelUtils(String path, String sheetName) {
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(path);
			this.workBook = new XSSFWorkbook(excelFile);
			this.sheet = workBook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public Object getCellData(int RowNum, int ColNum){
			Object CellData = null; 
		try{
			this.cell = this.sheet.getRow(RowNum).getCell(ColNum);
			if (cell!=null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					CellData = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					CellData = cell.getNumericCellValue();
//					Log.info("\nnumber " + cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					CellData = cell.getStringCellValue();
//					Log.info("\nString " + cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
					// CELL_TYPE_FORMULA will never occur
				case Cell.CELL_TYPE_FORMULA: 
					break;
				}
			}
			return CellData;
		}catch (Exception e){
			return null; 
		}
	}

	//This method is to write in the Excel cell, Row num and Col num are the parameters
	public void setCellData(String Result,  int RowNum, int ColNum, String fileOutputPath) throws Exception {
		try{
			row  = this.sheet.getRow(RowNum);
			cell = row.getCell(ColNum, row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(Result);
			} else {
				cell.setCellValue(Result);
			}
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(fileOutputPath);
			this.workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			throw (e);
		}
	}

	public int getRowCount() {
		return this.sheet.getPhysicalNumberOfRows();
	}

	public int getColCont() {
		return this.sheet.getRow(0).getPhysicalNumberOfCells();
	}
}
