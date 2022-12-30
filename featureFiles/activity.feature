
@tag
Feature: Activity's booking

  @tag1
  Scenario: user should book packages from avticity
  
    Given open chrome browser with "https://www.makemytrip.com/" url
    Then system should display homepage of site
    
    When click on Activities
    Then system should display Activities homepage
    
    And enter destinstion as "Dubai"
    
    When click on Search button
    Then system should display list of filghtes /hotels/homestays/ trains/ Buses /Cabs
    
    When Click on any "sightseeing"
    #When Click on any "popular"
    Then System should display list of activity packs
    
    When Click on any pack 
    Then system should open new tab
    
    And add "3" person 
    When click on Book Now
    Then system should display Traveller Details page 
    
    And send first name as "Mahesh" 
    And send last name as "Mathane" 
    And send mail as "Maheshmathane71@gmail.com"
    And send mobile no as "9876546575" 
    
    When click on proceed to pay
    Then system should display one pop-up
    
    And select "living locally" as Hotel pickup details 
    And click on procced 
    
    
    When click on googlepay
    And enter upi id as "abcd@upi"
    And click on verify and pay
    Then user should able to book pay
    And take screenshot named as "activity"
     
    And close the browser
      
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    