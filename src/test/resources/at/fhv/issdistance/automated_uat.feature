Feature: Automated User Acceptance Tests for the ISS Distance Finder

  Background:
    Given Open https://issdistance-staging.herokuapp.com/

  Scenario: Test login and logout
    Given Login with user 'testUser'
    When I press logout
    Then I see the login page

  Scenario: Test refresh
    Given Login with user 'testUser'
    Then Click on Refresh
    And I press logout
