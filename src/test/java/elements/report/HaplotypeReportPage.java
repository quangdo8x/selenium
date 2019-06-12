package elements.report;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import supports.CommonFunctions;

import java.awt.*;
import java.io.IOException;

public class HaplotypeReportPage {

    private WebDriver driver;
    private CommonFunctions function;

    public HaplotypeReportPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        function = new CommonFunctions(driver);
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
        if (!Breed.isEmpty()) {
            drdSelectBreeds.click();
            driver.findElement(By.xpath("//div[text()=' " + Breed.trim() + " ']")).click();
        }

//        Enter keyword if the argument is not blank
        if (!Keyword.isEmpty()){
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

//        Select bull in result table
        driver.findElement(By.xpath("//td[text()='" + Bull + "']/preceding-sibling::td/label/span")).click();

//      Wait for Print button is clickable
        function.waitForElementEnabled(btnPrint, 30);

//        Click Print button
        btnPrint.click();

//        Wait for loading completed
        function.waitForLoadingDisappears(60);

//        Delete old report before printing new file if exist before that
        function.deleteFile(filePath);

//        Wait for Print ready
        Thread.sleep(10000);

//        Enter report name
        Runtime.getRuntime().exec( "autoit/SavePrintOutputAs.exe");

//        Wait for file created
        function.waitForFileExists(filePath, 30);
    }
}