package pages;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class GeneralFilterSelection extends SeMethods {

	WebDriver driver;
	By allOptionsList = By.name("AllFieldList");
	By addFilterOptionButton = By.id("QTP_Add_With_Arrow");
	By saveFilterButton = By.id("SaveB");
	By cframe = By.id("contentframe");

	public GeneralFilterSelection(WebDriver driver) {
		this.driver = driver;
		switchToWindow(1);
	}

	public GeneralFilterSelection selectFilterOptions(ArrayList<String> text) {
		for (String element : text) {
			selectDropDownUsingText(locateElement(allOptionsList), element);
		}
		return this;
	}
	
	public GeneralFilterSelection clickAddFilter() {
		click(locateElement(addFilterOptionButton));
		return this;
	}

	public GeneralListingPage saveFilter() {
		click(locateElement(saveFilterButton));
		switchToWindow(0);
		//switchToFrame(locateElement(cframe));
		return new GeneralListingPage(driver);
	}
}
