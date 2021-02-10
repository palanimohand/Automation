package zmilestoneReview1Code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC01 {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		// Browser Launch
		System.setProperty("webdriver.chrome.driver",
				"D:\\PALANIMOHAN - 455493\\Desktop\\DRIVERS\\chromedriver_win32_2.33\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Login Data
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File("./src/config.properties")));
		String[] sUsername = prop.getProperty("USERNAME").split(",");
		String[] sPassword = prop.getProperty("PASSWORD").split(",");

		for (int i = 0; i < sPassword.length; i++) {

			// Login
			driver.get("https://pratesting.cognizant.com/digite/Request?Key=login");
			driver.findElement(By.id("loginId")).sendKeys(sUsername[i]);
			driver.findElement(By.id("password")).sendKeys(sPassword[i]);
			driver.findElement(By.id("QTP_LoginButton")).click();
			Thread.sleep(8000);

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

			Actions action = ZCommonFunc.selectProject("56041", driver);

			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("LOCK_Plan"))));

			// Milestone Navigation
			WebElement Plan = driver.findElement(By.id("LOCK_Plan"));
			action.moveToElement(Plan).moveToElement(driver.findElement(By.id("LOCK_Milestone"))).click().build()
					.perform();
			Thread.sleep(2000);

			// Milestone Creation
			driver.findElement(By.id("KEY_BUTTON_Add-btnIconEl")).click();
			Thread.sleep(3000);
			driver.switchTo().frame(driver.findElement(By.id("contentframe")));
			System.out.println("Test Case 1: Filling the mandatory fields and creating a new Milestone");
			driver.findElement(By.id("_Text_Check_CM_Name")).sendKeys("Entered through Automation");
			System.out.println("Mandatory field - Name is entered");
			Thread.sleep(1000);
			driver.findElement(By.id("_Text_Check_field4")).sendKeys("Entered through Automation");
			System.out.println("Mandatory field - Key External Dependencies is entered");
			Thread.sleep(1000);
			driver.findElement(By.id("_Text_Check_field3")).sendKeys("Entered through Automation");
			System.out.println("Mandatory field - Key Internal Dependencies is entered");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//textarea[@id='field2']")).sendKeys("Entered through Automation");
			System.out.println("Mandatory field - Scope of Milestone is entered");
			Thread.sleep(1000);
			driver.findElement(By.id("field1")).sendKeys("30-Sep-2018");
			System.out.println("Mandatory field - Milestone Due Date is entered");
			Thread.sleep(1000);
			driver.findElement(By.id("SaveBtn")).click();
			Thread.sleep(3000);
			System.out.println("Milestone saved successfully");

		}

		// driver.close();
	}
}
