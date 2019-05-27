package configurations;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ConfigureTest {

    public static WebDriver driver;
    protected static Logger logger = Logger.getLogger("rootLogger");

    /*==================================================================================================================
     'Method name:  selectBrowserType(String Remote, String Address, String Port, String Browser)
     'Description:  Open Website with selecting remote execution or not and what browser type
     'Arguments:    String Remote: remote or not. If true, execute tests remotely on hub. If false, execute tests on local
     '              String Address: the IP address of hub
     '              String Port: the port of hub
     '              String Browser: the type of browser(chrome/firefox/edge)
     'Created by:   Quang Do
     'Created date: May-10-2019
     =================================================================================================================*/
    @BeforeTest
    @Parameters({"remote", "port", "browser"})
    public void selectBrowserType(String Remote, String Port, String Browser) throws MalformedURLException {

        if (Remote.equalsIgnoreCase("false")) {
//           Select browser type
            switch (Browser){
                case "chrome":
//                Open Chrome browser
                    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "firefox":
//                Open Firefox browser
                    System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                case "internet explorer":
//                Open IE browser
                    System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
                    driver = new InternetExplorerDriver();
                    break;
                case "MicrosoftEdge":
//                Open Edge browser
                    System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
                    driver = new EdgeDriver();
                    break;
            }
        } else {
//            Setup for grid execution on Hub
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setBrowserName(Browser);
            capability.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.213.21:"  + Port + "/wd/hub"), capability);
        }
//       Set the waiting time for elements visible
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

//        Maximize browser
        driver.manage().window().maximize();

//        Enter web URL to navigate to page
        driver.navigate().to("http://qc-datavat.datagene.com.au");

/*//        Handle to go through Certificate Error on IE and Edge
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        if (Browser.equals("internet explorer")) {
//            Click on "More information" link
            executor.executeScript("document.getElementById('moreInfoContainer').click();");

//            Click on "Go to the webpage (not recommended)" link
            executor.executeScript("document.getElementById('overridelink').click();");

        }else if (Browser.equals("MicrosoftEdge")){
//            Click on "Details" link
            executor.executeScript("document.getElementById('moreInformationDropdownSpan').click();");

//            Click on "Go to the webpage (Not recommended)" link
            executor.executeScript("javascript:document.getElementById('invalidcert_continue').click();");
        }*/
        logger.info("The website was opened!");
    }

    @AfterTest
    public void cleanUp() {
        driver.quit();
    }
}