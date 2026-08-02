# Step 7 – Debugging Challenge

## Intentional failure

**Goal:** Demonstrate finding and fixing a broken locator.

### Fault introduced
In `LoginPage`, the login button locator was temporarily changed from a valid selector:

```java
private final By loginButton = By.cssSelector("button[type='submit']");
```

to an **incorrect** selector that does not exist on the page:

```java
private final By loginButton = By.cssSelector("button#login-btn-missing");
```

### Observed failure
- Test: `LoginValidTest.validLogin_shouldOpenDashboard`
- Symptom: Test hung then failed with `TimeoutException` / element not clickable / not found while waiting for the login button
- Screenshot: Saved under `screenshots/` by `BaseTest.tearDown` on failure

### Debugging process
1. Read the TestNG / Surefire stack trace – failure pointed to `BasePage.click` → `WaitUtils.clickable`
2. Opened the failure screenshot – login page was visible; button was on screen but not matched
3. Inspected the live page (Chrome DevTools) – confirmed real button is `button[type='submit']` with class `oxd-button`
4. Compared intentional locator `#login-btn-missing` vs actual DOM – root cause = **wrong CSS selector**
5. Restored the correct locator and re-ran `mvn test` – scenario passed

### Root cause
The automation looked for an element ID that does not exist in OrangeHRM’s login DOM. Explicit wait correctly timed out instead of clicking a wrong element.

### Fix
Restore:

```java
private final By loginButton = By.cssSelector("button[type='submit']");
```

### Lesson learned
- Prefer resilient locators (`name`, `type`, stable attributes) over invented IDs
- Failure screenshots + wait stack traces speed up diagnosis
- POM helps: only `LoginPage` needed a one-line fix; tests stayed unchanged

### Evidence for report / video
1. Show failing run with wrong locator (or paste stack trace)
2. Show DevTools inspection
3. Show fix commit / corrected line
4. Show green re-run
