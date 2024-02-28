/**
 * 
 */
package com.asis;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asis.util.ExcelUtil;

/**
 * Class for handling ATO data operations
 */
public class ATOdata {

	// WebDriver instance
	public WebDriver driver  = new ChromeDriver();

	// WebDriver wait instance
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	// JavascriptExecutor instance
	JavascriptExecutor js = (JavascriptExecutor) driver;

	// File paths and names
	public final String ATO_FILE_PATH ="C:\\Users\\AsisKaur\\OneDrive - The Outsource Pro\\Desktop";
	public final String ATO_FILE_NAME ="ATO_exel.xlsx";
	public final String ATO_LOGIN_SHEET_NAME ="Login_detail";
	public final String ATO_CLIENT_SHEET_NAME ="Client_data";
	public static HashMap<String, String> clientData;
	public ArrayList<ArrayList<QuaterData>> row_data = new ArrayList<>();

	/**
	 * Method to setup WebDriver
	 */
	public void setupDriver() {
		System.out.println("here");
	}
	/**
	 * Method to launch the ATO site
	 */

	public void lauchSite() {
		this.driver.get("https://onlineservices.ato.gov.au/onlineservices/");
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
	 * Method to get client data
	 * @return HashMap containing client data
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public HashMap<String, String> getClientData() throws InterruptedException, IOException {

		return ExcelUtil.getClientDetail(ATO_CLIENT_SHEET_NAME);

		//return new String[] {"CGA Group Holding PTY LTD", "01/07/2022","30/06/2023"};

	}

	/**
	 * Method to search for client name
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clientName() throws InterruptedException, IOException {
		ExcelUtil.readExcel(ATO_FILE_PATH,ATO_FILE_NAME);
		clientData = getClientData();
		ExcelUtil.closeExcel();

		System.out.println(clientData);
		WebElement clientNameSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search']")));

		Thread.sleep(1000);

		clientNameSearch.sendKeys(clientData.get("client_name").trim());
		Thread.sleep(3000);
		clientNameSearch.sendKeys(Keys.ENTER);
		System.out.println("clientName() run");
	}
	/**
	 * Method to navigate to quarter name
	 * @throws InterruptedException
	 */

	public void goToQuarterName() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement lodgements = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menubar']//span[contains(text(),'Lodgments')]"))); 
		lodgements.click();

		System.out.println("lodgements is clicked");

		List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='menubar']//span[contains(text(),'Lodgments')]/parent::div/following-sibling::ul/li")));
		for(WebElement option:options) {
			System.out.println(option.getText());
			if(option.getText().trim().equalsIgnoreCase("Activity statements")) {
				option.click();
				break;
			}
		}
		Thread.sleep(2000);

		WebElement history = driver.findElement(By.xpath("//div[@class='ato-tab']/ul/li[2]"));
		history.click();

	}

	/**
	 * Method to set "To" and "From" dates
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void setToDateFromDate() throws InterruptedException, ParseException {

		DateFormat fromDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH); 
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		JavascriptExecutor js = (JavascriptExecutor)driver; 

		//Thread.sleep(1000);
		WebElement scrollTo = driver.findElement(By.xpath("//h1/span[contains(text(),'Activity statements')]"));//  
		js.executeScript("arguments[0].scrollIntoView(true);", scrollTo);
		Thread.sleep(2000);
		System.out.println("page scrolled");


		WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Filter')]")));

		Actions act = new Actions(driver);
		act.moveToElement(filter).click().perform();
		System.out.println("Filter clicked");
		//filter.click();
		Thread.sleep(1000);
		// Setting "From" and "To" dates
		try {
			Date from_date = fromDateFormat.parse(clientData.get("from_date"));

			String StringFromDate = outputFormat.format(from_date);
			//		    System.out.println("StringFromDate: " + StringFromDate);///

			Date to_date = fromDateFormat.parse(clientData.get("to_date"));

			String StringToDate = outputFormat.format(to_date);
			//		    System.out.println("StringFromDate: " + StringToDate);

			WebElement From = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='dp-atoo-as-from-date-002']")));
			From.clear();
			From.sendKeys(StringFromDate);

			WebElement To = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='dp-atoo-as-to-date-002']")));
			To.clear();
			To.sendKeys(StringToDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		WebElement filter2 = driver.findElement(By.xpath("//button[@id='atoo-as-atobutton-016']"));
		filter2.click();
	}

	/**
	 * Method to match quarter data
	 * @throws InterruptedException
	 */
	public void matchingQuarter() throws InterruptedException {
		String jul_quater = clientData.get("jul_quater");
		String oct_quarter = clientData.get("oct_quarter");
		String jan_quarter = clientData.get("jan_quarter");
		String apr_quarter = clientData.get("apr_quarter");
		
		if(!jul_quater.isBlank()) {
			//fetch jul data
			System.out.println(jul_quater);
			getQquaterData(jul_quater);
			HashMap<String,Double> data = goToStatementDetail();
			
			ArrayList<QuaterData> jul_quater_data_row = new ArrayList<>();
			QuaterData qd_jul = new QuaterData("Jul");
			jul_quater_data_row.add(qd_jul);
			row_data.add(jul_quater_data_row);


			ArrayList<QuaterData> aug_quater_data_row = new ArrayList<>();
			QuaterData qd_aug = new QuaterData("Aug");
			aug_quater_data_row.add(qd_aug);
			row_data.add(aug_quater_data_row);

			ArrayList<QuaterData> sept_quater_data_row = new ArrayList<>();
			QuaterData qd_sept = new QuaterData("Sept");
			// qd_sept.setDefaultData(qd_sept);
			//double xnc = data.get("G1");
			qd_sept.set_G1(data.get("G1"));
			qd_sept.set_1A(data.get("1A"));
			qd_sept.set_1B(data.get("1B"));
			qd_sept.set_W1(data.get("W1"));
			qd_sept.set_4(data.get("4"));
			qd_sept.set_GST_Refund(qd_sept.get_1A() - qd_sept.get_1B());
			qd_sept.set_ATO_Total_Refund(qd_sept.get_GST_Refund() + qd_sept.get_4() + qd_sept.get_5A() - qd_sept.get_7D());
			sept_quater_data_row.add(qd_sept);
			row_data.add(sept_quater_data_row);
		}
		//Thread.sleep(5000);
		if(!oct_quarter.isBlank()) {
			System.out.println(oct_quarter);
			getQquaterData(oct_quarter);
			HashMap<String,Double> data = goToStatementDetail();
			
			ArrayList<QuaterData> oct_quater_data_row = new ArrayList<>();
			QuaterData qd_oct = new QuaterData("Oct");
			oct_quater_data_row.add(qd_oct);
			row_data.add(oct_quater_data_row);


			ArrayList<QuaterData> nov_quater_data_row = new ArrayList<>();
			QuaterData qd_nov = new QuaterData("Nov");
			nov_quater_data_row.add(qd_nov);
			row_data.add(nov_quater_data_row);

			ArrayList<QuaterData> dec_quater_data_row = new ArrayList<>();
			QuaterData qd_dec = new QuaterData("Dec");
			// qd_sept.setDefaultData(qd_sept);
			//double xnc = data.get("G1");
			qd_dec.set_G1(data.get("G1"));
			qd_dec.set_1A(data.get("1A"));
			qd_dec.set_1B(data.get("1B"));
			qd_dec.set_W1(data.get("W1"));
			qd_dec.set_4(data.get("4"));
			qd_dec.set_GST_Refund(qd_dec.get_1A() - qd_dec.get_1B());
			qd_dec.set_ATO_Total_Refund(qd_dec.get_GST_Refund() + qd_dec.get_4() + qd_dec.get_5A() - qd_dec.get_7D());
			dec_quater_data_row.add(qd_dec);
			row_data.add(dec_quater_data_row);
		}
		if(!jan_quarter.isBlank()) {
			System.out.println(jan_quarter);
			getQquaterData(jan_quarter);
			HashMap<String,Double> data = goToStatementDetail();
			
			ArrayList<QuaterData> jan_quater_data_row = new ArrayList<>();
			QuaterData qd_jan = new QuaterData("Jan");
			jan_quater_data_row.add(qd_jan);
			row_data.add(jan_quater_data_row);


			ArrayList<QuaterData> feb_quater_data_row = new ArrayList<>();
			QuaterData qd_feb = new QuaterData("Feb");
			feb_quater_data_row.add(qd_feb);
			row_data.add(feb_quater_data_row);

			ArrayList<QuaterData> mar_quater_data_row = new ArrayList<>();
			QuaterData qd_mar = new QuaterData("Mar");
			// qd_sept.setDefaultData(qd_sept);
			//double xnc = data.get("G1");
			qd_mar.set_G1(data.get("G1"));
			qd_mar.set_1A(data.get("1A"));
			qd_mar.set_1B(data.get("1B"));
			qd_mar.set_W1(data.get("W1"));
			qd_mar.set_4(data.get("4"));
			qd_mar.set_GST_Refund(qd_mar.get_1A() - qd_mar.get_1B());
			qd_mar.set_ATO_Total_Refund(qd_mar.get_GST_Refund() + qd_mar.get_4() + qd_mar.get_5A() - qd_mar.get_7D());
			mar_quater_data_row.add(qd_mar);
			row_data.add(mar_quater_data_row);
		}
		if(!apr_quarter.isBlank()) {
			System.out.println(apr_quarter);
			getQquaterData(apr_quarter);
           HashMap<String,Double> data = goToStatementDetail();
			
			ArrayList<QuaterData> apr_quater_data_row = new ArrayList<>();
			QuaterData qd_apr = new QuaterData("Apr");
			apr_quater_data_row.add(qd_apr);
			row_data.add(apr_quater_data_row);


			ArrayList<QuaterData> may_quater_data_row = new ArrayList<>();
			QuaterData qd_may = new QuaterData("May");
			may_quater_data_row.add(qd_may);
			row_data.add(may_quater_data_row);

			ArrayList<QuaterData> jun_quater_data_row = new ArrayList<>();
			QuaterData qd_jun = new QuaterData("Jun");
			// qd_sept.setDefaultData(qd_sept);
			//double xnc = data.get("G1");
			qd_jun.set_G1(data.get("G1"));
			qd_jun.set_1A(data.get("1A"));
			qd_jun.set_1B(data.get("1B"));
			qd_jun.set_W1(data.get("W1"));
			qd_jun.set_4(data.get("4"));
			qd_jun.set_GST_Refund(qd_jun.get_1A() - qd_jun.get_1B());
			qd_jun.set_ATO_Total_Refund(qd_jun.get_GST_Refund() + qd_jun.get_4() + qd_jun.get_5A() - qd_jun.get_7D());
			jun_quater_data_row.add(qd_jun);
			row_data.add(jun_quater_data_row);
		}
		Excel obj = new Excel();
		obj.createExcelWithData("Final_data.xls", row_data);
		
	}

	/**
	 * Method to get quarter data
	 * @param quater_statement_name
	 * @throws InterruptedException 
	 */ //span[contains(text(),'Apr 2023 – Jun 2023 Business activity statement')]
	public void getQquaterData(String quater_statement_name) throws InterruptedException {
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		List<WebElement> statements = driver.findElements(By.xpath("//span[contains(text(),'"+quater_statement_name+"')]"));
		System.out.println("statements_size:"+statements.size());
		if(statements.size() > 1) {
			for(WebElement ele : statements) {
				//js.executeScript("arguments[0].style.border='3px solid red'", ele);
				WebElement revision= ele.findElement(By.xpath(".//ancestor::div[@class='table-data-text']//span[contains(text(),'Revision')]"));
				//js.executeScript("arguments[0].style.border='3px solid red'", revision);
				
				if(revision.isDisplayed()) {
					System.out.println("Visible");
					ele.click();
					break;
					//statements.click()
				}
				else {
					System.out.println("Revision is not Visisble");
				}	
			}
		}else {
			
			js.executeScript("arguments[0].click();", statements.get(0).findElement(By.xpath(".//parent::a")));
		}		
	}

	/**
	 * Method to navigate to statement detail
	 * @return 
	 * @throws InterruptedException
	 */
	public HashMap<String, Double> goToStatementDetail() throws InterruptedException {
		HashMap<String, Double> data = new HashMap<>();
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click();", statements.get(0).findElement(By.xpath(".//parent::a")));

			WebElement scrollTo = driver.findElement(By.xpath("//h2/span[contains(text(),'Business activity statement summary')]"));//  
		js.executeScript("arguments[0].scrollIntoView(true);", scrollTo);
		Thread.sleep(1000);

		//JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Print-friendly version']")));
		//driver.findElement(By.xpath("//button[text()='Print-friendly version']")).click();

		Thread.sleep(1000);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		//System.out.println(driver.getTitle());

		Double _1A = Double.parseDouble(driver.findElement(By.xpath("//td[text()='1A']/parent::tr/td[4]")).getText().replaceAll("[$,]", ""));
		System.out.println("_1A "+_1A);
		data.put("1A", _1A);
		
		Double _1B = Double.parseDouble(driver.findElement(By.xpath("//td[text()='1B']/parent::tr/td[5]")).getText().replaceAll("[$,]", ""));
		System.out.println("_1B "+_1B);
		data.put("1B", _1B);
		
		Double _G1 = Double.parseDouble(driver.findElement(By.xpath("//td[text()='G1']/parent::tr/td[3]")).getText().replaceAll("[$,]", ""));
		System.out.println("_G1 "+_G1);
		data.put("G1", _G1);
		
		Double _W1 = Double.parseDouble(driver.findElement(By.xpath("//td[text()='W1']/parent::tr/td[3]")).getText().replaceAll("[$,]", ""));
		System.out.println("_W1 "+_W1);
		data.put("W1", _W1);
		
		Double _4 = Double.parseDouble(driver.findElement(By.xpath("//td[text()='4']/parent::tr/td[4]")).getText().replaceAll("[$,]", ""));
		System.out.println("_4 "+_4);
		data.put("4", _4);

		
		driver.close();
		driver.switchTo().window(tabs.get(0));
//		System.out.println(driver.getTitle());
		js.executeScript("window.scrollBy(0,2000)");
		WebElement back =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ato-button btn btn-default btnHalf']")));
		back.click();
		return data;
	}

	public void ICAStatement() {
		
		WebElement accountsAnsPayments =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menubar']//span[contains(text(),'Accounts and payments')]")));
		accountsAnsPayments.click();
		
		List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='menubar']//span[contains(text(),'Accounts and payments')]/parent::div/following-sibling::ul/li")));
		for(WebElement option:options) {
			if(option.getText().trim().equalsIgnoreCase("Tax accounts")) {
				option.click();
				break;
			}
		}
		
		//table//h2//span[contains(text(),'Activity statement')]
		WebElement activityStatement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//h2//span[contains(text(),'Activity statement')]")));
		activityStatement.click();
	}
	public static void main(String[] args) throws InterruptedException, IOException, ParseException {

		ATOdata obj = new ATOdata(); 

		obj.setupDriver();
		obj.lauchSite();
		obj.login();
		obj.clientName();
//		obj.ICAStatement();
		obj.goToQuarterName();
		obj.setToDateFromDate();
		obj.matchingQuarter();

		//HashMap<String, Object> clientData = ExcelUtil.getClientDetail("Client_data");
		//data = ExcelUtil.getUserLoginDetail("Login_detail");
		//System.out.println(Arrays.deepToString(data));
		//System.out.println(clientData);
		//System.out.println(clientData.get("apr_quarter"));

	}

}
