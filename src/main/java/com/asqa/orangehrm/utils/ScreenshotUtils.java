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

    private static final Path DEFAULT_DIR = Paths.get("screenshots");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String name) {
        return capture(driver, DEFAULT_DIR, name, true);
    }

    /** Saves a named screenshot for manual test evidence (no timestamp). */
    public static String captureEvidence(WebDriver driver, Path directory, String fileName) {
        return capture(driver, directory, fileName, false);
    }

    private static String capture(WebDriver driver, Path directory, String name, boolean addTimestamp) {
        try {
            Files.createDirectories(directory);
            String baseName = name.endsWith(".png") ? name.substring(0, name.length() - 4) : name;
            Path target = addTimestamp
                    ? directory.resolve(baseName + "_" + timestamp() + ".png")
                    : directory.resolve(baseName + ".png");
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(target, bytes);
            return target.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to capture screenshot: " + name, e);
        }
    }

    private static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
}
