package com.asis.util;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asis.QuaterData;

public class BaseClass {
	public WebDriver driver;
	// File paths and names
	public final String ATO_FILE_PATH ="C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\Desktop";
	public final String ATO_FILE_NAME ="ATO_exel.xlsx";
	public final String ATO_LOGIN_SHEET_NAME ="Login_detail";
	public final String ATO_CLIENT_SHEET_NAME ="Client_data";
	public ArrayList<ArrayList<String>> activityStatementData = new ArrayList<>();
	
	public ArrayList<ArrayList<QuaterData>> row_data = new ArrayList<>();
	
	public ArrayList<ArrayList<QuaterData>> XERO_DATA = new ArrayList<>();

	// WebDriver wait instance
	public WebDriverWait wait;

	// JavascriptExecutor instance
	public JavascriptExecutor js;
	
	public QuaterData qd_lastJune = new QuaterData("Jun-22");
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

	/**
	 * Method to setup WebDriver
	 */
	public void setupDriver() {
		System.out.println("here");
		driver  = new ChromeDriver();

	}
	/**
	 * Method to launch the ATO site
	 */

	public void lauchSite() {
		this.driver.get("https://onlineservices.ato.gov.au/onlineservices/");
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
	public void login() throws IOException, InterruptedException {// Filling login details
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
