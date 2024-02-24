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
    private List<WebElement> listOptions;

    @FindBy(id="select2-postform-expiration-result-i8vb-10M")
    private WebElement expirationListSelectedOption;

    @FindBy(id="select2-postform-format-container")
    private WebElement highlightingListExpander;

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

    public void setSelectOption(WebElement select, String optionValue){
        select.click();

        for (WebElement selectedOption : listOptions) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(selectedOption));
            if (selectedOption.getText().trim().equals(optionValue.trim())) {
                selectedOption.click();
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.invisibilityOf(selectedOption));
                break;
            }
        }
    }

    public void selectHighlighting(String highlighting) {
        setSelectOption(highlightingListExpander, highlighting);
    }

    public void selectExpiration(String expiration) {
        setSelectOption(expirationListExpander, expiration);
    }
    public void pasteName(String name){
        pasteNameEntryField.sendKeys(name);
    }

    public CreatedPaste clickCreateNewPasteButton(){
        String homePageUrl = driver.getCurrentUrl();

        createNewPasteButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.changingURL(homePageUrl));

        return new CreatedPaste(driver);
    }
}
