package testcases;

import configurations.ConfigureTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class GitHub extends ConfigureTest {

    @Test
    public void SignInGitHub(){

//        Navigate to Github
        driver.navigate().to("https://github.com/login");

//        Enter username
        driver.findElement(By.id("login_field")).sendKeys("quangdo8x");

//        Enter password
        driver.findElement(By.id("password")).sendKeys("q@29072011gh");

//        Click Sign in button
        driver.findElement(By.name("commit")).click();
    }
}
