package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC04 {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		WebDriver driver = ZCommonFunc.startApp("455493", "thefighter56");

		Actions action = ZCommonFunc.selectProject("56041", driver);

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("LOCK_Execute"))));

		// Milestone Navigation
		WebElement Plan = driver.findElement(By.id("LOCK_Plan"));
		action.moveToElement(Plan).moveToElement(driver.findElement(By.id("LOCK_Milestone"))).click().build().perform();
		Thread.sleep(2000);

		// Milestone Creation
		driver.findElement(By.id("KEY_BUTTON_Add-btnIconEl")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("contentframe")));
		System.out.println("Test Case 4: Verify the Workpackage is closed when the status is set as cancelled");
		driver.findElement(By.id("_Text_Check_CM_Name")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field4")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field3")).sendKeys("Entered through Automation");
		driver.findElement(By.xpath("//textarea[@id='field2']")).sendKeys("Entered through Automation");
		driver.findElement(By.id("field1")).sendKeys("30-Sep-2018");
		driver.findElement(By.id("SaveBtn")).click();
		Thread.sleep(3000);
		Select sl = new Select(driver.findElement(By.id("field5")));
		sl.selectByVisibleText(" Cancelled ");
		Thread.sleep(1000);
		driver.findElement(By.id("SaveBtn")).click();
		System.out.println("Saved with status as cancelled");
		Thread.sleep(3000);
		driver.findElement(By.name("KEY_Activity_Log")).click();
		Thread.sleep(3000);
		try {
			driver.findElement(By.xpath("//input[@id='_Text_Check_field10']"));
		} catch (NoSuchElementException e) {
			System.out.println("Remarks fiels is Readonly");
		}
		driver.switchTo().frame(driver.findElement(By.id("921504")));
		String closuretext = driver.findElement(By.xpath("//table[@id='QTP_activitylog']/tbody/tr[1]/td[2]")).getText();
		if (closuretext.equals("Workitem closed")) {
			System.out.println("Workitem closed as per activity log");
		} else {
			System.out.println("Workitem not closed as per activity log");
		}
	}
}
