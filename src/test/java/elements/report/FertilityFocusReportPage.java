package elements.report;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import supports.CommonFunctions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class FertilityFocusReportPage {

    private WebDriver driver;
    private CommonFunctions function;

    public FertilityFocusReportPage(WebDriver driver){
        this.driver = driver;

        function = new CommonFunctions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h3[@class='block-title']")
    private WebElement hedFertilityFocusReport;

    @FindBy(xpath = "//div[text()='Select herd']")
    public WebElement drdSelectHerd;

    @FindBy(xpath = "//button[@id='btn-preview-ffr']")
    private WebElement btnPreview;

    @FindBy(xpath = "//button[@id='btn-download-ffr']")
    private WebElement btnDownload;

    @FindBy(xpath = "//button[@id='btn-print-ffr']")
    private WebElement btnPrint;

    /*===============================================================================
    'Method name:  printReport(String HerdName)
    'Description:  Print report in Fertility Focus Report page
    'Arguments:    String HerdName: the name of Herd
    'Created by:   Quang Do
    'Created date: May-10-2019
    ===============================================================================*/
    public void printReport(String HerdName) throws InterruptedException, AWTException, IOException {

        String projectDirectory = System.getProperty("user.dir");
        String userPath = System.getProperty("user.home");
        String filePath = userPath + "\\automation_outputs\\FertilityFocusReport.pdf";

//        Delete old report before printing new file if exist before that
        function.deleteFile(filePath);

//        Handle to select the herd by clicking dropdown then select name
        drdSelectHerd.click();
        driver.findElement(By.xpath("//div[text()='" + HerdName + "']")).click();

//        Click Preview button
        btnPreview.click();

//        Wait for loading completed
        function.waitForLoadingDisappears(60);

//      Wait for Print button is clickable
        function.waitForElementEnabled(btnPrint, 30);

//        Click Print button
        btnPrint.click();

//        Wait for Print ready
        Thread.sleep(3000);

//        Handle to click Print button on Print frame by pressing key {Enter}
        Robot action = new Robot();
        action.keyPress(KeyEvent.VK_ENTER);
        action.keyRelease(KeyEvent.VK_ENTER);

//        Wait for Save As opened
        Thread.sleep(3000);

//        Enter report name as C:\Fertility_Focus_Report.pdf
        Runtime.getRuntime().exec(projectDirectory + "\\autoit\\SavePrintOutputAs.exe");

//        Wait for file created
        function.waitForFileExists(filePath, 30);
    }
}