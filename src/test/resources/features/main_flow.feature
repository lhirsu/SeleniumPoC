Feature: Main Use Cases

  @scenario-1 @regression
  Scenario Outline: 1. Simple Test Scenario


    Given I navigate to home page

    Given I add <number_of_items> random items to my cart

    When I view my cart

    Then I find total <number_of_items> items listed in my cart

    When I search for lowest price item

    And I am able to remove the lowest price item from my cart

    Then I am able to verify <final_number_of_items> items in my cart

    Then assert all above checks

    Examples:
      | number_of_items | final_number_of_items |
      | 4               | 3                     |
#      | 6               | 5                     |

