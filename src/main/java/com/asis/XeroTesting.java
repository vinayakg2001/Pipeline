/**
 * 
 */
package com.asis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asis.util.ExcelUtil;
import com.asis.util.xeroexcel;

/**
 * Class for Xero Testing operations
 */
public class XeroTesting {

	/**
	 * WebDriver instance
	 */
	public WebDriver driver  = new ChromeDriver();
	public WebDriverWait wait;
	public final String XERO_FILE_PATH ="C:\\Excel";
	public final String XERO_FILE_NAME ="XeroSheet.xlsx";
	public final String XERO_LOGIN_SHEET_NAME ="Xero";
	public static HashMap<String, String> questAns;
	public ArrayList<ArrayList<QuaterData>> row_data = new ArrayList<>();

	/**
	 * Method to get client data
	 * @return HashMap containing client data
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void getQuestAnsw() throws InterruptedException, IOException {
		questAns= xeroexcel.getQuestAnsw(XERO_LOGIN_SHEET_NAME);

	}
	// Variables to store data
	public String A1 = null; 
	public String G1 = null; 
	public String B1 = null; 
	public String payable_amount = null;
	public String recievable_amount = null;
	public String GST_asperBalanceSheet = null;
	public String GSTr = null;

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
	 * Method for two-factor authentication
	 */
	public void twofactorAuth() {
		System.out.println("twofactorAuth");
		try {
			getQuestAnsw();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement anotherAuthMethod = driver.findElement(By.xpath("//button[contains(text(),\"Use another authentication method\")]"));

		anotherAuthMethod.click();		

		WebElement securityQsn = driver.findElement(bySecurityQsn);
		securityQsn.click();
		
		for(Map.Entry<String, String> ele:questAns.entrySet()) {
			System.out.println(ele.getKey()+" "+ele.getValue());
		}
		

		String ques1 = questAns.get("Security_qa1");
		String ans1 = questAns.get("Security_qa1_answer");
		String ques2 = questAns.get("Security_qs2");
		String ans2 = questAns.get("Security_qa2_answer");
		String ques3 = questAns.get("Security_qs3");
		String ans3 = questAns.get("Security_qa3_answer");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement firstquestion =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(@class,\"auth-firstquestion\")]")));
		WebElement firstanswer =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-automationid,\"auth-firstanswer\")]/div/input")));


		WebElement secondquestion =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(@class,\"auth-secondquestion\")]")));
		WebElement secondanswer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-automationid,\"auth-secondanswer\")]/div/input")));
		WebElement submitAns = driver.findElement(By.xpath("//button[@type='submit']"));		

		if(firstquestion.getText().equals(ques1)) {

			firstanswer.sendKeys(ans1);
		}
		else if(firstquestion.getText().equals(ques2)) {

			firstanswer.sendKeys(ans2);
		}
		else{
			firstanswer.sendKeys(ans3);
		}		

		if(secondquestion.getText().equals(ques1)){

			secondanswer.sendKeys(ans1);
		}
		else if(secondquestion.getText().equals(ques2)) {

			secondanswer.sendKeys(ans2);
		}
		else{
			secondanswer.sendKeys(ans3);
		}
		submitAns.click();
	}	
	private List<Map<String, String>> getQuestAnsw(String xERO_LOGIN_SHEET_NAME2) {
		// TODO Auto-generated method stub
		return null;
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
	 * Method to get and Set "From" and "To" dates
	 * @throws InterruptedException
	 */
	public void getAndSetFromAndTodate() throws InterruptedException, ParseException {

		DateFormat fromDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH); 
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			Date from_date = fromDateFormat.parse(questAns.get("From date"));

			String StringFromDate = outputFormat.format(from_date);

			Date to_date = fromDateFormat.parse(questAns.get("To date"));

			String StringToDate = outputFormat.format(to_date);

			WebElement From = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='fromDate']")));
			From.clear();
			From.sendKeys(StringFromDate);

			WebElement To = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='dateTo']")));
			To.clear();
			To.sendKeys(StringToDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	public void GSTReconciliation() throws InterruptedException {
		//a[contains(text(),'GST Reconciliation')]
		WebElement accountingButton = driver.findElement(By.xpath("//button[contains(text(),'Accounting')]"));
		accountingButton.click();

		WebElement GSTreconcil =  driver.findElement( By.xpath("//a[contains(text(),'GST Reconciliation')]"));
		GSTreconcil.click();
		
		ArrayList<String> tabs = new ArrayList<String>();
		List<WebElement> tableTr = driver.findElements(By.xpath("//table[@id='statementTable']/tbody/tr"));

		for(WebElement tr : tableTr) {
			if(tr.isDisplayed()) {
				List<WebElement> tdData =  tr.findElements(By.tagName("td"));
//				ArrayList<String> tdRowData = new ArrayList<String>();
				for(WebElement td: tdData) {
//					tdRowData.add(td.getText());
//					System.out.print(tdRowData+" ");
					 System.out.print(td.getText() + " ");
				}
				System.out.println();
			}		
		}
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
		obj.setupDriver();
		obj.lauchSite();
		obj.login();
		obj.twofactorAuth();
		obj.GSTReconciliation();
//		obj.gotoAccountsClick();
//		obj.captureA1G1B1Data();
//		obj.goToAgedPayableSymmary();
//		obj.goToAgedRecievableSummry();
//		obj.balanceSheet();
		//				obj.createExcel();
	}

}
