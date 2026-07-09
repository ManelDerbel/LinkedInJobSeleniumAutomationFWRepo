package com.qa.LinkedInJob.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static final String TEST_DATA_PATH = "/src/test/resources/testdata/";

	public static Object[][] getDataExcludingHeader(String fileName, String sheetName) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);
		Object[][] data = null;

		try (FileInputStream fi = new FileInputStream(filePath); Workbook book = WorkbookFactory.create(fi)) {
			Sheet sheet = book.getSheet(sheetName);
			System.out.println(
					"Total rows: " + sheet.getLastRowNum() + " and columns: " + sheet.getRow(0).getLastCellNum());

			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
					data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void setCellData(String fileName, String sheetName, int rowNum, int cellNum, String data) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);

		try {
			File xlFile = new File(filePath);
			Workbook book;

			// If Excel file not exists then create new Excel file
			if (xlFile.exists()) {
				try (FileInputStream fi = new FileInputStream(filePath)) {
					book = WorkbookFactory.create(fi);
				}
			} else {
				book = new XSSFWorkbook();
			}

			// If Sheet not exists the create new Sheet
			Sheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				sheet = book.createSheet(sheetName);
			}

			// If Row is not exists then create new Row
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}

			Cell cell = row.getCell(cellNum);
			if (cell == null) {
				cell = row.createCell(cellNum);
			}
			cell.setCellValue(data);

			try (FileOutputStream fo = new FileOutputStream(filePath)) {
				book.write(fo);
			}
			book.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getRowCount(String fileName, String sheetName) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);

		try (FileInputStream fi = new FileInputStream(filePath); Workbook book = WorkbookFactory.create(fi)) {
			Sheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				return 0;
			}

			return sheet.getLastRowNum() + 1;
		} catch (IOException e) {
			System.out.println("Unable to get row count from sheet: " + sheetName);
			e.printStackTrace();
			return 0;
		}
	}

	public static int getRowCountInColumn(Sheet sheet, int columnNum) {
		if (sheet == null) {
			return 0;
		}

		int count = 0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Cell cell = row.getCell(columnNum);
			if (row != null && cell != null && !cell.toString().trim().isEmpty()) {
				count++;
			}
		}
		return count;
	}

	public static Set<String> getUniqueColumnData(String fileName, String sheetName, int columnNum) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);
		Set<String> data = new HashSet<>();

		try (FileInputStream fi = new FileInputStream(filePath); Workbook book = WorkbookFactory.create(fi)) {
			Sheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				System.out.println("Sheet '" + sheetName + "' does not exist.");
				return data;
			}

			int rowCount = getRowCountInColumn(sheet, columnNum);
			System.out.println("Total columns are: " + rowCount);

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				Cell cell = row.getCell(columnNum);
				if (cell != null && !cell.toString().trim().isEmpty()) {
					data.add(row.getCell(columnNum).toString());
				}
			}
		} catch (IOException e) {
			System.out.println("Unable to read Excel sheet: " + sheetName);
			e.printStackTrace();
		}
		return data;
	}

	public static List<String> getColumnData(String fileName, String sheetName, int columnNum) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);
		List<String> data = new ArrayList<>();

		try (FileInputStream fi = new FileInputStream(filePath); Workbook book = WorkbookFactory.create(fi)) {
			Sheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				System.out.println("Sheet '" + sheetName + "' does not exists.");
				return data;
			}
			System.out.println("Total rows are:" + sheet.getLastRowNum());

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				Cell cell = row.getCell(columnNum);
				if (cell != null && !cell.toString().trim().isEmpty()) {
					data.add(cell.toString());
				}
			}
		} catch (IOException e) {
			System.out.println("Unable to read Excel sheet: " + sheetName);
			e.printStackTrace();
		}
		return data;
	}

	public static Object[][] getDataFromRowIndex(String fileName, String sheetName, int startRowIndex) {
		String filePath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName + ".xlsx";
		System.out.println("filePath" + filePath);
		Object[][] data = null;
		int dataRow = 0;

		try (FileInputStream fi = new FileInputStream(filePath); Workbook book = WorkbookFactory.create(fi)) {
			Sheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				System.out.println("Sheet '" + sheetName + "' does not exists.");
				return data;
			}

			System.out.println(
					"Total rows: " + sheet.getLastRowNum() + " and columns: " + sheet.getRow(0).getLastCellNum());
			data = new Object[sheet.getLastRowNum() - startRowIndex + 1][sheet.getRow(startRowIndex).getLastCellNum()];

			for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell != null && !cell.toString().isEmpty()) {
						data[dataRow][j] = cell.toString();
					}
				}
				dataRow++;
			}
		} catch (IOException e) {
			System.out.println("Unable to read Excel sheet: " + sheetName);
			e.printStackTrace();
		}
		return data;
	}
}
