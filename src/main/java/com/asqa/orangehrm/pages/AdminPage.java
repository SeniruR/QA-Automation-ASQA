package com.asqa.orangehrm.pages;

import com.asqa.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Admin module – system users management landing area.
 */
public class AdminPage extends BasePage {

    private final By pageHeader = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By systemUsersHeader = By.xpath("//h5[normalize-space()='System Users']");
    private final By usernameSearch = By.xpath(
            "//label[text()='Username']/../following-sibling::div//input");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By recordsFound = By.cssSelector(".orangehrm-horizontal-padding span");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminPageDisplayed() {
        return wait.urlContains("admin")
                && "Admin".equalsIgnoreCase(getText(pageHeader).trim());
    }

    public boolean isSystemUsersSectionVisible() {
        return waitVisible(systemUsersHeader).isDisplayed();
    }

    public AdminPage searchUser(String username) {
        type(usernameSearch, username);
        click(searchButton);
        return this;
    }

    public String getRecordsLabel() {
        return getText(recordsFound);
    }
}
