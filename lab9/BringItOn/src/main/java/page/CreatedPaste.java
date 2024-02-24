package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreatedPaste {
    private WebDriver driver;

    @FindBy(xpath="//div[@class='info-top']//h1")
    private WebElement titleOnPastedPage;

    @FindBy(xpath="//div[@class='highlighted-code']/descendant::a")
    private WebElement highlightingOnPastedPage;

    @FindBy(xpath="//div[@class='highlighted-code']//ol")
    private WebElement codeOnPastedPage;

    public CreatedPaste(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String getPageTitle(){
        return titleOnPastedPage.getText();
    }

    public String getPageHighlighting(){
        return highlightingOnPastedPage.getText();
    }

    public String getPageText(){
        return codeOnPastedPage.getText();
    }
}
