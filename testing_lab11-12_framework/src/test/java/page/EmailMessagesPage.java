package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmailMessagesPage extends AbstractPage{
    private final String EMAIL_LIST_OF_MESSAGES_PAGE_URL =
            "https://yopmail.com/ru/wm";

    // для логгирования (записи сообщений в лог)
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(id = "refresh")
    WebElement refreshEmailsButton;

    @FindBy(xpath = "//iframe[@id='ifinbox']")
    WebElement inboxFrame;

    @FindBy(xpath = "//iframe[@id='ifinbox']")
    WebElement emailFrame;

    @FindBy(xpath = "//span[@class='lmf']")
    WebElement senderNameSpan;

    @Override
    public EmailMessagesPage openPage() {
        driver.navigate().to(EMAIL_LIST_OF_MESSAGES_PAGE_URL);
//        PageFactory.initElements(driver, this);
        return this;
    }

    public EmailMessagesPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean checkIfMessageFromSiteExists(String senderName){
        while (!inboxFrame.isDisplayed()) {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(refreshEmailsButton));
            refreshEmailsButton.click();
        }
        // если элемент разметки находится в iframe/frame, то во фрейм нужно зайти сначала driver.switchTo().frame(emailFrame);
        driver.switchTo().frame(emailFrame);
        // имя отправителя сообщения, найденного в списке сообщений
        String senderNameFromEmail = senderNameSpan.getText();

        if(senderNameFromEmail == null){
            senderNameFromEmail = "-no messages found-";
        }else{
            senderNameFromEmail = senderName.trim();
        }
        logger.info("The last message in list of email messages was from: ["+senderNameFromEmail+"]");

        return senderName.trim().equals(senderNameFromEmail.trim());
    }
}
