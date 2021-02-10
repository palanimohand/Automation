package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import wdMethods.SeMethods;

public class WorkpackageDetailsPage extends SeMethods{

	WebDriver driver;
	By wpId = By.id("CM_ItemCode");
	By byWpStatus = By.id("field192");
	By linkTaskTab = By.name("KEY_Linked_Tasks");
	By detailsTab = By.name("KEY_Details");
	By taskDependencyClosureChecklist = By.name("Task Dependency Closure Checklist");
	By cframe = By.id("contentframe");
	By statusSelect = By.id("field192");
	By saveButton = By.id("SaveBtn");

	public WorkpackageDetailsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getWpId(){
		elementToBeClickableWait(locateElement(wpId));
		String WpId = getText(locateElement(wpId));
		return WpId;
	}
	
	public String getWpStatus(){
		elementToBeClickableWait(locateElement(byWpStatus));
		Select ss = new Select(locateElement(byWpStatus));
		String wpStatus = ss.getFirstSelectedOption().getText().trim();
		return wpStatus;
	}
	
	public void SelectWpStatus(int index){
		selectDropDownUsingIndex(locateElement(statusSelect),index);
	}
	
	public void SelectWpStatus(String index){
		selectDropDownUsingText(locateElement(statusSelect),index);
	}
	
	public void clickLinkedTaskTab(){
		elementToBeClickableWait(locateElement(linkTaskTab));
		click(locateElement(linkTaskTab));	
		new LinkedTaskPage(driver).switchToLinkedTaskFrame();
	}
	
	public void clickDetailsTab(){
		elementToBeClickableWait(locateElement(detailsTab));
		click(locateElement(detailsTab));		
	}
	
	public void clickTDCTab(){
		elementToBeClickableWait(locateElement(taskDependencyClosureChecklist));
		click(locateElement(taskDependencyClosureChecklist));		
	}
	
	public void switchToContentFrame(){
		switchToFrame(locateElement(cframe));
	}
	
	public WorkpackageDetailsPage clickSaveButton(){
		click(locateElement(saveButton));
		return this;
	}

}
