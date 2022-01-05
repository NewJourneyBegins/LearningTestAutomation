package ZeroBank;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class ZeroBankTest {

	public WebDriver driver ;
	public WebDriverWait ewait;

	@BeforeSuite(alwaysRun=true)
	public void SetUp() {

		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumBrowserDrivers\\chromedriver.exe");

		// open browser
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// open url
		driver.get("http://zero.webappsecurity.com/");

	}

	@BeforeMethod(alwaysRun=true)
	public void LoginTest_CorrectUsername_CorrectPassword() {

		driver.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver.findElement(By.xpath("//input[@name ='user_login' ]")).sendKeys("username");
		driver.findElement(By.xpath("//input[contains(@id,'password')]")).sendKeys("password");
		driver.findElement(By.cssSelector("[name = 'submit']")).click();


		if(!driver.getTitle().contains("Zero - Account Summary")) {
			driver.findElement(By.id("details-button")).click();
			driver.findElement(By.id("proceed-link")).click();
		}
		assertEquals(driver.getTitle(), "Zero - Account Summary");
		System.out.println("---------------Login Successfull-----------------");

	}

	//Positive  ----- 1. Automate 'Purchase foreign currency cash' flow by keeping all the field empty and click on 'purchase' button.It will give an Alert, you have to handle it.
	@Test(priority = 1,groups = {"Smoke"})
	public void Purchase_Foreign_Currency_Cash_Alert_Test() {
		driver.findElement(By.linkText("Pay Bills")).click();

		//Explicit Wait
		ewait = new WebDriverWait(driver, 10);
		ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Purchase Foreign Currency')]")));

		driver.findElement(By.linkText("Purchase Foreign Currency")).click();
		ewait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2[contains(text(),'Purchase foreign currency cash')]"), "Purchase foreign currency cash"));

		//click on purchase cash button
		driver.findElement(By.xpath("//input[@id='purchase_cash']")).click();

		//switching to alert
		Alert purchaseAlert = driver.switchTo().alert();
		String alertText = purchaseAlert.getText();
		System.out.println("You are getting Alert :" + alertText);
		//Assert
		assertEquals(alertText, "Please, ensure that you have filled all the required fields with valid values.","Alert test Failed because text doesn't match");
		purchaseAlert.accept();
		System.out.println("Alert Accepted Successfully");

	}

	//Positive  --- 2. Write flow for Fund transfer positive test
	@Test(priority = 2,groups = {"Smoke"})
	public void Fund_Transfer_Test() {

		//Explicit Wait
		ewait = new WebDriverWait(driver, 10);
		driver.findElement(By.xpath("//a[contains(text(),'Transfer Funds')]")).click();
		ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments')]")));

		WebElement fromAccount = driver.findElement(By.id("tf_fromAccountId"));
		WebElement toAccount = driver.findElement(By.id("tf_toAccountId"));

		Select fromAccountSel = new Select(fromAccount);
		fromAccountSel.selectByVisibleText("Savings(Avail. balance = $ 1548)");

		Select toAccountSel = new Select(toAccount);
		toAccountSel.selectByValue("6");

		driver.findElement(By.id("tf_amount")).sendKeys("10");
		driver.findElement(By.id("tf_description")).sendKeys("Tranferring 10 dollars");

		driver.findElement(By.id("btn_submit")).click();

		//Verify user directed to correct page
		assertEquals(driver.findElement(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments - Verify')]")).getText(), "Transfer Money & Make Payments - Verify","Assertion Failed");

		driver.findElement(By.id("btn_submit")).click();

		//soft Assert 
		//Verifying success message
		SoftAssert sa = new SoftAssert();
		String sMsg = driver.findElement(By.xpath("//div[contains(text(),'You successfully submitted your transaction.')]")).getText();
		sa.assertEquals(sMsg,"You successfully submitted your transaction.");		
		sa.assertAll();
		System.out.println(sMsg);
	}

	//Negative	--------1
	@Test(priority = 3,groups = {"Regression"})
	public void Purchase_Foreign_Currency_Cash_Invalid_Amount_Test() {

		driver.findElement(By.linkText("Pay Bills")).click();

		//Explicit Wait
		ewait = new WebDriverWait(driver, 10);
		ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Purchase Foreign Currency')]")));

		driver.findElement(By.linkText("Purchase Foreign Currency")).click();
		ewait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2[contains(text(),'Purchase foreign currency cash')]"), "Purchase foreign currency cash"));

		WebElement radiobutton= driver.findElement(By.id("pc_inDollars_true"));

		boolean dollarIsSelected = radiobutton.isSelected();

		if(!dollarIsSelected) {
			radiobutton.click();
			System.out.println("Radio buton was not selected I have selected US Dollar radio button");
		}

		WebElement currency = driver.findElement(By.id("pc_currency"));

		Select sel = new Select(currency);
		sel.selectByVisibleText("Denmark (krone)");

		//Amount Data Type -Negative Test
		WebElement Amount = driver.findElement(By.id("pc_amount"));
		Amount.sendKeys("ABD");
		System.out.println("Entered string value in Amount field");

		//click on purchase cash button
		driver.findElement(By.xpath("//input[@id='purchase_cash']")).click();

		//switching to alert
		Alert purchaseAlert = driver.switchTo().alert();
		String alertText = purchaseAlert.getText();
		System.out.println("You are getting Alert :" + alertText);
		//Assert
		assertEquals(alertText, "Please, ensure that you have filled all the required fields with valid values.","Alert test Failed because text doesn't match");
		purchaseAlert.accept();
		System.out.println("Alert Accepted Successfully");

	}

	//Negative ---2
	@Test(priority = 4,groups = {"Regression"})
	public void Fund_Transfer_Test_NotContinue_for_InvalidValue() {

		//Explicit Wait
		ewait = new WebDriverWait(driver, 10);
		driver.findElement(By.xpath("//a[contains(text(),'Transfer Funds')]")).click();
		ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments')]")));

		WebElement fromAccount = driver.findElement(By.id("tf_fromAccountId"));
		WebElement toAccount = driver.findElement(By.id("tf_toAccountId"));

		Select fromAccountSel = new Select(fromAccount);
		fromAccountSel.selectByVisibleText("Savings(Avail. balance = $ 1548)");

		Select toAccountSel = new Select(toAccount);
		toAccountSel.selectByValue("6");

		driver.findElement(By.id("tf_amount")).sendKeys("INVALID INPUT");
		driver.findElement(By.id("tf_description")).sendKeys("Tranferring 10 dollars");

		driver.findElement(By.id("btn_submit")).click();

		//Verify user is not directed to new page and is present in same page
		assertEquals(driver.findElement(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments')]")).getText(),"Transfer Money & Make Payments","Assertion Failed");	
	}

	//Negative --3

	@Test(priority = 5,groups = {"Regression"})
	public void Invalid_Data_In_Search(){
		
		Actions act = new Actions(driver);
		
		//Keyboard
		
		WebElement search  = driver.findElement(By.id("searchTerm"));
		search.sendKeys("123");
		search.sendKeys(Keys.ENTER);
		assertEquals(driver.findElement(By.xpath("//*[contains(text(),'Search Results:')]")).getText(),"Search Results:" );
	}

	//Negative ----4
	@Test(priority = 6,groups = {"Regression"})
	public void On_Cancel_Go_Back(){
		//Explicit Wait
		ewait = new WebDriverWait(driver, 10);
		driver.findElement(By.xpath("//a[contains(text(),'Transfer Funds')]")).click();
		ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments')]")));

		WebElement fromAccount = driver.findElement(By.id("tf_fromAccountId"));
		WebElement toAccount = driver.findElement(By.id("tf_toAccountId"));

		Select fromAccountSel = new Select(fromAccount);
		fromAccountSel.selectByVisibleText("Savings(Avail. balance = $ 1548)");

		Select toAccountSel = new Select(toAccount);
		toAccountSel.selectByValue("6");

		driver.findElement(By.id("tf_amount")).sendKeys("10");
		driver.findElement(By.id("tf_description")).sendKeys("Tranferring 10 dollars");

		driver.findElement(By.id("btn_submit")).click();

		//Verify user directed to correct page
		assertEquals(driver.findElement(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments - Verify')]")).getText(), "Transfer Money & Make Payments - Verify","Assertion Failed");

		driver.findElement(By.id("btn_cancel")).click();
		assertEquals(driver.findElement(By.xpath("//h2[contains(text(),'Transfer Money & Make Payments')]")).getText(), "Transfer Money & Make Payments","Assertion Failed");

	}
	
	//Just mentioning to show use of all Testng attributes
	
	
	@BeforeClass
	  public void beforeClass() {
			System.out.println("This is @BeforeClass");

	  }
	  

	  @AfterClass
	  public void afterClass() {
			System.out.println("This is @AfterClass");
			
	  }
	  
	  @BeforeTest
	  public void beforeTest() {
			System.out.println("This is @BeforeTest");

	  }

	  @AfterTest
	  public void afterTest() {
			System.out.println("This is @AfterTest");

	  }


	@AfterMethod(alwaysRun=true)
	public void Logout() {

		// logout
		driver.findElement(By.xpath(".//body/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]/a[1]")).click();
		driver.findElement(By.xpath("//a[contains(@id,'logout')]")).click();
		System.out.println("---------------Logout Successfull------------------");
	}

	@AfterSuite(alwaysRun=true)
	public void CleanUp() {

		// close and quit driver
		driver.close();
		driver.quit();
	}

}
