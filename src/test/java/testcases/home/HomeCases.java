package testcases.home;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.LoginPage;
import elements.report.FertilityFocusReportPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
    public void TC01_PrintFertilityFocusReport() throws InterruptedException, IOException, AWTException {
        String userName = "dgadmin1";
        String passWord = "12345678x@X";
        String heardName = "";
        String reportPath = "outputs/files/FertilityFocusReport.pdf";

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
        logger.info("Fertility Focus Report page was opened!");

//        Step 5: Select the herd
//        Step 6: Click Preview button
//        Step 7: Click Print button
//        Step 8: Click Print button on Print Preview
//        Step 9: Enter file path of report
//        Step 10: Click Save button
        fertilityFocusReportPage.printReport(heardName);
        logger.info("Fertility Focus Report was saved!");

//        Expected result: Fertility Focus report is saved
        File file = new File(reportPath);
        softAssert.assertTrue(file.exists());

//        Expected result: Fertility Focus report content is correct
        fertilityFocusReportPage.verifyFertilityFocusReport(heardName);

//        Count failed assertions if any
        softAssert.assertAll();
    }
}