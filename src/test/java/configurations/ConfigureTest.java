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
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ConfigureTest {

    public static WebDriver driver;
    protected static Logger logger;

    /*==================================================================================================================
     'Method name:  setUpTest(String Remote, String Address, String Port, String Browser)
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
    public void setUpTest(String Remote, String Port, String Browser) throws MalformedURLException {

        if (Remote.equals("true")){

            DesiredCapabilities capability = new DesiredCapabilities();

//           Select browser type
            switch (Browser){
                case "chrome":
//                   Open Chrome browser
                    capability.setBrowserName("chrome");
                    logger  = Logger.getLogger("chromeLogger");
                    break;
                case "firefox":
//                    Open Firefox browser
                    capability.setBrowserName("firefox");
                    logger  = Logger.getLogger("firefoxLogger");
                    break;
                case "internet explorer":
//                    Open IE browser
                    capability.setBrowserName("internet explorer");
                    logger  = Logger.getLogger("ieLogger");
                    break;
                case "MicrosoftEdge":
//                    Open Edge browser
                    capability.setBrowserName("MicrosoftEdge");
                    logger  = Logger.getLogger("edgeLogger");
                    break;
            }
            capability.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.213.21:"  + Port + "/wd/hub"), capability);
        } else if (Remote.equals("false")) {

//           Select browser type
        switch (Browser){
            case "chrome":
//                    Open Chrome browser
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                driver = new ChromeDriver();
                logger  = Logger.getLogger("chromeLogger");
                break;
            case "firefox":
//                    Open Firefox browser
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                logger  = Logger.getLogger("firefoxLogger");
                break;
            case "internet explorer":
//                    Open IE browser
                System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                logger  = Logger.getLogger("ieLogger");
                break;
            case "MicrosoftEdge":
//                    Open Edge browser
                System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
                driver = new EdgeDriver();
                logger  = Logger.getLogger("edgeLogger");
                break;
            }
        }
//       Set the waiting time for elements visible
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//        Maximize browser
        driver.manage().window().maximize();
    }

    @AfterTest
    public void cleanUp(){
        driver.quit();
    }
}