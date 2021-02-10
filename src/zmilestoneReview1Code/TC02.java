package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pages.ZCommonFunc;

public class TC02{

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		WebDriver driver = ZCommonFunc.startApp("455493", "thefighter56");

		Actions action = ZCommonFunc.selectProject("56041", driver);

		// Fetch Project Start Date and Project End Date from project profile
		driver.switchTo().frame("contentframe");
		System.out.println(
				"Test Case 2: Verify the Milestone due date cannot be greater the Project Start Date and End Date");
		String PStartDate = driver.findElement(By.xpath("//input[@name='STARTDATE']")).getAttribute("value");
		System.out.println("Fetching Project Start Date:" + PStartDate + " to check");
		String PEndDate = driver.findElement(By.xpath("//input[@name='ENDDATE']")).getAttribute("value");
		System.out.println("Fetching Project End Date:" + PEndDate + " to check");

		// Split the dates for use
		String[] splitSDate = PStartDate.split("-");
		int sYear = Integer.parseInt(splitSDate[2]);
		String[] splitEDate = PEndDate.split("-");
		int eYear = Integer.parseInt(splitEDate[2]);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);

		// Milestone Navigation
		WebElement Plan = driver.findElement(By.id("LOCK_Plan"));
		action.moveToElement(Plan).moveToElement(driver.findElement(By.id("LOCK_Milestone"))).click().build().perform();
		Thread.sleep(2000);

		// Milestone Creation
		driver.findElement(By.id("KEY_BUTTON_Add-btnIconEl")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("contentframe")));
		driver.findElement(By.id("_Text_Check_CM_Name")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field4")).sendKeys("Entered through Automation");
		driver.findElement(By.id("_Text_Check_field3")).sendKeys("Entered through Automation");
		driver.findElement(By.xpath("//textarea[@id='field2']")).sendKeys("Entered through Automation");
		driver.findElement(By.id("field1")).sendKeys(splitSDate[0] + "-" + splitSDate[1] + "-" + (sYear - 1));
		System.out.println("Entering Milestone Due Date:" + (splitSDate[0] + "-" + splitSDate[1] + "-" + (sYear - 1))
				+ " Less than Project Start Date:" + PStartDate);
		driver.findElement(By.id("SaveBtn")).click();
		Thread.sleep(2000);

		// Alert Section
		try {
			String errortext = driver.switchTo().alert().getText();
			System.out.println("1st Error Occured");
			if (errortext.equals("Milestone Due Date should be in between Project Start Date and Project End Date")) {
				System.out.println(
						"Appropriate Error Message: Milestone Due Date should be in between Project Start Date and Project End Date");
				driver.switchTo().alert().accept();
				driver.findElement(By.id("field1")).clear();
				driver.findElement(By.id("field1")).sendKeys(splitEDate[0] + "-" + splitEDate[1] + "-" + (eYear + 1));
				System.out.println(
						"Entering Milestone Due Date:" + (splitEDate[0] + "-" + splitEDate[1] + "-" + (eYear + 1))
								+ " Greater than Project End Date:" + PEndDate);
				driver.findElement(By.id("SaveBtn")).click();
				Thread.sleep(2000);
				String errortext2 = driver.switchTo().alert().getText();
				System.out.println("2nd Error Occured");
				if (errortext2
						.equals("Milestone Due Date should be in between Project Start Date and Project End Date")) {
					System.out.println(
							"Appropriate Error Message: Milestone Due Date should be in between Project Start Date and Project End Date");
				} else {
					System.out.println("In Appropriate Error");
				}
			} else {
				System.out.println("In Appropriate Error");
			}

		} catch (NoAlertPresentException e) {
			System.out.println("No Alert is present");
		}
	}
}
