Feature: Generate Access Token based on roles

  @AuthSO
  Scenario Outline: Generate Access Token
    When Send username "<username>" and password "<password>" to get Access Token from Authentication Endpoint.
    Then Validate that status code is 200

    Examples:
      | username | password |
      |          |          |
      |          |          |
      |          |          |
      |          |          |

