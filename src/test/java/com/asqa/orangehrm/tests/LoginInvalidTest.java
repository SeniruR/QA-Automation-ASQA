package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Scenario TS-02 (Automated): Invalid credentials show an authentication error.
 */
public class LoginInvalidTest extends BaseTest {

    @Test(description = "Invalid credentials should show Invalid credentials error")
    public void invalidLogin_shouldShowError() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(ConfigReader.get("invalid.username"))
                .enterPassword(ConfigReader.get("invalid.password"))
                .clickLogin();

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.toLowerCase().contains("invalid"),
                "Expected invalid credentials message, got: " + error);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "User should remain on the login page");
    }
}
