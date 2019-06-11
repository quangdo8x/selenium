package testcases.login;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import supports.CommonFunctions;

public class LoginCases extends ConfigureTest {

    private LoginPage loginPage;
    private HomePage homePage;

    private String userName = "dgadmin1";
    private String passWord = "12345678x@X";

    @BeforeTest
    public void setUp(){
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    /*===============================================================================
     'TestCaseID:   TC_01
     'Description:  Verify that user logs in unsuccessfully with invalid account
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC01_LoginWithInvalidAccount() throws InterruptedException {

        SoftAssert softAssert01 = new SoftAssert();

//        Pre-condition: Accept term and condition
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(homePage.btnAcceptAndContinue));
        homePage.btnAcceptAndContinue.click();

//        Step 1: Enter invalid username
//        Step 2: Enter invalid password
//        Step 3: Click Login button
        loginPage.logIn("invalidusername", "invalidpassword");
        logger.info("User has been logged in with invalid account!");

//        Expected result: Login page is still opened
        softAssert01.assertEquals("DataGene Identity Server", driver.getTitle().trim());
        logger.info("The title of page has been verified!");

//        Expected result: Error "Invalid username or password" appears
        softAssert01.assertEquals("Error: Invalid username or password", loginPage.msgError.getText());
        logger.info("The error message has been verified!");

//        Count failed assertions if any
        System.out.println("Counting assertion!!");
        softAssert01.assertAll();
    }

    /*===============================================================================
     'TestCaseID:   TC_02
     'Description:  Verify that user logs in successfully with valid account
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC02_LoginWithValidAccount() throws InterruptedException {

        SoftAssert softAssert02 = new SoftAssert();

//        Pre-condition: Navigate to page
        driver.navigate().to("http://qc-datavat.datagene.com.au");

//        Step 1: Enter username
//        Step 2: Enter password
//        Step 3: Click Login button
        loginPage.logIn(userName, passWord);
        logger.info("User has been logged in!");

//        Step 4: Select party
        homePage.switchParty("DATAGENE");

//        Expected result: Home page is opened
        softAssert02.assertEquals("Homepage | datavat", driver.getTitle().trim());
        logger.info("The title of page has been verified!");

//        Expected result: The user name is displayed correctly
        softAssert02.assertEquals(userName, homePage.lblUsername.getText());
        logger.info("Terms & Conditions popup has been checked!");

//        Count failed assertions if any
        System.out.println("Counting assertion!!");
        softAssert02.assertAll();
    }

    /*===============================================================================
     'TestCaseID:   TC_03
     'Description:  Verify that user logs out successfully
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC03_LogOut() throws InterruptedException {

        String loginXPath = "//a[@id='btn-login-redirect']";
        SoftAssert softAssert03 = new SoftAssert();

//        Precondition: Login
        driver.navigate().to("http://qc-datavat.datagene.com.au");
        CommonFunctions function = new CommonFunctions(driver);
        if (function.isElementDisplayed(loginXPath)){
            loginPage.logIn(userName, passWord);
        }

//        Step 1: Select menu item Sign Out from Username label
        homePage.clickOnSubMenu(homePage.lblUsername, homePage.lnkSignOut);
        logger.info("User has logged out!");

//        Expected result: Login button is displayed
        softAssert03.assertTrue(function.isElementDisplayed(loginXPath));
        logger.info("Login button has been verified!");

//        Expected result: Username label disappears
        softAssert03.assertFalse(function.isElementDisplayed("//li[@class='user-name-menu-li']/span"));
        logger.info("Username label has been verified!");

//        Count failed assertions if any
        System.out.println("Counting assertion!!");
        softAssert03.assertAll();
    }
}