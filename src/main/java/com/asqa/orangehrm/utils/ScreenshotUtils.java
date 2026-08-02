package com.asqa.orangehrm.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures PNG screenshots for failure analysis and report evidence.
 */
public final class ScreenshotUtils {

    private static final Path SCREENSHOT_DIR = Paths.get("screenshots");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String name) {
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path target = SCREENSHOT_DIR.resolve(name + "_" + timestamp + ".png");
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(target, bytes);
            return target.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to capture screenshot: " + name, e);
        }
    }
}
