package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;

    // //div[@id='primary-menu']//a
    // //div[@id='primary-menu']//a[text()='Cart']
    // //div[@id='primary-menu']//a[text()='Checkout']
    // //div[@id='primary-menu']//a[text()='Shop']

    @FindBy(how = How.CSS, using = ".site-branding .site-title")
    private WebElement logo;

    @FindBy(how = How.XPATH, using = "//div[@id='primary-menu']//a[text()='Cart']")
    private WebElement link_Cart;

    @FindBy(how = How.CSS, using = ".next.page-numbers")
    private WebElement link_NextPage;

    @FindAll(@FindBy(how = How.CSS, using = ".content-area .products"))
    private List<WebElement> prd_List;

    @FindAll(@FindBy(how = How.CSS, using = ".content-area .products .add_to_cart_button.ajax_add_to_cart"))
    private List<WebElement> btn_AddToCart;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HomePage addToCart(int productIndex) {

        String productId = getElementAttribute(btn_AddToCart.get(productIndex), "data-product_id");

        btn_AddToCart.get(productIndex).click();

        // wait for product's View Cart button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.ByXPath.xpath(getProductAddedToCartXpath(productId))));

        return this;
    }

    public HomePage addToCart(String productName) {
        // ToDo
        return this;
    }

    public int getDisplayedProductsNumber() {
        return btn_AddToCart.size();
    }

    public CartPage clickCartLink() {
        link_Cart.click();
        return new CartPage(driver);
    }

    public HomePage clickOnLogo() {
        logo.click();
        return this;
    }

    private void scrollElementIntoView(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    private String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    private String getProductAddedToCartXpath(String productId) {
        return "//a[@data-product_id='" + productId + "']/ancestor::div/a[@class='added_to_cart wc-forward']";
    }

}