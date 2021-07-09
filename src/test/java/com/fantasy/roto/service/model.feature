Feature: roto rankings

  Scenario: raw stats entered

    Given a map of each player stats with
      | name |

    And a list of stats
      | runs | homeRuns | rbis | sbs | avg  | ops  |
      | 30   | 10       | 30   | 10  | .300 | .850 |
      | 25   | 10       | 25   | 11  | .250 | .750 |
    
    When rankAllColumns

    Then the players are ranked

    And the list of ranks for each player found
