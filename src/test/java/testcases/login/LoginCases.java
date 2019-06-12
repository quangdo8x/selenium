package testcases.login;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginCases extends ConfigureTest {

    private LoginPage loginPage;
    private HomePage homePage;

    private String userName = "dgadmin1";
    private String passWord = "12345678x@X";

    @BeforeMethod
    public void setUp(){
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);

//        Navigate to page
        driver.navigate().to("http://qc-datavat.datagene.com.au");
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

        SoftAssert softAssert = new SoftAssert();

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
        softAssert.assertEquals("DataGene Identity Server", driver.getTitle().trim());
        logger.info("The title of page has been verified!");

//        Expected result: Error "Invalid username or password" appears
        softAssert.assertEquals("Error: Invalid username or password", loginPage.msgError.getText());
        logger.info("The error message has been verified!");

//        Count failed assertions if any
        softAssert.assertAll();
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

        SoftAssert softAssert = new SoftAssert();

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
        softAssert.assertEquals("Homepage | datavat", driver.getTitle().trim());
        logger.info("The title of page has been verified!");

//        Expected result: The user name is displayed correctly
        softAssert.assertEquals(userName, homePage.lblUsername.getText());
        logger.info("Terms & Conditions popup has been checked!");

//        Count failed assertions if any
        softAssert.assertAll();
    }
}