package testcases.home;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import elements.report.FertilityFocusReportPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import supports.CommonFunctions;
import testcases.login.LoginCases;

public class HomeCases extends ConfigureTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private FertilityFocusReportPage fertilityFocusReportPage;

    @BeforeClass
    public void setUp(){
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        fertilityFocusReportPage = new FertilityFocusReportPage(driver);
    }

    /*===============================================================================
     'TestCaseID:   TC_01
     'Description:  Verify that the fertility focus report is printed correctly
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC01_PrintFertilityFocusReport() throws InterruptedException {
        String userName = "dgadmin1";
        String passWord = "12345678x@X";
        String heardName = "";

//        Step 1: Login
        loginPage.logIn(userName, passWord);
        logger.info("User logged in!");

//        Step 2: Accept Terms & Conditions
        homePage.btnAcceptAndContinue.click();
        logger.info("User accepted the terms and conditions!");

//        Step 3: Select party "DataGene"
        homePage.switchParty("DataGene");
        logger.info("User selected the party!");

//        Step 4: Go to Fertility Focus Report
        driver.navigate().to("http://qc-datavat.datagene.com.au/reports/fertility-focus-report");
        logger.info("Fertility Focus Report was opened!");

//        Step 5: Select the herd
        fertilityFocusReportPage.drdSelectHerd.click();
        driver.findElement(By.xpath("//div[text()='" + heardName + "']")).click();

    }
}
