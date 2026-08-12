package com.asqa.orangehrm.pages;

import com.asqa.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM authentication page – username, password and login submit.
 */
public class LoginPage extends BasePage {

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By styledLoginButton = By.cssSelector("button.oxd-button[type='submit']");
    private final By errorAlert = By.cssSelector(".oxd-alert-content-text");
    private final By loginTitle = By.cssSelector("h5.orangehrm-login-title");
    private final By loginBranding = By.cssSelector(".orangehrm-login-branding");
    private final By loginLogo = By.cssSelector(".orangehrm-login-branding img");
    private final By loginLayout = By.cssSelector(".orangehrm-login-layout");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Wait until login page CSS, logo and styled button are fully loaded.
     * Capturing too early shows unstyled orange page with link-style Login text.
     */
    public LoginPage waitForLoginPageReady() {
        wait.waitForDocumentReady();
        waitVisible(loginLayout);
        waitVisible(loginTitle);
        waitVisible(loginBranding);
        wait.visible(loginLogo);
        wait.waitForAllImagesLoaded();
        wait.visible(styledLoginButton);
        wait.waitForStyledElement(styledLoginButton, 35);
        wait.clickable(loginButton);
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage(driver);
    }

    public DashboardPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    public String getErrorMessage() {
        return getText(errorAlert);
    }

    public boolean isLoginPageDisplayed() {
        return waitVisible(loginTitle).isDisplayed();
    }

    public String getLoginTitle() {
        return getText(loginTitle);
    }
}
