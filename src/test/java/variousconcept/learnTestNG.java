package variousconcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class learnTestNG {String browser;
String url;
WebDriver driver;

// Element List
By USER_NAME_FIELD = By.xpath("//input[@id='username']");
By PASSWORD_FIELD = By.xpath("//input[@id='password']");
By SIGN_IN_BUTTON_FIELD = By.xpath("//button[@name='login']");
By DASHBOARD_HEADER_FIELD = By.xpath("//h2[text()=' Dashboard ']");
By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
By ADD_CUSTOMER_MENU_FIELD = By.xpath("//a[contains(text(), 'Add Customer')]");
By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//h5[contains(text(), 'Add Contact')]");
By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");

// Test/Mock Data
String userName = "demo@techfios.com";
String password = "abc123";
String fullName = "selenium";
String company = "Techfios";
String email = "demo@techfios.com";
String country = "United States";



@BeforeClass
public void readConfig() {

	// Scanner //FileReader //InputStream //BufferedReader
	try {
		InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");

		Properties prop = new Properties();
		prop.load(input);
		browser = prop.getProperty("browser");
		url = prop.getProperty("url");

	} catch (IOException e) {
		e.printStackTrace();
	}
}

@BeforeMethod
public void init() {

	if (browser.equalsIgnoreCase("Chrome")) {
		System.setProperty("webdriver.chrome.driver","drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	} else if (browser.equalsIgnoreCase("Edge")) {
		System.setProperty("webdriver.edge.driver","drivers\\msedgedriver.exe");
		driver = new EdgeDriver();
	} else {
		System.out.println();
	}

	driver.manage().deleteAllCookies();
	driver.get(url);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

}

//@Test
public void loginTest() {
	driver.findElement(USER_NAME_FIELD).sendKeys(userName);
	driver.findElement(PASSWORD_FIELD).sendKeys(password);
	driver.findElement(SIGN_IN_BUTTON_FIELD).click();

	Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard",
			"Dashboard page not found!!!");

}

@Test
public void addCustomer() throws InterruptedException {
	loginTest();
	driver.findElement(CUSTOMER_MENU_FIELD).click();
	driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
	
	Thread.sleep(2000);
	Assert.assertEquals(driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText(), "Add Contact", "Add Customer page not found!!!");
	
	driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generateRandomNum(9999));
	
	Select sel = new Select(driver.findElement(COMPANY_DROPDOWN_FIELD));
	sel.selectByVisibleText(company);
	
	driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(999) + email);
	
	Select sel1 = new Select(driver.findElement(COUNTRY_DROPDOWN_FIELD));
	sel1.selectByVisibleText(country);
	
}

private int generateRandomNum(int bounderyNum) {
	Random rnd = new Random();
	int generatedNum = rnd.nextInt(bounderyNum);
	return generatedNum;
}




}

	
	

	
