
/**********************************************************************************************************************
Tester Name   : Shruti Pandey                   ClassName    : LoginPayBillAddNewPayeeAndLogout

Project Name  : ZeroBank Application Testing    Scenario     : 1

Version       : 1.0

Date Created  : 29/12/2021                      Date Updated :


 ************************************************************************************************************************/
package assignments;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPayBillAddNewPayeeAndLogout {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver","C:\\SeleniumBrowserDrivers\\chromedriver.exe");

		//open browser
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		//open url
		driver.get("http://zero.webappsecurity.com/");

		//########### Locator ##############

		//----- By classname ---------------------
		driver.findElement(By.className("search-query")).sendKeys("Credit Cards");

		//------ By ID ----------------------
		driver.findElement(By.id("signin_button")).click();
		
		//---By name------------------------
		driver.findElement(By.name("user_login")).sendKeys("username");

		//----By Partial link text---
		driver.findElement(By.partialLinkText("Forgot")).click();
		
		//---By linkText------
		driver.findElement(By.linkText("Zero Bank")).click();
		
		//-----tagname must be unique in webpage-------
		driver.findElement(By.tagName("button")).click();

		//#############CSS locators ##################
		
		//----------   By id css  ------------
		driver.findElement(By.cssSelector("#user_login")).sendKeys("username");

		//-------By attribute css -----------
		driver.findElement(By.cssSelector("[type='password']")).sendKeys("password");
		
		//------By tag+id+attribute ---css-->tag#id[attribute=value]
		driver.findElement(By.cssSelector("input#user_remember_me[name='user_remember_me']")).click();

		
		driver.findElement(By.cssSelector("[name = 'submit']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//------By tag+id ---css----------
		driver.findElement(By.cssSelector("button#details-button")).click();
		
		driver.findElement(By.id("proceed-link")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
		//------By class name css----------
		driver.findElement(By.cssSelector(".search-query")).sendKeys("Pay Bills");
		
		
		driver.findElement(By.linkText("Pay Bills")).click();
		driver.findElement(By.linkText("Pay Saved Payee")).click();
		
		//------By tag+class ---css-------
		driver.findElement(By.cssSelector("i.icon-question-sign")).click();
		
		driver.findElement(By.linkText("Add New Payee")).click();
	
		//------By tag+attribute ---css------
		driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Sundar Pichai");
		
		//-----By tag css ------tag---
		driver.findElement(By.cssSelector("textarea")).sendKeys("Wrong Address");
		
		//------By tag+class+attribute ---css-------
		driver.findElement(By.cssSelector("textarea.span4[name='address']")).clear();

		//------By tag+class+id ---css---------
		driver.findElement(By.cssSelector("textarea.span4#np_new_payee_address")).sendKeys("Entered Correct Address /n Google Banglore");
		
		//------By tag+class+id+attribute ---css------
		 driver.findElement(By.cssSelector("input.span4#np_new_payee_account[type='text']")).sendKeys("1111111000000111111000000");
	
		 
		//########XPATH#############

		//absolute xpath
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/form[1]/div[1]/div[1]/article[1]/fieldset[1]/div[4]/div[1]/input[1]")).sendKeys("Shruti Pandey");
		

		//relative xpath
		driver.findElement(By.xpath("//input[@id='add_new_payee']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//xpath with * sign
		String  addNewPayeSuccMsg  = driver.findElement(By.xpath("//*[@id='alert_content' ]")).getText();
		System.out.println(addNewPayeSuccMsg);

		//xpath with . sign
		driver.findElement(By.xpath(".//body/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]/a[1]")).click();

		//xpath with contains--------
		driver.findElement(By.xpath("//a[contains(@id,'logout')]")).click();
		
		//Using this below locators because all other combinations are already shown.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("feedback")).click();
		driver.findElement(By.id("name")).sendKeys("Shruti Pandey");
		driver.findElement(By.id("email")).sendKeys("Shruti@123.com");
		driver.findElement(By.id("subject")).sendKeys("There is no registeration page");
		
		driver.findElement(By.xpath("//textarea[@id='comment']")).sendKeys("No registeration page is present!!");
		
		driver.findElement(By.xpath("//input[@name='submit']")).click();
		
		//xpath with contains() with text
		driver.findElement(By.xpath("//h3[contains(text(),'Feedback')]")).click();
		
		//1st example-------xpath with contains() + href + indexing, index starts with one
		driver.findElement(By.xpath("//a[contains(@href,'/index')][1]")).click();
		
		//xpath with contains + images
		driver.findElement(By.xpath("//img[contains(@src,'main_carousel_1')]")).click();
		System.out.println("User has clicked on image");
		
		//2nd example ======    xpath with contains() + href + indexing, index starts with one)
		//driver.findElement(By.xpath("//a[contains(@href,'about/legal/')][2]")).click();
		
		//close browser
		driver.close();
		System.out.println("Driver closed successfuly");
		
		//kill driver
		driver.quit();


	}

}
