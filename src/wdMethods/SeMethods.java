package wdMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeMethods {

	public static RemoteWebDriver driver;
	public String sHubUrl, sHubPort;
	public String sUrl = "https://pratesting.cognizant.com/digite/Request?Key=login";
	public Properties prop;

	public SeMethods() {

	}

	public void startApp(String browser, boolean bRemote) {
		try {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);
			// this is for grid run
			if (bRemote)
				try {
					driver = new RemoteWebDriver(new URL("http://" + sHubUrl + ":" + sHubPort + "/wd/hub"), dc);
				} catch (MalformedURLException e) {
				}
			else { // this is for local run
				if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							"D:\\PALANIMOHAN - 455493\\Desktop\\DRIVERS\\chromedriver_win32\\chromedriver.exe");
					driver = new ChromeDriver();
				} else {
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					driver = new FirefoxDriver();
				}
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.get("https://pratesting.cognizant.com/digite/Request?Key=nonssologin");
			System.out.println("The browser: " + browser + " launched successfully : PASS");
		} catch (WebDriverException e) {
			System.out.println("The browser: " + browser + " could not be launched : FAIL");
		}
	}

	public void startApp(String browser) {
		startApp(browser, false);
	}

	public WebElement locateElement(String locator, String locValue) {
		try {
			switch (locator) {
			case ("id"):
				return driver.findElementById(locValue);
			case ("link"):
				return driver.findElementByLinkText(locValue);
			case ("xpath"):
				return driver.findElementByXPath(locValue);
			case ("name"):
				return driver.findElementByName(locValue);
			case ("class"):
				return driver.findElementByClassName(locValue);
			case ("tag"):
				return driver.findElementByTagName(locValue);
			}
		} catch (NoSuchElementException e) {
			System.out.println("The element with locator " + locator + " not found : FAIL");
			throw new RuntimeException();
		} catch (WebDriverException e) {
			System.out.println(
					"Unknown exception occured while finding " + locator + " with the value " + locValue + "FAIL");
		}
		return null;
	}

	public WebElement locateElement(By locValue) {
		return driver.findElement(locValue);
	}
	
	public List<WebElement> locateElements(By locValue) {
		return driver.findElements(locValue);
	}

	public void type(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			String x = "" + ele;
			//System.out.println("The data: " + data + " entered successfully in the field :" + ele + "PASS");
		} catch (InvalidElementStateException e) {
			System.out.println("The data: " + data + " could not be entered in the field :" + ele + "FAIL");
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while entering " + data + " in the field :" + ele + "FAIL");
		}
	}

	public void click(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			//System.out.println("The element " + text + " is clicked :" + "PASS");
		} catch (InvalidElementStateException e) {
			System.out.println("The element: " + text + " could not be clicked" + "FAIL");
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while clicking in the field :" + "FAIL");
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			System.out.println("The element :" + text + "  is clicked." + "PASS");
		} catch (InvalidElementStateException e) {
			System.out.println("The element: " + text + " could not be clicked" + "FAIL");
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while clicking in the field :" + "FAIL");
		}
	}

	public String getText(WebElement ele) {
		String bReturn = "";
		try {
			bReturn = ele.getText();
		} catch (WebDriverException e) {
			System.out.println("The element: " + ele + " could not be found." + "FAIL");
		}
		return bReturn;
	}

	public String getTitle() {
		String bReturn = "";
		try {
			bReturn = driver.getTitle();
		} catch (WebDriverException e) {
			System.out.println("Unknown Exception Occured While fetching Title : FAIL");
		}
		return bReturn;
	}

	public String getAttribute(WebElement ele, String attribute) {
		String bReturn = "";
		try {
			bReturn = ele.getAttribute(attribute);
		} catch (WebDriverException e) {
			//System.out.println("The element: " + ele + " could not be found : FAIL");
		}
		return bReturn;
	}
	

	public String getTagNameof(WebElement ele) {
		String bReturn = "";
		try {
			bReturn = ele.getTagName();
		} catch (WebDriverException e) {
			System.out.println("The element: " + ele + " could not be found : FAIL");
		}
		return bReturn;
	}

	public void selectDropDownUsingText(WebElement ele, String value) {
		try {
			new Select(ele).selectByVisibleText(value);
			//System.out.println("The dropdown is selected with text " + value + "PASS");
		} catch (WebDriverException e) {
			System.out.println("The element: " + ele + " could not be found." + "FAIL");
		}

	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {
			new Select(ele).selectByIndex(index);
			//System.out.println("The dropdown is selected with index " + index + "PASS");
		} catch (WebDriverException e) {
			System.out.println("The element: " + ele + " could not be found." + "FAIL");
		}
	}

	public void selectAllOptions(WebElement ele) {
		try {
			Iterator<WebElement> iterator = new Select(ele).getOptions().iterator();
			while (iterator.hasNext()) {
				iterator.next().click();
			}
			//System.out.println("All the options are selected");
		} catch (WebDriverException e) {
			System.out.println("All options are not selected due to unexpected error");
		}
	}

	public boolean verifyTitle(String title) {
		boolean bReturn = false;
		try {
			if (getTitle().equals(title)) {
				System.out.println("The title of the page matches with the value :" + title + "PASS");
				bReturn = true;
			} else {
				System.out.println("The title of the page:" + driver.getTitle() + " did not match with the value :"
						+ title + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the title" + "FAIL");
		}
		return bReturn;
	}

	public boolean verifyContainsTitle(String title) {
		boolean bReturn = false;
		try {
			if (getTitle().contains(title)) {
				System.out.println("The title of the page matches contains the value :" + title + "PASS");
				bReturn = true;
			} else {
				System.out.println("The title of the page:" + driver.getTitle() + " did not match with the value :"
						+ title + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the title" + "FAIL");
		}
		return bReturn;
	}

	public void verifyExactText(WebElement ele, String expectedText) {
		try {
			if (getText(ele).equals(expectedText)) {
				System.out.println("The text: " + getText(ele) + " matches with the value :" + expectedText + "PASS");
			} else {
				System.out.println("The text " + getText(ele) + " doesn't matches the actual " + expectedText + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text" + "FAIL");
		}

	}

	public void verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (getText(ele).contains(expectedText)) {
				System.out.println("The expected text contains the actual " + expectedText + "PASS");
			} else {
				System.out.println("The expected text doesn't contain the actual " + expectedText + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text" + "FAIL");
		}
	}

	public void verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (getAttribute(ele, attribute).equals(value)) {
				System.out.println(
						"The expected attribute :" + attribute + " value matches the actual " + value + "PASS");
			} else {
				System.out.println("The expected attribute :" + attribute + " value does not matches the actual "
						+ value + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text" + "FAIL");
		}

	}

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (getAttribute(ele, attribute).contains(value)) {
				System.out.println(
						"The expected attribute :" + attribute + " value contains the actual " + value + "PASS");
			} else {
				System.out.println("The expected attribute :" + attribute + " value does not contains the actual "
						+ value + "FAIL");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text : FAIL");
		}
	}

	public boolean verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				System.out.println("The element " + ele + " is selected" + "PASS");
				return true;
			} else {
				System.out.println("The element " + ele + " is not selected" + "FAIL");
				return false;
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
			return false;
		}
	}

	public void verifyDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				System.out.println("The element " + ele + " is visible" + "PASS");
			} else {
				System.out.println("The element " + ele + " is not visible" + "FAIL");
			}
		} catch (WebDriverException e) {
		}
	}

	public void switchToWindow(int index) {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>();
			allHandles.addAll(allWindowHandles);
			driver.switchTo().window(allHandles.get(index));
		} catch (NoSuchWindowException e) {
			System.out.println("The driver could not move to the given window by index " + index + "PASS");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		}
	}

	public void switchToFrame(WebElement ele) {
		try {
			driver.switchTo().frame(ele);
			//System.out.println("switch In to the Frame " + ele + "PASS");
		} catch (NoSuchFrameException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		}
	}

	public void acceptAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			//System.out.println("The alert " + text + " is accepted : PASS");
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present : FAIL");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		}
	}

	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			System.out.println("The alert " + text + " is dismissed : PASS");
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present : FAIL");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		}

	}

	public String getAlertText() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			System.out.println("The alert Text is: "+text);
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present : FAIL");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage() + "FAIL");
		}
		return text;
	}

	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + number + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

	public void closeBrowser() {
		try {
			driver.close();
			System.out.println("The browser is closed : PASS");
		} catch (Exception e) {
			System.out.println("The browser could not be closed : FAIL");
		}
	}

	public void closeAllBrowsers() {
		try {
			driver.quit();
			System.out.println("The opened browsers are closed : PASS");
		} catch (Exception e) {
			System.out.println("Unexpected error occured in Browser : FAIL");
		}
	}

	public void clearContent(WebElement ele) {
		try {
			ele.clear();
			//System.out.println("The webelement is cleared : PASS");
		} catch (Exception e) {
			System.out.println("The webelement is not cleared : FAIL");
		}
	}

	public void moveToElement(WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			//System.out.println("Moved to the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}

	public void moveToElementAndClick(WebElement ele, WebElement ele1) {
		try {
			new Actions(driver).moveToElement(ele).moveToElement(ele1).click().build().perform();
			//System.out.println("Moved to the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}

	public void moveToElementAndClick(WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).click().build().perform();
			//System.out.println("Moved to the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}

	public void moveToElementAndtype(WebElement ele, String text) {
		try {
			new Actions(driver).moveToElement(ele).sendKeys(text).build().perform();
			//System.out.println("Moved to the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}

	public void elementToBeClickableWait(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}
	
	public void elementToBeSelectedWait(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.elementToBeSelected(ele));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}
	
	public void textToBePresentWait(WebElement locator, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.textToBePresentInElement(locator, text));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}
	
	public void doubleclickAndSendText(WebElement ele,String text) {
		try {
			new Actions(driver).moveToElement(ele).doubleClick().sendKeys(Keys.chord((Keys.CONTROL), "a",Keys.BACK_SPACE)).sendKeys(text).build().perform();
			//System.out.println("Double click on the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to double click and send text to the webelement : FAIL");
		}
	}
	
	public void doubleclick(WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).doubleClick(ele).build().perform();
			//System.out.println("Double click on the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}
	
	public void actionsendtext(WebElement ele,String text) {
		try {
			new Actions(driver).sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME,Keys.BACK_SPACE)).sendKeys(ele, text).build().perform();
			//System.out.println("Double click on the webelement : PASS");
		} catch (Exception e) {
			System.out.println("Unable to move to the webelement : FAIL");
		}
	}
	
	public void elementToBeVisibleWait(By ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}
	
	public void elementToBePresentWait(By ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.presenceOfElementLocated(ele));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}

	public void elementToBeInvisibleWait(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}

	public void elementToBeInvisibleWithTextWait(By locator, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text ));
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}
	
	public void alertWait() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			wait.until(ExpectedConditions.alertIsPresent());
			ExpectedConditions.alertIsPresent();
			//System.out.println("Wait executed");
		} catch (Exception e) {
			System.out.println("Wait unexecuted");
		}
	}

}