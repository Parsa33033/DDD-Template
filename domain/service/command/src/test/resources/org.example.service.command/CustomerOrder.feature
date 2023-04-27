Feature: Ensure customer

  Scenario: create order for customer John
    Given customer John
    When customer John orders "shoes"
    Then result of order is ok
    And order "shoes" was created
    And order "shoes" was returned for customer John
