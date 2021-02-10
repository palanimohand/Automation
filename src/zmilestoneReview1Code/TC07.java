package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC07 {

	public static void main(String[] args)
			throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException {

		WebDriver driver = ZCommonFunc.startApp("455493", "thefighter56");

		Actions action = ZCommonFunc.selectProject("56041", driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("LOCK_Execute"))));

		// Workpackage Navigation
		WebElement Execute = driver.findElement(By.id("LOCK_Execute"));
		action.moveToElement(Execute).moveToElement(driver.findElement(By.id("LOCK_Workpackages"))).click().build()
				.perform();

		// Workpackage Creation
		System.out.println("Test Case 7: Check whether Milestone Due date is Planned Finish when WP is not baselined");
		wait.until(ExpectedConditions.presenceOfElementLocated((By.id("KEY_BUTTON_Add-btnIconEl"))));
		driver.findElement(By.id("KEY_BUTTON_Add-btnIconEl")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("contentframe")));
		driver.findElement(By.id("_Text_Check_CM_Name")).sendKeys("Test Workpackage");
		driver.findElement(By.id("field2")).sendKeys("01-Mar-2019");
		driver.findElement(By.id("CM_DUEDATE")).sendKeys("31-Mar-2019");
		// Dropdown value selection
		Select sel = new Select(driver.findElement(By.id("field200")));
		sel.selectByValue("78545");
		Thread.sleep(2000);
		Select sel2 = new Select(driver.findElement(By.id("field191")));
		for (int j = 0; j < sel2.getOptions().size(); j++) {
			sel2.selectByIndex(j);
		}
		driver.findElement(By.xpath("//select[@id='field172']/option[2]")).click();
		driver.findElement(By.id("SaveBtn")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();

		// Navigating to Linked Task Tab
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("KEY_Linked_Tasks")));
		String wpkgid = driver.findElement(By.id("CM_ItemCode")).getText();
		Thread.sleep(1000);
		driver.findElement(By.name("KEY_Linked_Tasks")).click();
		Thread.sleep(3000);

		driver.switchTo().frame(driver.findElement(By.id("299876")));

		// Finding the mark task as milestone column
		int wantedColumnIndex = ZCommonFunc.wantedColumnIndexFinder("Mark Task as Milestone", action, driver);
		int secColumnIndex = ZCommonFunc.wantedColumnIndexFinder("Planned Finish", action, driver);
		int thrdColumnIndex = ZCommonFunc.wantedColumnIndexFinder("Baseline Finish", action, driver);
		
		//Check out WP
		driver.findElement(By.id("editButton-btnInnerEl")).click();

		// Getting the milestone table
		int milestonetable = 0;
		List<WebElement> taskstable = driver.findElements(By.xpath("//table[contains(@id,'treeview')]"));
		int e = 1;
		for (WebElement webElement : taskstable) {
			String milestone = webElement.findElement(By
					.xpath("//table[contains(@id,'treeview')][" + e + "]/tbody/tr/td[" + (wantedColumnIndex + 1) + "]"))
					.getText();
			if (milestone.equals("Y")) {
				milestonetable = e;
				break;
			}
			e++;
		}

		// Fetching the milestone task names and Planned End date
		String milestonename;
		String fullDate;
		
		// fetching the Name of the Task
		milestonename = driver
				.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[3]"))
				.getText();
		System.out.println("The Fetched Milestone Task Name is: "+milestonename);

		// fetching the Planned End date
		fullDate = driver.findElement(By.xpath(
				"//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[" + (secColumnIndex + 1) + "]"))
				.getText();
		System.out.println("The Planned Finish of Milestone Task -"+milestonename+"is :"+fullDate);



		// forming the new date
		String[] split = fullDate.split("-");
		int year = Integer.parseInt(split[2]);
		int date = Integer.parseInt(split[0]);
		if (date > 28) {
			date = date - 1;
		} else {
			date = date + 1;
		}
		DecimalFormat df = new DecimalFormat("00");
		String zdate = df.format(date);
		String newdate = zdate + "-" + split[1] + "-" + year;
		System.out.println("The new calculated Planned Finish date is: "+newdate);
		

		// Entering the new date
		action.doubleClick(driver.findElement(By.xpath(
				"//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[" + (secColumnIndex + 1) + "]")))
				.build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[contains(@id,'datefield') and contains(@class,'x-form-required-field')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@id,'datefield') and contains(@class,'x-form-required-field')]"))
				.sendKeys(newdate);
		System.out.println("The new Planned finish date: "+newdate+"is entered");

		// Check in
		Thread.sleep(2000);
		driver.findElement(By.id("commitButton-btnInnerEl")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='No']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='OK']")).click();
		Thread.sleep(2000);

		// Fetching the milestone planned end date if baselined end date is
		// not present
		if (driver.findElement(By.xpath(
				"//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[" + (thrdColumnIndex + 1) + "]"))
				.getText() != null) {
			newdate = driver.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetable
					+ "]/tbody/tr/td[" + (thrdColumnIndex + 1) + "]")).getText();
		}
		newdate = driver.findElement(By.xpath(
				"//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[" + (secColumnIndex + 1) + "]"))
				.getText();

		driver.switchTo().defaultContent();

		// Milestone Navigation
		WebElement Plan = driver.findElement(By.id("LOCK_Plan"));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("LOCK_Plan"))));
		action.moveToElement(Plan).moveToElement(driver.findElement(By.id("LOCK_Milestone"))).click().build().perform();
		Thread.sleep(40000);

		// Clear Milestone Filter
		driver.findElement(By.id("clearFilterButton-btnIconEl")).click();
		Thread.sleep(2000);

		// Filter Name with Workpackage id
		action.moveToElement(driver.findElement(By.id("CM_NAME-textContainerEl")))
				.moveToElement(driver.findElement(By.id("CM_NAME-triggerEl"))).click().build().perform();
		action.moveToElement(driver.findElement(By.xpath("//input[@name='CM_NAME']")))
				.sendKeys(wpkgid + " - " + milestonename).build().perform();
		driver.findElement(By.xpath("//span[text()='Apply']")).click();
		Thread.sleep(3000);

		// Selecting the first Line Item
		driver.findElement(By.xpath("//table[contains(@id,'gridview')][1]")).click();
		Thread.sleep(3000);

		// Check Planned End date in Milestone e-form
		driver.switchTo().frame(driver.findElement(By.id("contentframe")));
		String mileDate = driver.findElement(By.id("field1")).getText();
		System.out.println("Fetched Milestone due date is :"+mileDate);
		Thread.sleep(1000);
		if (mileDate.equals(newdate)) {
			System.out.println("Milestone Due date: "+mileDate+" match with Planned Finish: "+newdate+" of Milestone Task");
		} else {
			System.out.println("Planned Finish of Milestone Task -"+newdate+" does not match with Milestone Due Date: "+mileDate);
		}

	}

}
