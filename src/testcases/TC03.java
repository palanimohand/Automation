package testcases;

import pages.GeneralListingPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MilestoneDetailsPage;
import pages.ProjListingPage;
import pages.ProjectHomePage;
import wdMethods.SeMethods;

public class TC03 extends SeMethods {
	
	public static void main(String[] args) throws InterruptedException{
		
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
		MilestoneDetailsPage milestoneDetailsPage = new MilestoneDetailsPage(driver);
		milestoneDetailsPage.switchtocframe();
		String projectStartDate = projectHomePage.getProjectStartDate();
		System.out.println("The Project Start Date is: "+projectStartDate);
		String projectEndDate = projectHomePage.getProjectEndDate();
		System.out.println("The Project End Date is: "+projectEndDate);
		driver.switchTo().defaultContent();
		projectHomePage.navigateToMilestone();
		
		GeneralListingPage generalListingPage = new GeneralListingPage(driver);
		generalListingPage.clickAddMilestone();
		
		milestoneDetailsPage.switchtocframe();
		
		milestoneDetailsPage.enterName("Entered Through Automation");
		milestoneDetailsPage.enterKED("Entered Through Automation");
		milestoneDetailsPage.enterKID("Entered Through Automation");
		milestoneDetailsPage.enterMDD(projectStartDate);
		milestoneDetailsPage.enterScopeOfMilestone("Entered Through Automation");
		milestoneDetailsPage.checkStatusfield();
		milestoneDetailsPage.clickSaveButton();
		Thread.sleep(2000);
		System.out.println("Milestone is created after entering the mandatory fields");
		String[] splitSDate = projectStartDate.split("-");
		int sYear = Integer.parseInt(splitSDate[2]);
		String[] splitEDate = projectEndDate.split("-");
		int eYear = Integer.parseInt(splitEDate[2]);
		
		milestoneDetailsPage.enterMDD(splitSDate[0] + "-" + splitSDate[1] + "-" + (sYear - 1));
		milestoneDetailsPage.clickSaveButton();
		milestoneDetailsPage.alertWait();
		milestoneDetailsPage.getAlertText();
		milestoneDetailsPage.acceptAlert();
		
		milestoneDetailsPage.enterMDD(splitEDate[0] + "-" + splitEDate[1] + "-" + (eYear + 1));
		milestoneDetailsPage.clickSaveButton();
		milestoneDetailsPage.alertWait();
		milestoneDetailsPage.getAlertText();
		milestoneDetailsPage.acceptAlert();
		
		milestoneDetailsPage.clickResetButton();
		milestoneDetailsPage.selectStatus("Completed");
		milestoneDetailsPage.checkAMEDMandatory();
		milestoneDetailsPage.clickSaveButton();
		milestoneDetailsPage.checkAMEDCurDate();
		Thread.sleep(2000);
		driver.close();
		
		
		
	}

}
