package testcases.login;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import supports.CommonFunctions;

public class LoginCases extends ConfigureTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private CommonFunctions function;

    @BeforeClass
    public void setUp(){
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        function = new CommonFunctions(driver);
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
//        Step 1: Enter username
        loginPage.enterUserName("dgadmin1");
        logger.info("The username was entered!");

//        Step 2: Enter password
        loginPage.enterPassWord("12345678x@X");
        logger.info("The password was entered!");

//        Step 3: Click Login button
        loginPage.clickLogIn();
        logger.info("Login button was clicked!");

//        Wait for loading completed
        CommonFunctions support = new CommonFunctions(driver);
        support.waitForLoadingDisappears(60);
        logger.info("The loading was completed!");

//        Expected result: Home page is opened
        function.verifyPageTitle("Homepage | datavati");
        logger.info("The title of page was verified!");

//        Expected result: Terms & Conditions popup is opened
        function.verifyElementExists(homePage.hedTermsAndConditions);
        logger.info("Terms & Conditions popup was checked!");

    /*    Clean up test case:
    'Close Terms & Conditions popup
    'Select party
    'Logout */
        homePage.acceptTermsAndConditions();
        logger.info("Terms & Conditions was accepted!");
        homePage.switchParty( "DataGene");
        logger.info("The party was selected!");
        loginPage.logOut();
        logger.info("User logged out!");

//        Count failed assertions if any
        function.countFailedAssertions();
    }

    /*===============================================================================
     'TestCaseID:   TC_02
     'Description:  Verify that user logs in unsuccessfully with invalid account
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC02_LoginWithInvalidAccount(){
//        Step 1: Enter invalid username
        loginPage.enterUserName("username");
        logger.info("The username was entered!");

//        Step 2: Enter invalid password
        loginPage.enterPassWord("password");
        logger.info("The password was entered!");

//        Step 3: Click Login button
        loginPage.clickLogIn();
        logger.info("Login button was clicked!");

//        Expected result: Login page is still opened
        function.verifyPageTitle("DataGene Identity Server");
        logger.info("The title of page was verified!");

//        Expected result: Error "Invalid username or password" appears
        function.verifyElementText(loginPage.msgError, "Error: Invalid username or passwordi");
        logger.info("The error message was verified!");

//        Count failed assertions if any
        function.countFailedAssertions();
    }
}
