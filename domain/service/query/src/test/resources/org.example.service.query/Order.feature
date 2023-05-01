Feature: Get customer

  Scenario: request for john returns successful result
    Given order Shoes
    When order Shoes is queried
    Then order Shoes is returned

  Scenario: request for john returns unsuccessful result
    When order Shoes is queried
    Then order result is returned with error "ORDER_NOT_FOUND"
