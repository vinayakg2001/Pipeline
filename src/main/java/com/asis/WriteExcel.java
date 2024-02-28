package com.asis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
public class WriteExcel {


	public static Map<String, ExcelAmount> hashMap;

	static String filePath ="C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\Book9 (2).xlsx"; // Path to the existing Excel file
	static String sheetName = "Sheet1"; // Name of the sheet in the Excel file


	@Test   // TestNG test method to execute the code
	public static void Read() throws Exception{
		String[] file= new String[] {"C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\1.pdf","C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\2.pdf","C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\3.pdf","C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\4.pdf"};
		String[] Result= new String[8];
		for(int i=0;i<file.length;i++) {

			FileInputStream fis = new FileInputStream(file[i]);
			PDDocument pdfDocument = PDDocument.load(fis);

			PDFTextStripper pdfTextStripper = new PDFTextStripper();  
			String docText = pdfTextStripper.getText(pdfDocument);

			int find_Element_By1A=docText.indexOf("1A");    // Extracting values from the PDF
			int id1=docText.indexOf('$',find_Element_By1A);
			int dot1=docText.indexOf('.',id1);
			Result[0] = (docText.substring(id1+1,dot1)+docText.substring(dot1,dot1+3));

			int find_Element_By1B=docText.indexOf("1B");          // Extracting values from the PDF
			int id2=docText.indexOf('$',find_Element_By1B);
			int dot2=docText.indexOf('.',id2);
			Result[1] = (docText.substring(id2+1,dot2)+docText.substring(dot2,dot2+3));

			int find_Element_ByG1=docText.indexOf("G1");                // Extracting values from the PDF
			int id3=docText.indexOf('$',find_Element_ByG1);
			int dot3=docText.indexOf('.',id3);
			Result[2]= (docText.substring(id3+1,dot3)+docText.substring(dot3,dot3+3));


			int find_Element_ByPAYG=docText.indexOf("PAYG");             // Extracting values from the PDF
			int id8=docText.indexOf('$',find_Element_ByPAYG);
			int dot8=docText.indexOf('.',id8);
			Result[7] = (docText.substring(id8+1,dot8)+docText.substring(dot8,dot8+3));


			int find_Element_ByW1=docText.indexOf("W1");                     // Extracting values from the PDF
			int id4=docText.indexOf('$',find_Element_ByW1);
			int dot4=docText.indexOf('.',id4);
			Result[3] = (docText.substring(id4+1,dot4)+docText.substring(dot4,dot4+3));


			int find_Element_ByW2=docText.indexOf("W2");                         // Extracting values from the PDF
			int id5=docText.indexOf('$',find_Element_ByW2);
			int dot5=docText.indexOf('.',id5);
			Result[4] = (docText.substring(id5+1,dot5)+docText.substring(dot5,dot5+3));


			int find_Element_ByW3=docText.indexOf("W3");                        // Extracting values from the PDF
			int id6=docText.indexOf('$',find_Element_ByW3);
			int dot6=docText.indexOf('.',id6);
			Result[5] = (docText.substring(id6+1,dot6)+docText.substring(dot6,dot6+3));


			int find_Element_ByW4=docText.indexOf("W4");                       // Extracting values from the PDF
			int id7=docText.indexOf('$',find_Element_ByW4);
			int dot7=docText.indexOf('.',id7);
			Result[6] = (docText.substring(id7+1,dot7)+docText.substring(dot7,dot7+3));

			// Extracting other values similarly
			System.out.println(Arrays.toString(Result));                     // Printing the extracted values   

			String[][] index= new String[][] {{"D13","G13","B13","J13","I13","W13","Z13","Y13"},              // Defining the cell indices in the Excel sheet
				{"D16","G16","B16","J16","I16","W16","Z16","Y16"},
				{"D19","G19","B19","J19","I19","W19","Z19","Y19"},
				{"D22","G22","B22","J22","I22","W22","Z22","Y22"}
			};

			hashMap=new HashMap<String,ExcelAmount>();                  // Creating and populating the HashMap with extracted values and cell indices
			hashMap.put("1A", new ExcelAmount(Result[0],index[i][0]));	
			hashMap.put("1B", new ExcelAmount(Result[1],index[i][1]));
			hashMap.put("G1", new ExcelAmount(Result[2],index[i][2]));
			hashMap.put("4",  new ExcelAmount(Result[7],index[i][3]));
			hashMap.put("W1", new ExcelAmount(Result[3],index[i][4]));
			hashMap.put("W2", new ExcelAmount(Result[4],index[i][5]));
			hashMap.put("W3", new ExcelAmount(Result[5],index[i][6]));
			hashMap.put("W4", new ExcelAmount(Result[6],index[i][7]));

			for(Map.Entry<String, ExcelAmount> entry: hashMap.entrySet()) {             // Iterating through the HashMap entries and printing key, value, and cell index
				System.out.println(entry.getKey()+" "+entry.getValue().getAmount()+" "+entry.getValue().getCell());
			}

			writeExcel();                   // Writing values to the Excel sheet
			System.out.println("Write pdf Done2");
			pdfDocument.close();              // Closing resources
			fis.close();
		}
	}

	public static void writeExcel() {                        // Method to write values to the Excel sheet
		try (FileInputStream inputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(inputStream)) {
			Sheet sheet = workbook.getSheet(sheetName);                  // Get the sheet

			for(Map.Entry<String, ExcelAmount> entry: hashMap.entrySet()) {                // Iterate through the HashMap entries and write values to corresponding cells
				String valueToWrite = entry.getValue().getAmount();                  	// Find the value corresponding to the key in the HashMap

				CellReference cellReference = new CellReference(entry.getValue().getCell());             		// Get the cell to write to
				Row row = sheet.getRow(cellReference.getRow());
				if (row == null) {
					row = sheet.createRow(cellReference.getRow());
				}
				Cell cell = row.getCell(cellReference.getCol());
				if (cell == null) {
					cell = row.createCell(cellReference.getCol());
				}
				int idx=valueToWrite.indexOf(',');
				if(idx!=-1) {
					valueToWrite=valueToWrite.substring(0,idx)+valueToWrite.substring(idx+1);					
				}
//				style.setDataFormat(BuiltinFormats.getBuiltinFormat("text"))
				cell.setCellValue(Double.parseDouble(valueToWrite));                    // Write the value to the cell
				System.out.println(valueToWrite);
//				System.out.println(Integer.parseInt(valueToWrite));


				try (FileOutputStream outputStream = new FileOutputStream(filePath)) {              	// Write the updated workbook content to the same file
					workbook.write(outputStream);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();                
		}
		System.out.println("Write pdf Done");
	}
}
