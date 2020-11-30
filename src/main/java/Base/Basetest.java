package Base;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.reportportal.message.ReportPortalMessage;


public class Basetest implements all_xpaths {

	public static WebDriver driver;
	static String url;
	private static final Logger logger = LogManager.getLogger(Basetest.class);

	// To read The Property File
	public static Properties prop;
	public static FileInputStream fso;
	public static DesiredCapabilities handleSSLError = new DesiredCapabilities();

	// Method to Lauch Browser

	public static void launchBrowser(String browser) throws IOException {
		try {
			if (browser.equalsIgnoreCase("IE")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");
				// create IE instance
				/*
				 * System.setProperty("webdriver.ie.driver",System.getProperty(
				 * prop.getProperty("IE")));
				 */
				driver = new InternetExplorerDriver();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				logger.info("IE Browser Opened Sucessfully");
			} else if (browser.equalsIgnoreCase("Chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				// create Chrome instance
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				logger.info("Chrome Browser Opened Sucessfully");
			} else if (browser.equalsIgnoreCase("Firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/geckodriver.exe");

				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				desiredCapabilities.setCapability("acceptInsecureCerts", true);
				WebDriver driver = new FirefoxDriver(desiredCapabilities);
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				logger.info("Firefox Browser Opened Sucessfully");

				driver.get("https://devbarfa.estockgifts.com/");

			}
		} catch (Exception e) {
			System.out.println("Failed to launch Browser.");
			logger.info("Failed to Open Browser ");
		}

	}

	// Method to launch application URL
	public static void sendURL(String url) {

		driver.navigate().to(url);
		driver.manage().window().maximize();
		logger.info("Passing URL ");
	}

	// Method to Close Browser
	public static void quitBrowser() {
		try {
			driver.close();
			// driver.quit();
			logger.info("Browser Quit Sucessfully");

		} catch (Exception e) {
			System.out.println("Failed to Quite Browser.");
			logger.info("Failed to Quit Browser ");
		}

	}

	// Method to perform click operation
	public static void click(String locator) {

		WebDriverWait wait = new WebDriverWait(driver,30);
		WebElement Action = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Action.click();
		logger.info("Click Operation Performed Sucessfully");

	}

	// Method to perform sending value to a Textbox
	public static void sendValue(String locator, String testdata) {

		try {
			WebDriverWait wait = new WebDriverWait(driver,30);
			WebElement Action = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			Action.clear();
			Action.sendKeys(testdata);
			logger.info("Data is Passed Sucessfully to the field");
		} catch (NoSuchElementException e) {
			System.out.println("Unable to locate and pass the data");
			logger.info("Unable to locate and pass the data");
		}
	}

	public static void Payment_Type(String Payment) throws IOException {
		try {
			if (Payment.equalsIgnoreCase("creditcard")) {
				click(CreditCard);
				logger.info("Credit/Debit card Selected. ");
				sendValue(Name_On_card, prop.getProperty("Name"));
				sendValue(Card_Number, prop.getProperty("Card_Num"));
				sendValue(Expiry_Date, prop.getProperty("Expiry"));
				sendValue(CVV_Number, prop.getProperty("CVV"));
				click(Terms_Conditions);
				click(Purchase);

			} else if (Payment.equalsIgnoreCase("Paypal")) {
				click(Paypal);
				logger.info("Paypal Selected. ");
				click(Terms_Conditions);
				click(paypal_Checkout);
			} else if (Payment.equalsIgnoreCase("Estockgifts_Payment")) {
				click(Estockgifts_Payment);
				logger.info("Estockgifts payment gateway Selected. ");
				click(Terms_Conditions);
				click(Purchase);

			}

		} catch (Exception e) {
			System.out.println("Failed to Select Payment option.");
			logger.info("Failed to select payment option ");
		}

	}

	public static void Crypto_Type(String Cryptocard) throws IOException {
		try {
			if (Cryptocard.equalsIgnoreCase("Bitcoin")) {
				click(Bitcoin);
				logger.info("Bitcoin Selected. ");

			} else if (Cryptocard.equalsIgnoreCase("Ethereum")) {
				click(Ethereum);
				logger.info("Ethereum Selected. ");

			} else if (Cryptocard.equalsIgnoreCase("Ripple")) {
				click(Ripple);
				logger.info("Ripple Selected. ");

			}
		} catch (Exception e) {
			System.out.println("Failed to Select Crypto type options.");
			logger.info("Failed to Select Crypto type options. ");
		}

	}

	// Locating upload image
	public static void Uploadimage(String locator, String image) {

		try {
			driver.findElement(By.xpath(locator)).sendKeys(image);

		} catch (NoSuchElementException e) {
			System.out.println("Unable to locate and pass the data");
			logger.info("Unable to locate image and pass the data");
		}
	}
	// Method to Upload Files
			public static void Upload(String Locator, String File) throws Exception {

				try {
//					driver.findElement(By.name(Locator));
					driver.findElement(By.xpath(Locator)).click();
					Thread.sleep(10000);
					StringSelection IMGsrc = new StringSelection(File);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(IMGsrc, null);
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_CONTROL);
					r.keyRelease(KeyEvent.VK_C);
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_CONTROL);
					r.keyRelease(KeyEvent.VK_V);
					r.keyPress(KeyEvent.VK_ENTER);
					r.keyRelease(KeyEvent.VK_ENTER);			
					
				} catch (Exception e) {
					System.out.println("Unable to Upload File ");
				}

			}

	public static void Select_Dropdown(String locator, String id) throws IOException {
		try {

			driver.findElement(By.xpath(locator)).click();
			driver.findElement(By.xpath(id)).click();

		} catch (Exception exp) {
			System.out.println("cause is:" + exp.getCause());
			logger.info("Select Expire Date Faild");

		}
	}

	// Take screenshots
	public static void onTestFailure() {

		ReportPortalMessage message = null;
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			java.util.Date d = new java.util.Date();
			org.apache.commons.io.FileUtils.copyFile(srcFile,
					new File("./ScreenShots/" + d.toString().replace(":", "_") + ".png"));
			 String rp_message = "test message for ReportPortal";
			 message = new ReportPortalMessage(srcFile, rp_message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.error(message);

	}

}
