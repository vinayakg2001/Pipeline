/**
 * 
 */
package com.asis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Class for Xero Testing operations
 */
public class XeroTesting {

	/**
     * WebDriver instance
     */
	public WebDriver driver  = new ChromeDriver();
	
	// Variables to store data
	public String A1 = null; 
	public String G1 = null; 
	public String B1 = null; 
	public String payable_amount = null;
	public String recievable_amount = null;
	public String GST_asperBalanceSheet = null;

	 // Locator for security questions button
	By bySecurityQsn =By.xpath("//button[contains(text(),'Security questions')]");

	/**
     * Method to setup WebDriver
     */
	public void setupDriver() {
		System.out.println("here");
	}
	 /**
     * Method to launch the Xero site
     */
	public void lauchSite() {
		this.driver.get("https://login.xero.com/");
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	/**
     * Method to quit WebDriver
     */
	public void tearDown() {
		this.driver.quit();
	}

	/**
     * Method to perform login
     */
	public void login() {
		// Filling login details
		System.out.println("login");
		WebElement Emailaddress = driver.findElement(By.id("xl-form-email"));
		WebElement Password = driver.findElement(By.id("xl-form-password"));
		WebElement loginButton = driver.findElement(By.id("xl-form-submit"));
		Emailaddress.sendKeys("accountant2@fortunaadvisors.com.au");		
		Password.sendKeys("User123456@");			
		loginButton.click();
	}
	/**
     * Method for two-factor authentication
     */
	public void twofactorAuth() {
		System.out.println("twofactorAuth");
		WebElement anotherAuthMethod = driver.findElement(By.xpath("//button[contains(text(),\"Use another authentication method\")]"));

		anotherAuthMethod.click();		

		WebElement securityQsn = driver.findElement(bySecurityQsn);

		securityQsn.click();

		WebElement firstquestion = driver.findElement(By.xpath("//label[contains(@class,\"auth-firstquestion\")]"));
		WebElement firstanswer = driver.findElement(By.xpath("//div[contains(@data-automationid,\"auth-firstanswer\")]/div/input"));


		WebElement secondquestion = driver.findElement(By.xpath("//label[contains(@class,\"auth-secondquestion\")]"));
		WebElement secondanswer = driver.findElement(By.xpath("//div[contains(@data-automationid,\"auth-secondanswer\")]/div/input"));
		WebElement submitAns = driver.findElement(By.xpath("//button[@type='submit']"));		

		if(firstquestion.getText().equals("What is your dream job?")) {

			firstanswer.sendKeys("Fortuna");
		}
		else if(firstquestion.getText().equals("What is your dream car?")) {

			firstanswer.sendKeys("Fortuna1");
		}
		else{
			firstanswer.sendKeys("Fortuna2");
		}		

		if(secondquestion.getText().equals("What is your dream job?")){

			secondanswer.sendKeys("Fortuna");
		}
		else if(secondquestion.getText().equals("What is your dream car?")) {

			secondanswer.sendKeys("Fortuna1");
		}
		else{
			secondanswer.sendKeys("Fortuna2");
		}
		submitAns.click();
	}
	
	/**
     * Method to navigate to accounts and click
     */
	public void gotoAccountsClick() {
		System.out.println("gotoAccountsClick");
		WebElement accountingButton = driver.findElement(By.xpath("//button[@data-name='navigation-menu/accounting']"));
		accountingButton.click();

		WebElement activitySatement =   driver.findElement(By.xpath("//a[contains(text(),'Activity Statement')]"));
		activitySatement.click();

	}

	 /**
     * Method to get "From" and "To" dates
     * @return Array containing "From" and "To" dates
     * @throws InterruptedException
     */
	public String[] getFromAndTodate() throws InterruptedException {
		System.out.println("getFromAndTodate");
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
			SimpleDateFormat sdfOutput = new SimpleDateFormat("dd MMM yyyy");        // Format the adjusted date to the desired string format
			adjustedDateString = sdfOutput.format(calendar.getTime());

		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		// Convert end date
		int serialDateTo = data2.getTo(); // The Excel date serial
		java.util.Date javaDateTo = DateUtil.getJavaDate((double) serialDateTo);  // Convert serial date to Java Date
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd MMM yyyy"); // Define the desired date format
		String formattedDateTo = sdfTo.format(javaDateTo); // Format the date 
		return new String[] {adjustedDateString,formattedDateTo};
	}
	 /**
     * Method to set "From" and "To" dates
     * @throws InterruptedException
     */

	public void setToDateFromDate() throws InterruptedException {
		System.out.println("setToDateFromDate");
		String[] toDateFromDate = getFromAndTodate();
		WebElement From = driver.findElement(By.xpath("//input[@id='fromDate']"));
		From.clear();
		From.sendKeys(toDateFromDate[0]);

		WebElement To = driver.findElement(By.xpath("//input[@id='dateTo']"));
		To.clear();           
		To.sendKeys(toDateFromDate[1]);

		WebElement UpdateButton = driver.findElement(By.xpath("//a[@id='ext-gen27']"));
		UpdateButton.click();
	}

	 /**
     * Method to capture A1, G1, B1 data
     */
	public void captureA1G1B1Data() {
		System.out.println("captureA1G1B1Data");
		G1 = driver.findElement(By.xpath("//tr//descendant::span[contains(text(),'Total sales')]/ancestor::tr/td[3]//descendant::span[1]")).getText();
		A1 = driver.findElement(By.xpath("//tr//descendant::span[contains(text(),'GST on sales')]/ancestor::tr/td[3]//descendant::span[1]")).getText();
		B1 = driver.findElement(By.xpath("//tr//descendant::span[contains(text(),'GST on purchases')]/ancestor::tr/td[3]//descendant::span[1]")).getText();

		System.out.println("G1 "+G1+" A1 "+A1+" B1 "+B1);
	}
	
	/**
     * Method to navigate to Aged Payable Summary
     * @throws InterruptedException
     */
	public void goToAgedPayableSymmary() throws InterruptedException {
		System.out.println("goToAgedPayableSymmary");
		WebElement accountingButton = driver.findElement(By.xpath("//button[contains(text(),'Accounting')]"));
		accountingButton.click();

		WebElement payable =  driver.findElement(By.xpath("//a[contains(text(),'Aged Payables Summary')]"));
		payable.click();

		WebElement colmSelected =  driver.findElement( By.xpath("//button[@id='report-settings-columns-button']"));
		colmSelected.click();
		Thread.sleep(3000);
		WebElement Outstanding_GST =  driver.findElement( By.xpath("//span[contains(text(),'Outstanding GST')]"));
		Outstanding_GST.click();
		Thread.sleep(1000);
		WebElement endOfMonth =  driver.findElement( By.xpath("//body/div[4]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[2]/button[1]/div[1]"));
		endOfMonth.click();

		WebElement lastFinancialYear =  driver.findElement( By.xpath("//span[contains(text(),'End of last quarter')]"));
		lastFinancialYear.click();

		WebElement Update =  driver.findElement( By.xpath("//button[contains(text(),'Update')]"));
		Update.click();

		WebElement GST2 =  driver.findElement( By.xpath("//tr//descendant::div[text()='Total']/ancestor::tr/td[9]/span/div"));
		payable_amount= GST2.getText();
		System.out.println("payable_amount "+payable_amount);
		System.out.println("G1 "+G1+" A1 "+A1+" B1 "+B1);
	}

	/**
     * Method to navigate to Aged Receivable Summary
     * @throws InterruptedException
     */
	public void goToAgedRecievableSummry() throws InterruptedException {
		System.out.println("goToAgedRecievableSummry");
		WebElement accountingButton = driver.findElement(By.xpath("//button[contains(text(),'Accounting')]"));
		accountingButton.click();

		WebElement recievable  =  driver.findElement( By.xpath("//a[contains(text(),'Aged Receivables Summary')]"));
		recievable.click();

		WebElement colSelected  =  driver.findElement( By.xpath("//button[@id='report-settings-columns-button']"));
		colSelected.click();
		Thread.sleep(3000);
		WebElement outstanding_gst_rec  =  driver.findElement( By.xpath("//label[@data-automationid='column-selection-taxamountdue--body--checkbox']"));
		outstanding_gst_rec.click();

		WebElement end_of_month  =  driver.findElement( By.xpath("//body/div[4]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[2]/button[1]/div[1]"));
		end_of_month.click();

		WebElement last_financial_year  =  driver.findElement( By.xpath("//span[contains(text(),'End of last financial year')]"));
		last_financial_year.click();

		WebElement update  =  driver.findElement( By.xpath("//*[@id=\"report-settings\"]/div/div/div[7]/button"));
		update.click();

		WebElement GST1  =  driver.findElement( By.xpath("//tr//descendant::div[text()='Total']/ancestor::tr/td[9]/span/div"));
		recievable_amount= GST1.getText();
		System.out.println("recievable_amount "+recievable_amount);
	}
	
	 /**
     * Method to navigate to Balance Sheet
     * @throws InterruptedException
     */
	public void balanceSheet() throws InterruptedException {
		System.out.println("balanceSheet");
		WebElement accountingButton = driver.findElement(By.xpath("//button[contains(text(),'Accounting')]"));
		accountingButton.click();
		WebElement balanceSheet =  driver.findElement( By.xpath("//a[contains(text(),'Balance Sheet')]"));
		balanceSheet.click();
//		WebElement dropDown =  driver.findElement( By.xpath("//*[@id=\"report-settings\"]/div/div/div[1]/div/div[2]/button/div"));
//		dropDown.click();
//		WebElement select =  driver.findElement( By.xpath("//body/div[8]/div[2]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/button[1]/span[1]"));
//		select.click();
//		WebElement update =  driver.findElement( By.xpath("//button[contains(text(),'Update')]"));
//		update.click();
//		Thread.sleep(3000);
		WebElement GST =  driver.findElement(By.xpath("//tr//descendant::div[contains(text(),'GST')]/ancestor::tr/td[2]//a"));
		GST_asperBalanceSheet= GST.getText().replaceAll("[()]", "");
		System.out.println("GST_asperBalanceSheet  "+GST_asperBalanceSheet);
	}
	
	  /**
     * Method to create Excel
     * @throws FileNotFoundException
     * @throws IOException
     */
	public void createExcel() throws FileNotFoundException, IOException {
		
	}
	 /**
     * Main method
     * @param args
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		XeroTesting obj = new XeroTesting();
//		obj.setupDriver();
//		obj.lauchSite();
//		obj.login();
//		obj.twofactorAuth();
//		obj.gotoAccountsClick();
//		obj.setToDateFromDate();
//		obj.captureA1G1B1Data();
//		obj.goToAgedPayableSymmary();
//		obj.goToAgedRecievableSummry();
//		obj.balanceSheet();
				obj.createExcel();
	}

}
