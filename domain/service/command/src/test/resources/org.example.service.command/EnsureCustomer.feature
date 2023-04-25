Feature: Ensure customer

  Scenario: create customer John
    When ensuring customer John
    Then result is ok
    And customer John is created
    And customer John is returned

  Scenario: update customer John
    Given customer John
    When ensuring customer
      | customer_identifier                  | customer_name |
      | 588faf9c-e150-11ed-b5ea-0242ac120002 | Johnny        |
    Then result is ok
    And customer is updated with
      | customer_identifier                  | customer_name |
      | 588faf9c-e150-11ed-b5ea-0242ac120002 | Johnny        |
    And returned customer is
      | customer_identifier                  | customer_name |
      | 588faf9c-e150-11ed-b5ea-0242ac120002 | Johnny        |