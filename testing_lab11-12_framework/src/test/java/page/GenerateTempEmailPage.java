package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;


public class GenerateTempEmailPage extends AbstractPage{
    private final String GENERATE_TEMP_EMAIL_PAGE_URL =
            "https://yopmail.com/ru/email-generator";

    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//button[@id='cprnd']")
    WebElement copyEmailButton;

    @FindBy(xpath = "//button/span[text()='Проверить почту']")
    WebElement checkEmailButton;

    @Override
    public GenerateTempEmailPage openPage() {
        driver.get(GENERATE_TEMP_EMAIL_PAGE_URL);
        driver.manage().window().maximize();
        return this;
    }

    public GenerateTempEmailPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
    }

    public String getGeneratedEmail() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(CustomConditions.isButtonEnabled(copyEmailButton));
        copyEmailButton.click();

        // получение значения из буфера обмена
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String generatedEmail = "-email wasn't generated-";
        try{
            generatedEmail = (String) clipboard.getData(DataFlavor.stringFlavor);
        }
        catch (UnsupportedFlavorException | IOException ignored) {  }

        logger.info("Generated email: ["+generatedEmail+"]");

        return generatedEmail;
    }

    public void checkEmailButtonClick() {
        checkEmailButton.click();
    }
}
