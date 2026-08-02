package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.DashboardPage;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Scenario TS-03 (Automated): Logout returns the user to the login screen.
 */
public class LogoutTest extends BaseTest {

    @Test(description = "Logout from user menu should return to Login page")
    public void logout_shouldReturnToLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = loginPage.loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        Assert.assertTrue(dashboard.isDashboardDisplayed());

        LoginPage afterLogout = dashboard.logout();
        Assert.assertTrue(afterLogout.isLoginPageDisplayed(),
                "Login page should be visible after logout");
        Assert.assertTrue(afterLogout.getCurrentUrl().contains("auth/login"),
                "URL should contain auth/login after logout");
    }
}
