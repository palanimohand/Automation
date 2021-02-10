package pages;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class FilterMenuWindow extends SeMethods {

	WebDriver driver;
	By selectableFilterOptions = By.name("AllFieldList");
	By addFilterOptionButton = By.id("QTP_Add_With_Arrow");
	By saveFilterButton = By.id("SaveB");
	By cframe = By.id("contentframe");

	public FilterMenuWindow(WebDriver driver) {
		this.driver = driver;
		switchToWindow(1);
	}

	public void selectFilterOption(String text) {
		elementToBeClickableWait(locateElement(selectableFilterOptions));
		selectDropDownUsingText(locateElement(selectableFilterOptions), text);
	}

	public void selectFilterOptions(ArrayList<String> text) {
		for (String element : text) {
			selectDropDownUsingText(locateElement(selectableFilterOptions), element);
		}
	}

	public void clickAddFilter() {
		click(locateElement(addFilterOptionButton));
	}

	public void saveFilter() {
		click(locateElement(saveFilterButton));
		switchToWindow(0);
		switchToFrame(locateElement(cframe));
	}

}
