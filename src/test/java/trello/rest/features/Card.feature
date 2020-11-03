Feature: Verify Trello Cards

  Scenario: Create an empty card.
    Given I have a board with List
    When I make an http post call to create a card
    Then I must get a status code 200 and a json object with card details

  Scenario Outline: Create a card without <parameter> query parameter.
    Given I have a board with List
    When I make an http post call without <parameter> query parameter to create a card
    Then I must get a status code <status> and the message <message>
    Examples:
      | parameter | status | message                                  |
      | "key"     | 401    | "invalid key"                            |
      | "token"   | 401    | "unauthorized card permission requested" |
      | "idList"  | 400    | "invalid value for idList"               |

  Scenario Outline: Edit card <key>.
    Given I have a card created
    When I make an http put call to edit <key> card to <value>
    Then I must get a status code <status> and the card <key> must be <value>
    Examples:
      | key    | value                | status |
      | "name" | "Test card name 123" | 200    |
      | "desc" | "Test card desc 123" | 200    |

  Scenario Outline: Edit card <key> with value integer type.
    Given I have a card created
    When I make an http put call to edit <key> card to <value>
    Then I must get a status code <status> and the message <message>
    Examples:
      | key    | value | status | message                  |
      | "name" | 4321  | 400    | "invalid value for name" |
      | "desc" | 1234  | 400    | "invalid value for desc" |

  Scenario Outline: Edit card without <parameter> query parameter.
    Given I have a board with List
    When I make an http put call without <parameter> query parameter to edit a card
    Then I must get a status code <status> and the message <message>
    Examples:
      | parameter | status | message                                  |
      | "key"     | 401    | "invalid key"                            |
      | "token"   | 401    | "unauthorized card permission requested" |

  Scenario: Archive card
    Given I have a card created
    When I make an http put call to edit "closed" card to "true"
    Then I must get a status code 200 and the card "closed" must be true
    And The list not must contain the card

  Scenario: Unarchive card
    Given I have a card created
    When I make an http put call to edit "closed" card to "false"
    Then I must get a status code 200 and the card "closed" must be false
    And The list must contain the card

  Scenario Outline: Add a <imageValue> Sticker to a Card
    Given I have a card created
    When I make an http post call to add a sticker with <imageKey>: <imageValue>, <topKey>: <topValue>, <leftKey>: <leftValue> and <zIndexKey>: <zIndexValue>
    Then I must get a status code <status>
    And The fields must be <imageKey> = <imageValue>, <topKey> = <topValue>, <leftKey> = <leftValue> and <zIndexKey> = <zIndexValue>
    Examples:
      | imageKey | imageValue   | topKey | topValue | leftKey | leftValue           | zIndexKey | zIndexValue | status |
      | "image"  | "check"      | "top"  | -59      | "left"  | 99.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "heart"      | "top"  | 99       | "left"  | -59.110236220472444 | "zIndex"  | 1           | 200    |
      | "image"  | "warning"    | "top"  | 100      | "left"  | 9.110236220472444   | "zIndex"  | 1           | 200    |
      | "image"  | "clock"      | "top"  | -60      | "left"  | 44.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "smile"      | "top"  | 2        | "left"  | -60.110236220472444 | "zIndex"  | 1           | 200    |
      | "image"  | "laugh"      | "top"  | 3        | "left"  | 44.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "huh"        | "top"  | 4        | "left"  | 99.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "frown"      | "top"  | 5        | "left"  | 99.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "thumbsup"   | "top"  | 6        | "left"  | 99.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "thumbsdown" | "top"  | 7        | "left"  | 17.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "star"       | "top"  | 8        | "left"  | 15.110236220472444  | "zIndex"  | 1           | 200    |
      | "image"  | "rocketship" | "top"  | 10       | "left"  | 12.110236220472444  | "zIndex"  | 1           | 200    |

  Scenario Outline: Verify <key> query parameter out of permitted range
    Given I have a card created
    When I make an http post call to add a sticker with <key> = <value>
    Then I must get a status code <status> and the message <message>
    Examples:
      | key      | value      | status | message                    |
      | "top"    | "100.001"  | 400    | "invalid value for top"    |
      | "top"    | "-60.001"  | 400    | "invalid value for top"    |
      | "left"   | "100.001"  | 400    | "invalid value for left"   |
      | "left"   | "-100.001" | 400    | "invalid value for left"   |
      | "rotate" | "-360.1"   | 400    | "invalid value for rotate" |
      | "rotate" | "360.1"    | 400    | "invalid value for rotate" |

  Scenario Outline: Create <colorValue> label
    Given I have a board
    When I make an http post call to create a new <colorValue> "color" label with "name" <nameValue>
    Then I must get a status code 200
    And The fields must be "name" = <nameValue>, "color" = <colorValue>
    Examples:
      | colorValue | nameValue      |
      | "blue"     | "Label Blue"   |
      | "yellow"   | "Label yellow" |
      | "purple"   | "Label purple" |
      | "red"      | "Label red"    |
      | "green"    | "Label green"  |
      | "orange"   | "Label orange" |
      | "black"    | "Label black"  |
      | "sky"      | "Label sky"    |
      | "pink"     | "Label pink"   |
      | "lime"     | "Label lime"   |

  Scenario Outline: Create a label without <parameter> query parameter.
    Given I have a board
    When I make an http post call without <parameter> query parameter to create a label
    Then I must get a status code <status> and the message <message>
    Examples:
      | parameter | status | message                             |
      | "token"   | 401    | "unauthorized permission requested" |
      | "key"     | 401    | "invalid key"                       |
      | "name"    | 400    | "invalid value for name"            |

  Scenario: Create a label without idBoard query parameter.
    Given I have a board
    When I make an http post call without idBoard query parameter to create a label
    Then I must get a status code 400 with obj Json

  Scenario Outline: Add <color> label on the card.
    Given I have a <color> label
    When I make an http post call to add the <color> label on a card
    Then I must get a status code 200
    And Must contain list of labels attributed to this card with the <color> label ID
    Examples:
      | color    |
      | "yellow" |
      | "purple" |
      | "blue"   |
      | "red"    |
      | "green"  |
      | "orange" |
      | "black"  |
      | "sky"    |
      | "pink"   |
      | "lime"   |

  Scenario: Adding the same label to a card
    Given I created a card
    And I created a label in the card
    When I make an http post call to add the same label on a card
    Then I must get a status code 400 with message: "that label is already on the card"

  Scenario Outline: Add label on the card without <parameter> query parameter.
    Given I created a card
    And I created a label
    When I make an http post call without <parameter> query parameter to Add label on the card
    Then I must get a status code <status> and the message <message>
    Examples:
      | parameter | status | message                                  |
      | "token"   | 401    | "unauthorized card permission requested" |
      | "key"     | 401    | "invalid key"                            |
      | "value"   | 400    | "invalid value for value"                |

  Scenario: Add a new comment to a Card
    Given I created a card
    When I make an http post call to Add a new comment to a Card with text = "Test Add a new comment to a Card"
    Then I must get a status code 200 with obj Json content text = "Test Add a new comment to a Card"

  Scenario: Update a comment on a Card
    Given I created a card
    And I created a comment with text = "Test Sensedia Update"
    When I make an http put call to update the comment with text = "Test Update a comment on a Card"
    Then I must get a status code 200 with obj Json content text = "Test Update a comment on a Card"

  Scenario: Delete a comment on a Card
    Given I created a card
    And I created a comment with text = "Test Sensedia DELETE"
    When I make an http delete call to remove the comment
    Then I must get a status code 200 with obj Json value: null

  Scenario: Remove a Label from a Card
    Given I created a card
    And I created a label in the card
    When I make an http delete call to remove the same label on a card
    Then I must get a status code 200 with obj Json value: null

  Scenario: Delete a card.
    Given I created a card
    When I make an http delete call to delete a card
    Then I must get a status code 200 with an empty json object

  Scenario Outline: Delete a card without <parameter> query parameter.
    Given I created a card
    When I make an http delete call to delete a card without <parameter> query parameter
    Then I must get a status code 401 and the message <message>
    Examples:
      | parameter | message                                  |
      | "key"     | "invalid key"                            |
      | "token"   | "unauthorized card permission requested" |

  Scenario: Delete all card in the list.
    Given I have a list with 2 or more cards
    When I make http delete calls until I run out of cards
    Then I query the list
    And Receive an empty array stating that there is no card in the list

  Scenario: Delete Board
    Given I have a board
    When I make an http delete call
    Then I must get a status code 200