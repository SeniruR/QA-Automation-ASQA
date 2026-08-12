# Step 7 – Debugging Challenge (with evidence)

## Intentional failure

**Test:** `LoginValidTest.validLogin_shouldOpenDashboard`  
**Change in `LoginPage.java`:**

```java
// BROKEN (intentional)
private final By loginButton = By.cssSelector("button#login-btn-missing");

// FIXED
private final By loginButton = By.cssSelector("button[type='submit']");
```

## Observed failure

- **Exception:** `TimeoutException` – element not clickable after 20 seconds
- **Locator:** `By.cssSelector: button#login-btn-missing`
- **Stack trace:** `WaitUtils.clickable` → `BasePage.click` → `LoginPage.clickLogin`

## Evidence files (in repo)

| File | Description |
|------|-------------|
| `docs/debugging/failure-log.txt` | Full Maven/Surefire output from failing run |
| `docs/debugging/success-log.txt` | Output after locator fix (BUILD SUCCESS) |
| `docs/debugging/debug_failure_login_button.png` | Screenshot at failure (login page visible, button not found) |

## Debugging process

1. Read Surefire output – pointed to login button wait timeout
2. Opened failure screenshot – page loaded but automation could not find `#login-btn-missing`
3. Inspected OrangeHRM login DOM – real button is `button[type='submit']`
4. Restored correct locator in `LoginPage` only (tests unchanged – POM benefit)
5. Re-ran `mvn test -Dtest=LoginValidTest` – passed

## Root cause

Invented CSS ID that does not exist on the OrangeHRM login page.

## Lesson learned

Use stable attributes (`type`, `name`) over guessed IDs. Combine stack traces with failure screenshots for faster diagnosis.
