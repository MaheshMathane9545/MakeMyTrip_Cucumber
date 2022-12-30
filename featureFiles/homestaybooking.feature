
@tag
Feature: Homestay Booking
  

  @tag1
  Scenario: user should able to book homestay
  
  	Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on homestay
    Then system should display homestay homepage
    
    And select from city as "Mumbai"
    And departure date as "1-May-2023"
    And chackout date as "5-June-2023"
    And select "3" adults from list
    And click on Apply button
    And select "Leisure" from travelling from
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs 
    
    And select anyone from list & click on View price
    Then system should open new tab
    
    And select room
    Then user should able to book hotel room
    And take screenshot named as "HomestayBooking"
    And close the browser

 
