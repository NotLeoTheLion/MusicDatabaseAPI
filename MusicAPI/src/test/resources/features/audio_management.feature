Feature: Audio Management

  Scenario: Retrieve all audios
    Given the audio records exist
    When I request all audios
    Then I should receive a list of all audios

  Scenario: Retrieve an audio by ID
    Given an audio record with ID 2 exists
    When I request the audio with ID 2
    Then I should receive the audio with ID 2
