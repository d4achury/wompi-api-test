Feature: Get Merchant Information

  Scenario: Retrieve merchant details successfully from Wompi Sandbox
    Given the QA tester is using the Wompi Sandbox environment
    When the tester retrieves the merchant information
    Then the response should have status 200
