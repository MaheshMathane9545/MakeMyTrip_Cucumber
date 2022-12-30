
@tag
Feature: train ticket booking
=
  @tag1
  Scenario: user should able to bool train ticket 
  
  	Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on train
    Then system should display train homepage
    
    And select from city as "Hyderabad"
    And select to city as "Nanded"
    And departure date as "10-March-2023"
    And click on traveller & class
		And select "Sleeper Class" class from list
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    And select anyone from list & click on View price
    And select as "No" in get free cancellation
    And click on add new adult
    
    And send first name as "Mahesh" 
    And send age as "27" 
    And select gender as "Male" from list
    And select berth as "Upper" from list
    And click on add
    And send irctc id name as "dineshmaske143"
    And send mail as "Maheshmathane71@gmail.com" 
    And send mobile no as "9876546575"
    
    When click on proceed to pay
    Then system should display payment options page
    
    When click on googlepay
    And enter upi id as "abcd@upi"
    And click on verify and pay
    Then user should able to book pay
    And take screenshot named as "TrainTicketBooking"
   	And close the browser
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	