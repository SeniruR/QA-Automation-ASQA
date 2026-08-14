# UCSC Practical Take-Home Assignment – QA Automation
## OrangeHRM Open Source Demo

**Student:** R.P.S.R. Ranasinghe  
**Student ID:** 22020782  
**Website:** [OrangeHRM Demo Login](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)  
**Stack:** Java 17, Selenium WebDriver 4, TestNG, Maven, Page Object Model  

---

## Selected Website

| Item | Detail |
|------|--------|
| Application | OrangeHRM OS Demo (v5.x) |
| URL | https://opensource-demo.orangehrmlive.com/web/index.php/auth/login |
| Approval | Lecturer approved |
| Why this site | Public HRMS demo with login, dashboard, PIM, Admin, Leave modules; suitable for manual + automated UI testing without local install |

**Demo credentials used for testing:** Username `Admin` / Password `admin123`

---

## Requirement Analysis

### 2.1 Application objectives
OrangeHRM is an open-source Human Resource Management System used to manage employees, leave, time, and system users. The demo allows exploration of HR workflows through a web UI.

### 2.2 Target users
| User type | Typical goals |
|-----------|----------------|
| System Admin | Manage users, roles, job titles, organisation structure |
| HR / PIM user | Add/search/edit employee records |
| Employee / ESS | View personal info, apply leave (role-dependent) |

### 2.3 Key features observed
1. Authentication (login / logout)
2. Dashboard with widgets and side navigation
3. PIM – employee list, add employee, search filters
4. Admin – system users, job, organisation
5. Leave – apply / assign / list leave
6. Time, Recruitment, My Info, Performance, Directory, Claim, Buzz (side menu)

### 2.4 Assumptions
- Demo site remains publicly available during the assignment window
- Default Admin credentials remain `Admin` / `admin123`
- Chrome browser is available for automation
- Shared demo data may be reset or changed by other users
- Network latency may require explicit waits

### 2.5 Risks
| Risk | Impact | Mitigation |
|------|--------|------------|
| Shared demo data changes | Flaky search/add results | Prefer stable flows (login, nav); use soft assertions where counts vary |
| Slow page load | Timeouts | Explicit waits (20s), page load timeout 60s |
| UI locator changes | Broken scripts | POM centralises locators for one-place fixes |
| Concurrent users | Session conflicts | Fresh browser per TestNG `@BeforeMethod` |
| Credential reset | Login failures | Keep credentials in `config.properties` |

### 2.6 Observations
- Modern SPA-like UI (OrangeHRM 5.x) with dynamic loads – Thread.sleep is unreliable; explicit waits are required
- Side menu uses text-based span labels (`PIM`, `Admin`, etc.)
- Login uses `name="username"` and `name="password"`
- Invalid login shows alert text containing “Invalid credentials”
- Logout is under the top-right user dropdown

---

## Manual Test Scenario Design

See full table in `docs/MANUAL_TEST_SCENARIOS.md` (15+ scenarios). Summary:

| ID | Title | Priority |
|----|-------|----------|
| TS-01 | Valid login | High |
| TS-02 | Invalid login | High |
| TS-03 | Empty credentials | High |
| TS-04 | Logout | High |
| TS-05 | Dashboard loads after login | High |
| TS-06 | Navigate to PIM | High |
| TS-07 | Search employee by name | Medium |
| TS-08 | Open Add Employee form | Medium |
| TS-09 | Navigate to Admin / System Users | High |
| TS-10 | Search system user | Medium |
| TS-11 | Navigate to Leave | Medium |
| TS-12 | Side menu collapse/expand | Low |
| TS-13 | Forgot password link | Medium |
| TS-14 | Session redirect when logged out | Medium |
| TS-15 | Browser back after logout | Low |
| TS-16 | Password field masks characters | Medium |
| TS-17 | Required field validation on login | High |

*(Attach screenshots in the PDF for TS-01, TS-02, TS-06, TS-09 while executing manually.)*

---

## Automation Decision

### Selected for automation (5 scenarios)

| ID | Scenario | Why automate |
|----|----------|--------------|
| TS-01 | Valid login | Critical path; high frequency; stable locators; strong ROI for regression |
| TS-02 | Invalid login | Negative auth check; deterministic expected error; quick smoke candidate |
| TS-04 | Logout | Security-related; clear expected URL/state; low data dependency |
| TS-07 | PIM employee search | Representative mid-flow after login; exercises navigation + form + table |
| TS-09 | Admin System Users | Covers second major module; validates menu + section + search |

### Not automated (examples) – and why

| ID | Scenario | Why not automate (now) |
|----|----------|-------------------------|
| TS-03 | Empty credentials | Overlaps TS-02; UI validation messages can be CSS-driven and brittle |
| TS-08 | Add Employee | Creates data on shared demo; cleanup unreliable; higher flakiness |
| TS-11 | Leave flows | Date pickers / calendars increase complexity for this assignment scope |
| TS-12 | Menu collapse | Visual/UX; low business risk |
| TS-13 | Forgot password | May send email / open external flow; environment dependent |
| TS-15 | Browser back | Browser history quirks; better as exploratory manual check |
| TS-16 | Password masking | Visual verification; limited value for WebDriver assertions |

**Decision rule used:** Prefer high priority, stable UI, low data side-effects, and reusable smoke coverage.

---

## Framework Design

### Technology choices
- **Java 17** – industry-standard language for Selenium courses
- **Maven** – dependency and build management
- **Selenium 4** – browser automation
- **TestNG** – annotations, suite XML, assertions
- **Page Object Model** – separate UI map from test logic
- **Selenium Manager** – automatic driver binaries (built into Selenium 4.6+)

### Package / folder map

```
src/main/java/com/asqa/orangehrm/
  base/          → BasePage (shared page helpers)
  pages/         → LoginPage, DashboardPage, PIMPage, AdminPage
  utils/         → ConfigReader, WaitUtils, ScreenshotUtils

src/test/java/com/asqa/orangehrm/
  base/          → BaseTest (browser lifecycle)
  tests/         → Five automated scenario classes

src/test/resources/
  config.properties → URL, credentials, timeouts, browser
  testng.xml        → Suite definition
```

| Package | Responsibility |
|---------|----------------|
| `base` | Shared driver/page setup; avoid duplication |
| `pages` | Locators + user actions only (no TestNG asserts) |
| `utils` | Cross-cutting helpers (config, waits, screenshots) |
| `tests` | Arrange–Act–Assert scenarios only |
| `resources` | Externalised config and suite XML |

### Design principles
1. One browser session per `@Test` method (isolation)
2. Explicit waits preferred over fixed sleeps
3. Screenshots on failure via `@AfterMethod`
4. Credentials outside hard-coded test bodies

---

## Automation Development

Implemented TestNG classes:

1. `LoginValidTest` – valid Admin login → Dashboard  
2. `LoginInvalidTest` – invalid credentials → error alert  
3. `LogoutTest` – logout → login URL  
4. `PIMSearchEmployeeTest` – open PIM → search  
5. `AdminUserManagementNavigationTest` – open Admin → System Users → search  

**Run command:**
```bash
mvn clean test
```

---

## Debugging Challenge

Full write-up: `docs/DEBUGGING_CHALLENGE.md`

**Summary:** An intentional wrong locator for the login button caused `TimeoutException`. Root cause was identified from the stack trace and failure screenshot. Locator was corrected from an invalid CSS selector to `button[type='submit']`, and the test passed.

---

## Reflection Video

Five-minute screen recording (implementation and lessons learned):  
https://drive.google.com/file/d/1LVjVxsYdon5JppayyAF42kQZpBJ2Ra5C/view?usp=sharing

File: `22020782-sqa.mp4`

Covers:
1. Project structure walkthrough  
2. Running `mvn test`  
3. One page object + one test explanation  
4. Debugging challenge (before/after)  
5. Lessons learned (waits, POM, shared demo risks)

---

## GitHub Repository

Maintain incremental commits (framework → pages → tests → docs → debug fix). Do not push a single dump commit.

---

## How to convert this to PDF
Open this Markdown in Word / Google Docs / VS Code Markdown PDF and export as PDF. Insert manual screenshots before submission.
