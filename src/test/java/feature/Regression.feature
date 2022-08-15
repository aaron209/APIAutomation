Feature: Test API Automation

  @Regression
  Scenario Outline: API test regression
    Given Test <testID> for "Regression : <Scenario>"
    And The customer logs into app with userName "userLogin" and password "password"
    When The customer calls "API" and provides "fromAccountId" as wells as "toAccountId"
    When The customer uses "API" API in "QA" region by "GET" method in "IO" channel
    Then The customer validates API response code as "responseCode"

    Examples:
    | testID |
    | 1      |