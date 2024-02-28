package com.asis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Xero {
	
	// setup driver & browser
	// url launch	
	// business logic
	
	// close browser
	
	
	
	
	
	public static void main(String[] args) throws InterruptedException, IOException {

		final String filePath = "C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\-Book9 (2).xlsx";
		final String sheetName = "Sheet1"; // Name of the sheet in the Excel file

		ReadExcel obj = new ReadExcel();   //Creation of Object
		ExcelData data = obj.ReadExcel1("Sheet1", 1, 0);  //Calling ReadExcel Function (Passing sheet name)
		ExcelData data1 = obj.ReadExcel1("Sheet1", 1, 3);         // Start Date
		ExcelData data2 = obj.ReadExcel1("Sheet1", 1, 4);         // End Date

		int serialDateFrom = data1.getFrom(); // The Excel date serial
		java.util.Date javaDateFrom = DateUtil.getJavaDate((double) serialDateFrom);  // Convert serial date to Java Date
		SimpleDateFormat sdfFrom = new SimpleDateFormat("dd MMM yyyy"); // Define the desired date format
		String dateString = sdfFrom.format(javaDateFrom); // Format the date
		String adjustedDateString =	 null;
		try {
			Date date = sdfFrom.parse(dateString);        // Parse the original date string
			Calendar calendar = Calendar.getInstance();        		// Adjust the month
			calendar.setTime(date);
			//			calendar.add(Calendar.MONTH, 1); // Add 1 month
			SimpleDateFormat sdfOutput = new SimpleDateFormat("dd MMM yyyy");        // Format the adjusted date to the desired string format
			adjustedDateString = sdfOutput.format(calendar.getTime());
			System.out.println("Adjusted Date: " + adjustedDateString);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		// Convert end date
		int serialDateTo = data2.getTo(); // The Excel date serial
		java.util.Date javaDateTo = DateUtil.getJavaDate((double) serialDateTo);  // Convert serial date to Java Date
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd MMM yyyy"); // Define the desired date format
		String formattedDateTo = sdfTo.format(javaDateTo); // Format the date
		System.out.println(formattedDateTo);
		System.out.println(data.getClientName());           // Print client name



		System.setProperty("webdriver.chrome.driver","C:\\selenium webdriver\\chromedriver-win64\\chromedriver.exe"); // Setting up WebDriver for Chrome
		WebDriver driver = new ChromeDriver();
		driver.get("https://login.xero.com/");
		driver.manage().window().maximize();
		WebElement Emailaddress = driver.findElement(By.id("xl-form-email")); // Filling login details
		Emailaddress.sendKeys("accountant2@fortunaadvisors.com.au");

		WebElement Password = driver.findElement(By.id("xl-form-password"));
		Password.sendKeys("User123456@");	

		WebElement loginButton = driver.findElement(By.id("xl-form-submit"));
		loginButton.click();

//		Thread.sleep(5000);
		WebElement UseanotherauthenticationmethodButton = fluentWait(driver, By.xpath("/html/body/div/div/div/div/button"));  // Handling authentication methods
		UseanotherauthenticationmethodButton.click();
//		Thread.sleep(1000);

		WebElement SecurityquestionsButton =  fluentWait(driver, By.xpath("/html/body/div/div/div/div/div/div/div/div[2]/button"));  // Filling security questions
		SecurityquestionsButton.click();
//		Thread.sleep(1000);
		//... Continue filling security questions and handling them
		WebElement Question1 = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[1]/label"));// Filling security questions
		//		System.out.println(Question1.getText());

		String answerOne="";

		if(Question1.getText().equals("What is your dream job?")) {
			answerOne="Fortuna";
		}
		else if(Question1.getText().equals("What is your dream car?")) {
			answerOne="Fortuna1";
		}
		else{
			answerOne="Fortuna2";
		}

		WebElement fillAnswerOne = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[1]/div/input"));
		fillAnswerOne.sendKeys(answerOne);

		WebElement Question2 = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[2]/label"));
		//		System.out.println(Question2.getText());

		String answerTwo="";

		if(Question2.getText().equals("What is your dream job?")){
			answerTwo="Fortuna";
		}
		else if(Question2.getText().equals("What is your dream car?")) {
			answerTwo="Fortuna1";
		}
		else{
			answerTwo="Fortuna2";
		}

		WebElement fillAnswerTwo = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[2]/div/input"));
		fillAnswerTwo.sendKeys(answerTwo);

		WebElement ConfirmButton = driver.findElement(By.xpath("/html/body/div/div/div/div/form/button[1]"));
		ConfirmButton.click();
//		Thread.sleep(9000);

		try {
//			Thread.sleep(5000);
			WebElement accountingButton =  fluentWait(driver, By.xpath("//*[@id=\"shell-nav\"]/header/div/nav/ol/li[3]/button"));
			accountingButton.click();
//			Thread.sleep(3000);
		} 
		catch (NoSuchElementException e){
			System.out.println("Element not found exception message :  "  + e.getMessage());
		} 
		try {       	// Click Activity Statement
//			Thread.sleep(3000);
			WebElement activitySatement =  fluentWait(driver, By.xpath("//*[@id=\"shell-nav\"]/header/div/nav/ol/li[3]/div/div[2]/div/ol[2]/li[1]/a"));
			activitySatement.click();
			Thread.sleep(3000);
		}
		catch(NoSuchElementException e){
			System.out.println("Element not found exception message :  "  +e.getMessage());
		}
		// Find the element you want to send keys to and perform Ctrl+A action
//		Thread.sleep(7000);
		WebElement From = driver.findElement(By.xpath("//*[@id=\"fromDate\"]"));
		From.click();
		Actions actions = new Actions(driver);            	// Create an instance of Actions class
		actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform(); 
		From.sendKeys(adjustedDateString);

//		Thread.sleep(3000);
		WebElement To = driver.findElement(By.xpath("//*[@id=\"dateTo\"]"));
		To.click();            
		actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform(); 
		To.sendKeys(formattedDateTo);

		WebElement UpdateButton = driver.findElement(By.xpath("//*[@id=\"ext-gen27\"]"));
		UpdateButton.click();
		Thread.sleep(9000);

		
		WebElement G1 =  fluentWait(driver, By.xpath("//*[@id=\"R4C4\"]"));
		WebElement A1 =  fluentWait(driver, By.xpath("//*[@id=\"R15C4\"]"));
		WebElement B1 =  fluentWait(driver, By.xpath("//*[@id=\"R19C4\"]"));
		
		String g1Value = G1.getText();
		String a1Value = A1.getText();
		String b1Value = B1.getText();
		Thread.sleep(3000);
		try{
//			Thread.sleep(10000);
			WebElement accountingButton =  fluentWait(driver, By.xpath("//*[@id=\"header\"]/header/div/nav/ol/li[3]/button"));
			accountingButton.click();
			Thread.sleep(3000);
		} 
		catch (NoSuchElementException e){
			System.out.println("Element not found exception message :  "  + e.getMessage());
		} 
		Thread.sleep(3000);
		WebElement recievable  =  fluentWait(driver, By.xpath("//*[@id=\"header\"]/header/div/nav/ol/li[3]/div/div[2]/div/ol[2]/li[3]/a"));
		recievable.click();
		Thread.sleep(6000);
		WebElement colSelected  =  fluentWait(driver, By.xpath("//*[@id=\"report-settings-columns-button\"]"));
		colSelected.click();
		Thread.sleep(3000);
		WebElement outstanding_gst_rec  =  fluentWait(driver, By.xpath("//*[@id=\"column-selection-taxamountdue\"]/div/div/label"));
		outstanding_gst_rec.click();
		Thread.sleep(6000);
		WebElement end_of_month  =  fluentWait(driver, By.xpath("//*[@id=\"report-settings\"]/div/div/div[1]/div/div[2]/button/div"));
		end_of_month.click();
		Thread.sleep(3000);
		WebElement last_financial_year  =  fluentWait(driver, By.xpath("//*[@id=\"report-settings-date-option-4\"]/button/span[1]"));
		last_financial_year.click();
		Thread.sleep(3000);
		WebElement update  =  fluentWait(driver, By.xpath("//*[@id=\"report-settings\"]/div/div/div[7]/button"));
		update.click();
		Thread.sleep(4000);
		WebElement GST1  =  fluentWait(driver, By.xpath("/html/body/div[4]/div[2]/div[5]/div[1]/div/div/div/span/div/div/div/div/div[1]/table/tbody/tr[6]/td[9]/span/div"));
		String recievable_amount= GST1.getText();
		try{
			Thread.sleep(10000);
			WebElement accountingButton =  fluentWait(driver, By.xpath("//*[@id=\"header\"]/header/div/nav/ol/li[3]/button"));
			accountingButton.click();
			Thread.sleep(3000);
		} 
		catch (NoSuchElementException e){
			System.out.println("Element not found exception message :  "  + e.getMessage());
		} 
		Thread.sleep(3000);
		WebElement payable =  fluentWait(driver, By.xpath("/html/body/div[1]/header/div/nav/ol/li[3]/div/div[2]/div/ol[2]/li[2]/a"));
		payable.click();
		Thread.sleep(5000);
		WebElement colmSelected =  fluentWait(driver, By.xpath("//*[@id=\"report-settings-columns-button\"]"));
		colmSelected.click();
		Thread.sleep(3000);
		WebElement Outstanding_GST =  fluentWait(driver, By.xpath("//*[@id=\"column-selection-taxamountdue\"]/div/div/label"));
		Outstanding_GST.click();
		Thread.sleep(3000);
		WebElement endOfMonth =  fluentWait(driver, By.xpath("//*[@id=\"report-settings\"]/div/div/div[1]/div/div[2]/button/div"));
		endOfMonth.click();
		Thread.sleep(3000);
		WebElement lastFinancialYear =  fluentWait(driver, By.xpath("//*[@id=\"report-settings-date-option-4\"]/button/span[1]"));
		lastFinancialYear.click();
		Thread.sleep(3000);
		WebElement Update =  fluentWait(driver, By.xpath("//*[@id=\"report-settings\"]/div/div/div[7]/button"));
		Update.click();
		Thread.sleep(3000);
		WebElement GST2 =  fluentWait(driver, By.xpath("/html/body/div[4]/div[2]/div[5]/div[1]/div/div/div/span/div/div/div/div/div[1]/table/tbody/tr[13]/td[9]/span/div"));
		String payable_amount= GST2.getText();

		try{
			Thread.sleep(7000);
			WebElement accountingButton =  fluentWait(driver, By.xpath("//*[@id=\"header\"]/header/div/nav/ol/li[3]/button"));
			accountingButton.click();
			Thread.sleep(3000);
		} 
		catch (NoSuchElementException e){
			System.out.println("Element not found exception message :  "  + e.getMessage());
		}
//		Thread.sleep(3000);

		WebElement BalanceSheet =  fluentWait(driver, By.xpath("/html/body/div[1]/header/div/nav/ol/li[3]/div/div[2]/div/ol[2]/li[4]/a"));
		BalanceSheet.click();
//		Thread.sleep(7000);

		WebElement GST =  fluentWait(driver, By.xpath("/html/body/div[4]/div[2]/div[5]/div[1]/div/div/div/span/div/div/div/div/div[1]/table/tbody/tr[29]/td[2]/span/a"));
		String GST_asperBalanceSheet= GST.getText();

		driver.quit();
		// Write data to Excel
		System.out.println(g1Value);
		System.out.println(a1Value);
		System.out.println(b1Value);
		DataExcel data_excel = new DataExcel();
		data_excel.dataExcel(g1Value, "Sheet1", 24, 1);  //Calling ReadExcel Function (Passing sheet name)
		data_excel.dataExcel(a1Value, "Sheet1", 24, 3);  //Calling ReadExcel Function (Passing sheet name)
		data_excel.dataExcel(b1Value, "Sheet1", 24, 6);  //Calling ReadExcel Function (Passing sheet name)
		data_excel.dataExcel(recievable_amount, "Sheet1", 31, 3);  //Calling ReadExcel Function (Passing sheet name)
		data_excel.dataExcel(payable_amount, "Sheet1", 30, 3);  //Calling ReadExcel Function (Passing sheet name)
		data_excel.dataExcel(GST_asperBalanceSheet, "Sheet1", 34, 3);  //Calling ReadExcel Function (Passing sheet name)
		WriteExcel we =new WriteExcel();
		try {
			we.Read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();   
		}
		System.out.println("writing pdf values data done");
	}
	public static WebElement fluentWait(WebDriver driver, By locator) {
		FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(30)) // Adjust timeout as per your requirement
				.pollingEvery(Duration.ofMillis(500)) // Polling frequency
				.ignoring(NoSuchElementException.class); // Ignore exception if element not found

		return wait.until((Function<WebDriver, WebElement>) driver1 -> driver1.findElement(locator));
	}
}


