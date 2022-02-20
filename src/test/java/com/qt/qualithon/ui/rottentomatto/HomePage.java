package com.qt.qualithon.ui.rottentomatto;

import com.qt.qualithon.TestSession;
import com.qt.qualithon.ui.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * represents IMDb Web Home Page elements and ui actions (page object)
 **/
public class HomePage extends Page {

    public HomePage(TestSession testSession){
        super(testSession);
    }

    /**
     * perform a search for movie title and return the resultlist page
     *
     * @param     movieTitle    movie name
     * @return    IMDb Results Page page object
     **/
    public ResultsPage search(String movieTitle){
        WebElement searchInput = this.testSession.driverWait().until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".search-text")
            )
        );
        searchInput.sendKeys(movieTitle);
        searchInput.submit();

        return new ResultsPage(this.testSession);
    }
}
