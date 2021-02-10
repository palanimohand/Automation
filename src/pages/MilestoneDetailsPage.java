package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import wdMethods.SeMethods;

public class MilestoneDetailsPage extends SeMethods{
	
	WebDriver driver;
	By byName = By.id("_Text_Check_CM_Name");
	By byRdOlyName = By.id("CM_Name");
	By byKED = By.id("_Text_Check_field4");
	By byKID = By.id("_Text_Check_field3");
	By byScopeOfMilestone = By.xpath("//textarea[@id='field2']");
	By byMilestoneStatus = By.id("field5");
	By byMilestoneDueDate = By.id("field1");
	By byActualEndDate = By.id("field14");
	By bycframe = By.id("contentframe");
	By bySaveButton = By.id("SaveBtn");
	By byReturnButton = By.id("CancelBtn");
	By byResetButton = By.id("ResetBtn");
	By byt = By.tagName("Input");
	By byMAEDMandatory = By.xpath(
			"//td[@title='Actual Milestone End Date']/following::td[contains(@class,'mandatoryField')]");
	ArrayList<String> inputTagCheckUp = inputTagCheckUp();
	
	
	public MilestoneDetailsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchtocframe(){
		elementToBePresentWait(bycframe);
		switchToFrame(locateElement(bycframe));
	}
	
	public void enterName(String text){
		type(locateElement(byName), text);
	}
	
	public void enterKED(String text){
		type(locateElement(byKED), text);
	}
	
	public void enterKID(String text){
		type(locateElement(byKID), text);
	}
	
	public void enterScopeOfMilestone(String text){
		type(locateElement(byScopeOfMilestone), text);
		System.out.println(text+" is entered in Scope");
	}
	
	public void selectStatus(String text){
		selectDropDownUsingText(locateElement(byMilestoneStatus), text);
	}
	
	public void enterMDD(String text){
		type(locateElement(byMilestoneDueDate), text);
		System.out.println("The date: "+text+" is entered in Milestone Due Date");
	}
	
	public void enterAED(String text){
		type(locateElement(byActualEndDate), text);
	}
	
	public void clickSaveButton(){
		elementToBeClickableWait(locateElement(bySaveButton));
		click(locateElement(bySaveButton));
	}

	public void clickReturnButton(){
		elementToBeClickableWait(locateElement(byReturnButton));
		click(locateElement(byReturnButton));
	}
	
	public void clickResetButton(){
		elementToBeClickableWait(locateElement(byResetButton));
		click(locateElement(byResetButton));
	}
	
	public ArrayList<String> inputTagCheckUp(){
		ArrayList<String> inputtags = new ArrayList<String>();
		for (WebElement els : locateElements(byt)) {
			inputtags.add(getAttribute(els, "id"));
		}
		return inputtags;
	}
	
	public void checkNamefield(){
		if(inputTagCheckUp.contains("_Text_Check_CM_Name")) {
			System.out.println("Name field is not Readonly");
		} else {
			System.out.println(getText(locateElement(byRdOlyName)));
			System.out.println("Name field is Readonly");
			
		}
	}
	
	public void checkStatusfield(){
		if(inputTagCheckUp.contains("field5")) {
			System.out.println("Status field is not Readonly");
		} else {
			System.out.println("Status field is Readonly");
		}
	}
	
	public void checkMDDfield(){
		if(inputTagCheckUp.contains("field1")){
			System.out.println("Milestone Due Date field is not Readonly");
		} else {
			System.out.println("Milestone Due Date field is Readonly");
		}
	}
	
	public void checkAEDfield(){
		if(inputTagCheckUp.contains("field14")) {
			System.out.println("Milestone Actual End Date field is not Readonly");
		} else {
			System.out.println("Milestone Actual End Date field is Readonly");
		}
	}
	
	public void checkKED(){
		if ((getAttribute(locateElement(byKED), "value").trim()).equals("Auto created from work package. To be updated by program/ project manager")) {
			System.out.println("Text Auto popualted Correctly for Key External Dependencies: "+getAttribute(locateElement(byKED), "value").trim());
		} else {
			System.out.println("Text mismatch for Key External Dependencies: "+getAttribute(locateElement(byKED), "value").trim());
		}
	}
	
	public void checkKID(){
		if ((getAttribute(locateElement(byKID), "value").trim()).equals("Auto created from work package. To be updated by program/ project manager")) {
			System.out.println("Text Auto popualted Correctly for Key Internal Dependencies: "+getAttribute(locateElement(byKID), "value").trim());
		} else {
			System.out.println("Text mismatch for Key Internal Dependencies: "+getAttribute(locateElement(byKID), "value").trim());
		}
	}
	
	public void checkScopeofMilestone(){
		if (getText(locateElement(byScopeOfMilestone)).trim().equals("Open")) {
			System.out.println("Text Auto popualted Correctly for Scope of Milestone: "+getText(locateElement(byScopeOfMilestone)));
		} else {
			System.out.println("Text mismatch for Scope of Milestone");
		}
	}
	
	public void checkAMEDMandatory(){
		try {
			elementToBePresentWait(byMAEDMandatory);
			locateElement(byMAEDMandatory);
			System.out.println("Actual Milestone End Date is made mandatory");
		} catch (NoSuchElementException e) {
			System.out.println("Actual Milestone End Date is not made mandatory");
		}
	}
	
	public String getMAED(){
		System.out.println(getText(locateElement(byActualEndDate)).trim());
		return getText(locateElement(byActualEndDate)).trim();
	}
	
	public void checkAMEDCurDate(){
		elementToBeInvisibleWait(bySaveButton);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate ldate = LocalDate.now();
		//System.out.println(dtf.format(ldate));
		if (getMAED().equals(dtf.format(ldate))) {
			System.out.println("Current Date: "+dtf.format(ldate)+"is populated in Actual Milestone End Date");
		} else {
			System.out.println("Current Date is not populated in Actual Milestone End Date");
		}
	}
	
}
