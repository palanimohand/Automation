package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class ProjectHomePage extends SeMethods{
	
	WebDriver driver;
	By plan = By.id("LOCK_Plan");
	By milestone = By.id("LOCK_Milestone");
	By execute = By.id("LOCK_Execute");
	By workpackage = By.id("LOCK_Workpackages");
	By byProjectStartDate = By.xpath("//span[@id='STARTDATE']");
	By byProjectEndDate = By.xpath("//span[@id='ENDDATE']");
	
	public ProjectHomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void navigateToMilestone(){
		driver.switchTo().defaultContent();
		elementToBeClickableWait(locateElement(plan));
		moveToElementAndClick(locateElement(plan), locateElement(milestone));
	}
	
	public void navigatetoWorkpackage(){
		elementToBeClickableWait(locateElement(execute));
		moveToElementAndClick(locateElement(execute));
		elementToBeClickableWait(locateElement(workpackage));
		moveToElementAndClick(locateElement(workpackage));
	}
	
	public String getProjectStartDate(){
		elementToBePresentWait(byProjectStartDate);
		return getText(locateElement(byProjectStartDate));
	}
	
	public String getProjectEndDate(){
		elementToBePresentWait(byProjectEndDate);
		return getText(locateElement(byProjectEndDate));
	}

}
