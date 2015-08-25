package com.success;

import java.net.URL;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opera.core.systems.OperaDriver;

public class Klasa1 {
//	private WebDriver driver;
	private RemoteWebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"c:\\selenium\\webdriver\\chromedriver.exe");
	
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
		baseUrl = "http://seleniumhq.org/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
	}

	// @Test
	public void test3() throws Exception {
		driver.get(baseUrl + "/download/");
		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.linkText("Selenium IDE")).click();

		// ERROR: Caught exception [ERROR: Unsupported command
		// [captureEntirePageScreenshot | d:\test3.jpg | #fff]]
		// Warning: verifyTextPresent may require manual changes
		try {
			assertTrue(driver.findElement(By.cssSelector("BODY")).getText()
					.matches("^[\\s\\S]*Selenium IDE[\\s\\S]*$"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			assertTrue(isElementPresent(By
					.xpath("//div[@id='mainContent']/table/tbody/tr/td[2]/img")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			assertTrue(isElementPresent(By.linkText("Projects")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		assertEquals("Selenium IDE Plugins", driver.getTitle());
		assertEquals(
				"Selenium IDE",
				driver.findElement(
						By.xpath("//div[@id='mainContent']/table/tbody/tr/td[1]/p[1]/b"))
						.getText());
		driver.close();
	}

	@Test
	public void testGoogle() {
		driver.get("http://google.pl");
		driver.findElement(By.id("lst-ib")).click();
		driver.findElement(By.id("lst-ib")).sendKeys("szukana fraza");
		driver.findElement(By.id("lst-ib")).submit();
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains("szukana fraza"));
		assertTrue(driver.findElement(By.partialLinkText("szukana fraza")).isDisplayed());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
