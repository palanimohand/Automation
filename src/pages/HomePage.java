package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class HomePage extends SeMethods {

	WebDriver driver;
	By muteicon = By.xpath("//span[text()='Notification']//preceding::div[@class='mute_advanced_notification unmute']");
	By ProjectProgramSection = By.xpath("//span[text()='Project / Program']");
	By LeftPane = By.xpath("//div[@class='left_menu_icon_overlay']//span");
	By ProjectProgramLink = By.xpath("//a[text()='View My Projects / Programs']");
	By tickIcon = By.xpath("//span[contains(@class,'icon-accept')][1]");
	By lockscreen = By.id("loadingDiv");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickMuteIcon() {
		elementToBeClickableWait(locateElement(tickIcon));
		locateElement(muteicon).click();
	}

	public void clickProjectProgramSection() {
		elementToBeClickableWait(locateElement(ProjectProgramSection));
		locateElement(ProjectProgramSection).click();
	}

	public void clickLeftPane() {
		elementToBeClickableWait(locateElement(LeftPane));
		locateElement(LeftPane).click();
	}

	public void clickProjLandingPageLink() {
		elementToBeClickableWait(locateElement(ProjectProgramLink));
		click(locateElement(ProjectProgramLink));
	}

	public void elcHandle() {
		try {
			clickMuteIcon();
			clickProjectProgramSection();
		} catch (Exception e) {
			clickLeftPane();
		}
	}
}