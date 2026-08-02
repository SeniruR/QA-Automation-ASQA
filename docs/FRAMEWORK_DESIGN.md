# Framework Design Notes

## Why Selenium + TestNG + POM?

| Choice | Reason |
|--------|--------|
| Selenium | Standard for browser UI automation taught in this module |
| TestNG | Suite XML, `@BeforeMethod` / `@AfterMethod`, rich assertions, parallel-ready |
| POM | Separates locators/actions from assertions → maintainable when UI changes |
| Maven | Reproducible builds and dependency versions |
| Selenium Manager | Resolves matching browser drivers automatically (Selenium 4.6+) |

## Layer responsibilities

```
┌─────────────────────────────────────┐
│  tests/   (what to verify)          │
├─────────────────────────────────────┤
│  pages/   (how to interact with UI) │
├─────────────────────────────────────┤
│  base/    (shared page + test life) │
├─────────────────────────────────────┤
│  utils/   (config, waits, shots)    │
└─────────────────────────────────────┘
```

## Wait strategy
- Short implicit wait for basic finds
- Explicit waits (20s) for clickable/visible critical elements
- No `Thread.sleep` in production test code

## Configuration
All environment values live in `src/test/resources/config.properties` so credentials and timeouts can change without recompiling page objects.
