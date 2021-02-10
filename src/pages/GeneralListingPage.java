package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wdMethods.SeMethods;

public class GeneralListingPage extends SeMethods{

	WebDriver driver;
	By wpAddIcon = By.id("KEY_BUTTON_Add-btnIconEl");
	By applyNameFilter = By.xpath("//span[text()='Apply']");
	By nameFilterTextbox = By.xpath("//input[@name='CM_NAME']");
	By idFilterTextbox = By.xpath("//div[contains(@id,'numberfield')][3]//div/div/div/input");
	By nameFilter = By.id("CM_NAME-triggerEl");
	By idFilter = By.id("CM_SEQNUMBER-triggerEl");
	By nameColumn = By.id("CM_NAME-textContainerEl");
	By idColumn = By.id("CM_SEQNUMBER-textEl");
	By mileclearButton = By.id("clearFilterButton-btnIconEl");
	By searchResult = By.xpath("//table[contains(@id,'gridview')]");
	By loading = By.xpath("//div[@class='x-mask-msg-text']");
	By mileAddIcon = By.id("KEY_BUTTON_Add-btnIconEl");
	By listingpageGrid = By.xpath("//table[contains(@id,'gridview')]");
	By columnHeaders = By.xpath("//div[contains(@class,'x-column-header-inner')]");
	By filterButton = By.id("KEY_LABEL_Display_Table_View");
	By editFilterButton = By.id("tableViewEditButton-KEY_LABEL_Display_Table_View-btnInnerEl");
	By listGrid = By.xpath("//table[contains(@id,'gridview')]");
	
	public GeneralListingPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickAddMilestone() {
		elementToBeClickableWait(locateElement(mileAddIcon));
		click(locateElement(mileAddIcon));
	}

	public void clearMilestoneListingPage() {
		elementToBeClickableWait(locateElement(mileclearButton));
		click(locateElement(mileclearButton));
	}

	public void applyNameFilter() {
		click(locateElement(applyNameFilter));
	}
	
	public List<WebElement> getAllResults(){
		List<WebElement> searchresultele = locateElements(listGrid);
		return searchresultele;
	}

	public ArrayList<String> fetchvaluesof(int index) {
		ArrayList<String> milestoneformnames = new ArrayList<String>();
		for (int z = locateElements(listingpageGrid).size(); z > 0; z--) {
			WebElement milee = locateElements(listingpageGrid).get(z - 1)
			.findElement(By.xpath("//table[contains(@id,'gridview')][" + z + "]/tbody/tr/td[" + (index+1) + "]"));
			milestoneformnames.add(milee
					.getText());
			System.out.println(milee.getText());		
		}
		return milestoneformnames;
	}

	public void clickAddWorkpackage() {
		elementToBeClickableWait(locateElement(wpAddIcon));
		click(locateElement(wpAddIcon));
	}
	
	public void clearListingPage() {
		elementToBeClickableWait(locateElement(mileclearButton));
		click(locateElement(mileclearButton));
	}
	
	public void moveToNameColumn() {
		moveToElement(locateElement(nameColumn));
	}

	public void moveToIdColumn() {
		moveToElement(locateElement(idColumn));
	}
	
	public void clickNameFilter() {
		click(locateElement(nameFilter));
	}
	
	public void clickIdFilter() {
		elementToBeVisibleWait(idFilter);
		click(locateElement(idFilter));
	}

	public void enterInNameFilter(String text) {
		type(locateElement(nameFilterTextbox), text);
	}
	
	
	public void enterInidFilter(String text) {
		elementToBeVisibleWait(idFilterTextbox);
		type(locateElement(idFilterTextbox), text);
	}

	public void applyFilter() {
		click(locateElement(applyNameFilter));
	}
	
	public void clickFirstSearchResult(){
		elementToBeInvisibleWait(loading);
		click(locateElement(searchResult));
	}
	
	public XSSFWorkbook columnSetter(ArrayList<String> reqColumns) throws IOException{
		ArrayList<String> avlColumns = getAvlColumns();
		if (getAvlColumns().containsAll(reqColumns)) {
			return tableformer(reqColumns, avlColumns);
		} else {
			ArrayList<String> buffer = new ArrayList<String>();
			buffer.addAll(reqColumns);
			buffer.removeAll(avlColumns);
			filtersAdder(buffer);
			avlColumns = getAvlColumns();
			return tableformer(reqColumns, avlColumns);
		}
	}

	public void filtersAdder(ArrayList<String> text) {
		clickTableViewButton()
		.clickEditFilterButton()
		.selectFilterOptions(text)
		.clickAddFilter()
		.saveFilter();		
	}

	public GeneralFilterSelection clickEditFilterButton() {
		elementToBeClickableWait(locateElement(editFilterButton));
		click(locateElement(editFilterButton));
		return new GeneralFilterSelection(driver);
	}

	public GeneralListingPage clickTableViewButton() {
		click(locateElement(filterButton));
		return this;
	}

	public ArrayList<String> getAvlColumns() {
		ArrayList<String> avlHeaders = new ArrayList<String>();
		for (WebElement header : locateElements(columnHeaders)) {
			moveToElement(header);
			avlHeaders.add(header.getText());
		}
		return avlHeaders;
	}
	
	public LinkedHashMap<String, Integer> reqColIndexFinder(ArrayList<String> reqColumns, ArrayList<String> avlColumns) {
		LinkedHashMap<String, Integer> allcolumnindex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < reqColumns.size(); i++) {
			for (int j = 0; j < avlColumns.size(); j++) {
				if (reqColumns.get(i).equals(avlColumns.get(j))) {
					allcolumnindex.put(reqColumns.get(i), j);
				}
			}
		}
		return allcolumnindex;
	}
	
	public XSSFWorkbook tableformer(ArrayList<String> reqColumns, ArrayList<String> linkcols) throws IOException {

		LinkedHashMap<String, Integer> allcolumnindex = reqColIndexFinder(reqColumns, linkcols);

		List<WebElement> listGridTables = locateElements(listGrid);
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\455493.old\\workspace\\Automation\\Data\\gfgcontribute.xlsx"));
		XSSFSheet sheet = workbook.getSheet("Milestone e-form Details");
		int rownum = 0;

		// fetching the required data
		for (int j = 0; j < listGridTables.size(); j++) {
			Set<String> keySet = allcolumnindex.keySet();
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (String key : keySet) {
				Cell cell = row.createCell(cellnum++);
				if (rownum == 1) {
					cell.setCellValue(key);
					j = -1;
				} else {
					String rown = driver.findElement(By.xpath("//table[contains(@id,'gridview')]["+(j+1)+ "]/tbody/tr/td[" + (allcolumnindex.get(key) + 1) + "]"))
							.getText();
					if (rown.trim().isEmpty()) {
						cell.setCellValue("Blank");
					} else if (rown.length() > 0) {
						cell.setCellValue(rown);
					}
				}
			}
		}

		FileOutputStream out = new FileOutputStream(
				new File(System.getProperty("user.dir") + "\\Data\\gfgcontribute.xlsx"));
		workbook.write(out);
		workbook.close();
		out.close();
		System.out.println("Milestone e-form Listing Data updated in excel");
		return workbook;

	}
	
}
