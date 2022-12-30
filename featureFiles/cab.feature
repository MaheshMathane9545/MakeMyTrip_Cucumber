
@tag
Feature: cab booking
  

  @tag1
  Scenario: user should able to book bus ticket
  	Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on cab
    Then system should display cab homepage
    
    And select from city as "Hyderabad"
    And select to city as "Amaravati"
    And departure date as "10-January-2023"
    And pickup time as  as "10:00 AM"
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    And  click on Book Now
		And system should display Traveller Details page
		
		And send pick-up addreess as "Railway Station"
		And send first name as "Mahesh"
		And select gender as "Male" from options
		And send mail as "Maheshmathane71@gmail.com"
		And send mobile no as "9876546575"
		
		When click on proceed to pay
    Then system should display payment options page
    
    When click on googlepay
    And enter upi id as "abcd@upi"
    And click on verify and pay
    Then user should able to book pay
    And take screenshot named as "cabbooking"
		And close the browser
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
    