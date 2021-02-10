package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import wdMethods.SeMethods;

public class TaskDependencyClosureChecklistPage extends SeMethods{
	
	WebDriver driver;
	By tdcFrame = By.id("1625401");
	By bychecklistTasks = By.xpath("//table[@id='upfDataListTable']/tbody/tr");
	By byQuestionFrame = By.xpath("//*[@id='ext-element-1']/div[5]/iframe");
	By bychecklistQues = By.xpath("//div[@id='resultDiv']/table/tbody/tr");
	By byPushButton = By.id("PushButton");
	By byRefreshButton = By.id("refreshBtn");
	By byXButton = By.xpath("//div[contains(@class,'close_light_box')]");
	By byCframe = By.id("contentframe");
	
	public TaskDependencyClosureChecklistPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchtoTDCFrame(){
		switchToFrame(locateElement(tdcFrame));
	}
	
	public void switchtoQuesFrame(){
		elementToBePresentWait(byQuestionFrame);
		switchToFrame(locateElement(byQuestionFrame));
	}
	
	public void switchToContentFrame(){
		switchToFrame(locateElement(byCframe));
	}
	
	public void closeAllQuestions(){
		switchtoTDCFrame();
		List<WebElement> checklistTasks = locateElements(bychecklistTasks);
		for (int i = 2; i <=checklistTasks.size(); i++) {
			WebElement cpt = driver.findElement(By.xpath("//table[@id='upfDataListTable']/tbody/tr["+i+"]/td/a"));
			elementToBeClickableWait(cpt);
			click(cpt);
			driver.switchTo().defaultContent();
			switchtoQuesFrame();
			List<WebElement> sst = locateElements(bychecklistQues);
			for (int j = 2; j <= sst.size(); j++) {
				WebElement csel = locateElement(By.xpath("//div[@id='resultDiv']/table/tbody/tr["+j+"]/td[3]/select"));				
				selectDropDownUsingText(csel, "Yes");
			}
			click(locateElement(byPushButton));
			elementToBeClickableWait(locateElement(byRefreshButton));
			driver.switchTo().defaultContent();
			elementToBeClickableWait(locateElement(byXButton));
			click(locateElement(byXButton));
			switchToContentFrame();
			switchtoTDCFrame();
		}
	}
}
