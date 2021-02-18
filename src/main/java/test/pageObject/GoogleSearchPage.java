package test.pageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GoogleSearchPage {

    public GoogleSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "q")
    private WebElement searchLine;

    @FindBy(className = "g")
    private List<WebElement> links;

    public void searchInGoogle(String name) {
        searchLine.sendKeys(name);
        searchLine.sendKeys(Keys.ENTER);
    }

    public void clickOnSearchLine() {
        searchLine.click();
    }

    public List<WebElement> getLinks(){
        return links;
    }
}
