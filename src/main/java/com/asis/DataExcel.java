package com.asis;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataExcel {

	public void dataExcel(String Value, String SheetName1, int Row, int Col) throws InterruptedException {

		try {
			FileInputStream fis = new FileInputStream("C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\Book9 (2).xlsx");
			Workbook workbook = WorkbookFactory.create(fis);

			// Get the row and cell where you want to write the data
			Sheet s1 = workbook.getSheet(SheetName1);  //Get sheet name

			if (s1 == null) {
				System.out.println("Sheet with name " + SheetName1 + " does not exist.");
				s1 = workbook.createSheet(SheetName1); // Create a new sheet
			}
			// Get the starting cell
			Row row = s1.getRow(Row);
			if (row == null) {
				row = s1.createRow(Row);
			}

			// Get the cell
			Cell cellA1 = row.getCell(Col);
			if (cellA1 == null) {
				cellA1 = row.createCell(Col);
			}

			// Set the values to the cells
			cellA1.setCellValue(Value);


			FileOutputStream fos = new FileOutputStream("C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\Book9 (2).xlsx");
			workbook.write(fos);

			// Close the streams
			fos.close();
			fis.close();

			System.out.println("Data has been written to the Excel file successfully.");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}


