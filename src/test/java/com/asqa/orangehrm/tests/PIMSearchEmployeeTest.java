package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.DashboardPage;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.pages.PIMPage;
import com.asqa.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Scenario TS-04 (Automated): Navigate to PIM and run employee name search.
 */
public class PIMSearchEmployeeTest extends BaseTest {

    @Test(description = "Admin can open PIM and search employees by name")
    public void pim_searchEmployee_shouldLoadResultsTable() {
        DashboardPage dashboard = new LoginPage(driver).loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        PIMPage pimPage = dashboard.openPIM();
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM module should open");

        pimPage.searchByEmployeeName("a");
        Assert.assertTrue(pimPage.hasSearchResults(),
                "Employee results table should be available after search");
    }
}
