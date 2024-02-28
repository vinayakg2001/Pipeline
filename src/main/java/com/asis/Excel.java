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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class Excel {

	public Workbook wb;
	public static Sheet sheet1;
	public int start_row = 0;
	public final int total_col = 14;
	public final String[][] col_data = { { "BAS SUMMARY" },
			{ "Client:", "Carnarvon Chamber of Commerce & Industry Inc." }, { "Year:", "30-Jun-23" },
			{ "Basis:", "Cash" },
			{ "Monthly/", "Income ", "Income", "GST", "Capital", "Purchases ", "GST", "GST", "Wages ", "PAYG", "PAYG ",
					"Fuel", "ATO Total", "ATO Report" },
			{ "Quarterly", "inc GST", "GST Free", "Received", "inc GST", "inc GST", "Paid", "Payment", "XXX",
					"W/holding", "Instalment", "Credit", "Payment" },
			{ "", "(G1)", "(G3)", "(1A)", "(G10)", "(G11)", "(1B)", "Refund", "(W1)", "(4)", "(5A)", "(7D)",
					"Refund" } };

	public ArrayList<ArrayList<QuaterData>> local_row_data = new ArrayList<>();

	public void abcd() {
		
		ArrayList<QuaterData> jul_quater_data_row = new ArrayList<>();
		QuaterData qd_jul = new QuaterData("Jul");
		jul_quater_data_row.add(qd_jul);
		local_row_data.add(jul_quater_data_row);


		ArrayList<QuaterData> aug_quater_data_row = new ArrayList<>();
		QuaterData qd_aug = new QuaterData("Aug");
		aug_quater_data_row.add(qd_aug);
		local_row_data.add(aug_quater_data_row);

		ArrayList<QuaterData> sept_quater_data_row = new ArrayList<>();
		QuaterData qd_sept = new QuaterData("Sept");
		// qd_sept.setDefaultData(qd_sept);
		qd_sept.set_G1(13732);
		qd_sept.set_1A(1244);
		qd_sept.set_1B(523);
		qd_sept.set_GST_Refund(qd_sept.get_1A() - qd_sept.get_1B());
		qd_sept.set_ATO_Total_Refund(qd_sept.get_GST_Refund() + qd_sept.get_4() + qd_sept.get_5A() - qd_sept.get_7D());
		sept_quater_data_row.add(qd_sept);
		local_row_data.add(sept_quater_data_row);

		ArrayList<QuaterData> oct_quater_data_row = new ArrayList<>();
		QuaterData qd_oct = new QuaterData("Oct");
		// qd_oct.setDefaultData(qd_oct);
		oct_quater_data_row.add(qd_oct);
		local_row_data.add(oct_quater_data_row);

		ArrayList<QuaterData> nov_quater_data_row = new ArrayList<>();
		QuaterData qd_nov = new QuaterData("Nov");
		// qd_nov.setDefaultData(qd_nov);
		nov_quater_data_row.add(qd_nov);
		local_row_data.add(nov_quater_data_row);

		ArrayList<QuaterData> dec_quater_data_row = new ArrayList<>();
		QuaterData qd_dec = new QuaterData("DEC");
		// qd_dec.setDefaultData(qd_dec);
		qd_dec.set_G1(11719);
		qd_dec.set_1A(978);
		qd_dec.set_1B(5756);
		qd_dec.set_GST_Refund(qd_dec.get_1A() - qd_dec.get_1B());
		qd_dec.set_ATO_Total_Refund(qd_dec.get_GST_Refund() + qd_dec.get_4() + qd_dec.get_5A() - qd_dec.get_7D());
		dec_quater_data_row.add(qd_dec);
		local_row_data.add(dec_quater_data_row);
		
		ArrayList<QuaterData> jan_quater_data_row = new ArrayList<>();
		QuaterData qd_jan = new QuaterData("Jan");
		jan_quater_data_row.add(qd_jan);
		// qd_jan.setDefaultData(qd_jan);
		local_row_data.add(jan_quater_data_row);
		
		ArrayList<QuaterData> feb_quater_data_row = new ArrayList<>();
		QuaterData qd_feb = new QuaterData("Feb");
		feb_quater_data_row.add(qd_feb);
		// qd_feb.setDefaultData(qd_feb);
		local_row_data.add(feb_quater_data_row);
		
		ArrayList<QuaterData> mar_quater_data_row = new ArrayList<>();
		QuaterData qd_mar = new QuaterData("Mar");
		// qd_mar.setDefaultData(qd_mar);
		qd_mar.set_G1(611);
		qd_mar.set_1A(42);
		qd_mar.set_1B(602);
		qd_mar.set_GST_Refund(qd_mar.get_1A() - qd_mar.get_1B());
		qd_mar.set_ATO_Total_Refund(qd_mar.get_GST_Refund() + qd_mar.get_4() + qd_mar.get_5A() - qd_mar.get_7D());
		mar_quater_data_row.add(qd_mar);
		local_row_data.add(mar_quater_data_row);
		
		ArrayList<QuaterData> apr_quater_data_row = new ArrayList<>();
		QuaterData qd_apr = new QuaterData("Apr");
		apr_quater_data_row.add(qd_apr);
		// qd_apr.setDefaultData(qd_apr);
		local_row_data.add(apr_quater_data_row);
		
		
		ArrayList<QuaterData> may_quater_data_row = new ArrayList<>();
		QuaterData qd_may = new QuaterData("May");
		may_quater_data_row.add(qd_may);
		// qd_may.setDefaultData(qd_may);
		local_row_data.add(may_quater_data_row);
		
		ArrayList<QuaterData> jun_quater_data_row = new ArrayList<>();
		QuaterData qd_jun = new QuaterData("Jun");
		// qd_jun.setDefaultData(qd_jun);
		qd_jun.set_G1(24958);
		qd_jun.set_1A(2252);
		qd_jun.set_1B(107);
		qd_jun.set_GST_Refund(qd_jun.get_1A() - qd_jun.get_1B());
		qd_jun.set_ATO_Total_Refund(qd_jun.get_GST_Refund() + qd_jun.get_4() + qd_jun.get_5A() - qd_jun.get_7D());
		jun_quater_data_row.add(qd_jun);
		//System.out.println(quater_data_row);
		local_row_data.add(jun_quater_data_row);
	}
	
	public void print_rowdata() {
		System.out.println(local_row_data);
		int rowNo = 0;
		for(ArrayList<QuaterData> row : local_row_data) {
			System.out.println(" ROw No "+rowNo);
			rowNo++;
			/*
			int colNo = 0;
			for(QuaterData r : row) {
				colNo++;
				System.out.println("COl No+"+colNo+" "+r.get_1A()); 
			} 
			*/
		}
	}
	

	public void createTest1() {

		Map<Integer, List<String>> tableData = new HashMap<>();
		// List<String> row1 = new ArrayList<String>();

		/*
		 * for (int i = 0; i<col_data.length;i++) { System.out.println("["); for(int j =
		 * 0; j<col_data[i].length;j++) { System.out.println(col_data[i][j]+" ,");
		 * 
		 * } System.out.println("],"); }
		 */

		// System.out.println(row_data);

		/*
		 * for(ArrayList<QuaterData> row : row_data) {
		 * 
		 * for(QuaterData r : row) {
		 * 
		 * System.out.println(r.get_1A()); } }
		 */

		
		  for (int i = 0; i<col_data.length;i++) { 
			  tableData.put(i, new ArrayList<String>(Arrays.asList(col_data[i]))); 
		  }
		  
		  wb = new HSSFWorkbook(); // or new XSSFWorkbook(); sheet1 =
		  sheet1 = wb.createSheet("Financial Summary");
		  
		  //printing header section
		  
		  for(Entry<Integer, List<String>> entry: tableData.entrySet()) { 
			  start_row =entry.getKey(); 
			  Row row = sheet1.createRow(start_row);
		  
		  int col_start = 0; 
		  for (String col : entry.getValue()) { 
			  Cell cell = row.createCell(col_start);
			  cell.setCellValue(col); 
			  col_start = col_start+1;
			  //System.out.println(col+","); 
			  } 
			  while(col_start < total_col) 
			  { 
				  Cell cell =  row.createCell(col_start);
				  cell.setCellValue(""); 
				  col_start = col_start+1; 
			  }
		  }
		 
		 
		// printing middle section
		for (ArrayList<QuaterData> row : local_row_data) {
			System.out.println(start_row);
			start_row++;
			int col_start = 0;
			Row new_row = sheet1.createRow(start_row);
			for(QuaterData r : row) {
				
				Cell cell = new_row.createCell(col_start);
				cell.setCellValue(r.getQuarter_name()); 
				col_start =col_start+1;
				System.out.println(col_start);
				Cell cell1 = new_row.createCell(col_start);
				cell1.setCellValue(r.get_G1()); 
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell2 = new_row.createCell(col_start);
				cell2.setCellValue(r.get_G3());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell3 = new_row.createCell(col_start);
				cell3.setCellValue(r.get_1A());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell4 = new_row.createCell(col_start);
				cell4.setCellValue(r.get_G10());	
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell5 = new_row.createCell(col_start);
				cell5.setCellValue(r.get_G11());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell6 = new_row.createCell(col_start);
				cell6.setCellValue(r.get_1B());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell7 = new_row.createCell(col_start);
				cell7.setCellValue(r.get_GST_Refund());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell8 = new_row.createCell(col_start);
				cell8.setCellValue(r.get_W1());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell9 = new_row.createCell(col_start);
				cell9.setCellValue(r.get_4());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell10 = new_row.createCell(col_start);
				cell10.setCellValue(r.get_5A());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell11 = new_row.createCell(col_start);
				cell11.setCellValue(r.get_7D());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell12 = new_row.createCell(col_start);
				cell12.setCellValue(r.get_ATO_Total_Refund());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell13 = new_row.createCell(col_start);
				cell13.setCellValue(r.get_ATO_Reports());
				col_start =col_start+1; 				
			}
		}

		
		  FileOutputStream fileOut; 
		  try {
			  fileOut = new FileOutputStream("wb.xls");
			  wb.write(fileOut); 
			  fileOut.close(); 
			  wb.close(); 
		  } catch(FileNotFoundException e) {// TODO Auto-generated catch block
			  e.printStackTrace(); 
		  } catch (IOException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); 
		  }
	}
	
	public void createExcelWithData(String fileName, ArrayList<ArrayList<QuaterData>> row_data) {
		Map<Integer, List<String>> tableData = new HashMap<>();
		// List<String> row1 = new ArrayList<String>();

		/*
		 * for (int i = 0; i<col_data.length;i++) { System.out.println("["); for(int j =
		 * 0; j<col_data[i].length;j++) { System.out.println(col_data[i][j]+" ,");
		 * 
		 * } System.out.println("],"); }
		 */

		// System.out.println(row_data);

		/*
		 * for(ArrayList<QuaterData> row : row_data) {
		 * 
		 * for(QuaterData r : row) {
		 * 
		 * System.out.println(r.get_1A()); } }
		 */

		
		  for (int i = 0; i<col_data.length;i++) { 
			  tableData.put(i, new ArrayList<String>(Arrays.asList(col_data[i]))); 
		  }
		  
		  wb = new HSSFWorkbook(); // or new XSSFWorkbook(); sheet1 =
		  sheet1 = wb.createSheet("Financial Summary");
		  
		  //printing header section
		  
		  for(Entry<Integer, List<String>> entry: tableData.entrySet()) { 
			  start_row =entry.getKey(); 
			  Row row = sheet1.createRow(start_row);
		  
		  int col_start = 0; 
		  for (String col : entry.getValue()) { 
			  Cell cell = row.createCell(col_start);
			  cell.setCellValue(col); 
			  col_start = col_start+1;
			  //System.out.println(col+","); 
			  } 
			  while(col_start < total_col) 
			  { 
				  Cell cell =  row.createCell(col_start);
				  cell.setCellValue(""); 
				  col_start = col_start+1; 
			  }
		  }
		 
		 
		// printing middle section
		for (ArrayList<QuaterData> row : row_data) {
			System.out.println(start_row);
			start_row++;
			int col_start = 0;
			Row new_row = sheet1.createRow(start_row);
			for(QuaterData r : row) {
				
				Cell cell = new_row.createCell(col_start);
				cell.setCellValue(r.getQuarter_name()); 
				col_start =col_start+1;
				System.out.println(col_start);
				Cell cell1 = new_row.createCell(col_start);
				cell1.setCellValue(r.get_G1()); 
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell2 = new_row.createCell(col_start);
				cell2.setCellValue(r.get_G3());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell3 = new_row.createCell(col_start);
				cell3.setCellValue(r.get_1A());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell4 = new_row.createCell(col_start);
				cell4.setCellValue(r.get_G10());	
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell5 = new_row.createCell(col_start);
				cell5.setCellValue(r.get_G11());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell6 = new_row.createCell(col_start);
				cell6.setCellValue(r.get_1B());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell7 = new_row.createCell(col_start);
				cell7.setCellValue(r.get_GST_Refund());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell8 = new_row.createCell(col_start);
				cell8.setCellValue(r.get_W1());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell9 = new_row.createCell(col_start);
				cell9.setCellValue(r.get_4());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell10 = new_row.createCell(col_start);
				cell10.setCellValue(r.get_5A());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell11 = new_row.createCell(col_start);
				cell11.setCellValue(r.get_7D());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell12 = new_row.createCell(col_start);
				cell12.setCellValue(r.get_ATO_Total_Refund());
				col_start =col_start+1; 
				System.out.println(col_start);
				Cell cell13 = new_row.createCell(col_start);
				cell13.setCellValue(r.get_ATO_Reports());
				col_start =col_start+1; 				
			}
		}

		
		  FileOutputStream fileOut; 
		  try {
			  fileOut = new FileOutputStream(fileName);
			  wb.write(fileOut); 
			  fileOut.close(); 
			  wb.close(); 
		  } catch(FileNotFoundException e) {// TODO Auto-generated catch block
			  e.printStackTrace(); 
		  } catch (IOException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); 
		  }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Excel obj = new Excel();
		//
		//obj.abcd();
		//obj.print_rowdata();
		obj.createTest1();
		//obj.createExcelWithData();
		// obj.printRowData();
		System.out.println("Done");
	}
}

