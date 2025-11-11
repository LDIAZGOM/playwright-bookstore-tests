package playwrightTraditional;

import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

class TestBookstorePurchaseFlow {

    @DisplayName("SKU Search Test")
    @Test
    void productSearchTest() {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setRecordVideoDir(Paths.get("videos/"))
                            .setRecordVideoSize(1280, 720)
            );

            Page page = context.newPage();
            page.navigate("https://depaul.bncollege.com/");

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("EARBUDS");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("search").setExact(true)).click();

            // Expand filters
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();

            // Apply "Price > $50"
            page.locator("li:nth-child(4) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();

            // Select Brand = JBL
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
            page.locator(".facet__list.js-facet-list.js-facet-top-values > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg")
                    .first().click();

            // Select Color = Black
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
            page.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg")
                    .first().click();

            // Click JBL product item link(uuuu)
            page.getByRole(AriaRole.LINK,
                            new Page.GetByRoleOptions().setName("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black"))
                    .click();

            // Assertions
            assertThat(page.getByLabel("main").getByRole(AriaRole.HEADING))
                    .containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            page.getByText("$164.98").click();
            assertThat(page.getByLabel("main")).containsText("$164.98");
            assertThat(page.getByLabel("main")).containsText("668972707");
            assertThat(page.getByLabel("main")).containsText(
                    "Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms."
            );

            // Anadir al carrito
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
            assertThat(page.locator("#headerDesktopView")).containsText("1 items");

            // Ver carrito
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
            assertThat(page.getByLabel("main")).containsText("Your Shopping Cart");
            assertThat(page.getByLabel("main"))
                    .containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            assertThat(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press")))
                    .hasValue("1");

            page.getByText("$").first().dblclick();
            page.getByText("$").first().dblclick();

            assertThat(page.getByLabel("main")).containsText("$164.98");
            assertThat(page.getByLabel("main")).containsText(
                    "Subtotal $164.98 Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00 Taxes TBD"
            );

            // Codigo de promocion
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
            assertThat(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code"))).hasValue("TEST");
            assertThat(page.locator("#js-voucher-result")).containsText("The coupon code entered is not valid.");

            // Checar bien
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();
            assertThat(page.getByLabel("main")).containsText("Create Account");

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("t");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("t");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("t");

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("t@gmail.com");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("3333333333");

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

            assertThat(page.getByLabel("main")).containsText("Full Name t t Email Address t@gmail.com Phone Number +13333333333");
            assertThat(page.getByLabel("main"))
                    .containsText("Order Summary Order Subtotal $164.98 Handling");

            assertThat(page.locator(".sub-check").first()).isVisible();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
            assertThat(page.getByLabel("main")).containsText("Your cart is empty");
        }
    }
}