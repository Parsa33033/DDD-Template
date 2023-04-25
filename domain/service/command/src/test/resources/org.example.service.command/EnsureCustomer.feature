Feature: Ensure customer

  Scenario: ensure customer John
    When ensuring customer John
    Then customer John is returned
