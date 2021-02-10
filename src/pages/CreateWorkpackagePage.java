package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class CreateWorkpackagePage extends SeMethods{

	WebDriver driver;
	By cframe = By.id("contentframe");
	By namefield = By.id("_Text_Check_CM_Name");
	By cStartDatefield = By.id("field2");
	By cEndDatefield = By.id("CM_DUEDATE");
	By wpTypeField = By.id("field200");
	By lifecycleField = By.id("field191");
	By techField = By.id("field172");
	By saveButton = By.id("SaveBtn");
	By isPrjMgmtChkBox = By.id("field189");
	

	public CreateWorkpackagePage(WebDriver driver) {
		this.driver = driver;
		switchToFrame(locateElement(cframe));
	}
	
	public void enterName(String text){
		type(locateElement(namefield), text);
	}
	
	public void entercStartDate(String text){
		type(locateElement(cStartDatefield), text);
	}
	
	public void entercEndDate(String text){
		type(locateElement(cEndDatefield), text);
	}
	
	public void selectWPtype(String index){
		selectDropDownUsingText(locateElement(wpTypeField), index);
	}
	
	public void selectAllLifeCyclePhases(){
		elementToBeInvisibleWithTextWait(lifecycleField, "--None--");
		selectAllOptions(locateElement(lifecycleField));
	}
	
	public void selectLifeCyclePhases(String text){
		elementToBeInvisibleWithTextWait(lifecycleField, "--None--");
		selectDropDownUsingText(locateElement(lifecycleField), text);
	}
	
	public void selectTechnology(String text){
		selectDropDownUsingText(locateElement(techField), text);
	}
	
	public void uncheckPrjMgmt(){
		if(verifySelected(locateElement(isPrjMgmtChkBox))){
			click(locateElement(isPrjMgmtChkBox));
		}else{
			System.out.println("Project Management Workpackage is unselected by default");
		}
	}
	
	public void clickSaveButton(){
		click(locateElement(saveButton));
	}
	
	public void acceptSaveAlert(){
		alertWait();
		acceptAlert();
	}

}
