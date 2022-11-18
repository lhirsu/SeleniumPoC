package pageObjects;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

@Slf4j
public class CartPage {

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item"))
    private List<WebElement> cartItem_List;

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item .product-remove"))
    private List<WebElement> btnRemoveItem_List;

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item .product-price .woocommerce-Price-amount"))
    private List<WebElement> labelPrice_List;

    @FindBy(how = How.XPATH, using = "//div[@id='primary-menu']//a[text()='Cart']")
    private WebElement link_Cart;

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getNumberOfItemsInCart() {
        return cartItem_List.size();
    }

    public CartPage reloadCartPage() {
        link_Cart.click();
        return new CartPage(driver);
    }

    public TreeMap<Double, List<String>> getPriceToProductIdListInfo() {

        TreeMap<Double, List<String>> priceToProductIdList = new TreeMap<>();

        cartItem_List.forEach(cartItem -> {
            String productId = getElementAttribute(cartItem.findElement(By.ByCssSelector.cssSelector(".product-remove .remove")), "data-product_id");
            Double price = Double.parseDouble(cartItem.findElement(By.ByCssSelector.cssSelector(".product-price .woocommerce-Price-amount")).getText().replace("$", ""));
            priceToProductIdList.computeIfAbsent(price, k -> new ArrayList<>()).add(productId);
        });

        return priceToProductIdList;
    }

    public void removeLowestPriceItems(TreeMap<Double, List<String>> productIdToPrice) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        productIdToPrice.pollFirstEntry().getValue().forEach(productId -> {
            String productName = driver.findElement(By.ByXPath.xpath(getProductNameXpath(productId))).getText();
            log.info("Removing product having id {} from cart", productId);
            WebElement currElement = driver.findElement(By.ByXPath.xpath(getProductToBeRemovedFromCartXpath(productId)));
            currElement.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.ByXPath.xpath("//div[@class='woocommerce-message'][contains(text(),'" + productName + "')]"))));
        });

    }

    private String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    private String getProductToBeRemovedFromCartXpath(String productId) {
        return "//td[@class='product-remove']/a[@data-product_id='" + productId + "']";
    }

    private String getProductNameXpath(String productId) {
        return "//td[@class='product-remove']/a[@data-product_id='" + productId + "']/ancestor::tr/td[@class='product-name']/a";
    }

}