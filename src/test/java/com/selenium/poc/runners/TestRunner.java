package com.selenium.poc.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "json:target/report.json",
                "junit:target/junit/junitResults.xml"
        },
        glue = "com.selenium.poc.stepdefinitions",
        features = "src/test/resources/features",
        tags = "@regression"
)
public class TestRunner {
}