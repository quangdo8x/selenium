package supports;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class CommonFunctions {

    private WebDriver driver;

    public CommonFunctions(WebDriver driver){
        this.driver = driver;
    }

    /*===============================================================================
     'Method name:  waitForLoadingDisappear(int MaxTime)
     'Description:  Wait for loading icon disappears
     'Arguments:    int MaxTime: the maximum time to wait for the loading disappears
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void waitForLoadingDisappears(int MaxTime) throws InterruptedException {

        int time = 1;

        Thread.sleep(time*1000);
//        Check loading icon appears or not
        boolean isVisible = isElementVisible("//div[@id='loader']");
        while (isVisible && time < MaxTime) {

//            Wait 1 second then check loading icon appears or not again
            Thread.sleep(time*100);
            isVisible = isElementVisible("//div[@id='loader']");
            time++;
        }
    }

    /*===============================================================================
     'Method name:  waitForElementEnabled(WebElement ElementName, int MaxTime)
     'Description:  Wait for element is enabled
     'Arguments:    WebElement ElementName: the name of element
     '              int MaxTime: the maximum time to wait
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void waitForElementEnabled(WebElement ElementName, int MaxTime) throws InterruptedException {

        boolean isClickable = ElementName.isEnabled();
        int time = 1;
        Thread.sleep(time*2000);

//        Wait until element is enabled or time out
        while (!isClickable && time < MaxTime){
            Thread.sleep(time*100);
            isClickable = ElementName.isEnabled();
            time++;
        }
    }

    /*===============================================================================
     'Method name:  deleteFile(String FilePath)
     'Description:  Delete file if existing
     'Arguments:    String FilePath: the path of file
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void deleteFile(String FilePath){

        File file = new File(FilePath);
        boolean isExist = file.exists();

//        Delete file if existing
        if (isExist) {
            file.delete();
        }
    }

    /*===============================================================================
     'Method name:  waitForFileExists(String FilePath, int MaxTime)
     'Description:  Wait until the file exists
     'Arguments:    String FilePath: the path of file
     '              int MaxTime: the maximum time to wait
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void waitForFileExists(String FilePath, int MaxTime) throws InterruptedException {

        File file = new File(FilePath);
        boolean isExist = file.exists();
        int time = 1;

//        Wait until file exists or time out
        while (!isExist && time < MaxTime){
            Thread.sleep(time*100);
            isExist = file.exists();
            time++;
        }
    }

    /*===============================================================================
    'Method name:  isElementVisible(String XPathText)
    'Description:  Check element is visible or not
    'Arguments:    String XPathText: the value of XPath
    'Created by:   Quang Do
    'Created date: May-10-2019
    ===============================================================================*/
    public boolean isElementVisible(String XPathText){
        boolean elementVisible;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPathText)));
            elementVisible = true;
        }
        catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ex){
            elementVisible = false;
        }
        return elementVisible;
    }

    /*===============================================================================
    'Method name:  getPDFContent(String FilePath)
    'Description:  Get the content in report
    'Arguments:    String FilePath: the path of PDF file
    'Created by:   Quang Do
    'Created date: May-10-2019
    ===============================================================================*/
    public String getPDFContent(String FilePath){

        PDDocument pdDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText;
        String contentPDF = null;

        File file = new File(FilePath);
        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            parsedText = pdfStripper.getText(pdDoc);
            contentPDF = parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
//            System.out.println(parsedText.replaceAll("[^A-Za-z0-9. ]+", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentPDF;
    }

    /*======================================================================================
    'Method name:  selectMenuItem(WebElement RootMenu, WebElement SubMenu)
    'Description:  Hover over root menu then click on sub menu to navigate to the web page of sub menu
    'Arguments:    WebElement RootMenu: the element name of root menu
    '              WebElement SubMenu: the element name of sub menu
    'Created by:   Quang Do
    'Created date: May-08-2019
    ======================================================================================*/
    public void selectMenuItem(WebElement RootMenu, WebElement SubMenu) throws InterruptedException {
//        Hover over root menu
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(RootMenu));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click()", RootMenu);

//        Wait for sub menu visible
        Thread.sleep(1000);

//        Click sub menu/link
        SubMenu.click();

//        Wait for loading completed
        waitForLoadingDisappears(60);
    }
}