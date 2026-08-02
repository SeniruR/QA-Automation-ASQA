package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.DashboardPage;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Scenario TS-01 (Automated): Valid admin login redirects to Dashboard.
 */
public class LoginValidTest extends BaseTest {

    @Test(description = "Valid credentials should land on the Dashboard")
    public void validLogin_shouldOpenDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be visible");

        DashboardPage dashboard = loginPage.loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        Assert.assertTrue(dashboard.isDashboardDisplayed(),
                "Dashboard should be displayed after valid login");
        Assert.assertEquals(dashboard.getHeaderText().trim(), "Dashboard");
    }
}
