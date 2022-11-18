package com.selenium.poc.hooks;

import io.cucumber.java.After;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;

public class Hook {

    @Steps(shared = true)
    private WebDriver driver;

    @After("@tear_down")
    public void tearDown() {
        driver.close();
        driver.quit();
    }

}