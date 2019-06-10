package elements.report;

import configurations.ConfigureTest;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import supports.CommonFunctions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class HaplotypeReportPage {

    private WebDriver driver;
    private CommonFunctions function;

    public HaplotypeReportPage(WebDriver driver){
        this.driver = driver;

        function = new CommonFunctions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h3[@class='block-title']")
    private WebElement hedHaplotypeReport;

    @FindBy(xpath = "//div[@class='select-selected form-control']")
    private WebElement drdSelectBreeds;

    @FindBy(id = "edit-search-text")
    private WebElement txtSearch;

    @FindBy(id = "btnSubmit")
    private WebElement btnSearch;

    @FindBy(id = "btn-clear")
    private WebElement btnClear;

    @FindBy(id = "btnExportExcel")
    private WebElement btnExportExcel;

    @FindBy(id = "btnPrint")
    private WebElement btnPrint;

    /*===============================================================================
    'Method name:  searchBreed(String Breed, String Keyword)
    'Description:  Search breed in Haplotype Report page
    'Arguments:    String Breed: the type of breed
    '              String Keyword: Bull ID, Name or Herdbook Number
    'Created by:   Quang Do
    'Created date: May-10-2019
    ===============================================================================*/
    public void searchBreed(String Breed, String Keyword) throws InterruptedException {
//        Select breed if the argument is not blank
        if (!Breed.equals("")) {
            drdSelectBreeds.click();
            driver.findElement(By.xpath("//div[text()='" + Breed + "']")).click();
        }

//        Enter keyword if the argument is not blank
        if (!Keyword.equals("")){
            txtSearch.sendKeys(Keyword);
        }

//        Click Search button
        btnSearch.click();

//        Wait for loading completed
        function.waitForLoadingDisappears(60);
    }

    /*===============================================================================
    'Method name:  printReport(String Bull)
    'Description:  Print report in Haplotype Report page
    'Arguments:    String Bull: Bull ID, Name or Herdbook Number
    'Created by:   Quang Do
    'Created date: May-10-2019
    ===============================================================================*/
    public void printReport(String Bull) throws InterruptedException, AWTException, IOException {

        String filePath = "outputs/files/Report.pdf";

//        Delete old report before printing new file if exist before that
        function.deleteFile(filePath);

//        Select bull in result table
        driver.findElement(By.xpath("//td[text()='" + Bull + "']/preceding-sibling::td/label/input")).click();

//      Wait for Print button is clickable
        function.waitForElementEnabled(btnPrint, 30);

//        Click Print button
        btnPrint.click();

//        Wait for loading completed
        function.waitForLoadingDisappears(60);

//        Wait for Print ready
        Thread.sleep(3000);

//        Handle to click Print button on Print frame by pressing key {Enter}
        Robot action = new Robot();
        action.keyPress(KeyEvent.VK_ENTER);
        action.keyRelease(KeyEvent.VK_ENTER);

//        Wait for Save As opened
        Thread.sleep(3000);

//        Enter report name as C:\Fertility_Focus_Report.pdf
//        Runtime.getRuntime().exec(projectDirectory + "\\autoit\\SavePrintOutputAs.exe");
        Runtime.getRuntime().exec( "autoit/SavePrintOutputAs.exe");

//        Wait for file created
        function.waitForFileExists(filePath, 30);
    }
}