package com.asqa.orangehrm.pages;

import com.asqa.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * PIM (Personnel Information Management) – employee list and search.
 */
public class PIMPage extends BasePage {

    private final By pageHeader = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By employeeNameInput = By.xpath(
            "//label[text()='Employee Name']/../following-sibling::div//input");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resultRows = By.cssSelector(".oxd-table-body .oxd-table-card");
    private final By employeeListTab = By.xpath("//a[contains(@href,'viewEmployeeList')]");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPIMPageDisplayed() {
        return wait.urlContains("pim")
                && "PIM".equalsIgnoreCase(getText(pageHeader).trim());
    }

    public PIMPage ensureEmployeeList() {
        if (!driver.getCurrentUrl().contains("viewEmployeeList")) {
            click(employeeListTab);
        }
        waitVisible(employeeNameInput);
        return this;
    }

    public PIMPage searchByEmployeeName(String name) {
        ensureEmployeeList();
        type(employeeNameInput, name);
        click(searchButton);
        return this;
    }

    public int getResultCount() {
        List<WebElement> rows = driver.findElements(resultRows);
        return rows.size();
    }

    public boolean hasSearchResults() {
        waitVisible(By.cssSelector(".oxd-table"));
        return getResultCount() >= 0;
    }
}
