package first_test;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject_model.page.SelemiumHQHomePage;

public class WebDriverSeleniumHQTest {
    private ChromeDriver driver;

    // метод, выполняемый перед тестами
    @BeforeMethod (alwaysRun = true)
    public void browesrSetup(){
        // получаем чистый объект дравера
        driver = new ChromeDriver();
    }

    // тестовый метод
    @Test (description = "Just first test")
    public void searchInputExists() throws InterruptedException {
        int amountOfSearchInputs = new SelemiumHQHomePage(driver)
                .openPage()
                .countAmountOfFoundSearchInputs();

        Assert.assertTrue(amountOfSearchInputs > 0,
                "Search input doesn’t exist!");

        Thread.sleep(1000);
    }

    // метод, выполняемый после тестов
    @AfterMethod (alwaysRun = true)
    public void browserTearDown(){
        // закрываем браузер
        driver.quit();
        driver = null;
    }
}
