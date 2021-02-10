package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wdMethods.SeMethods;

public class LoginPage extends SeMethods {

	WebDriver driver;
	By eLogin = By.id("loginId");
	By ePwd = By.id("password");
	By ePwdB = By.id("QTP_LoginButton");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterLoginID(String uid) {
		type(locateElement(eLogin), uid);
	}

	public void enterPwd(String upass) {
		type(locateElement(ePwd), upass);
	}

	public void clickLogin() {
		click(locateElement(ePwdB));
	}
	
	public void login(String uid, String upass){
		enterLoginID(uid);
		enterPwd(upass);
		clickLogin();
	}

}
