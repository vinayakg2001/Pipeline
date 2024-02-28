/**
 * 
 */
package com.asis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 
 */
public class Excel_bkp {

	public Workbook wb;
	public static  Sheet sheet1;
	public int start_row = 0;
	public final int total_col = 14;
	public final String[][] col_data ={
			{"BAS SUMMARY"},
			{"Client:", "Carnarvon Chamber of Commerce & Industry Inc."},
			{"Year:", "30-Jun-23"},
			{"Basis:", "Cash"},
			{"Monthly/", "Income ", "Income", "GST", "Capital", "Purchases ","GST", "GST", "Wages ", "PAYG", "PAYG ","Fuel", "ATO Total", "ATO Report"},
			{"Quarterly","inc GST","GST Free","Received","inc GST","inc GST","Paid","Payment","XXX","W/holding","Instalment","Credit","Payment"},
			{"","(G1)","(G3)","(1A)","(G10)","(G11)","(1B)","Refund","(W1)","(4)","(5A)","(7D)","Refund"}
	};
	
	public ArrayList<ArrayList<QuaterData>> row_data = new ArrayList<>();
	

	public void abcd() {
		ArrayList<QuaterData> quater_data_row = new ArrayList<>();
		QuaterData qd_jul = new QuaterData("Jul");
		quater_data_row.add(qd_jul);
		
		//qd_jul.setDefaultData(qd_jul);
		
		QuaterData qd_aug = new QuaterData("Aug");
		quater_data_row.add(qd_aug);
		//qd_aug.setDefaultData(qd_aug);
		
		QuaterData qd_sept = new QuaterData("Sept");
		//qd_sept.setDefaultData(qd_sept);
		qd_sept.set_G1(13732);
		qd_sept.set_1A(1244);
		qd_sept.set_1B(523);
		qd_sept.set_GST_Refund(qd_sept.get_1A() - qd_sept.get_1B());
		qd_sept.set_ATO_Total_Refund(qd_sept.get_GST_Refund() + qd_sept.get_4() + qd_sept.get_5A() - qd_sept.get_7D());
		quater_data_row.add(qd_sept);		
		
		
		QuaterData qd_oct = new QuaterData("Oct");	
		//qd_oct.setDefaultData(qd_oct);
		quater_data_row.add(qd_oct);
		
		QuaterData qd_nov = new QuaterData("Nov");	
		//qd_nov.setDefaultData(qd_nov);
		quater_data_row.add(qd_nov);
		
		QuaterData qd_dec = new QuaterData("DEC");
		//qd_dec.setDefaultData(qd_dec);
		qd_dec.set_G1(11719);
		qd_dec.set_1A(978);
		qd_dec.set_1B(5756);
		qd_dec.set_GST_Refund(qd_dec.get_1A() - qd_dec.get_1B());
		qd_dec.set_ATO_Total_Refund(qd_dec.get_GST_Refund() + qd_dec.get_4() + qd_dec.get_5A() - qd_dec.get_7D());	
		quater_data_row.add(qd_dec);
		
		QuaterData qd_jan = new QuaterData("Jan");	
		quater_data_row.add(qd_jan);
		//qd_jan.setDefaultData(qd_jan);
		
		QuaterData qd_feb = new QuaterData("Feb");	
		quater_data_row.add(qd_feb);
		//qd_feb.setDefaultData(qd_feb);
		
		QuaterData qd_mar = new QuaterData("Mar");
		//qd_mar.setDefaultData(qd_mar);
		qd_mar.set_G1(611);
		qd_mar.set_1A(42);
		qd_mar.set_1B(602);
		qd_mar.set_GST_Refund(qd_mar.get_1A() - qd_mar.get_1B());
		qd_mar.set_ATO_Total_Refund(qd_mar.get_GST_Refund() + qd_mar.get_4() + qd_mar.get_5A() - qd_mar.get_7D());
		quater_data_row.add(qd_mar);
		
		QuaterData qd_apr = new QuaterData("Apr");	
		quater_data_row.add(qd_apr);
		//qd_apr.setDefaultData(qd_apr);
		
		QuaterData qd_may = new QuaterData("May");
		quater_data_row.add(qd_may);
		//qd_may.setDefaultData(qd_may);
		
		QuaterData qd_jun = new QuaterData("Jun");
		//qd_jun.setDefaultData(qd_jun);
		qd_jun.set_G1(24958);
		qd_jun.set_1A(2252);
		qd_jun.set_1B(107);
		qd_jun.set_GST_Refund(qd_jun.get_1A() - qd_jun.get_1B());
		qd_jun.set_ATO_Total_Refund(qd_jun.get_GST_Refund() + qd_jun.get_4() + qd_jun.get_5A() - qd_jun.get_7D());
		quater_data_row.add(qd_jun);
		
		row_data.add(quater_data_row);
	}	
	
	
	
	public void printRowData()
	{
		System.out.println(row_data);
	}
	

	
	
	private static void createCell(Cell cell, Workbook wb, HorizontalAlignment halign, VerticalAlignment valign,boolean bold, boolean merge) {

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);

		Font font = wb.createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		if (merge) {
			try {
				sheet1.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), 0, 13));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		}
		cell.setCellStyle(cellStyle);
	}
	public void createTest1() {		

		Map<Integer,List<String>> tableData = new HashMap<>();
		//List<String> row1 = new ArrayList<String>();
		
		/*
		for (int i = 0; i<col_data.length;i++) {
			System.out.println("[");			
			for(int j = 0; j<col_data[i].length;j++) {
				System.out.println(col_data[i][j]+" ,");
				
			}
			System.out.println("],");
		}*/
		
		//System.out.println(row_data);
	
		/*
		for (int i = 0; i<col_data.length;i++) {
			tableData.put(i, new ArrayList<String>(Arrays.asList(col_data[i])));
		}
		
		wb = new HSSFWorkbook();  // or new XSSFWorkbook();
		sheet1 = wb.createSheet("Financial Summary");
		
		//printing header section
		
		for(Entry<Integer, List<String>> entry: tableData.entrySet()) {
			start_row = entry.getKey();
			Row row = sheet1.createRow(start_row);
			
			//System.out.println("Row"+rowNum);
			int col_start = 0;
			for (String col : entry.getValue()) {
				Cell cell = row.createCell(col_start);
				cell.setCellValue(col);
				col_start = col_start+1;
				//System.out.println(col+",");
			}
			while(col_start < total_col) {
				Cell cell = row.createCell(col_start);
				cell.setCellValue("");
				col_start = col_start+1;
			}
		}*/
		/*
		//printing middle section
		for(ArrayList<QuaterData> row : row_data) {
			start_row++;
			int col_start = 0;
			Row new_row = sheet1.createRow(start_row);
			
			for(QuaterData r : row) {
				Cell cell = new_row.createCell(col_start);
			//cell.setCellValue(r);
				String temp = r;
				cell.setCellValue(temp);
				col_start = col_start+1;
				
				//System.out.println(r);
			}
		}*/
		//System.out.println(tableData);
		
		//tableData.put(0, new ArrayList<String>(Arrays.asList("BAS SUMMARY")));		
		
		/*
		for (int i = 0; i < col_data.length; i++) {
			Row row = sheet1.createRow(i);
			for (int j = 0; j < col_data[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data[i][j]);
				boolean bold = i == 0;
				boolean merge = i == 0 && j == 0;

				createCell(cell, wb, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, bold, merge);
			}
		}*/
		
		/*
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("wb.xls");
			wb.write(fileOut);
			fileOut.close();
			wb.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Excel obj = new Excel();
		//
		obj.abcd();
		obj.createTest1();
		//obj.printRowData();
		System.out.println("Done");
	}
}
