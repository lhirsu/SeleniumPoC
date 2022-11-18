# SeleniumPoC
This project is just a PoC.

## Overview

This project uses [Serenity BDD](https://github.com/serenity-bdd) and [Cucumber](https://cucumber.io/) with [Gherkin](https://cucumber.io/docs/gherkin/reference/) 
for organizing the tests in a BDD fashion.


## Run Tests

The tests should be run with:

``mvn clean verify``


### Specify which tests should be executed (create test suites)

By running the tests with ``mvn clean verify``, all scenarios under `/src/test/resources/features` will be executed 
(as specified in `TestRunner` class).

In order to narrow the selection and specify which tests should run you should use the `-Dcucumber.filter.tags` property, for example:

`mvn clean verify "-Dcucumber.filter.tags=@regression"` - will run all regression tests.


## Improvements

* Add a new class - `NavigationMenu` - and use composition to add it in each existing page object because all pages (Cart, My Account, Shop, etc) display this navigation menu in the upper part of the page
* Add a hook tear down @After method in order to close the driver

