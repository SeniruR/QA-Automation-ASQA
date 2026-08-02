package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.AdminPage;
import com.asqa.orangehrm.pages.DashboardPage;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Scenario TS-05 (Automated): Navigate to Admin and verify System Users section.
 */
public class AdminUserManagementNavigationTest extends BaseTest {

    @Test(description = "Admin menu should open System Users management")
    public void admin_navigation_shouldShowSystemUsers() {
        DashboardPage dashboard = new LoginPage(driver).loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        AdminPage adminPage = dashboard.openAdmin();
        Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Admin module should open");
        Assert.assertTrue(adminPage.isSystemUsersSectionVisible(),
                "System Users section should be visible");

        adminPage.searchUser(ConfigReader.get("admin.username"));
        String records = adminPage.getRecordsLabel();
        Assert.assertTrue(records.toLowerCase().contains("record"),
                "Expected records found label, got: " + records);
    }
}
