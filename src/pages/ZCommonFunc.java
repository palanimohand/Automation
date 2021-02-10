package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ZCommonFunc {

	// wantedColumnIndexFinder method
	public static int wantedColumnIndexFinder(String colName, Actions action, WebDriver driver)
			throws InterruptedException {
		List<WebElement> linkTaskCol = driver.findElements(By.xpath("//div[@id='taskGrid']/div[1]/div[1]/div[1]/div"));
		int pos = 0;
		for (int c = 0; c < linkTaskCol.size(); c++) {
			action.moveToElement(linkTaskCol.get(c)).build().perform();
			if (linkTaskCol.get(c).getText().equals(colName)) {
				pos = c;
				break;
			}
		}
		// Column Filter adder
		if (pos == 0) {
			System.out.println(colName + " is not present");
			driver.findElement(By.id("tableViewButton-btnWrap")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("tableViewEditButton-tableViewButton-btnInnerEl")).click();
			Thread.sleep(2000);
			Set<String> windowHandles = driver.getWindowHandles();
			ArrayList<String> winList = new ArrayList<String>();
			winList.addAll(windowHandles);
			driver.switchTo().window(winList.get(1));
			Select filsel = new Select(driver.findElement(By.name("AllFieldList")));
			filsel.selectByVisibleText(colName);
			Thread.sleep(1000);
			driver.findElement(By.id("QTP_Add_With_Arrow")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("SaveB")).click();
			Thread.sleep(3000);
			driver.switchTo().window(winList.get(0));
			Thread.sleep(3000);
			driver.switchTo().frame(driver.findElement(By.id("contentframe")));
			driver.switchTo().frame(driver.findElement(By.id("299876")));
			return wantedColumnIndexFinder(colName, action, driver);
		} else {
			return pos;
		}
	}

	public static WebDriver startApp(String uName, String pass) throws InterruptedException {
		// Browser Launch
		System.setProperty("webdriver.chrome.driver",
				"D:\\PALANIMOHAN - 455493\\Desktop\\DRIVERS\\chromedriver_win32_2.33\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Login
		driver.get("https://pratesting.cognizant.com/digite/Request?Key=login");
		driver.findElement(By.id("loginId")).sendKeys(uName);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("QTP_LoginButton")).click();
		Thread.sleep(8000);
		// Navigation to Project Loading screen after login based on ELC
		// Notification
		try {
			driver.findElement(By
					.xpath("//span[text()='Notification']//preceding::div[@class='mute_advanced_notification unmute']"))
					.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Project / Program']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text()='View My Projects / Programs']")).click();
			Thread.sleep(5000);

		} catch (ElementNotVisibleException e) {
			driver.findElement(By.xpath("//div[@class='left_menu_icon_overlay']//span")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text()='View My Projects / Programs']")).click();
			Thread.sleep(5000);
		}

		return driver;
	}

	public static Actions selectProject(String projId, WebDriver driver) throws InterruptedException {
		// Project Selection
		WebElement projectfilter = driver.findElement(By.xpath("//span[@id='PROJECTCODE-textContainerEl']"));
		Actions action = new Actions(driver);
		action.moveToElement(projectfilter).build().perform();
		driver.findElement(By.xpath("//div[@id='PROJECTCODE-triggerEl']")).click();
		WebElement subfilter = driver.findElement(By.xpath("//*[@name='PROJECTCODE']"));
		action.moveToElement(subfilter).sendKeys(projId).build().perform();
		driver.findElement(By.xpath("//span[text()='Apply']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//td[@data-columnid='PROJECTNAME']")).click();
		Thread.sleep(3000);
		return action;
	}

}
