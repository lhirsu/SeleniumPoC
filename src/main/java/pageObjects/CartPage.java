package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;


public class CartPage {

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item"))
    private List<WebElement> cartItem_List;

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item .product-remove"))
    private List<WebElement> btnRemoveItem_List;

    @FindAll(@FindBy(how = How.CSS, using = ".cart_item .product-price .woocommerce-Price-amount"))
    private List<WebElement> labelPrice_List;

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getNumberOfItemsInCart() {
        return cartItem_List.size();
    }

    public HashMap<String, Double> getProductPriceInfo() {

        HashMap<String, Double> productIdToPrice = new HashMap<>();

        cartItem_List.forEach(cartItem -> {
            String productId = getElementAttribute(cartItem.findElement(By.ByCssSelector.cssSelector(".product-remove .remove")), "data-product_id");
            Double price = Double.parseDouble(cartItem.findElement(By.ByCssSelector.cssSelector(".product-price .woocommerce-Price-amount")).getText().replace("$", ""));
            productIdToPrice.put(productId, price);
        });

        return productIdToPrice;
    }

    private String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    private String getProductAddedToCartXpath(String productId) {
        return "//a[@data-product_id='" + productId + "']/ancestor::div/a[@class='added_to_cart wc-forward']";
    }

}