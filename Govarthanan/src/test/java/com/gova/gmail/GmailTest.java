package com.gova.gmail;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GmailTest {
	
	// Variable declaration for Webdriver & User Directory
	WebDriver driver;
	static String userDirectory = System.getProperty("user.dir").replaceAll("\\\\", "/");
	
	@BeforeClass
	public void testSetup()
	{
		// getting the chrome driver from the resources folder 
	System.setProperty("webdriver.chrome.driver", userDirectory + "/src/test/resources/Driver/chromedriver.exe"); 
	driver=new ChromeDriver();
	//We can execute this in Heqdless mode
	//driver= new RemoteWebDriver();
	//driver = new PhantomJSDriver();
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	driver.manage().window().maximize();

	}

	@BeforeMethod(description="Before every test we have go to Gmail Login Page")
	public void openGmailLogin()
	{
	driver.get("https://www.gmail.com/mail/help/intl/en/about.html?utm_expid=...");
	WebDriverWait wait = new WebDriverWait(driver, 120); 
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign in')]"))); 
	driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
	System.out.println("We are currently on the following URL" +driver.getCurrentUrl());
	}
	
	
	/*
	 * This method validates the Sending the mail like both sender & Receiver are same
	 */
	@Test()
	public void valid_SendingMail_SameEmail() throws InterruptedException
	{
		
	//Entering the Username & Password
    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ep.automation2020");
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    Thread.sleep(2000);
	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
	Thread.sleep(1000);
	driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
	// Click on Inbox
	
	assertTrue(driver.findElement(By.xpath("//div[@role='button']")).isDisplayed());
	Thread.sleep(2000);

	// Click on the first rox in inbox
	driver.findElement(By.xpath("//div[@class=\"T-I T-I-KE L3\"]")).click();
	
	//compose the mail

	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@class='Hq aUG']")).click();
	driver.findElement(By.xpath("//textarea[@name='to']")).sendKeys("ep.automation2020@gmail.com");
    driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("SendingTheMailUsingGmail");
    driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("Hi Sir, Hope you doing good");
    Thread.sleep(2000);
    driver.findElement(By.xpath(" //div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
    Thread.sleep(3000);
    
    //Verifying the Inbox mails
    driver.findElement(By.xpath("//tr[@jsmodel=\"nXDxbd\"]/td[5]/div/div/div/span/span")).click();
    WebElement inboxSubjectText = driver.findElement(By.xpath("//h2[contains(text(),'SendingTheMailUsingG')]"));
    String inboxSubjectName = inboxSubjectText.getText();
    System.out.println(inboxSubjectName);

    /// Verifying the mails in sent folder
    Thread.sleep(4000);
    driver.findElement(By.xpath("//div[@class=\"TN bzz aHS-bnu\"]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//tr[@jscontroller=\"ZdOxDb\"]/td[5]/div/div/div[3]/span/span")).click();
    WebElement sentMailSubjectText = driver.findElement(By.xpath("//h2[contains(text(),'SendingTheMailUsingG')]"));
    String sentMailSubjectName = sentMailSubjectText.getText();
    System.out.println(sentMailSubjectName);
    assertEquals(inboxSubjectName, sentMailSubjectName, "Comparing mail with Subject name whether it is equal");
    


	}
	
	/*
	 * This method validates the Sending mail from one email id to different email id
	 */
	
	@Test()
	public void valid_SendingMail_DifferentEmail() throws InterruptedException
	{
		
		//login in to the gmail
    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ep.automation2020");
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    Thread.sleep(2000);
	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
	Thread.sleep(1000);
	driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
	
	assertTrue(driver.findElement(By.xpath("//div[@role='button']")).isDisplayed());
	Thread.sleep(2000);

	driver.findElement(By.xpath("//div[@class=\"T-I T-I-KE L3\"]")).click();
	
	// Compose the mail

	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@class='Hq aUG']")).click();
	driver.findElement(By.xpath("//textarea[@name='to']")).sendKeys("senderemailto@gmail.com");
    driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("SendingTheMailUsingGmail");
    driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("Hi Sir, Hope you doing good");
    Thread.sleep(2000);
    
    
    driver.findElement(By.xpath(" //div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();


    Thread.sleep(4000);
    driver.findElement(By.xpath("//div[@class=\"TN bzz aHS-bnu\"]")).click();
    Thread.sleep(2000);
    try {
    	driver.findElement(By.xpath("//tr[@jsmodel=\"nXDxbd\"]/td[5]/div/div/div/span/span")).click();
	} catch (Exception e) {
		driver.findElement(By.xpath("//tr[@jscontroller=\"ZdOxDb\"]/td[5]/div/div/div[3]/span/span")).click();	}
    
    WebElement sentMailSubjectText = driver.findElement(By.xpath("//h2[contains(text(),'SendingTheMailUsingG')]"));
    String sentMailSubjectName = sentMailSubjectText.getText();
    System.out.println(sentMailSubjectName);
    Thread.sleep(2000);
    driver.findElement(By.xpath("//a[@class=\"gb_D gb_Ra gb_i\"]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//a[contains(text(),'Sign out')]")).click();
    Thread.sleep(1000);
    
    
    //Verifying the mail another in email
    
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[contains(text(),'Een ander account gebruiken')]")).click();
    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("senderemailto");
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    
    try {
    	driver.findElement(By.xpath("//tr[@jscontroller=\"ZdOxDb\"]/td[5]/div/div/div[3]/span/span")).click();
	} catch (Exception e) {
		driver.findElement(By.xpath("//tr[@jsmodel=\"nXDxbd\"]/td[5]/div/div/div/span/span")).click();	}
    
    //Verifying the mail inbox for another mail
    
    WebElement inboxSubjectText = driver.findElement(By.xpath("//h2[contains(text(),'SendingTheMailUsingG')]"));
    String inboxSubjectName = inboxSubjectText.getText();
    System.out.println(inboxSubjectName);
    Thread.sleep(2000);
    assertEquals(inboxSubjectName, sentMailSubjectName, "Comparing mail with Subject name whether it is equal");
    

	}
	
	/*
	 * This method validates the Sending mail for Wrong Email ID
	 */
	
	@Test()
	public void valid_SendingMail_WrongEmailID() throws InterruptedException
	{
		
		//Login to Gmail
    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ep.automation2020");
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    Thread.sleep(2000);
	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
	Thread.sleep(1000);
	driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
	
	assertTrue(driver.findElement(By.xpath("//div[@role='button']")).isDisplayed());
	Thread.sleep(2000);

	driver.findElement(By.xpath("//div[@class=\"T-I T-I-KE L3\"]")).click();
	
    // Trying to send the mail for invalid email id
	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@class='Hq aUG']")).click();
	driver.findElement(By.xpath("//textarea[@name='to']")).sendKeys("ep.automation2020@gmal.com");
    driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("SendingTheMailUsingGmail");
    driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("Hi Sir, Hope you doing good");
    Thread.sleep(2000);
    driver.findElement(By.xpath(" //div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
    Thread.sleep(15000);
    
    driver.findElement(By.xpath("//tr[@jscontroller=\"ZdOxDb\"]/td[5]/div")).click();
    
  // Verifying failure  delivery like address not found
    assertTrue(driver.findElement(By.xpath("//span[@name=\"Mail Delivery Subsystem\"]")).isDisplayed());
    
    
/*
 * This method validates the Sending mail for without address
 */

	}
	@Test(description="This method validates the Sending mail from and To Both are same mail ID ")
	public void valid_SendingMail_WithoutToAddress() throws InterruptedException
	{
    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ep.automation2020");
    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
    Thread.sleep(2000);
	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
	Thread.sleep(1000);
	driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
	
	assertTrue(driver.findElement(By.xpath("//div[@role='button']")).isDisplayed());
	Thread.sleep(2000);

	driver.findElement(By.xpath("//div[@class=\"T-I T-I-KE L3\"]")).click();
    // Trying to send the mail for invalid email id
	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@class='Hq aUG']")).click();

    driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("SendingTheMailUsingGmail");
    driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("Hi Sir, Hope you doing good");
    Thread.sleep(2000);
    driver.findElement(By.xpath(" //div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
    Thread.sleep(2000);
    
    //Verify the Error pop up Message like "Please specify at least one recipient."
    assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Error')]")).isDisplayed());
    assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Please specify at least one recipient.')]")).isDisplayed());
    

	}
	
	/*
	 * This method validates the Sending mail for without address
	 */

		
		@Test(description="This method validates the Sending mail from and To Both are same mail ID ")
		public void valid_SendingMail_WithoutSubject() throws InterruptedException
		{
	    driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ep.automation2020");
	    driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
	    Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
		
		assertTrue(driver.findElement(By.xpath("//div[@role='button']")).isDisplayed());
		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class=\"T-I T-I-KE L3\"]")).click();
	    // Trying to send the mail for invalid email id
		Thread.sleep(2000);
		driver.findElement(By.xpath("//img[@class='Hq aUG']")).click();
		
		driver.findElement(By.xpath("//textarea[@name='to']")).sendKeys("ep.automation2020@gmail.com");
	    //driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("SendingTheMailUsingGmail");
	    driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("Hi Sir, Hope you doing good");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath(" //div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
	    Thread.sleep(2000);
	    
	    
	  //Verifying the Inbox Mail which has No Subject
	    
	    WebElement inboxSubjectText = driver.findElement(By.xpath("//tr[@jsmodel=\"nXDxbd\"]/td[5]/div/div/div/span/span"));
	    String inboxSubjectName = inboxSubjectText.getText();
	    System.out.println(inboxSubjectName);
	    assertEquals("(no subject)", inboxSubjectName);
	    

		}
		
	
	//Just for Verification to get the current URL using Selenium
	@AfterMethod
	public void postSignIn()
	{
	System.out.println(driver.getCurrentUrl());

	}

	@AfterClass
	public void afterClass()
	{
	driver.quit();
	}

}

