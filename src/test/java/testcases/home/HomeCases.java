package testcases.home;

import configurations.ConfigureTest;
import elements.HomePage;
import elements.report.HaplotypeReportPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import supports.CommonFunctions;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HomeCases extends ConfigureTest {

    private HaplotypeReportPage haplotypeReportPage;
    private CommonFunctions function;

    private String bull = "29HO17747";

    @BeforeTest
    public void setUp(){
        haplotypeReportPage = new HaplotypeReportPage(driver);
        function = new CommonFunctions(driver);
    }

    /*===============================================================================
     'TestCaseID:   TC_01
     'Description:  Verify that the bull is searched correctly
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void
    TC01_SearchBreadInHaplotypeReport() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        SoftAssert softAssert01 = new SoftAssert();

//        Pre-condition: Accept term and condition
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(homePage.btnAcceptAndContinue));
        homePage.btnAcceptAndContinue.click();
        logger.info("User has accepted the terms and conditions!");

//        Step 1: Navigate to Haplotype Report page
        driver.navigate().to("http://qc-datavat.datagene.com.au/reports/haplotype");
        function.waitForLoadingDisappears(60);
        logger.info("Haplotype Report page has opened!");

//        Step 2: Select breed
//        Step 3: Enter Bull ID
//        Step 4: Click Search button
        haplotypeReportPage.searchBreed("Holstein cross", bull);
        logger.info("User has searched the bull!");

//        Expected result: The bull is found and displayed in table result
        softAssert01.assertTrue(function.isElementDisplayed("//td[text()='" + bull + "']"));

//        Count failed assertions if any
        softAssert01.assertAll();
    }

    /*===============================================================================
     'TestCaseID:   TC_02
     'Description:  Verify that the haplotype report is printed correctly
     'Created By:   Quang Do
     'Created Date: May-10-2019
     'Source:
     ===============================================================================*/
    @Test
    public void TC02_PrintHaplotypeReport() throws IOException, AWTException, InterruptedException {

        String reportPath = "outputs/files/Report.pdf";
        SoftAssert softAssert02 = new SoftAssert();

//        Pre-condition: Search bull
        driver.navigate().to("http://qc-datavat.datagene.com.au/reports/haplotype");
        function.waitForLoadingDisappears(60);
        haplotypeReportPage.searchBreed("Holstein cross", bull);
        logger.info("User has searched the bull!");

//        Step 5: Check on checkbox to select bull
//        Step 6: Click Print button
//        Step 7: Click Print button on Print panel
//        Step 8: Enter report path
//        Step 9: Click Save button
        haplotypeReportPage.printReport(bull);
        logger.info("Haplotype Report has been saved!");

//        Expected result: The report is saved
        File file = new File(reportPath);
        softAssert02.assertTrue(file.exists());

//        Count failed assertions if any
        softAssert02.assertAll();
    }
}