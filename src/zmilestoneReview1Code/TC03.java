package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC03 {

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
		System.out.println("Test Case 3: Verify the Actual Milestone end date is made mandatory and autofilled with Current date when status is set as Completed");
		driver.findElement(By.id("_Text_Check_CM_Name")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field4")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field3")).sendKeys("Entered through Automation");
		driver.findElement(By.xpath("//textarea[@id='field2']")).sendKeys("Entered through Automation");
		driver.findElement(By.id("field1")).sendKeys("30-Sep-2018");
		driver.findElement(By.id("SaveBtn")).click();
		Thread.sleep(3000);
		Select sl = new Select(driver.findElement(By.id("field5")));
		sl.selectByVisibleText(" Completed ");
		System.out.println("Selecting Status as completed");
		Thread.sleep(3000);
		try {
			//Checking for mandatory field
			driver.findElement(By.xpath(
					"//td[@title='Actual Milestone End Date']/following::td[contains(@class,'mandatoryField')]"));
			System.out.println("Actual Milestone End Date is made mandatory");
		} catch (NoSuchElementException e) {
			System.out.println("Actual Milestone End Date is not made mandatory");
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate ldate = LocalDate.now();
		System.out.println("Current date is: "+dtf.format(ldate));
		driver.findElement(By.id("SaveBtn")).click();
		Thread.sleep(3000);
		String mdate = driver.findElement(By.id("field14")).getText().trim();
		System.out.println("Fetched Date is: "+mdate);
		if (mdate.equals(dtf.format(ldate))) {
			System.out.println("Current Date is populated in Actual Milestone End Date");
		} else {
			System.out.println("Current Date is not populated in Actual Milestone End Date");
		}

	}
}
