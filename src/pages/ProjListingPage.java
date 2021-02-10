package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class ProjListingPage extends SeMethods{

	WebDriver driver;
	By projectfilter = By.xpath("//span[@id='PROJECTCODE-textContainerEl']");
	By projectfilterIcon = By.xpath("//div[@id='PROJECTCODE-triggerEl']");
	By projectFilterTextBox = By.xpath("//*[@name='PROJECTCODE']");
	By applybutton = By.xpath("//span[text()='Apply']");
	By searchResult = By.xpath("//td[@data-columnid='PROJECTNAME']");
	public By loading = By.xpath("//div[@class='x-mask-msg-text']");
	
	public ProjListingPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void moveToProjectFilter(){
		moveToElement(locateElement(projectfilter));
	}
	
	public void clickProjectFilterIcon(){
		click(locateElement(projectfilterIcon));
	}
	
	public void enterProjectID(String text){
		moveToElementAndtype(locateElement(projectFilterTextBox), text);
	}
	
	public void applyProjectIdSearch(){
		click(locateElement(applybutton));
	}
	
	public void clickFirstSearchResult(){
		elementToBeInvisibleWait(loading);
		click(locateElement(searchResult));
	}

	
}
