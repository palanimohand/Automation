package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class CreateMilestonePage extends SeMethods {
	
	WebDriver driver;
	By cframe = By.id("contentframe");
	By nameField = By.id("_Text_Check_CM_Name");
	By keyEdField = By.id("_Text_Check_field4");
	By keyIdField = By.id("_Text_Check_field3");
	By scopeField = By.xpath("//textarea[@id='field2']");
	By mileDueDateField = By.id("field1");
	By saveButton = By.id("SaveBtn");
	
	public CreateMilestonePage(WebDriver driver) {
		this.driver = driver;
		switchToFrame(locateElement(cframe));
	}
	
	public CreateMilestonePage enterName(String text){
		type(locateElement(nameField), text);
		return this;
	}
	
	public CreateMilestonePage enterkeyEdField(String text){
		type(locateElement(keyEdField), text);
		return this;
	}
	
	public CreateMilestonePage enterkeyIdField(String text){
		type(locateElement(keyIdField), text);
		return this;
	}
	
	public CreateMilestonePage enterscopeField(String text){
		type(locateElement(scopeField), text);
		return this;
	}
	
	public CreateMilestonePage entermileDueDateField(String text){
		type(locateElement(mileDueDateField), text);
		return this;
	}
	
	public CreateMilestonePage clickSaveButton(){
		click(locateElement(saveButton));
		return this;
	}	
	
}
