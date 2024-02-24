package test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.EmailMessagesPage;
import page.GenerateTempEmailPage;
import page.MainPage;

import java.util.ArrayList;

public class EmailForSubscriptionTest {
    private ChromeDriver driver;
    private MainPage mainPage;
    private GenerateTempEmailPage generateTempEmailPage;
    private EmailMessagesPage emailMessagesPage;

    @BeforeMethod(alwaysRun = true)
    public void browserSetupAndOpenGoodPage() {
        driver = new ChromeDriver();
    }

    @Test
    public void emailForSubscriptionIsSending() throws InterruptedException {
        generateTempEmailPage = new GenerateTempEmailPage(driver);
        generateTempEmailPage.open();
        generateTempEmailPage.copyEmailButtonClick();

        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        mainPage = new MainPage(driver);
        mainPage
                .open()
                .pasteEmailAddressInField()
                .clickSubscribe();
        // всплывающее окно после нажатия кнопки "Подписаться" должно быть
        // о том, что всё прошло ок и письмо отправлено
        Assert.assertTrue(mainPage.checkIfResponsePopupIsAboutEmailWasSend());
        Thread.sleep(2000);

        driver.switchTo().window(tabs.get(0));
        generateTempEmailPage.checkEmailButtonClick();
        emailMessagesPage = new EmailMessagesPage(driver);
        // открываем страницу со списком сообщений только что сгенерированной почты
        emailMessagesPage
                .open();
        Assert.assertTrue(emailMessagesPage.checkIfMessageFromSiteExists("coffee@everydaycoffee.by"));
        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }
}
