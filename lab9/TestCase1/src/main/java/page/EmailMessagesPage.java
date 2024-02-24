package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmailMessagesPage {
    WebDriver driver;
    private final String EMAIL_LIST_OF_MESSAGES_PAGE_URL =
            "https://yopmail.com/ru/wm";

    @FindBy(id = "refresh")
    WebElement refreshEmailsButton;

    @FindBy(xpath = "//iframe[@id='ifinbox']")
    WebElement inboxFrame;

    @FindBy(xpath = "//iframe[@id='ifinbox']")
    WebElement emailFrame;

    @FindBy(xpath = "//span[@class='lmf']")
    WebElement senderNameSpan;

    public EmailMessagesPage(WebDriver driver){
        this.driver = driver;
    }

    public EmailMessagesPage open(){
        driver.navigate().to(EMAIL_LIST_OF_MESSAGES_PAGE_URL);
        PageFactory.initElements(driver, this);
        return this;
    }

    public boolean checkIfMessageFromSiteExists(String senderName){
        while (!inboxFrame.isDisplayed()) {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(refreshEmailsButton));
            refreshEmailsButton.click();
        }
        // если элемент разметки находится в iframe/frame, то во фрейм нужно зайти сначала driver.switchTo().frame(emailFrame);
        driver.switchTo().frame(emailFrame);
        String senderNameFromEmail = senderNameSpan.getText();
        if(senderNameFromEmail == null)
            return false;
        return senderName.trim().equals(senderNameFromEmail.trim());
    }
}
