package testcases.login;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginCases extends ConfigureTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private String expectedTitle = "DataGene Identity Server";
    private String actualTitle = driver.getTitle().trim();

    @BeforeClass
    public void setUp(){
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    /*===============================================================================
     'TestCaseID:   TC_01
     'Description:  Verify that user logs in successfully with valid account
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC01_LoginWithValidAccount() throws InterruptedException {

        String userName = "dgadmin1";
        String passWord = "12345678x@X";
        String expectedTitle = "Homepage | datavat";

//        Step 1: Enter username
//        Step 2: Enter password
//        Step 3: Click Login button
        loginPage.logIn(userName, passWord);
        logger.info("User has been logged in!");

//        Expected result: Home page is opened
        softAssert.assertEquals(expectedTitle, actualTitle);
        logger.info("The title of page has been verified!");

//        Expected result: Terms & Conditions popup is opened
        softAssert.assertTrue(homePage.hedTermsAndConditions.isDisplayed());
        logger.info("Terms & Conditions popup has been checked!");

//        Count failed assertions if any
        softAssert.assertAll();
    }

    /*===============================================================================
     'TestCaseID:   TC_02
     'Description:  Verify that user logs out successfully
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC02_LogOut() throws InterruptedException {

//        Pre-conditions: Click Accept & Continue button
        homePage.btnAcceptAndContinue.click();
        logger.info("Terms & Conditions has been accepted!");

//        Pre-conditions: Select the party
        homePage.switchParty( "DataGene");
        logger.info("The party has been selected!");

//        Step 1: Select menu item Sign Out from Username label
        homePage.clickOnSubMenu(homePage.lblUsername, homePage.lnkSignOut, false);
        logger.info("User has logged out!");

//        Expected result: Login page is opened
        softAssert.assertEquals(expectedTitle, actualTitle);
        logger.info("The title of page has been verified!");

//        Expected result: Username field is displayed
        softAssert.assertTrue(loginPage.txtUsername.isDisplayed());
        logger.info("The field has been verified!");

//        Expected result: Password field is displayed
        softAssert.assertTrue(loginPage.txtPassword.isDisplayed());
        logger.info("The field has been verified!");

//        Count failed assertions if any
        softAssert.assertAll();
    }

    /*===============================================================================
     'TestCaseID:   TC_03
     'Description:  Verify that user logs in unsuccessfully with invalid account
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC03_LoginWithInvalidAccount() throws InterruptedException {

        String userName = "invalidusername";
        String passWord = "invalidpassword";
        String expectedMessage = "Error: Invalid username or password";
        String actualMessage = loginPage.msgError.getText();

//        Step 1: Enter invalid username
//        Step 2: Enter invalid password
//        Step 3: Click Login button
        loginPage.logIn(userName, passWord);
        logger.info("User has been logged in!");

//        Expected result: Login page is still opened
        softAssert.assertEquals(expectedTitle, actualTitle);
        logger.info("The title of page has been verified!");

//        Expected result: Error "Invalid username or password" appears
        softAssert.assertEquals(expectedMessage, actualMessage);
        logger.info("The error message has been verified!");

//        Count failed assertions if any
        softAssert.assertAll();
    }
}