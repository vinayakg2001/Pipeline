package com.asis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginXero {
	public void XeroLogin() throws InterruptedException{
	System.setProperty("webdriver.chrome.driver","C:\\selenium webdriver\\chromedriver-win64\\chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.get("https://login.xero.com/");
	driver.manage().window().maximize();
	WebElement Emailaddress = driver.findElement(By.id("xl-form-email"));
	Emailaddress.sendKeys("accountant2@fortunaadvisors.com.au");

	WebElement Password = driver.findElement(By.id("xl-form-password"));
	Password.sendKeys("User123456@");

	WebElement loginButton = driver.findElement(By.id("xl-form-submit"));
	loginButton.click();

	WebElement UseanotherauthenticationmethodButton = driver.findElement(By.xpath("/html/body/div/div/div/div/button"));
	UseanotherauthenticationmethodButton.click();
	Thread.sleep(1000);

	WebElement SecurityquestionsButton = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/div[2]/button"));
	SecurityquestionsButton.click();
	Thread.sleep(1000);

	WebElement Question1 = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[1]/label"));
	System.out.println(Question1.getText());

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
	System.out.println(Question2.getText());

	String answerTwo="";

	if(Question2.getText().equals("What is your dream job?")) {
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
	Thread.sleep(9000);
	}
}
