package com.adswizz.qa.iforecast.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.CartPage;
import pageObjects.HomePage;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StepDefinitions {

    CartPage cartPage;
    HomePage homePage;

    SoftAssertions softly = new SoftAssertions();
    HashMap<String, Double> productIdToPrice;

    WebDriver driver;

    @Given("I navigate to home page")
    public void i_navigate_to_home_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://cms.demo.katalon.com/");
        homePage = new HomePage(driver);
    }

    @Given("I add {int} random items to my cart")
    public void i_add_random_items_to_cart(Integer itemsToBeAddedToCart) {
        Set<Integer> randomProducts = getRandomDistinctIndexes(homePage.getDisplayedProductsNumber(), itemsToBeAddedToCart);
        randomProducts.forEach(prodIndex -> homePage.addToCart(prodIndex));
    }

    @When("I view my cart")
    public void i_view_my_cart() {
        cartPage = homePage.clickCartLink();
    }

    @Then("I find total {int} items listed in my cart")
    public void i_find_total_items_in_my_cart(Integer itemsInCart) {
        softly.assertThat(cartPage.getNumberOfItemsInCart()).isEqualTo(itemsInCart);
    }

    @When("I search for lowest price item")
    public void i_search_for_lowest_price_item() {
        productIdToPrice = cartPage.getProductPriceInfo();
    }

    @When("I am able to remove the lowest price item from my cart")
    public void i_am_able_to_remove_the_lowest_price_item_from_my_cart() {
        // ToDo - use productIdToPrice info
    }

    @Then("I am able to verify {int} items in my cart")
    public void i_am_able_to_verify_three_items_in_my_cart(Integer itemsInCart) {
        softly.assertThat(cartPage.getNumberOfItemsInCart()).isEqualTo(itemsInCart);
        // ToDo
    }

    @Then("assert all above checks")
    public void assert_all_checks() {
        softly.assertAll();
    }

    private Set<Integer> getRandomDistinctIndexes(int max, int indexesNeeded) {

        Random random = new Random();
        Set<Integer> generated = new HashSet<>();

        while (generated.size() < indexesNeeded) {
            Integer next = random.nextInt(max);
            generated.add(next);
        }

        return generated;
    }

}