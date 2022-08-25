Feature: Test API Automation

  @Regression
  Scenario Outline: API test regression
    Given Test <testID> for "Regression : <Scenario>"
   # And The customer logs into app with userName "userLogin" and password "password"
    When The customer calls "CreateRecord" and provides "name" as wells as "job"
    When The customer uses "CreateRecord" API in "QA" region by "POST" method in "IO" channel
    Then The customer validates API response code as "responseCode"
    Then The customer validates "validateName" and value is "expectedName" in response body

    Examples:
    | testID |
    | 1      |
