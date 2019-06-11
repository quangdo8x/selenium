package elements;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import supports.CommonFunctions;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
    }

    @FindBy(id = "btn-login-redirect")
    private WebElement btnRedirectToLogin;

    @FindBy(id = "username")
    public WebElement txtUsername;

    @FindBy(id = "password")
    public WebElement txtPassword;

    @FindBy(id = "login")
    public WebElement btnLogin;

    @FindBy(linkText = "Forgot your password?")
    private WebElement lnkForgotPassword;

    @FindBy(linkText = "Sign up")
    private WebElement lnkSignUp;

    @FindBy(xpath = "//div[@class='alert alert-danger validation-div']")
    public WebElement msgError;

    /*===============================================================================
     'Method name:  logIn(String UserName, String PassWord)
     'Description:  Login with username and password
     'Arguments:    String UserName: username
     '              String PassWord: password
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void logIn(String UserName, String PassWord) throws InterruptedException {
//        Click Login button to redirect to Login page
        CommonFunctions function = new CommonFunctions(driver);
        function.waitForElementEnabled(btnRedirectToLogin, 30);
        btnRedirectToLogin.click();

//        Enter username
        wait.until(ExpectedConditions.visibilityOf(txtUsername));
        txtUsername.sendKeys(UserName);

//        Enter password
        txtPassword.sendKeys(PassWord);

//        Click Login
        btnLogin.click();

//        Handle to click on Security alert on Firefox
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        if (browserName.equals("firefox") || browserName.equals("internet explorer") || browserName.equals("microsoftedge")){
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        }

//        Wait for loading completed
        function.waitForLoadingDisappears(60);
    }
}