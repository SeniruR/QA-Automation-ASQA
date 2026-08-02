package com.asqa.orangehrm.base;

import com.asqa.orangehrm.utils.ConfigReader;
import com.asqa.orangehrm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Parent for all Page Objects. Holds shared driver helpers and initialises
 * PageFactory annotations in subclasses.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, ConfigReader.getInt("explicit.wait"));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitVisible(By locator) {
        return wait.visible(locator);
    }

    protected void click(By locator) {
        wait.clickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.visible(locator).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
