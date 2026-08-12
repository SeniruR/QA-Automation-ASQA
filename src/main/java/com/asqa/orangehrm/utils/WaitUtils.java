package com.asqa.orangehrm.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralised explicit waits to reduce flaky timing issues on the shared demo site.
 */
public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean urlContains(String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }

    public boolean textPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /** Waits until document.readyState is complete. */
    public void waitForDocumentReady() {
        wait.until(d -> "complete".equals(
                ((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    /** Waits until OrangeHRM loading spinners are no longer visible. */
    public void waitForLoadingSpinnersToFinish() {
        By spinner = By.cssSelector(".oxd-loading-spinner, .oxd-loading-spinner-container");
        wait.until(d -> {
            List<WebElement> spinners = d.findElements(spinner);
            return spinners.isEmpty()
                    || spinners.stream().noneMatch(el -> el.isDisplayed());
        });
    }

    /** Waits until all images on the page have finished loading. */
    public void waitForAllImagesLoaded() {
        wait.until(d -> Boolean.TRUE.equals(((JavascriptExecutor) d).executeScript(
                "return Array.from(document.images).every(function(img) {"
                        + " return img.complete && img.naturalWidth > 0; })")));
    }

    /** Waits until an element has non-trivial computed height (CSS applied). */
    public void waitForStyledElement(By locator, int minHeightPx) {
        wait.until(d -> {
            WebElement element = d.findElement(locator);
            Object height = ((JavascriptExecutor) d).executeScript(
                    "return parseInt(window.getComputedStyle(arguments[0]).height, 10);", element);
            return height instanceof Long && (Long) height >= minHeightPx
                    || height instanceof Integer && (Integer) height >= minHeightPx
                    || height instanceof Double && (Double) height >= minHeightPx;
        });
    }
}
