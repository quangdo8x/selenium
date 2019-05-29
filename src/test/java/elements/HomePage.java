package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import supports.CommonFunctions;

public class HomePage {

    private WebDriver driver;
    private CommonFunctions function;

    public HomePage(WebDriver driver){
        this.driver = driver;

        function = new CommonFunctions(driver);
        PageFactory.initElements(driver, this);
    }

//    ----------------------Terms and Conditions popup------------------
    @FindBy(xpath = "//h2[text()='DataVat Website Terms & Conditions']")
    public WebElement hedTermsAndConditions;

    @FindBy(id = "acceptTermCondition")
    public WebElement btnAcceptAndContinue;

    @FindBy(id = "declineTermCondition")
    private WebElement btnDecline;

//    ----------------------Select Party popup----------------------------------------
    @FindBy(xpath = "//h4[text()='SELECT PARTY']")
    private WebElement hedSelectParty;

//    ----------------------Header--------------------------------------------
    @FindBy(className = "logo-datavat")
    private WebElement imgLogo;

    @FindBy(xpath = "//ul[@id='user-menu']/li/a[@href='/contact-and-support']")
    private WebElement lnkContactSupport;

    @FindBy(id = "btn-login-redirect")
    private WebElement btnLogin;

    @FindBy(xpath = "//li[@class='user-name-menu-li']/span")
    public WebElement lblUsername;

//    ----------------------Sub menu of username label----------------------
    @FindBy(xpath = "//a[@href='/user']")
    private WebElement lnkAccountSettings;

    @FindBy(xpath = "//a[@class='link-switch-party is-active']")
    private WebElement lnkSwitchParty;

    @FindBy(xpath = "//a[@href='/user_process/logout']")
    public WebElement lnkSignOut;

//    ----------------------Root menu item on the top----------------------
    @FindBy(xpath = "//ul[@id='main-menu']/li/a[@href='/']")
    private WebElement mnuDashboard;

    @FindBy(xpath = "//a[@href='/search-bulls']")
    private WebElement mnuAnimalSearch;

    @FindBy(xpath = "//a[text()='Report & Tools']")
    private WebElement mnuReportTools;

    @FindBy(xpath = "//a[text()='ABV Lists']")
    private WebElement mnuABVLists;

    @FindBy(xpath = "//a[@href='/coming-soon-page']")
    private WebElement mnuComingEvents;

    @FindBy(xpath = "//a[text()='DataGene Services']")
    private WebElement mnuDataGeneServices;

//    ----------------------Sub menu of Report & Tools----------------------
    @FindBy(xpath = "//a[@href='/reports/fertility-focus-report']")
    private WebElement lnkFertilityFocus;

    @FindBy(xpath = "//a[@href='/reports/genetic-futures']")
    private WebElement lnkGeneticFutures;

    @FindBy(xpath = "//a[@href='/reports/genetic-progress']")
    private WebElement lnkGeneticProgress;

    @FindBy(xpath = "//a[@href='/reports/haplotype']")
    private WebElement lnkHaplotype;

    @FindBy(xpath = "//a[@href='/reports/semen-fertility']")
    private WebElement lnkSemenFertility;

    @FindBy(xpath = "//a[@href='/heifer-selector']")
    private WebElement lnkGenomicValueTool;

//    ----------------------Sub menu of ABV Lists---------------------------
    @FindBy(xpath = "//a[@href='/bull-abvs']")
    private WebElement lnkBullABVs;

    @FindBy(xpath = "//a[@href='/cow-abvs']")
    private WebElement lnkCowABVs;

//    ----------------------Sub menu of DataGene Services-------------------------------
    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/animals/main']")
    private WebElement lnkAnimalManagement;

    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/abv-report/main/report-bull']")
    private WebElement lnkABVReports;

    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/animals/main/NASISBull']")
    private WebElement lnkNasis;

    @FindBy(xpath = "//a[text()='Genomics']")
    private WebElement lnkGenomics;

    @FindBy(xpath = "//a[text()='Export Heifer']")
    private WebElement lnkExportHeifer;

    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/progeny-test/main/pt-team']")
    private WebElement lnkProgenyTest;

    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/abv-report/main/report-run']")
    private WebElement lnkReportRunManagement;

    @FindBy(xpath = "//a[@href='http://preprod-web.adhis.com.au/cdrui/#/nrr/main']")
    private WebElement lnkNRR;

//    ----------------------Menu item at the bottom page----------------------------------------------------------------------------------------

    @FindBy(xpath = "//ul[@class='menu menu--footer nav']/li/a[text()='Dashboard']")
    private WebElement lnkBottomDashboard;

    @FindBy(xpath = "//ul[@class='menu menu--footer nav']/li/a[text()='About DataGene']")
    private WebElement lnkAboutDataGene;

    @FindBy(xpath = "//ul[@class='menu menu--footer nav']/li/a[text()='Contact & Support']")
    private WebElement lnkBottomContactSupports;

    @FindBy(xpath = "//ul[@class='menu menu--footer-bottom nav']/li/a[text()='Privacy & Cookie Policy']")
    private WebElement lnkPrivacyCookiePolicy;

    @FindBy(xpath = "//ul[@class='menu menu--footer-bottom nav']/li/a[text()='Disclaimer']")
    private WebElement lnkDisclaimer;

    /*======================================================================================
    'Method name:  clickOnSubMenu(WebElement RootMenu, WebElement SubMenu, Boolean HasLoading)
    'Description:  Hover over root menu then click on sub menu to navigate to the web page of sub menu
    'Arguments:    WebElement RootMenu: the element name of root menu
    '              WebElement SubMenu: the element name of sub menu
    '              Boolean HasLoading: there is loading after clicking sub menu or not
    'Created by:   Quang Do
    'Created date: May-08-2019
    ======================================================================================*/
    public void clickOnSubMenu(WebElement RootMenu, WebElement SubMenu, Boolean HasLoading) throws InterruptedException {
//        Hover over root menu
        Actions action = new Actions(driver);
        action.moveToElement(RootMenu).build().perform();

//        Wait for sub menu visible
        Thread.sleep(1000);

//        Click sub menu/link
        action.click(SubMenu).perform();

        if (HasLoading) {
//        Wait for loading completed
            function.waitForLoadingDisappears(60);
        }
    }

    /*======================================================================================
    'Method name:  switchParty(String PartyName)
    'Description:  Open Select Party from menu item Username, then select party name
    'Arguments:    String PartyName: party name
    'Created by:   Quang Do
    'Created date: May-08-2019
    ======================================================================================*/
    public void switchParty(String PartyName) throws InterruptedException {
//        Open Select Party popup if not opened yet
        boolean isVisible = hedSelectParty.isDisplayed();
        if (!isVisible){
//              Click Switch Party link
            clickOnSubMenu(lblUsername, lnkSwitchParty, true);
        }
//        Handle to wait for party ready to click
        Thread.sleep(1500);

//        Select party on list by text
        driver.findElement(By.xpath("//button[text()='" + PartyName + "']")).click();

//        Wait for loading completed
        function.waitForLoadingDisappears(60);
    }
}