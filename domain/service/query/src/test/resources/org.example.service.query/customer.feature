Feature: Get customer

  Scenario: request for john returns successful result
    Given customer John
    When customer John is queried
    Then customer John is returned
