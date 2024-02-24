package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import waits.CustomConditions;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private static final String HOMEPAGE_URL = "https://pastebin.com/";
    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(id="postform-text")
    private WebElement newPasteTextArea;

    @FindBy(id="select2-postform-expiration-container")
    private WebElement expirationListExpander;

    @FindBy(xpath = "//li[@class='select2-results__option']")
    private List<WebElement> expirationListOptions;

    @FindBy(id="postform-name")
    private WebElement pasteNameEntryField;

    @FindBy(xpath="//button[text()='Create New Paste']")
    private WebElement createNewPasteButton;

    public MainPage openPage(){
        driver.get(HOMEPAGE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public void enterNewText(String newText){
        newPasteTextArea.sendKeys(newText);
    }

    public void selectExpiration(String expiration){
        expirationListExpander.click();

        for (WebElement selectedOption : expirationListOptions) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(selectedOption));
            if (selectedOption.getText().trim().equals(expiration.trim())) {
                selectedOption.click();
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.invisibilityOf(selectedOption));
                break;
            }
        }
    }

    public void pasteName(String name){
        pasteNameEntryField.sendKeys(name);
    }

    public void clickCreateNewPasteButton(){
        createNewPasteButton.click();
    }
}
