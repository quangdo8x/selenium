package testcases.home;

import configurations.ConfigureTest;
import elements.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomeCases extends ConfigureTest {

    private HomePage homePage;

    @BeforeClass
    public void setUp(){
        homePage = new HomePage(driver);
    }

    /*===============================================================================
     'TestCaseID:   TC_01
     'Description:  Test
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/

    @Test
    public void TC01_Test() throws Exception {
        homePage.acceptTermsAndConditions();
        homePage.openLoginPage();
        Assert.fail("true");
//        loginPage.logIn("dgadmin1", "12345678x@X");
    }
}