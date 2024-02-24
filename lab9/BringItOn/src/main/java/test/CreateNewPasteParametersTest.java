package test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.CreatedPaste;
import page.MainPage;

public class CreateNewPasteParametersTest {
    private ChromeDriver driver;

    private static final String PASTE_TEXT = "git config --global user.name  \"New Sheriff in Town\"\n" + "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" + "git push origin master --force";
    private static final String PASTE_NAME = "how to gain dominance among developers";
    private static final String PASTE_HIGHLIGHTING = "Bash";
    private static final String PASTE_EXPIRATION = "10 Minutes";

    private CreatedPaste createdPaste;

    @BeforeMethod
    public void browserSetupAndCreateNewPaste(){
        driver = new ChromeDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.enterNewText(PASTE_TEXT);
        mainPage.selectHighlighting(PASTE_HIGHLIGHTING);
        mainPage.selectExpiration(PASTE_EXPIRATION);
        mainPage.pasteName(PASTE_NAME);

        createdPaste = mainPage.clickCreateNewPasteButton();
    }

    @Test
    public void compareNames() {
        Assert.assertEquals(createdPaste.getPageTitle(), PASTE_NAME);
    }

    @Test
    public void compareHighlightings() {
        Assert.assertEquals(createdPaste.getPageHighlighting(), PASTE_HIGHLIGHTING);
    }

    @Test
    public void compareTexts() {
        Assert.assertEquals(createdPaste.getPageText(), PASTE_TEXT);
    }

    @AfterMethod
    public void browserTearDown(){
        driver.quit();
        driver = null;
    }
}
