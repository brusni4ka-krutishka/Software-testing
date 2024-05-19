package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.CatalogPage;
import page.SortedCatalogPage;

public class SortTest extends CommonConditions{
    private CatalogPage catalogPage;
    private SortedCatalogPage sortedCatalogPage;

    @Test
    public void priceSortTest() throws InterruptedException {
        catalogPage = new CatalogPage(driver);
        catalogPage.openPage();
        sortedCatalogPage = catalogPage.clickButtonToSortByPrice("desc");
        Thread.sleep(1000);
        Assert.assertTrue(sortedCatalogPage.checkIfSortByPriceCorrect("desc"));
    }

    @Test
    public void alphabetSortTest() throws InterruptedException {
        catalogPage = new CatalogPage(driver);
        catalogPage.openPage();
        sortedCatalogPage = catalogPage.clickButtonToSortByName("a-z");
        Thread.sleep(1000);
        Assert.assertTrue(sortedCatalogPage.checkIfSortByNameCorrect("a-z"));   // провален
    }
}
