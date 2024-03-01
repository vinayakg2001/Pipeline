/**
 * @auther : Shrikant Rakshe
 * 28 Feb 2024
 */
package com.asis;

//table//tr[1][@class='table-row']//a[1]
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

import com.asis.util.BaseClass;
import com.asis.util.ExcelUtil;

/**
 * Class for handling ATO data operations
 */
public class ATOdata extends BaseClass {

	// WebDriver instance

	public static HashMap<String, String> clientData;
	

	/**
	 * Method to get client data
	 * @return HashMap containing client data
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public HashMap<String, String> getClientData() throws InterruptedException, IOException {
		return ExcelUtil.getClientDetail(ATO_CLIENT_SHEET_NAME);

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

		WebElement scrollTo = driver.findElement(By.xpath("//h1/span[contains(text(),'Activity statements')]"));//  
		js.executeScript("arguments[0].scrollIntoView(true);", scrollTo);
		Thread.sleep(2000);
		System.out.println("page scrolled");


		WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Filter')]")));

		Actions act = new Actions(driver);
		act.moveToElement(filter).click().perform();
		System.out.println("Filter clicked");

		Thread.sleep(1000);
		// Setting "From" and "To" dates
		try {
			Date from_date = fromDateFormat.parse(clientData.get("from_date"));

			String StringFromDate = outputFormat.format(from_date);

			Date to_date = fromDateFormat.parse(clientData.get("to_date"));

			String StringToDate = outputFormat.format(to_date);

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
			
			ArrayList<QuaterData> last_jun_quater_data_row = new ArrayList<>();
			
			last_jun_quater_data_row.add(qd_lastJune);
			row_data.add(last_jun_quater_data_row);

			ArrayList<QuaterData> jul_quater_data_row = new ArrayList<>();
			
			jul_quater_data_row.add(qd_jul);
			row_data.add(jul_quater_data_row);

			ArrayList<QuaterData> aug_quater_data_row = new ArrayList<>();
			
			aug_quater_data_row.add(qd_aug);
			row_data.add(aug_quater_data_row);

			ArrayList<QuaterData> sept_quater_data_row = new ArrayList<>();
								
			qd_sept.set_G1(data.get("G1"),true);
			qd_sept.set_1A(data.get("1A"),true);
			qd_sept.set_1B(data.get("1B"),true);
			qd_sept.set_W1(data.get("W1"),true);
			qd_sept.set_4(data.get("4"),true);
			qd_sept.set_GST_Refund(qd_sept.get_1A() - qd_sept.get_1B(),true);
			qd_sept.set_ATO_Total_Refund((qd_sept.get_GST_Refund() + qd_sept.get_4() + qd_sept.get_5A() - qd_sept.get_7D()),true);
			sept_quater_data_row.add(qd_sept);
			row_data.add(sept_quater_data_row);
		}
		if(!oct_quarter.isBlank()) {
			System.out.println(oct_quarter);
			getQquaterData(oct_quarter);
			HashMap<String,Double> data = goToStatementDetail();

			ArrayList<QuaterData> oct_quater_data_row = new ArrayList<>();
			
			oct_quater_data_row.add(qd_oct);
			row_data.add(oct_quater_data_row);


			ArrayList<QuaterData> nov_quater_data_row = new ArrayList<>();
			
			nov_quater_data_row.add(qd_nov);
			row_data.add(nov_quater_data_row);

			ArrayList<QuaterData> dec_quater_data_row = new ArrayList<>();
			
			qd_dec.set_G1(data.get("G1"),true);
			qd_dec.set_1A(data.get("1A"),true);
			qd_dec.set_1B(data.get("1B"),true);
			qd_dec.set_W1(data.get("W1"),true);
			qd_dec.set_4(data.get("4"),true);
			qd_dec.set_GST_Refund(qd_dec.get_1A() - qd_dec.get_1B(),true);
			qd_dec.set_ATO_Total_Refund(qd_dec.get_GST_Refund() + qd_dec.get_4() + qd_dec.get_5A() - qd_dec.get_7D(),true);
			dec_quater_data_row.add(qd_dec);
			row_data.add(dec_quater_data_row);
		}
		if(!jan_quarter.isBlank()) {
			System.out.println(jan_quarter);
			getQquaterData(jan_quarter);
			HashMap<String,Double> data = goToStatementDetail();

			ArrayList<QuaterData> jan_quater_data_row = new ArrayList<>();
			
			jan_quater_data_row.add(qd_jan);
			row_data.add(jan_quater_data_row);


			ArrayList<QuaterData> feb_quater_data_row = new ArrayList<>();
			
			feb_quater_data_row.add(qd_feb);
			row_data.add(feb_quater_data_row);

			ArrayList<QuaterData> mar_quater_data_row = new ArrayList<>();
			
			qd_mar.set_G1(data.get("G1"),true);
			qd_mar.set_1A(data.get("1A"),true);
			qd_mar.set_1B(data.get("1B"),true);
			qd_mar.set_W1(data.get("W1"),true);
			qd_mar.set_4(data.get("4"),true);
			qd_mar.set_GST_Refund(qd_mar.get_1A() - qd_mar.get_1B(),true);
			qd_mar.set_ATO_Total_Refund(qd_mar.get_GST_Refund() + qd_mar.get_4() + qd_mar.get_5A() - qd_mar.get_7D(),true);
			mar_quater_data_row.add(qd_mar);
			row_data.add(mar_quater_data_row);
		}
		if(!apr_quarter.isBlank()) {
			
			System.out.println(apr_quarter);
			getQquaterData(apr_quarter);
			HashMap<String,Double> data = goToStatementDetail();

			ArrayList<QuaterData> apr_quater_data_row = new ArrayList<>();
			
			apr_quater_data_row.add(qd_apr);
			row_data.add(apr_quater_data_row);

			ArrayList<QuaterData> may_quater_data_row = new ArrayList<>();
			
			may_quater_data_row.add(qd_may);
			row_data.add(may_quater_data_row);

			ArrayList<QuaterData> jun_quater_data_row = new ArrayList<>();
			
			qd_jun.set_G1(data.get("G1"),true);
			qd_jun.set_1A(data.get("1A"),true);
			qd_jun.set_1B(data.get("1B"),true);
			qd_jun.set_W1(data.get("W1"),true);
			qd_jun.set_4(data.get("4"),true);
			qd_jun.set_GST_Refund(qd_jun.get_1A() - qd_jun.get_1B(),true);
			qd_jun.set_ATO_Total_Refund(qd_jun.get_GST_Refund() + qd_jun.get_4() + qd_jun.get_5A() - qd_jun.get_7D(),true);
			jun_quater_data_row.add(qd_jun);
			row_data.add(jun_quater_data_row);
		}
	}
	
	public void getXeroData() {
		ArrayList<QuaterData> xero_data = new ArrayList<>();
		QuaterData xeroObj = new QuaterData("As per the book");
		xeroObj.set_G1(51020.00,false);
		xeroObj.set_1A(4517.95,false);
		xeroObj.set_1B(7150.36,false);
		xeroObj.set_W1(0,false);
		xeroObj.set_4(0,false);
		xeroObj.set_GST_Refund(xeroObj.get_1A() - xeroObj.get_1B(),false);
		xeroObj.set_ATO_Total_Refund(xeroObj.get_GST_Refund() + xeroObj.get_4() + xeroObj.get_5A() - xeroObj.get_7D(),false);
		xero_data.add(xeroObj);
		XERO_DATA.add(xero_data);
		
		ArrayList<QuaterData> variance_data = new ArrayList<>();
		QuaterData variance = new QuaterData("Variance");
		variance.set_G1(QuaterData.getTotal_of_year_G1() - xeroObj.get_G1(),false);
		variance.set_1A(QuaterData.getTotal_of_year_1A() - xeroObj.get_1A(),false);
		variance.set_1B(QuaterData.getTotal_of_year_1B() - xeroObj.get_1B(),false);
		variance.set_W1(QuaterData.getTotal_of_year_W1() - xeroObj.get_W1(),false);
		variance.set_4(QuaterData.getTotal_of_year_4() - xeroObj.get_4(),false);
		variance.set_GST_Refund(variance.get_1A() - variance.get_1B(),false);
		variance.set_ATO_Total_Refund(variance.get_GST_Refund() + variance.get_4() + variance.get_5A() - variance.get_7D(),false);
		variance_data.add(variance);
		XERO_DATA.add(variance_data);	
		
		ArrayList<QuaterData> bas_relodged_data = new ArrayList<>();
		QuaterData bas_relodged = new QuaterData("BAS to be relodged for Period ended Jun 23");
		bas_relodged.set_G1(qd_jun.get_G1() - variance.get_G1(),false);
		bas_relodged.set_1A(qd_jun.get_1A() - variance.get_1A(),false);
		bas_relodged.set_1B(qd_jun.get_1B() - variance.get_1B(),false);
		bas_relodged.set_W1(qd_jun.get_W1() - variance.get_W1(),false);
		bas_relodged.set_4(qd_jun.get_4() - variance.get_4(),false);
		bas_relodged.set_GST_Refund(qd_jun.get_GST_Refund() - variance.get_GST_Refund(),false);
		bas_relodged.set_ATO_Total_Refund(bas_relodged.get_GST_Refund() + bas_relodged.get_4() + bas_relodged.get_5A() - bas_relodged.get_7D(),false);
		bas_relodged_data.add(bas_relodged);
		XERO_DATA.add(bas_relodged_data);	
	}
	
	public void generateExcel() {
		Excel obj = new Excel();
		obj.createFinancialSummaryExcelWithData("Final_data.xls", row_data, XERO_DATA);
	}
	
	
	/**
	 * Method to get quarter data
	 * @param quater_statement_name
	 * @throws InterruptedException 
	 */
	public void getQquaterData(String quater_statement_name) throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> statements = driver.findElements(By.xpath("//span[contains(text(),'"+quater_statement_name+"')]"));
		System.out.println("statements_size:"+statements.size());

		if(statements.size() > 1) {
			for(WebElement ele : statements) {
				WebElement revision= ele.findElement(By.xpath(".//ancestor::div[@class='table-data-text']//span[contains(text(),'Revision')]"));				
				if(revision.isDisplayed()) {
					System.out.println("Visible");
					ele.click();
					break;
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

		WebElement scrollTo = driver.findElement(By.xpath("//h2/span[contains(text(),'Business activity statement summary')]"));//  
		js.executeScript("arguments[0].scrollIntoView(true);", scrollTo);
		Thread.sleep(1000);
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Print-friendly version']")));


		Thread.sleep(1000);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

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
		js.executeScript("window.scrollBy(0,2000)");
		WebElement back =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ato-button btn btn-default btnHalf']")));
		back.click();
		return data;
	}

	public void gotoICAStatement() throws InterruptedException {

		WebElement accountsAnsPayments =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menubar']//span[contains(text(),'Accounts and payments')]")));
		accountsAnsPayments.click();

		List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='menubar']//span[contains(text(),'Accounts and payments')]/parent::div/following-sibling::ul/li")));
		for(WebElement option:options) {
			if(option.getText().trim().equalsIgnoreCase("Tax accounts")) {
				option.click();
				break;
			}
		}
		WebElement activityStatements =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Activity statement')]/ancestor::div[@class='row table-panel-header']/following-sibling::div//span[contains(text(),'Activity statement ')]/parent::a")));
		activityStatements.click();


		WebElement filter =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Filter')]/parent::a")));
		js.executeScript("arguments[0].scrollIntoView(true)", filter);
		Thread.sleep(1000);

		filter.click();


		WebElement fromDate =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'from-date')]")));
		fromDate.clear();
		fromDate.sendKeys(getClientFromDateAsString(clientData));

		WebElement toDate =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'to-date')]")));
		toDate.clear();
		toDate.sendKeys(getClientToDateAsString(clientData));

		System.out.println("date entered");
		//button[contains(text(),'Filter')]
		WebElement submit =  driver.findElement(By.xpath("//button[contains(text(),'Filter')]"));
		js.executeScript("arguments[0].click();", submit);

		System.out.println("Submit button clicked");

		WebElement printFriendlyVersion =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Print-friendly version')]")));
		js.executeScript("arguments[0].click();", printFriendlyVersion);

	}

	public void fetchICAStatementData() {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		List<WebElement> tableTr = driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));

		for(WebElement tr : tableTr) {
			if(tr.isDisplayed()) {
				List<WebElement> tdData =  tr.findElements(By.tagName("td"));
				ArrayList<String> tdRowData = new ArrayList<String>();
				for(WebElement td: tdData) {
					tdRowData.add(td.getText());
				}
				activityStatementData.add(tdRowData);
			}else {
			}			
		}
		driver.close();
		driver.switchTo().window(tabs.get(0));
	}
	public static void main(String[] args) throws InterruptedException, IOException, ParseException {

		ATOdata obj = new ATOdata(); 

		obj.setupDriver();
		obj.lauchSite();
		obj.login();
		obj.clientName();
//		obj.gotoICAStatement();
//		obj.fetchICAStatementData();

		obj.goToQuarterName();
		obj.setToDateFromDate();
		obj.matchingQuarter();
		obj.getXeroData();
		obj.generateExcel();

		//HashMap<String, Object> clientData = ExcelUtil.getClientDetail("Client_data");
		//data = ExcelUtil.getUserLoginDetail("Login_detail");
		//System.out.println(Arrays.deepToString(data));
		//System.out.println(clientData);
		//System.out.println(clientData.get("apr_quarter"));

	}

}
