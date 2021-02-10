package zmilestoneReview1Code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ZCommonFunc;

public class TC05 {

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
		System.out.println("Test Case 5: Check whether all the milestones in WP are getting listed in Milestone e-form");
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

		// Finding the mark task as milestone column or selecting it and getting the index
		int wantedColumnIndex = ZCommonFunc.wantedColumnIndexFinder("Mark Task as Milestone", action, driver);

		// Getting the milestone table numbers
		ArrayList<Integer> milestonetables = new ArrayList<Integer>();
		List<WebElement> taskstable = driver.findElements(By.xpath("//table[contains(@id,'treeview')]"));
		int e = 1;
		for (WebElement webElement : taskstable) {
			String milestone = webElement.findElement(By
					.xpath("//table[contains(@id,'treeview')][" + e + "]/tbody/tr/td[" + (wantedColumnIndex + 1) + "]"))
					.getText();
			if (milestone.equals("Y")) {
				milestonetables.add(e);
			}
			e++;
		}

		// Fetching the milestone task names
		ArrayList<String> milestonenames = new ArrayList<String>();
		for (int j = 0; j < milestonetables.size(); j++) {
			milestonenames.add(driver
					.findElement(By
							.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(j) + "]/tbody/tr/td[3]"))
					.getText());
		}

		// Sorting milestone names in workpackages
		Collections.sort(milestonenames);

		// Check in and Check out
		driver.findElement(By.id("editButton-btnInnerEl")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("commitButton-btnInnerEl")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='No']")).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();

		//////////////////////////////////////////////////////////////////////////////////////// Milestone
		//////////////////////////////////////////////////////////////////////////////////////// e-form////////////////////////////////////////////////////////////////////////////////////

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
		action.moveToElement(driver.findElement(By.xpath("//input[@name='CM_NAME']"))).sendKeys(wpkgid + " - ").build()
				.perform();
		driver.findElement(By.xpath("//span[text()='Apply']")).click();
		Thread.sleep(3000);

		// Get Milestone names from Milestone eform listing page
		List<WebElement> milestoneeform = driver.findElements(By.xpath("//table[contains(@id,'gridview')]"));
		ArrayList<String> milestoneformnames = new ArrayList<String>();
		int x = 1;
		for (WebElement mwebElement : milestoneeform) {
			String metext = mwebElement
					.findElement(By.xpath("//table[contains(@id,'gridview')][" + x + "]/tbody/tr/td[4]")).getText();
			milestoneformnames.add(metext);
			x++;
		}

		// Sort Milestones names from milestone e-form
		Collections.sort(milestoneformnames);

		// Check if milestones from work packages are matching with
		// milestones from milestone e-form
		if (milestonenames.size() == milestoneformnames.size()) {
			for (int z = 0; z < milestonenames.size(); z++) {
				if ((wpkgid.concat(" - " + milestonenames.get(z))).equals((milestoneformnames).get(z))) {
					System.out.println("Milestone match :" + wpkgid + " - " + milestonenames.get(z) + " -  "
							+ milestoneformnames.get(z));
				} else {
					System.out.println("Mismatch of Milestone Tasks in WP and Milestone e-form");
					break;
				}
			}
		} else {
			System.out.println("Basic mismatch of Milestone Tasks in WP and Milestone e-form");
		}

	}

}
