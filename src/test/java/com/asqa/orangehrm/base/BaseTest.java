package com.asqa.orangehrm.base;

import com.asqa.orangehrm.utils.ConfigReader;
import com.asqa.orangehrm.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;

/**
 * TestNG lifecycle: create a fresh browser per test method and tear it down after.
 * Prefer an explicit edge.driver.path in config when network blocks Selenium Manager.
 * Supports chrome, edge, and firefox via config.properties (browser=...).
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigReader.get("browser").toLowerCase();
        driver = createDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            ScreenshotUtils.capture(driver, result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver createDriver(String browser) {
        switch (browser) {
            case "firefox":
                return new FirefoxDriver();
            case "chrome":
                return createChromeDriver();
            case "edge":
            default:
                return createEdgeDriver();
        }
    }

    private WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        Optional<File> driverExe = localDriver("chrome.driver.path");
        if (driverExe.isPresent()) {
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverExe.get())
                    .build();
            return new ChromeDriver(service, options);
        }
        return new ChromeDriver(options);
    }

    private WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        Optional<File> driverExe = localDriver("edge.driver.path");
        if (driverExe.isPresent()) {
            EdgeDriverService service = new EdgeDriverService.Builder()
                    .usingDriverExecutable(driverExe.get())
                    .build();
            return new EdgeDriver(service, options);
        }
        return new EdgeDriver(options);
    }

    private Optional<File> localDriver(String configKey) {
        return ConfigReader.getOptional(configKey)
                .map(Path::of)
                .filter(Files::isRegularFile)
                .map(Path::toFile);
    }
}
