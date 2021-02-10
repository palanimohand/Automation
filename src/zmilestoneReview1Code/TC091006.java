package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC091006 {

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
		System.out.println(
				"Test case 9: Check whether Actual Milestone End date is Milestone Task - Actual Finish");
		System.out.println("Test Case 10: Check whether Milestone is closed when Milestone Task is closed by close time entry");
		System.out.println("Test Case 6: Verify the Key Internal and External Dependencies are auto populated for milestones in WP");  
		System.out.println("Verify the name of the milestone is the milestone name and workpackage id ");
		
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
		int secColumnIndex = ZCommonFunc.wantedColumnIndexFinder("Actual Finish", action, driver);

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

		// Selecting the task and closing it
		String milestonename;
		driver.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[1]"))
				.click();
		driver.findElement(By.id("closeTimeButton-btnIconEl")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		System.out.println("The first milestone is selected and closed");
		
		// Fetching the actual end date
		String aEndDate = driver.findElement(By.xpath(
				"//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[" + (secColumnIndex + 1) + "]"))
				.getText();

		// fetching the Name of the Task closed
		milestonename = driver
				.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetable + "]/tbody/tr/td[3]"))
				.getText();
		
		System.out.println("The name and actual finish date of the task is: "+milestonename+","+aEndDate);

		// Check in
		driver.findElement(By.id("editButton-btnInnerEl")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("commitButton-btnInnerEl")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='No']")).click();
		Thread.sleep(2000);
		System.out.println("Checked out and checked in");
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

		// Check Actual End date in Milestone e-form
		driver.switchTo().frame(driver.findElement(By.id("contentframe")));
		boolean namecheck = driver.findElement(By.id("CM_Name")).getText().trim()
				.equals((wpkgid + " - " + milestonename).trim());
		if (namecheck) {
			System.out.println("Milestone Task Name: "+driver.findElement(By.id("CM_Name")).getText().trim());
			System.out.println("WP Milestone Task Name: "+(wpkgid + " - " + milestonename));
			System.out.println("Milestone Name match with WP");
		} else {
			System.out.println("Milestone Task Name: "+driver.findElement(By.id("CM_Name")).getText().trim());
			System.out.println("WP Milestone Task Name: "+(wpkgid + " - " + milestonename));
			System.out.println("Milestone Name mismatch with WP");
		}
		Thread.sleep(1000);
		String ked = driver.findElement(By.id("field4")).getText().trim();
		Thread.sleep(1000);
		String kld = driver.findElement(By.id("field3")).getText().trim();
		Thread.sleep(1000);
		if (ked.equals("Auto created from work package. To be updated by program/ project manager")
				&& kld.equals("Auto created from work package. To be updated by program/ project manager")) {
			System.out.println("Text Auto popualted Correctly for Key Internal & External Dependencies");
		} else {
			System.out.println("Text mismatch for Key Internal & External Dependencies");
		}
		String mileStatus = driver.findElement(By.id("field5")).getText().trim();
		Thread.sleep(1000);
		if (mileStatus.equals("Completed")) {
			System.out.println(milestonename+": Milestone is Closed");
		} else {
			System.out.println(milestonename+": Milestone is Not Closed");
		}
		String mEndDate = driver.findElement(By.id("field14")).getText().trim();
		if ((mEndDate).equals(aEndDate)) {
			System.out.println("Milestone End date :"+mEndDate);
			System.out.println("Milestone Actual Finish date :"+aEndDate);
			System.out.println("Actual End Date Match with WP");
		} else {
			System.out.println("Milestone End date :"+mEndDate);
			System.out.println("Milestone Actual Finish date :"+aEndDate);
			System.out.println("Actual End Date mismatch with WP");
		}

	}

}
