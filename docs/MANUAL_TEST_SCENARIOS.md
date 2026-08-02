# Manual Test Scenarios – OrangeHRM Demo

**Application:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login  
**Preconditions (unless stated):** Fresh browser session; demo site reachable  

Capture screenshots for High-priority scenarios and attach them in the PDF report.

---

### TS-01 – Valid login with Admin credentials
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Open login URL 2. Enter username `Admin` 3. Enter password `admin123` 4. Click Login |
| Expected | User is redirected to Dashboard; header shows “Dashboard”; side menu is visible |
| Screenshot | Recommended |

### TS-02 – Invalid login credentials
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Open login 2. Enter invalid username/password 3. Click Login |
| Expected | Error message “Invalid credentials” (or equivalent); user remains on login page |
| Screenshot | Recommended |

### TS-03 – Login with empty username and password
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Open login 2. Leave fields empty 3. Click Login |
| Expected | Required-field validation appears; login does not proceed |

### TS-04 – Logout from Dashboard
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Login as Admin 2. Open user dropdown (top right) 3. Click Logout |
| Expected | User returns to login page; URL contains `auth/login` |

### TS-05 – Dashboard loads after successful login
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Login as Admin 2. Observe dashboard widgets/layout |
| Expected | Dashboard module header visible; no blank/error page |

### TS-06 – Navigate to PIM module
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Login 2. Click **PIM** in side menu |
| Expected | PIM page opens; URL contains `pim`; breadcrumb/header shows PIM |
| Screenshot | Recommended |

### TS-07 – Search employee by name in PIM
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Login 2. Open PIM 3. Enter partial name in Employee Name 4. Click Search |
| Expected | Results table updates; records found label or rows displayed (data may vary on shared demo) |

### TS-08 – Open Add Employee form
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Login 2. PIM → Add Employee |
| Expected | Add Employee form shows First Name, Last Name, Employee Id fields |

### TS-09 – Navigate to Admin – System Users
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Login 2. Click **Admin** |
| Expected | Admin module opens; “System Users” section visible |
| Screenshot | Recommended |

### TS-10 – Search system user by username
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Login 2. Admin 3. Enter `Admin` in Username filter 4. Search |
| Expected | Matching user record(s) listed or records-found message shown |

### TS-11 – Navigate to Leave module
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Login 2. Click **Leave** |
| Expected | Leave module loads without application error |

### TS-12 – Collapse and expand side menu
| Field | Value |
|-------|-------|
| Priority | Low |
| Steps | 1. Login 2. Click menu toggle/chevron 3. Expand again |
| Expected | Side menu collapses/expands; page remains usable |

### TS-13 – Forgot password link visibility
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Open login 2. Locate Forgot password link 3. Click (optional) |
| Expected | Link is visible; reset username page opens if clicked |

### TS-14 – Direct dashboard URL when logged out
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Ensure logged out 2. Paste dashboard URL in browser |
| Expected | User redirected to login (unauthenticated access blocked) |

### TS-15 – Browser Back after logout
| Field | Value |
|-------|-------|
| Priority | Low |
| Steps | 1. Login 2. Logout 3. Press browser Back |
| Expected | Protected dashboard content is not freely accessible without re-login |

### TS-16 – Password field masks input
| Field | Value |
|-------|-------|
| Priority | Medium |
| Steps | 1. Open login 2. Type password |
| Expected | Password characters are masked (type=password) |

### TS-17 – Username only filled (password empty)
| Field | Value |
|-------|-------|
| Priority | High |
| Steps | 1. Enter username only 2. Click Login |
| Expected | Validation for missing password; login fails |

---

## Traceability to automation

| Manual ID | Automated? | Test class |
|-----------|------------|------------|
| TS-01 | Yes | `LoginValidTest` |
| TS-02 | Yes | `LoginInvalidTest` |
| TS-04 | Yes | `LogoutTest` |
| TS-07 | Yes | `PIMSearchEmployeeTest` |
| TS-09 (+ TS-10 search) | Yes | `AdminUserManagementNavigationTest` |
| Others | Manual only | — |
