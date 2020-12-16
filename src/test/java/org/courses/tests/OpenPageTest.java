package org.courses.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class OpenPageTest {

    private WebDriver myPersonalDriver;
    private Logger logger = LoggerFactory.getLogger(OpenPageTest.class);
    //I decided to try the page
    private final String w3schoolPage = "https://www.w3schools.com";
    private final String pathToWebDriver = "C://PersonalDrivers//chromedriver.exe";
    private final String browserName = "chrome";
    private String titleToCheck = "w3schools online web tutorials";

    @Test
    @Parameters({"browser", "pathToDriver", "loginPageUrl"})
    public void openPageTest(@Optional(browserName) String browser,
                             @Optional(pathToWebDriver) String pathToDriver,
                             @Optional(w3schoolPage) String loginPageUrl) {


        //System's properties we have to set to use drivers
        String[] sysProperty = new String[]{
                "webdriver.gecko.driver",
                "webdriver.ie.driver",
                "webdriver.chrome.driver"
        };

        //Check whether file is exist
        File f = new File(pathToDriver);
        if ((!f.exists()) || f.isDirectory()) {
            logger.error("Error! Check your browser's path in testng.xml!");
            Assert.fail("Error! Check your browser's path in testng.xml!");
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty(sysProperty[0], pathToDriver);
                myPersonalDriver = new FirefoxDriver();
                break;
            case "ie":
                System.setProperty(sysProperty[1], pathToDriver);
                myPersonalDriver = new InternetExplorerDriver();
                break;
            case "chrome":
                System.setProperty(sysProperty[2], pathToDriver);
                myPersonalDriver = new ChromeDriver();
                break;
            default:
                logger.error("Error! Check your browser's type in testng.xml!");
                Assert.fail("Error! Check your browser's type in testng.xml!");
        }

        //Set implicit wait
        myPersonalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        //Open page
        myPersonalDriver.get(loginPageUrl);
        myPersonalDriver.manage().window().maximize();
        //Check title (Also it is possible to look for some elements on the page)
        Assert.assertTrue(myPersonalDriver.getTitle().toLowerCase().contains(titleToCheck),
                "the test does not pass");
        //Close page
        myPersonalDriver.close();
        //Quit driver
        myPersonalDriver.quit();
    }
}
