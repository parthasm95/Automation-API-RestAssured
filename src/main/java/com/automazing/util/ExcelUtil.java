package com.automazing.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * @author Partha
 *
 */
public class ExcelUtil {

	public static Workbook book;
	public static Sheet sheet;

	public static Object[][] getTestData(String filePath, String sheetName) {

		try {
			FileInputStream ip = new FileInputStream(filePath);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Please provide correct file path...");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int row = 0; row < sheet.getLastRowNum(); row++) {
			for (int col = 0; col < sheet.getRow(0).getLastCellNum(); col++) {
				data[row][col] = sheet.getRow(row + 1).getCell(col).toString();
			}
		}
		return data;
	}
}
