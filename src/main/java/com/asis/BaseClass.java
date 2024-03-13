package com.asis;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public WebDriver driver;
	// File paths and names
	public final String ATO_FILE_PATH ="C:\\Users\\vinay\\Downloads";
	public final String ATO_FILE_NAME ="ATO_exel.xlsx";
	public final String ATO_LOGIN_SHEET_NAME ="Login_detail";
	public final String ATO_CLIENT_SHEET_NAME ="Client_data";
	
	public static HashMap<String, String> clientData;
	
	public static ArrayList<ArrayList<String>> ACTIVITY_STATEMENT_DATA = new ArrayList<>();	
	public static ArrayList<ArrayList<String>> GST_Reconciliation_DATA = new ArrayList<>();	
	public static ArrayList<ArrayList<QuaterData>> ATO_ROW_DATA = new ArrayList<>();	
	public static ArrayList<ArrayList<QuaterData>> XERO_DATA = new ArrayList<>();
	
	public static ArrayList<HashMap<String, Double>> LAST_TABLE_DATA = new ArrayList<>();
	
	public final String XERO_FILE_PATH ="C:\\Users\\vinay\\Downloads";
	public final String XERO_FILE_NAME ="XeroSheet.xlsx";
	public final String XERO_LOGIN_SHEET_NAME ="Xero";
	
	public static ArrayList<String> tempData = new ArrayList<String>();
	// WebDriver wait instance
	public WebDriverWait wait;

	// JavascriptExecutor instance
	public JavascriptExecutor js;
	
	public QuaterData qd_lastJune = new QuaterData("Jun");
	public QuaterData qd_jul = new QuaterData("Jul");
	public QuaterData qd_aug = new QuaterData("Aug");
	public QuaterData qd_sept = new QuaterData("Sept");
	public QuaterData qd_oct = new QuaterData("Oct");
	public QuaterData qd_nov = new QuaterData("Nov");
	public QuaterData qd_dec = new QuaterData("Dec");
	public QuaterData qd_jan = new QuaterData("Jan");
	public QuaterData qd_feb = new QuaterData("Feb");
	public QuaterData qd_mar = new QuaterData("Mar");
	public QuaterData qd_apr = new QuaterData("Apr");
	public QuaterData qd_may = new QuaterData("May");
	public QuaterData qd_jun = new QuaterData("Jun");
	
	public QuaterData qd_1 = new QuaterData("BAS not yet Paid/(Received)");
	public QuaterData qd_2 = new QuaterData("June BAS (2023)");
	public QuaterData qd_3 = new QuaterData("Add: GST on Debtors");
	public QuaterData qd_4 = new QuaterData("Less: GST on Creditors");
	public QuaterData qd_5 = new QuaterData("GST as per Balance sheet");
	public QuaterData qd_6 = new QuaterData("Reason for Variance:");
	public QuaterData qd_7 = new QuaterData("Reporting variance");
	public QuaterData qd_8 = new QuaterData("Unknown varaince");

	/**
	 * Method to setup WebDriver
	 */
	public void setupDriver() {
		//System.out.println("Driver Setup Done");
		driver  = new ChromeDriver();

	}
	/**
	 * Method to launch the ATO site
	 */

	public void lauchSite(String url) {
		this.driver.get(url);
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		js = (JavascriptExecutor) driver;
	}
	
	
	

	/**
	 * Method to perform login
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void login_ato() throws IOException, InterruptedException {// Filling login details
		ExcelUtil.readExcel(ATO_FILE_PATH,ATO_FILE_NAME);
		//Object[][] data;
		String user_id= ExcelUtil.getUserLoginDetail(ATO_LOGIN_SHEET_NAME);

		System.out.println("login");
		WebElement myGOV = driver.findElement(By.xpath("//a[@id='btn-myGovID']"));
		myGOV.click();

		WebElement emailAddress = driver.findElement(By.xpath("//input[@placeholder='myGovID email']"));
		emailAddress.sendKeys(user_id);

		WebElement loginButton = driver.findElement(By.xpath("//button[@title='Submit']"));
		loginButton.click();
		ExcelUtil.closeExcel();
	}
	
	public void login_xero() {
		// Filling login details
		//System.out.println("login");
		xeroexcel.readExcel(XERO_FILE_PATH,XERO_FILE_NAME);
		String[] loginDetails = xeroexcel.getUserLoginDetail(XERO_LOGIN_SHEET_NAME);
		String userId = loginDetails[0];
		String password = loginDetails[1];

		WebElement Emailaddress = driver.findElement(By.id("xl-form-email"));
		WebElement Password = driver.findElement(By.id("xl-form-password"));
		WebElement loginButton = driver.findElement(By.id("xl-form-submit"));
		Emailaddress.sendKeys(userId);		
		Password.sendKeys(password);			
		loginButton.click();
	}

	/**
	 * Method to quit WebDriver
	 */
	public void tearDown() {
		this.driver.quit();
	}

	public String getClientFromDateAsString(HashMap<String, String> clientData) {
		DateFormat fromDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH); 
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date from_date = fromDateFormat.parse(clientData.get("from_date"));

			String StringFromDate = outputFormat.format(from_date);

			return StringFromDate;

		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getClientToDateAsString(HashMap<String, String> clientData) {
		DateFormat fromDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH); 
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {	   

			Date to_date = fromDateFormat.parse(clientData.get("to_date"));
			String StringToDate = outputFormat.format(to_date);	
			return StringToDate;

		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

}
