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
    private HomePage homePage;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver){
        this.driver = driver;

        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "username")
    private WebElement txtUsername;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "login")
    private WebElement btnLogin;

    @FindBy(linkText = "Forgot your password?")
    private WebElement lnkForgotPassword;

    @FindBy(linkText = "Sign up")
    private WebElement lnkSignUp;

    @FindBy(xpath = "//div[@class='alert alert-danger validation-div']")
    public WebElement msgError;

    /*===============================================================================
     'Method name:  enterUserName(String UserName)
     'Description:  Enter text into Username field
     'Arguments:    String UserName: username
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void enterUserName(String UserName){
//        Enter text into username field
        txtUsername.sendKeys(UserName);
    }

    /*===============================================================================
     'Method name:  enterPassWord(String PassWord)
     'Description:  Enter text into password field
     'Arguments:    String PassWord: password
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void enterPassWord(String PassWord){
//        Enter text into password field
        txtPassword.sendKeys(PassWord);
    }

    /*===============================================================================
     'Method name:  clickLogIn()
     'Description:  Click on Login button
     'Arguments:    N/A
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void clickLogIn(){
//        Click on Login button
        btnLogin.click();
    }

    /*===============================================================================
     'Method name:  logIn(String UserName, String PassWord)
     'Description:  Login with username and password
     'Arguments:    String UserName: username
     '              String PassWord: password
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void logIn(String UserName, String PassWord) throws InterruptedException {
//        Enter username
        wait.until(ExpectedConditions.visibilityOf(txtUsername));
        enterUserName(UserName);

//        Enter password
        enterPassWord(PassWord);

//        Click Login
        clickLogIn();

//        Handle to click on Security alert on Firefox
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        if (browserName.equals("firefox") || browserName.equals("internet explorer") || browserName.equals("microsoftedge")){
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        }

//        Wait for loading completed
        CommonFunctions function = new CommonFunctions(driver);
        function.waitForLoadingDisappears(60);
    }

    /*===============================================================================
     'Method name:  logOut()
     'Description:  Logout web page
     'Arguments:    N/A
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void logOut() throws InterruptedException {
//        Select menu item Logout
        homePage.clickOnSubMenu(homePage.lblUsername, homePage.lnkSignOut, false);
    }
}
