# SE333 Assignment 6

## GitHub Repository
Please reference GitHub repository link: [Your Repo Here](https://github.com/yourusername/your-repo)

## GitHub Actions
The GitHub Action is configured to execute all tests. All tests must pass for the workflow to succeed.

## Reflection
This project implemented two approaches for automating UI testing using Playwright:

1. **Direct element locators and interactions**  
   - Selected elements explicitly using getByRole, getByText, and locator.
   - Worked reliably with the current website structure.

2. **Programmatic filtering and pattern-based locators**  
   - Attempted to use dynamic patterns and conditional filters (Pattern.compile) for searches.
   - This approach was less stable because some dynamic content and filter selectors did not always match.

**Conclusion:** The direct locator approach was more stable and reliable, ensuring all tests pass and the workflow succeeds.

> **Important:** If the code does not compile, it results in an instant zero.

