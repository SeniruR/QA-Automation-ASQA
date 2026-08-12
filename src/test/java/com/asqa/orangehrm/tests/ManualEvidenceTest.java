package com.asqa.orangehrm.tests;

import com.asqa.orangehrm.base.BaseTest;
import com.asqa.orangehrm.pages.AdminPage;
import com.asqa.orangehrm.pages.DashboardPage;
import com.asqa.orangehrm.pages.LoginPage;
import com.asqa.orangehrm.pages.PIMPage;
import com.asqa.orangehrm.utils.ConfigReader;
import com.asqa.orangehrm.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Captures screenshots for manual test scenarios referenced in the PDF report.
 * Waits for full page/widget load before each capture to avoid spinner screenshots.
 *
 * Run: mvn test -Dtest=ManualEvidenceTest
 */
public class ManualEvidenceTest extends BaseTest {

    private static final Path EVIDENCE_DIR = Paths.get("docs", "screenshots");

    @Test(description = "Capture TS-01 valid login and TS-05 dashboard evidence")
    public void captureValidLoginAndDashboard() {
        // Fresh navigation ensures CSS/assets load from a clean state
        driver.navigate().refresh();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageReady();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-01_login_page");

        DashboardPage dashboard = loginPage.loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        dashboard.waitForDashboardFullyLoaded();
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-01_dashboard_after_login");
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-05_dashboard_widgets");
    }

    @Test(description = "Capture TS-02 invalid login error evidence")
    public void captureInvalidLoginError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageReady();
        loginPage.enterUsername(ConfigReader.get("invalid.username"))
                .enterPassword(ConfigReader.get("invalid.password"))
                .clickLogin();

        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("invalid"));
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-02_invalid_login_error");
    }

    @Test(description = "Capture TS-06 PIM navigation evidence")
    public void capturePIMNavigation() {
        DashboardPage dashboard = new LoginPage(driver).loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        dashboard.openPIM();
        PIMPage pimPage = new PIMPage(driver);
        Assert.assertTrue(pimPage.isPIMPageDisplayed());
        pimPage.waitForLoadingSpinnersToFinish();
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-06_pim_module");
    }

    @Test(description = "Capture TS-09 Admin System Users evidence")
    public void captureAdminNavigation() {
        DashboardPage dashboard = new LoginPage(driver).loginAs(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password"));

        dashboard.openAdmin();
        AdminPage adminPage = new AdminPage(driver);
        Assert.assertTrue(adminPage.isAdminPageDisplayed());
        Assert.assertTrue(adminPage.isSystemUsersSectionVisible());
        adminPage.waitForLoadingSpinnersToFinish();
        ScreenshotUtils.captureEvidence(driver, EVIDENCE_DIR, "TS-09_admin_system_users");
    }
}
