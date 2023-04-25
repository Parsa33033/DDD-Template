Feature: Get customer

  Scenario: request for john returns successful result
    Given customer John
    When customer John is queried
    Then customer John is returned

  Scenario: request for john returns unsuccessful result
    When customer John is queried
    Then result is returned with error "CUSTOMER_NOT_FOUND"
