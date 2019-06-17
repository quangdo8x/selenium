package configurations;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ConfigureTest {

    public static WebDriver driver;
    protected static Logger logger;

    /*==================================================================================================================
     'Method name:  setUpTest(String port)
     'Description:  Open Website with selecting remote execution or not and what browser type
     'Arguments:    String Port: the port of hub
     'Created by:   Quang Do
     'Created date: May-10-2019
     =================================================================================================================*/
    @BeforeClass
    @Parameters({"port"})
    public void setUpTest(String port) throws MalformedURLException {

        String urlPage = "http://qc-datavat.datagene.com.au";
        String urlHub = "http://192.168.213.21:";

//       Select browser type
        switch (port){
            case "4445":
                DesiredCapabilities capability1 = new DesiredCapabilities();
                capability1.setBrowserName("chrome");
                capability1.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(  urlHub + port + "/wd/hub"), capability1);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                driver.navigate().to(urlPage);
                logger  = Logger.getLogger("chromeLogger");
                break;
            case "4446":
                DesiredCapabilities capability2 = new DesiredCapabilities();
                capability2.setBrowserName("firefox");
                capability2.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(urlHub  + port + "/wd/hub"), capability2);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                driver.navigate().to(urlPage);
                logger  = Logger.getLogger("firefoxLogger");
                break;
            case "4447":
                DesiredCapabilities capability3 = new DesiredCapabilities();
                capability3.setBrowserName("internet explorer");
                capability3.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(urlHub  + port + "/wd/hub"), capability3);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                driver.navigate().to(urlPage);
                logger  = Logger.getLogger("ieLogger");
                break;
            case "4448":
                DesiredCapabilities capability4 = new DesiredCapabilities();
                capability4.setBrowserName("MicrosoftEdge");
                capability4.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(urlHub  + port + "/wd/hub"), capability4);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                driver.navigate().to(urlPage);
                logger  = Logger.getLogger("edgeLogger");
                break;
            }
    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}