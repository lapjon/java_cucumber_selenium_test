Feature: ITHS.se Homepage



#Test 1
  Scenario: Confirm website load and title
    Given I am on the iths.se homepage
    Then the page title should be "IT-Högskolan – Här startar din IT-karriär!"


#Test 2
  Scenario: Navigate to Stockholm page
    Given I navigate to the Stockholm page
    When I click on the Stockholm link
    Then the Stockholm page URL should contain "iths.se/kursort/stockholm"

#Test 3
  Scenario: Navigate to Ubildningar page
    Given I am on iths main page
    When I click on the Utbildningar link
    Then the Utbildningar page URL should contain "iths.se/utbildningar"

#Test 4
  Scenario: Verify number of links on main menu page
    Given I am navigating main page
    When I maximize window
    Then the main menu should contain 9 links

    #Test 5
Scenario: Navigate to Viktiga Datum page
  Given I am on the iths.se homepage with maximized window
   When I hover over "Hur Du Ansöker"
  And I click on "Viktiga Datum"
 Then the Viktiga Datum page URL should contain "viktiga-datum"
  And the page title should contain "Viktiga datum"

#Test 6

  Scenario: Verify email address in footer
    Given I am on the iths.se homepage
    When I scroll down to the page footer
    Then the email address should be "info@iths.se"

#Test 7
  Scenario: Verify email address in footer

    Given I am on the iths.se homepage
    When I locate the contact section in the page footer
    Then the phone number should be "031-790 42 55"

    #Test 8
  Scenario: Navigate to Kontakt page and verify Stockholm contact info
    Given I am navigating iths.se homepage with maximized window
    When I click on the Kontakt link
    Then the Stockholm contact information should be:
      """
      IT-Högskolan Stockholm är beläget ett stenkast från Liljeholmens tunnelbanestation.

      Telefon:
      08-557 683 53

      Besöksadress:
      Trekantsvägen 1
      117 43 Stockholm
      """

    #Test 9
 #Scenario: Navigate to Programmering course page
  #Given I am on the iths.se homepage with maximized window
  #When I hover over "Utbildningar"
  #And I click on "Programmering"
   #Then the Programmering page URL should contain "/kursort/betalutbildning/"
   #And the course page title should be "Programmering – Utbildning distans | IT-Högskolan"

  Scenario: Verify Programmering course page content
    Given I am navigating main page with maximized window
    When I hover over the "Utbildningar" menu and click on the "Programmering" link
    Then the Programmering page URL should contain "/kursort/betalutbildning/"
    And the "Programmering grundkurs" link should be visible
    When I click on the "Programmering grundkurs" link
    Then I should be taken to the Programmering course page with URL "https://www.iths.se/utbildningar/programmering-grundkurs/"
    And the page title should be "Programmering – Utbildning distans | IT-Högskolan"




  #Test 10
  Scenario: Verify hamburger menu on mobile
    Given I have resized the window to iPhone dimensions
    When I click on the hamburger menu
    Then the mobile menu should be visible