package ZeroBank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ZeroBankLoginTest {

	public WebDriver driver1 ;
	public WebDriverWait ewait;

	@BeforeSuite(alwaysRun = true)
	public void SetUp() {

		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumBrowserDrivers\\chromedriver.exe");

	// open browser
		driver1 = new ChromeDriver();
		driver1.manage().window().maximize();
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// open url
		driver1.get("http://zero.webappsecurity.com/");

	}
	
	@AfterMethod(alwaysRun = true)
	public void goBackToHomePage() {
		driver1.findElement(By.xpath("//a[contains(text(),'Zero Bank')]")).click();
		assertEquals(driver1.findElement(By.xpath("//a[contains(text(),'Zero Bank')]")).getText(), "Zero Bank","User Not able to Go back to Homepage");
		
	}

	//Negative ---5 
	@Test(groups = {"Regression"})
	public void LoginTest_InorrectUsername_CorrectPassword() {

		driver1.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver1.findElement(By.xpath("//input[@name ='user_login' ]")).sendKeys("Wrong username");
		driver1.findElement(By.xpath("//input[contains(@id,'password')]")).sendKeys("password");
		driver1.findElement(By.cssSelector("[name = 'submit']")).click();

		WebElement errMsg = driver1.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
		assertEquals(errMsg.getText(), "Login and/or password are wrong.");
		System.out.println("Successfully received Message : "+ errMsg.getText());

	}

	//Negative ----6
	@Test(groups = {"Regression"})
	public void LoginTest_CorrectUsername_InorrectPassword() {

		driver1.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver1.findElement(By.xpath("//input[@name ='user_login' ]")).sendKeys("username");
		driver1.findElement(By.xpath("//input[contains(@id,'password')]")).sendKeys("Wrong password");
		driver1.findElement(By.cssSelector("[name = 'submit']")).click();

		WebElement errMsg = driver1.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
		assertEquals(errMsg.getText(), "Login and/or password are wrong.");
		System.out.println("Successfully received Message : "+errMsg.getText());

	}

	//Negative ----7
	@Test(groups = {"Regression"})
	public void LoginTest_EmptyUsername_EmptyPassword() {

		driver1.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver1.findElement(By.cssSelector("[name = 'submit']")).click();

		WebElement errMsg = driver1.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
		assertEquals(errMsg.getText(), "Login and/or password are wrong.");
		System.out.println("Successfully received Message : "+errMsg.getText());

	}

	@AfterSuite(alwaysRun = true)
	public void CleanUp() {

		// close and quit driver
		driver1.close();
		driver1.quit();
	}


}
