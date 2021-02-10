package testcases;

import pages.GeneralListingPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MilestoneDetailsPage;
import pages.ProjListingPage;
import pages.ProjectHomePage;
import wdMethods.SeMethods;

public class TC01 extends SeMethods {

	public static void main(String[] args) throws InterruptedException {

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
		System.out.println(projectStartDate);
		String projectEndDate = projectHomePage.getProjectEndDate();
		System.out.println(projectEndDate);
		driver.switchTo().defaultContent();
		projectHomePage.navigateToMilestone();

		GeneralListingPage generalListingPage = new GeneralListingPage(driver);
		generalListingPage.clickAddMilestone();

		milestoneDetailsPage.switchtocframe();

		milestoneDetailsPage.enterName("Entered Through Automation");
		milestoneDetailsPage.enterKED("Emtered Through Automation");
		milestoneDetailsPage.enterKID("Emtered Through Automation");
		milestoneDetailsPage.enterMDD(projectStartDate);
		milestoneDetailsPage.enterScopeOfMilestone("Entered Through Automation");
		milestoneDetailsPage.checkStatusfield();
		milestoneDetailsPage.clickSaveButton();
		Thread.sleep(2000);
		milestoneDetailsPage.selectStatus("Cancelled");
		milestoneDetailsPage.checkAMEDMandatory();
		milestoneDetailsPage.clickSaveButton();
		Thread.sleep(2000);
		driver.close();
	}
}
