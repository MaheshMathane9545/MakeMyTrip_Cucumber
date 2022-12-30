
@tag
Feature: Hotel Room booking
  

  @tag1
  Scenario: user shoud able to bool hotel room
    Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on hotel
    Then system should display hotels homepage
    
    And select from city as "Mumbai"
    And departure date as "1-May-2023"
    And chackout date as "5-May-2023"
    
    And select "2" rooms from list 
    And select "3" adults from list
    #And select "3" childrens from list
     
    And click on Apply button
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    And select anyone from list & click on View price
    Then system should open new tab
    
    And select room
    Then user should able to book hotel room
    And take screenshot named as "HotelBooking"
    And close the browser
    
    
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
     
    
