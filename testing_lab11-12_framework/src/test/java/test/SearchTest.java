package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.AbstractPage;
import page.MainPage;
import page.SearchResultsPage;

public class SearchTest extends CommonConditions {
    private MainPage mainPage;
    private SearchResultsPage searchResultsPage;

    @Test
    public void searchTest(){
        mainPage = new MainPage(driver)
                .openPage();
        searchResultsPage = mainPage.enterSearchStringInSearchField("")
                .clickSearchButton();
        Assert.assertFalse(searchResultsPage.checkSearchResultsNotEmpty());

        // возврат обратно на главную
        driver.navigate().back();

        searchResultsPage = mainPage.enterSearchStringInSearchField("кофе")
                .clickSearchButton();
        Assert.assertTrue(searchResultsPage.checkSearchResultsNotEmpty());
    }
}
