
@tag
Feature: Bus Ticket Booking
  

  @tag1
  Scenario: user should able to book bus ticket
  
  	Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on buses
    Then system should display buses homepage
    
    And select from city as "Hyderabad"
    And select to city as "Pune"
    And departure date as "10-January-2023"
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    Then close that pop-up
    And select anyone from list & click on View price
    And select any seat
    And  click on Book Now
    Then system should display complete your booking page
    
    And send first name as "Mahesh" 
    And send age as "27" 
    And gender as "Male" from list
    And send mobile no as "9876546575" 
     And send mail as "Maheshmathane71@gmail.com" 
    And select trip insurance as "No" 
    
    When click on proceed to pay
    Then system should display payment options page
    
    When click on googlepay
    And enter upi id as "abcd@upi"
    And click on verify and pay
    Then user should able to book pay
    And take screenshot named as "busbooking"
    And close the browser
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 