package test.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FBPageObject {

    public FBPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "_2nlw _2nlv")
    WebElement userName;

    public String getUserName(){
        return userName.getText();
    }
}
