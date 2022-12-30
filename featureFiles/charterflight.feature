
@tag
Feature: charterflight Booking
  

  @tag1
  Scenario: user should able to book charter flight
  	
  	Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on charterflight
    Then system should display charterflight homepage
    
    And select from city as "Mumbai"
    And select to city as "Hyderabad"
    And departure date as "10-May-2023"
    And pickup time as  as "10:00"
    And click on traveller
    And select "3" adults from list
    And click on Search button
    
    And send first name as "Ritesh" 
    And send mobile no as "9876543210"
    And send mail as "riteshpatil009988@gmail.com"
    And click on Get call back
    And take screenshot named as "charterflightBooking"
    And close the browser
    #When click on Go to homepage
   #	Then system should display homepage of site
   #	And take screenshot named as "charterflightBooking"
    #
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  