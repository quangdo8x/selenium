package supports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.File;

public class CommonFunctions {

    private WebDriver driver;
    private SoftAssert softAssert;

    public CommonFunctions(WebDriver driver){
        this.driver = driver;

        softAssert = new SoftAssert();
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

//        Check loading icon appears or not
        boolean isVisible = driver.findElement(By.id("loader")).isDisplayed();
        while (isVisible && time < MaxTime) {

//            Wait 1 second then check loading icon appears or not again
            Thread.sleep(time*100);
            isVisible = driver.findElement(By.id("loader")).isDisplayed();
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
     'Method name:  countFailedAssertions()
     'Description:  Count total failed assertions in test case
     'Arguments:    N/A
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void countFailedAssertions(){
        softAssert.assertAll();
    }

    /*===============================================================================
     'Method name:  verifyPageTitle(String PageTitle)
     'Description:  Check the title of page
     'Arguments:    String PageTitle: the title of page
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void verifyPageTitle(String PageTitle){
//        Get the title of page then check with expectation
        softAssert.assertEquals(PageTitle, driver.getTitle());
    }

    /*===============================================================================
     'Method name:  verifyElementExists(WebElement ElementName)
     'Description:  Check the element of page appears or not
     'Arguments:    WebElement ElementName: the name of element (txt_Username, btn_Login...)
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void verifyElementExists(WebElement ElementName){
//        Check the element appears or not
        softAssert.assertTrue(ElementName.isDisplayed());
    }

    /*===============================================================================
     'Method name:  verifyElementText(WebElement ElementName, String Text)
     'Description:  Check the text on element
     'Arguments:    WebElement ElementName: the name of element (txt_Username, btn_Login...)
     '              String Text: the expected text
     'Created by:   Quang Do
     'Created date: May-08-2019
     ===============================================================================*/
    public void verifyElementText(WebElement ElementName, String Text){
//        Get text on element then check with expectation
        softAssert.assertEquals(ElementName.getText().trim(), Text);
    }
}