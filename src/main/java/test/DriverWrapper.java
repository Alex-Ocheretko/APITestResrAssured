package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class DriverWrapper {
    WebDriver driver;

    public DriverWrapper() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.88.0.4324.96");
        this.driver = new ChromeDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void init() {
        this.driver.get("https://www.google.com/");
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    public void close() {
        this.driver.quit();
    }
}
