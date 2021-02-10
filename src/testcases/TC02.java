package testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import pages.CreateWorkpackagePage;
import pages.ZExcelRead;
import pages.GeneralListingPage;
import pages.HomePage;
import pages.LinkedTaskPage;
import pages.LoginPage;
import pages.MilestoneDetailsPage;
import pages.ProjListingPage;
import pages.ProjectHomePage;
import pages.TaskDependencyClosureChecklistPage;
import pages.WorkpackageDetailsPage;
import wdMethods.SeMethods;

public class TC02 extends SeMethods {

	public static void main(String[] args) throws IOException, InterruptedException {

		new SeMethods().startApp("chrome");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("455493", "thefighter56");

		HomePage homePage = new HomePage(driver);
		homePage.elcHandle();
		homePage.clickProjLandingPageLink();

		// search project
		ProjListingPage projListingPage = new ProjListingPage(driver);
		projListingPage.moveToProjectFilter();
		projListingPage.clickProjectFilterIcon();
		projListingPage.enterProjectID("57208");
		projListingPage.applyProjectIdSearch();
		projListingPage.clickFirstSearchResult();

		ProjectHomePage projectHomePage = new ProjectHomePage(driver);
		projectHomePage.navigatetoWorkpackage();

		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////// STEP 1															  ////////////////////
		/////////////// Create Work package												  ////////////////////
		///////////////	Check the Milestone Present in the BRD are present in Linked Task ////////////////////
		///////////////	Add a custom Task in Linked Task								  ////////////////////	
		/////////////// Check and compare the manipulation in milestone e-form			  ////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// create a new Work package
		GeneralListingPage generalListingPage = new GeneralListingPage(driver);
		generalListingPage.clickAddWorkpackage();
		CreateWorkpackagePage createWorkpackagePage = new CreateWorkpackagePage(driver);
		createWorkpackagePage.enterName("Test");
		createWorkpackagePage.entercStartDate("01-Aug-2019");
		createWorkpackagePage.entercEndDate("31-Aug-2019");
		createWorkpackagePage.selectWPtype("Cloud Pre-Migration");
		createWorkpackagePage.selectAllLifeCyclePhases();
		createWorkpackagePage.selectTechnology("AB INITIO");
		createWorkpackagePage.uncheckPrjMgmt();
		createWorkpackagePage.clickSaveButton();
		createWorkpackagePage.acceptSaveAlert();

		// get Work package ID and Status
		WorkpackageDetailsPage workpackageDetailsPage = new WorkpackageDetailsPage(driver);
		String wpId = workpackageDetailsPage.getWpId();
		String wpStatus = workpackageDetailsPage.getWpStatus();

		// move to linked task
		workpackageDetailsPage.clickLinkedTaskTab();
		LinkedTaskPage linkedTaskPage = new LinkedTaskPage(driver);

		// Required columns in linked task to write into excel
		ArrayList<String> reqColumns = new ArrayList<String>();
		reqColumns.add("Mark Task as Milestone");
		reqColumns.add("ID");
		reqColumns.add("Phase");
		reqColumns.add("Standard Code");
		reqColumns.add("Name");
		reqColumns.add("Overall Status");
		reqColumns.add("Planned Finish");
		reqColumns.add("Baseline Finish");
		reqColumns.add("Actual Finish");

		// stores linked task milestone data in a excel
		XSSFWorkbook excelformer = linkedTaskPage.excelformer(reqColumns);

		// Fetch the data from the same excel
		XSSFSheet sheet = excelformer.getSheet("Workpackage Milestone Details");

		// Concatenate Phase, Standard code and Name from excel
		System.out.printf("%n");
		System.out.println("PRINTING CONCATENATED VALUES OF PHASE - ACTIVITY NAME - ACTIVITY CODE FROM EXCEL");
		ArrayList<String> concatValFrmExl = new ArrayList<String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			
			String ccat = sheet.getRow(i).getCell(reqColumns.indexOf("Phase")).getStringCellValue().replaceAll("\\s","")
					+ " - "
					+ sheet.getRow(i).getCell(reqColumns.indexOf("Standard Code")).getStringCellValue().replaceAll("\\s", "")
					+ " - "
					+ sheet.getRow(i).getCell(reqColumns.indexOf("Name")).getStringCellValue().replaceAll("\\s", "");
			
			concatValFrmExl.add(ccat);
			System.out.println(ccat);
		}

		// gets concatenated values from BRD
		System.out.printf("%n");
		System.out.println("PRINTING CONCATENATED VALUES OF PHASE - ACTIVITY NAME - ACTIVITY CODE FROM BRD");
		ZExcelRead excel = new ZExcelRead();
		ArrayList<String> concatValFrmBRD = excel.brdreader();

		// comparing the concatenated values from excel and BRD
		System.out.println("");
		System.out.println("COMPARISION OF MILESTONE NAMES FROM BRD AND APPLICATION");
		if (concatValFrmBRD.size() == concatValFrmExl.size()) {
			for (int i = 0; i < concatValFrmExl.size(); i++) {
				if (concatValFrmExl.get(i).equalsIgnoreCase(concatValFrmBRD.get(i))) {
					System.out.println(concatValFrmExl.get(i) + "- match");
				} else {
					System.out.println("Name mismatch");
				}
			}
		} else {
			System.out.println("BRD Reader size: " + concatValFrmBRD.size());
			System.out.println("F Compare size: " + concatValFrmExl.size());
		}

		workpackageDetailsPage.switchToContentFrame();
		linkedTaskPage.switchToLinkedTaskFrame();
		ArrayList<String> linkcols = linkedTaskPage.getlinkTaskCol();

		// Add a custom task in the linked Task section of Work package

		// copy last task standard code and check out
		String lastTaskStandardCode = linkedTaskPage.lastRowTaskStandardCodeCopy(linkcols.indexOf("Standard Code"));
		linkedTaskPage.clickCheckoutButton();

		// click last row task check box
		linkedTaskPage.lastRowTaskCheckboxClick();
		linkedTaskPage.clickAddTaskButton();

		// fill new task mandatory fields
		linkedTaskPage.lastRowTaskSendText(linkcols.indexOf("Name"), "Task added manually in Linked Task");
		linkedTaskPage.lastRowTaskSendText(linkcols.indexOf("Mark Task as Milestone"), "Y");
		linkedTaskPage.lastRowTaskSendText(linkcols.indexOf("Standard Code"), lastTaskStandardCode);
		System.out.printf("%n");
		System.out.println("New Task is added and Name, Standard code and mark as milestone are entered");
		
		// check in and no baseline
		linkedTaskPage.clickCheckinButton();
		linkedTaskPage.clickBaselineNoButton();
		linkedTaskPage.clickBaselineOkButton();

		linkedTaskPage.excelformer(reqColumns);
		
		projectHomePage.navigateToMilestone();

		/////////////////////////////////////////////////// Milestone//////////////////////////////////////////////////

		Thread.sleep(60000);
		
		// required fields in milestone listing page
		ArrayList<String> generalreqColumns = new ArrayList<String>();
		generalreqColumns.add("ID");
		generalreqColumns.add("Name");
		generalreqColumns.add("Status");
		generalreqColumns.add("Actual Milestone End Date");
		generalreqColumns.add("Milestone Due Date");
		generalreqColumns.add("Scope of Milestone");

		// search the name field with work package id
		generalListingPage.clearMilestoneListingPage();
		generalListingPage.moveToNameColumn();
		generalListingPage.clickNameFilter();
		generalListingPage.enterInNameFilter(wpId + " - ");
		generalListingPage.applyNameFilter();

		projListingPage.elementToBeInvisibleWait(projListingPage.loading);

		// sets the necessary columns in milestone listing page and writes the same data in the already existing excel
		XSSFWorkbook allindex = new GeneralListingPage(driver).columnSetter(generalreqColumns);

		// fetch the data from the same excel
		XSSFSheet efromsheet = allindex.getSheet("Milestone e-form Details");
		XSSFSheet wpsheet = allindex.getSheet("Workpackage Milestone Details");

		// method to compare Work package and milestone Name , Status , Milestone Due date and Actual End date of milestone
		excel.comparisionMethod(wpsheet, efromsheet, wpId, reqColumns, generalreqColumns, wpStatus);

		projectHomePage.navigatetoWorkpackage();

		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////// STEP 2														  		    	//////////
		/////////////// Change Work package status										  			//////////
		///////////////	Change Planned Finish date										  			//////////
		/////////////// Check every milestone with field Validations and enter scope of milestone	//////////	
		/////////////// Check and compare the manipulation in milestone e-form			  			//////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////// Work package//////////////////////////////////////////////////

		// search for the created Work package in listing page
		generalListingPage.clearListingPage();
		generalListingPage.moveToIdColumn();
		generalListingPage.clickIdFilter();
		generalListingPage.enterInidFilter(wpId);
		generalListingPage.applyFilter();
		generalListingPage.clickFirstSearchResult();

		// change to in progress status and save
		workpackageDetailsPage.switchToContentFrame();
		workpackageDetailsPage.SelectWpStatus(2);
		workpackageDetailsPage.clickSaveButton();
		projListingPage.elementToBeInvisibleWait(projListingPage.loading);
		// store the status 
		wpStatus = workpackageDetailsPage.getWpStatus();
		System.out.println();
		System.out.println("Workpackage Status Changed to: "+wpStatus);
		
		workpackageDetailsPage.clickLinkedTaskTab();
		//linkedTaskPage.switchToLinkedTaskFrame();
		
		// Check out Work package
		linkedTaskPage.clickCheckoutButton();
		/*driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();*/

		// Change the milestone finish date of all the milestone tasks in Work package
		String changePlannedFinish = new LinkedTaskPage(driver).changePlannedFinish(reqColumns);
		
		// Check out and no baseline
		linkedTaskPage.clickCheckinButton();
		linkedTaskPage.clickBaselineNoButton();
		linkedTaskPage.clickBaselineOkButton();

		// Write the required data to excel from the same Work package columns once again
		linkedTaskPage.excelformer(reqColumns);

		projectHomePage.navigateToMilestone();

		/////////////////////////////////////////////////// Milestone//////////////////////////////////////////////////

		// wait for 1 min and search all Work package milestone and store the same in Excel and compare the WP and milestone data
		milepage(generalListingPage, projListingPage, wpId, reqColumns, generalreqColumns, wpStatus);
		
		// navigate inside each milestone and change the scope of milestone and validate all the fields
		MilestoneDetailsPage milestoneDetailsPage = new MilestoneDetailsPage(driver);
		List<WebElement> allResults = generalListingPage.getAllResults();
		for (int i = 0; i < allResults.size(); i++) {
			System.out.printf("%n");
			allResults.get(i).click();
			milestoneDetailsPage.switchtocframe();
			milestoneDetailsPage.checkNamefield();
			milestoneDetailsPage.checkKED();
			milestoneDetailsPage.checkKID();
			milestoneDetailsPage.checkMDDfield();
			milestoneDetailsPage.checkAEDfield();
			milestoneDetailsPage.checkScopeofMilestone();
			milestoneDetailsPage.checkStatusfield();
			milestoneDetailsPage.enterScopeOfMilestone("Extra");
			milestoneDetailsPage.clickSaveButton();
			milestoneDetailsPage.clickReturnButton();
			driver.switchTo().defaultContent();
			allResults = generalListingPage.getAllResults();
		}

		projectHomePage.navigatetoWorkpackage();

		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////// STEP 3														  			    //////////
		/////////////// Change Work package status										  			//////////
		/////////////// Check out and check in with Work package baseline							//////////
		///////////////	Change Planned Finish date										  			//////////	
		/////////////// Check and compare the manipulation in milestone e-form			  			//////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////// Workpackage//////////////////////////////////////////////////

		// search for the created Work package in listing page		
		generalListingPage.clearListingPage();
		generalListingPage.moveToIdColumn();
		generalListingPage.clickIdFilter();
		generalListingPage.enterInidFilter(wpId);
		generalListingPage.applyFilter();
		generalListingPage.clickFirstSearchResult();

		// change to in progress status and save
		workpackageDetailsPage.switchToContentFrame();
		workpackageDetailsPage.SelectWpStatus(3);
		workpackageDetailsPage.clickSaveButton();
		projListingPage.elementToBeInvisibleWait(projListingPage.loading);
		
		// Store Work package status
		wpStatus = workpackageDetailsPage.getWpStatus();
		System.out.println();
		System.out.println("Workpackage Status Changed to: "+wpStatus);

		// Navigate to Linked Task
		workpackageDetailsPage.clickLinkedTaskTab();
		//linkedTaskPage.switchToLinkedTaskFrame();
		
		// Check out and Check in 
		linkedTaskPage.clickCheckoutButton();
		linkedTaskPage.clickCheckinButton();
		
		// Baseline Work package
		linkedTaskPage.clickBaselineYesButton();
		linkedTaskPage.clickBaselineOkButton();
		
		// Check out once again
		linkedTaskPage.clickCheckoutButton();

/*		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();*/

		// change Planned Finish date once again
		linkedTaskPage.changePlannedFinish(reqColumns, changePlannedFinish);

		// check in Work package
		linkedTaskPage.clickCheckinButton();
		linkedTaskPage.clickBaselineOkButton();

/*		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();*/

		// Write the required data to excel from the same Work package columns once again
		linkedTaskPage.excelformer(reqColumns);

		projectHomePage.navigateToMilestone();

		/////////////////////////////////////////////////// Milestone//////////////////////////////////////////////////

		// wait for 1 min and search all Work package milestone and store the same in Excel and compare the WP and milestone data
		milepage(generalListingPage, projListingPage, wpId, reqColumns, generalreqColumns, wpStatus);
		
		projectHomePage.navigatetoWorkpackage();

		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////// STEP 4														  			    //////////
		/////////////// Change Work package status										  			//////////
		///////////////	Answer all checklist questions									  			//////////	
		/////////////// Close all milestone task													//////////
		/////////////// Check and compare the manipulation in milestone e-form			  			//////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////// Work package//////////////////////////////////////////////////

		// search for the created Work package in listing page
		generalListingPage.clearListingPage();
		generalListingPage.moveToIdColumn();
		generalListingPage.clickIdFilter();
		generalListingPage.enterInidFilter(wpId);
		generalListingPage.applyFilter();
		generalListingPage.clickFirstSearchResult();

		// save status from details tab of Work package
		workpackageDetailsPage.switchToContentFrame();
		wpStatus = workpackageDetailsPage.getWpStatus();
		
		// Navigate to Task Dependency closure checklist Tab
		workpackageDetailsPage.clickTDCTab();
		TaskDependencyClosureChecklistPage taskDependencyClosureChecklistPage = new TaskDependencyClosureChecklistPage(driver);
		
		// close all milestone questions
		taskDependencyClosureChecklistPage.closeAllQuestions();

		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();

		// navigate to linked task tab
		workpackageDetailsPage.clickLinkedTaskTab();
		//linkedTaskPage.switchToLinkedTaskFrame();

/*		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();*/

		// close all milestone tasks
		linkedTaskPage.closeMilestoneTasks(reqColumns);
		
		// check out and check in
		linkedTaskPage.clickCheckoutButton();
		linkedTaskPage.clickCheckinButton();
		linkedTaskPage.clickBaselineOkButton();
/*
		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();*/
		
		// Write the required data to excel from the same Work package columns once again
		linkedTaskPage.excelformer(reqColumns);

		projectHomePage.navigateToMilestone();

		/////////////////////////////////////////////////// Milestone//////////////////////////////////////////////////

		// wait for 1 min and search all Work package milestone and store the same in Excel and compare the WP and milestone data
		milepage(generalListingPage, projListingPage, wpId, reqColumns, generalreqColumns, wpStatus);

		driver.close();

	}

	
	public static void milepage(GeneralListingPage generalListingPage, ProjListingPage projListingPage, String wpId,
			ArrayList<String> reqColumns, ArrayList<String> generalreqColumns, String wpStatus)
			throws IOException, InterruptedException {

		Thread.sleep(60000);

		generalListingPage.clearMilestoneListingPage();
		generalListingPage.moveToNameColumn();
		generalListingPage.clickNameFilter();
		generalListingPage.enterInNameFilter(wpId + " - ");
		generalListingPage.applyNameFilter();

		projListingPage.elementToBeInvisibleWait(projListingPage.loading);

		// selects the necessary columns in milestone listing page
		XSSFWorkbook allindex = new GeneralListingPage(driver).columnSetter(generalreqColumns);

		XSSFSheet efromsheet = allindex.getSheet("Milestone e-form Details");
		XSSFSheet wpsheet = allindex.getSheet("Workpackage Milestone Details");

		new ZExcelRead().comparisionMethod(wpsheet, efromsheet, wpId, reqColumns, generalreqColumns, wpStatus);

	}
}
