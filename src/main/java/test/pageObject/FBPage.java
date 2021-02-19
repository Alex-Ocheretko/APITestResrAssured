package test.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FBPage {

    public FBPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "_2nlw")
    private WebElement userName;

    @FindBy(css = "div[id='BrowseResultsContainer']>div:nth-child(1) a[class='_32mo']")
    private WebElement firstUser;

    public String getUserName(){
        return userName.getText();
    }

    public Boolean firstUserVisibility()  {
        return firstUser.isEnabled();
    }

    public void firstUserClick() {
        firstUser.click();
    }
}
