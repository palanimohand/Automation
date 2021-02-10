package testcases;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pages.CreateWorkpackagePage;
import pages.ZExcelRead;
import pages.GeneralListingPage;
import pages.HomePage;
import pages.LinkedTaskPage;
import pages.LoginPage;
import pages.ProjListingPage;
import pages.ProjectHomePage;
import pages.WorkpackageDetailsPage;
import wdMethods.SeMethods;

public class TC04 extends SeMethods{

	public static void main(String[] args) throws IOException, InterruptedException {

		new SeMethods().startApp("chrome");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("455493", "thefighter56");

		HomePage homePage = new HomePage(driver);
		homePage.elcHandle();
		homePage.clickProjLandingPageLink();

		ProjListingPage projListingPage = new ProjListingPage(driver);
		projListingPage.moveToProjectFilter();
		projListingPage.clickProjectFilterIcon();
		projListingPage.enterProjectID("57208");
		projListingPage.applyProjectIdSearch();
		projListingPage.clickFirstSearchResult();

		ProjectHomePage projectHomePage = new ProjectHomePage(driver);
		projectHomePage.navigatetoWorkpackage();

		GeneralListingPage generalListingPage = new GeneralListingPage(driver);
		generalListingPage.clickAddWorkpackage();

		CreateWorkpackagePage createWorkpackagePage = new CreateWorkpackagePage(driver);
		createWorkpackagePage.enterName("Test");
		createWorkpackagePage.entercStartDate("01-Mar-2019");
		createWorkpackagePage.entercEndDate("31-Mar-2019");
		createWorkpackagePage.selectWPtype("Cloud Pre-Migration");
		createWorkpackagePage.selectAllLifeCyclePhases();
		createWorkpackagePage.selectTechnology("AB INITIO");
		createWorkpackagePage.uncheckPrjMgmt();
		createWorkpackagePage.clickSaveButton();
		createWorkpackagePage.acceptSaveAlert();

		WorkpackageDetailsPage workpackageDetailsPage = new WorkpackageDetailsPage(driver);
		workpackageDetailsPage.clickLinkedTaskTab();

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
		LinkedTaskPage linkedTaskPage = new LinkedTaskPage(driver);
		linkedTaskPage.clickCheckoutButton();
		linkedTaskPage.clickCheckinButton();
		linkedTaskPage.clickBaselineNoButton();
		linkedTaskPage.clickBaselineOkButton();
		driver.switchTo().defaultContent();
		workpackageDetailsPage.switchToContentFrame();
		workpackageDetailsPage.clickDetailsTab();
		String wpId = workpackageDetailsPage.getWpId();
		workpackageDetailsPage.SelectWpStatus(4);
		String wpStatus = workpackageDetailsPage.getWpStatus();
		System.out.println(wpStatus);
		createWorkpackagePage.clickSaveButton();
		createWorkpackagePage.acceptSaveAlert();
		Thread.sleep(2000);
		workpackageDetailsPage.clickLinkedTaskTab();
		
		linkedTaskPage.excelformer(reqColumns);

		projectHomePage.navigateToMilestone();

		///////////////////////////////////////////////////Milestone//////////////////////////////////////////////////

		Thread.sleep(60000);

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

		// selects the necessary columns in milestone listing page
		XSSFWorkbook allindex = new GeneralListingPage(driver).columnSetter(generalreqColumns);

		XSSFSheet efromsheet = allindex.getSheet("Milestone e-form Details");
		XSSFSheet wpsheet = allindex.getSheet("Workpackage Milestone Details");

		new ZExcelRead().comparisionMethod(wpsheet, efromsheet, wpId, reqColumns, generalreqColumns, wpStatus);
		Thread.sleep(2000);
		driver.close();
	}

}
