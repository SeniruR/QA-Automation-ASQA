package com.asqa.orangehrm.pages;

import com.asqa.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Post-login dashboard – header branding, side menu navigation and user menu.
 */
public class DashboardPage extends BasePage {

    private final By dashboardHeader = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink = By.xpath("//a[contains(@href,'logout')]");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By leaveMenu = By.xpath("//span[text()='Leave']");
    private final By dashboardWidgets = By.cssSelector(".orangehrm-dashboard-widget");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        return wait.urlContains("dashboard")
                && waitVisible(dashboardHeader).isDisplayed();
    }

    /** Wait until dashboard widgets finish AJAX loading (no spinners). */
    public DashboardPage waitForDashboardFullyLoaded() {
        isDashboardDisplayed();
        wait.waitForDocumentReady();
        wait.waitForLoadingSpinnersToFinish();
        wait.visible(dashboardWidgets);
        return this;
    }

    public String getHeaderText() {
        return getText(dashboardHeader);
    }

    public LoginPage logout() {
        click(userDropdown);
        click(logoutLink);
        return new LoginPage(driver);
    }

    public PIMPage openPIM() {
        click(pimMenu);
        return new PIMPage(driver);
    }

    public AdminPage openAdmin() {
        click(adminMenu);
        return new AdminPage(driver);
    }

    public void openLeave() {
        click(leaveMenu);
    }
}
