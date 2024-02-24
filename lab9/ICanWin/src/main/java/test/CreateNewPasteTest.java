package test;

import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import page.MainPage;

public class CreateNewPasteTest {
    private ChromeDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        driver = new ChromeDriver();
    }

    @Test
    public void newPasteCreated() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.enterNewText("Hello from WebDriver");
        mainPage.selectExpiration("10 Minutes");
        mainPage.pasteName("helloweb");

        String oldUrl = driver.getCurrentUrl();

        mainPage.clickCreateNewPasteButton();

        Assert.assertNotEquals(oldUrl, driver.getCurrentUrl());

        Thread.sleep(5000);
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown(){
        driver.quit();
        driver = null;
    }
}
