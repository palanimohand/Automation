package pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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

public class LinkedTaskPage extends SeMethods {

	WebDriver driver;
	By linktaskcols = By.xpath("//div[@id='taskGrid']/div[1]/div[1]/div[1]/div");
	By tableviewButton = By.id("tableViewButton-btnWrap");
	By editFilterButton = By.id("tableViewEditButton-tableViewButton-btnInnerEl");
	By cframe = By.id("contentframe");
	By linkTaskFrame = By.id("1625395");
	// Unified - 1625395
	// AVM - 299876
	// CIS - 1241297
	By checkoutButton = By.id("editButton-btnInnerEl");
	By checkinButton = By.id("commitButton-btnInnerEl");
	By baselineYesButton = By.xpath("//span[text()='Yes']");
	By baselineNoButton = By.xpath("//span[text()='No']");
	By checkinOkButton = By.xpath("//span[text()='OK']");
	By linkedTaskTable = By.xpath("//table[contains(@id,'treeview')]");
	By editableDateFields = By.xpath("//input[contains(@id,'datefield') and contains(@class,'x-form-required-field')]");
	By addTaskBtn = By.id("addTaskButton-btnIconEl");
	By closeTimeEntryBtn = By.id("closeTimeButton-btnIconEl");
	By ltdatef = By.xpath("//input[contains(@id,'datefield') and contains(@class,'x-form-required-field')]");

	public LinkedTaskPage(WebDriver driver) {
		this.driver = driver;
	}

	public void switchToLinkedTaskFrame() {
		elementToBePresentWait(linkTaskFrame);
		switchToFrame(locateElement(linkTaskFrame));
	}

	public void switchToContentFrame() {
		switchToFrame(locateElement(cframe));
	}

	public void clickTableViewButton() {
		click(locateElement(tableviewButton));
	}

	public void clickEditFilterButton() {
		click(locateElement(editFilterButton));

	}

	public void clickCheckoutButton() {
		click(locateElement(checkoutButton));
	}

	public void clickCheckinButton() {
		click(locateElement(checkinButton));
	}

	public void clickAddTaskButton() {
		click(locateElement(addTaskBtn));
	}

	public void clickBaselineYesButton() {
		click(locateElement(baselineYesButton));
	}

	public void clickBaselineOkButton() {
		elementToBeClickableWait(locateElement(checkinOkButton));
		click(locateElement(checkinOkButton));
	}

	public void clickBaselineNoButton() {
		click(locateElement(baselineNoButton));
		/*
		 * driver.switchTo().defaultContent(); switchToContentFrame();
		 */
	}

	public void clickCloseTimeButton() {
		click(locateElement(closeTimeEntryBtn));
	}

	public void clearDateField() {
		clearContent(locateElement(editableDateFields));
	}

	public void enterDateField(String Date) {
		type(locateElement(editableDateFields), Date);
	}

	public void acceptSaveAlert() {
		alertWait();
		acceptAlert();
	}

	/**
	 * @param text
	 */
	public void filtersAdder(ArrayList<String> text) {
		clickTableViewButton();
		clickEditFilterButton();
		new FilterMenuWindow(driver).selectFilterOptions(text);
		new FilterMenuWindow(driver).clickAddFilter();
		new FilterMenuWindow(driver).saveFilter();
	}

	/**
	 * @return
	 */
	public ArrayList<String> getlinkTaskCol() {
		// switchToLinkedTaskFrame();
		List<WebElement> linkTaskCol = locateElements(linktaskcols);
		ArrayList<String> linkc = new ArrayList<String>();
		for (WebElement webElement : linkTaskCol) {
			moveToElement(webElement);
			linkc.add(webElement.getText());
		}
		return linkc;
	}

	/**
	 * @param wantedColumnIndex
	 * @return
	 */
	public ArrayList<Integer> milestonetablesfinder(int wantedColumnIndex) {
		ArrayList<Integer> milestonetables = new ArrayList<Integer>();
		List<WebElement> taskstable = locateElements(linkedTaskTable);
		for (int i = 0; i < taskstable.size(); i++) {
			String milestone = taskstable.get(i).findElement(By.xpath(
					"//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[" + (wantedColumnIndex + 1) + "]"))
					.getText();
			if (milestone.equals("Y")) {
				milestonetables.add(i + 1);
			}
		}
		return milestonetables;
	}

	/**
	 * @param wantedColumnIndex
	 * @param wantedColumnIndex2
	 * @return
	 */
	public ArrayList<Integer> milestonetablesfinder(int wantedColumnIndex, int wantedColumnIndex2) {
		ArrayList<Integer> milestonetables = new ArrayList<Integer>();
		List<WebElement> taskstable = locateElements(linkedTaskTable);
		for (int i = 0; i < taskstable.size(); i++) {
			String milestone = taskstable.get(i).findElement(By.xpath(
					"//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[" + (wantedColumnIndex + 1) + "]"))
					.getText();
			String sc = taskstable.get(i).findElement(By.xpath(
					"//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[" + (wantedColumnIndex2 + 1) + "]"))
					.getText();
			if ((milestone.equals("N")) && (!(sc.equals("--None--")))) {
				milestonetables.add(i + 1);
			}
		}
		return milestonetables;
	}

	/**
	 * @param reqColumns
	 * @param linkcols
	 * @return
	 */
	public LinkedHashMap<String, Integer> reqColIndexFinder(ArrayList<String> reqColumns, ArrayList<String> linkcols) {
		LinkedHashMap<String, Integer> allcolumnindex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < reqColumns.size(); i++) {
			for (int j = 0; j < linkcols.size(); j++) {
				if (reqColumns.get(i).equals(linkcols.get(j))) {
					allcolumnindex.put(reqColumns.get(i), j);
				}
			}
		}
		return allcolumnindex;
	}

	/**
	 * @param reqColumns
	 * @param linkcols
	 * @return
	 * @throws IOException
	 */
	public XSSFWorkbook tableformer(ArrayList<String> reqColumns, ArrayList<String> linkcols) throws IOException {

		LinkedHashMap<String, Integer> allcolumnindex = reqColIndexFinder(reqColumns, linkcols);

		ArrayList<Integer> milestonetables = milestonetablesfinder(allcolumnindex.get("Mark Task as Milestone"));

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Workpackage Milestone Details");
		workbook.createSheet("Milestone e-form Details");
		int rownum = 0;

		// fetching the required data
		for (int j = 0; j < milestonetables.size(); j++) {
			Set<String> keySet = allcolumnindex.keySet();
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (String key : keySet) {
				Cell cell = row.createCell(cellnum++);
				if (rownum == 1) {
					cell.setCellValue(key);
					j = -1;
				} else {
					String rown = driver.findElement(By.xpath("//table[contains(@id,'treeview')]["
							+ milestonetables.get(j) + "]/tbody/tr/td[" + (allcolumnindex.get(key) + 1) + "]"))
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
		System.out.println();
		System.out.println("Workpackage Linked Task Data updated in Excel");
		driver.switchTo().defaultContent();
		return workbook;

	}

	/**
	 * @param reqColumns
	 * @return
	 * @throws IOException
	 */
	public XSSFWorkbook excelformer(ArrayList<String> reqColumns) throws IOException {
		ArrayList<String> linkcols = getlinkTaskCol();
		if (linkcols.containsAll(reqColumns)) {
			return tableformer(reqColumns, linkcols);
		} else {
			ArrayList<String> buffer = new ArrayList<String>();
			buffer.addAll(reqColumns);
			buffer.removeAll(linkcols);
			filtersAdder(buffer);
			linkcols = getlinkTaskCol();
			return tableformer(reqColumns, linkcols);
		}
	}

	
	/**
	 * @param reqColumns
	 * @return
	 * @throws InterruptedException
	 */
	public String changePlannedFinish(ArrayList<String> reqColumns) throws InterruptedException {
		ArrayList<String> linkcols = getlinkTaskCol();
		LinkedHashMap<String, Integer> allcolumnindex = reqColIndexFinder(reqColumns, linkcols);
		ArrayList<Integer> milestonetables = milestonetablesfinder(allcolumnindex.get("Mark Task as Milestone"));
		String fullDate = driver.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(0)
				+ "]/tbody/tr/td[" + (allcolumnindex.get("Planned Finish") + 1) + "]")).getText();

		// forming the new date
		String[] split = fullDate.split("-");
		int year = Integer.parseInt(split[2]);
		int date = Integer.parseInt(split[0]);
		if (date > 28) {
			date = date - 1;
		} else {
			date = date + 1;
		}
		DecimalFormat df = new DecimalFormat("00");
		String zdate = df.format(date);
		String newdate = zdate + "-" + split[1] + "-" + year;
		System.out.println();
		System.out.println("The new calculated Planned Finish date is: " + newdate);

		// Entering the new date
		for (int i = 0; i < milestonetables.size(); i++) {
			doubleclick(locateElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(i)
					+ "]/tbody/tr/td[" + (allcolumnindex.get("Planned Finish") + 1) + "]")));
			elementToBeVisibleWait(ltdatef);
			actionsendtext(locateElement(ltdatef), newdate);
		}
		System.out.println();
		System.out.println("The new Planned finish date: " + newdate + " is entered");

		return fullDate;

	}

	
	/**
	 * Change the Planned Finish date of all the milestone task
	 * @param reqColumns
	 * @param date
	 * @return
	 * @throws InterruptedException
	 */
	public String changePlannedFinish(ArrayList<String> reqColumns, String date) throws InterruptedException {
		ArrayList<String> linkcols = getlinkTaskCol();
		LinkedHashMap<String, Integer> allcolumnindex = reqColIndexFinder(reqColumns, linkcols);
		ArrayList<Integer> milestonetables = milestonetablesfinder(allcolumnindex.get("Mark Task as Milestone"));
		String fullDate = driver.findElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(0)
				+ "]/tbody/tr/td[" + (allcolumnindex.get("Planned Finish") + 1) + "]")).getText();

		// Entering the new date
		for (int i = 0; i < milestonetables.size(); i++) {
			doubleclick(locateElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(i)
					+ "]/tbody/tr/td[" + (allcolumnindex.get("Planned Finish") + 1) + "]")));
			elementToBeVisibleWait(ltdatef);
			actionsendtext(locateElement(ltdatef), date);
		}
		System.out.println();
		System.out.println("The Old Planned finish date: " + date + "is entered");

		return fullDate;

	}

	/**
	 * close all the milestone task
	 * @param reqColumns
	 */
	public void closeMilestoneTasks(ArrayList<String> reqColumns) {
		ArrayList<String> linkcols = getlinkTaskCol();
		LinkedHashMap<String, Integer> allcolumnindex = reqColIndexFinder(reqColumns, linkcols);
		ArrayList<Integer> milestonetables = milestonetablesfinder(allcolumnindex.get("Mark Task as Milestone"));

		// Selecting the milestone tasks
		for (int i = 0; i < milestonetables.size(); i++) {
			click(locateElement(
					By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(i) + "]/tbody/tr/td[1]")));
		}

		clickCloseTimeButton();
		acceptSaveAlert();
		for (int j = 0; j < milestonetables.size(); j++) {
			WebElement cl = locateElement(By.xpath("//table[contains(@id,'treeview')][" + milestonetables.get(j)
					+ "]/tbody/tr/td[" + allcolumnindex.get("Overall Status") + "]"));
			textToBePresentWait(cl, "Closed");
			break;
		}
	}

	/**
	 * selects the last task check box
	 */
	public void lastRowTaskCheckboxClick() {
		List<WebElement> taskstable = locateElements(linkedTaskTable);
		for (int i = 0; i < taskstable.size(); i++) {
			while (i == (taskstable.size() - 1)) {
				taskstable.get(i)
						.findElement(By.xpath("//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[1]"))
						.click();
				break;
			}
		}
	}

	/**
	 * clears and sends text to the cell of the specified index
	 * 
	 * @param index
	 * @param text
	 */
	public void lastRowTaskSendText(int index, String text) {
		List<WebElement> taskstable = locateElements(linkedTaskTable);
		for (int i = 0; i < taskstable.size(); i++) {
			while (i == (taskstable.size() - 1)) {
				WebElement ele = taskstable.get(i).findElement(By
						.xpath("//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[" + (index + 1) + "]"));
				doubleclickAndSendText(ele, text);
				break;
			}
		}
	}

	/**
	 * copies the last task standard code
	 * 
	 * @param index
	 * @return
	 * @throws InterruptedException
	 */
	public String lastRowTaskStandardCodeCopy(int index) throws InterruptedException {
		List<WebElement> taskstable = locateElements(linkedTaskTable);
		String tex = null;
		for (int i = 0; i < taskstable.size(); i++) {
			while (i == (taskstable.size() - 1)) {
				WebElement ele = taskstable.get(i).findElement(By
						.xpath("//table[contains(@id,'treeview')][" + (i + 1) + "]/tbody/tr/td[" + (index + 1) + "]"));
				tex = getText(ele);
				break;
			}
		}
		System.out.printf("%n");
		System.out.println("Last Task Standard Code Copied");
		return tex;
	}
}
