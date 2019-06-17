package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MultiBrowsers{

    private WebDriver driver;

    @Parameters({"port"})
    @BeforeClass()
    public void setUp(String port) throws MalformedURLException {
        DesiredCapabilities cap =  new DesiredCapabilities();
        if(port.equalsIgnoreCase("4446"))
        {
            cap.setBrowserName("firefox");
            cap.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.213.21:" + port + "/wd/hub"),cap);
            driver.navigate().to("https://github.com/login");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        else if(port.equalsIgnoreCase("4445"))
        {
            cap.setBrowserName("chrome");
            cap.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.213.21:" + port + "/wd/hub"),cap);
            driver.navigate().to("https://github.com/login");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        else if(port.equalsIgnoreCase("4448"))
        {
            cap.setBrowserName("MicrosoftEdge");
            cap.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.213.21:" + port + "/wd/hub"),cap);
            driver.navigate().to("https://github.com/login");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    @Test
    public void githubLogin(){
        driver.findElement(By.id("login_field")).sendKeys("quangdo8x");
        driver.findElement(By.id("password")).sendKeys("q@29072011gh");
        driver.findElement(By.name("commit")).click();
    }
}