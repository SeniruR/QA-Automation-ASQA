# OrangeHRM QA Automation – UCSC Take-Home

**Student:** R.P.S.R. Ranasinghe (`22020782`)  
**Repository:** https://github.com/SeniruR/QA-Automation-ASQA

Selenium + TestNG + Page Object Model automation for the lecturer-approved site:

**https://opensource-demo.orangehrmlive.com/web/index.php/auth/login**

## Prerequisites
- JDK 17+
- Maven 3.9+
- Microsoft Edge **or** Google Chrome

Default browser in `config.properties` is `edge` (works on Windows 11).

If Selenium cannot download the driver (CDN blocked), set a local path:

```properties
edge.driver.path=C:/Users/YOU/tools/edgedriver/msedgedriver.exe
```

Download matching EdgeDriver from: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/

Set `browser=chrome` (and optional `chrome.driver.path`) if Chrome is installed.

## Run tests
```bash
mvn clean test
```

## Project structure
```
src/main/java/com/asqa/orangehrm/
  base/      BasePage
  pages/     LoginPage, DashboardPage, PIMPage, AdminPage
  utils/     ConfigReader, WaitUtils, ScreenshotUtils
src/test/java/com/asqa/orangehrm/
  base/      BaseTest
  tests/     Five automated scenarios
src/test/resources/
  config.properties
  testng.xml
docs/        Assignment report content (export to PDF)
```

## Automated scenarios
1. Valid login  
2. Invalid login  
3. Logout  
4. PIM employee search  
5. Admin System Users navigation  

## Documentation for submission
| Deliverable | Location |
|-------------|----------|
| Report content (→ PDF) | `docs/ASSIGNMENT_REPORT.md` |
| Manual scenarios | `docs/MANUAL_TEST_SCENARIOS.md` |
| Debugging challenge | `docs/DEBUGGING_CHALLENGE.md` |
| AI usage | `AI_USAGE.md` |

## Config
Edit `src/test/resources/config.properties` for URL, credentials, browser, and timeouts.
