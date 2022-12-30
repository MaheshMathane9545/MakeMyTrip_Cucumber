
@tag
Feature: Flight Ticket Booking
  
  @tag1
  Scenario: user sholud able to book flight ticket
  
    Given open chrome browser with "https://www.makemytrip.com" url
    Then system should display homepage of site

    Then select "oneway" trip
    And select from city as "Mumbai"
    And select to city as "Kolkata"
    And departure date as "10-May-2023"
    And click on traveller & class 
    And select "1" adults from list 
    And select "0" childrens from list 
    And select "0" infants from list 
    And select "Business" class from list 
    
    And click on Apply button
    
    When click on Search button
    Then system should display one pop-up
    Then close that pop-up
    
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    And select anyone from list & click on View price
    And  click on Book Now
    Then system should open new tab
    And system should display Traveller Details page
    Then scroll upto "Traveller Details"
    And click on add new adult
    
    And send first name as "Mahesh" 
    And send last name as "Mathane" 
    And gender as "Male"
    
    Then scroll upto "Seats & Meals"
    And send mobile no as "9876546575" 
    And send mail as "Maheshmathane71@gmail.com" 
    When click on continue
    Then system should display one pop-up
    And click on confirm
    And  click on yes,please
    
    Then scroll upto "Skip to add-ons"
    When click on skip add on
    Then system should display complete your booking page
    
    When click on proceed to pay
    Then system should display payment options page
    
    When click on googlepay
    And enter upi id as "abcd@upi"
    And click on verify and pay
    Then user should able to book pay
    And take screenshot named as "flight"
    And close the browser
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    