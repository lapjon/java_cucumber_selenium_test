
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
import io.cucumber.java.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinition {

    static WebDriver driver;
    private WebElement stockholmCta;
    private String emailAddress;
    private String phoneNumber;
    private WebDriverWait wait;

    @Before
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origin=*");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.get("https://www.iths.se");

    }
    // Eftersom jag fick stora problem med att cookie-consent störde övriga tester så valde jag att lägga den så den körs inför varje testkörning.
    public void closeCookie() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Väntar i 5 sekunder
        try {
            // Väntar på att cookie-knappen ska visas
            WebElement cookieButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Cookie dialog not found or already closed.");
        }

    }

@DisplayName("Scenario 1")
@Given("I am on the iths.se homepage")
    public void ithsIsAvailable(){
    //driver.get("https://www.iths.se/");
    assertEquals("IT-Högskolan – Här startar din IT-karriär!", driver.getTitle());
}
@Then("the page title should be {string}")
        public void checkWebsiteTitle(String expectedTile){
    String websiteTitle = driver.getTitle();
    System.out.println("titeln på ITHS.se: " + websiteTitle);
    assertEquals(expectedTile, websiteTitle);
        }

@DisplayName("Scenario 2")
@Given("I navigate to the Stockholm page")
public void stockholmPageIsAvailable() throws InterruptedException{
    // Behöver stänga cookie-fönstret här annars störs efterföljande tester
        closeCookie();
        stockholmCta = driver.findElement(By.cssSelector("a.btn.btn--dark[href='https://www.iths.se/kursort/stockholm/']"));
    }

@When("I click on the Stockholm link")
public void clickStockholmLink () {
    stockholmCta.click();

    }
@Then("the Stockholm page URL should contain {string}")
public void stockholmPageUrlShouldContain(String string) {
    String currentUrl = driver.getCurrentUrl();
    // Verfifierar att URL har ändrats till "iths.se/utbildningar"
    assertTrue(currentUrl.contains("iths.se/kursort/stockholm"), "The URL has changed to the Stockholm page");
    // String stockholmUrl = driver.getTitle("IT-utbildningar Stockholm");
    }


@DisplayName("Scenario 3")
@Given("I am on iths main page")
public void ithsMainPage() throws InterruptedException {
    driver.get("https://www.iths.se");
    closeCookie();

    }
@When("I click on the Utbildningar link")
public void clickUtbildningarLink() {
    WebElement allaUtbildningarCta = driver.findElement(By.cssSelector("a.btn.btn--dark[href='https://www.iths.se/utbildningar/']"));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    // Klickar på länken till iths.se/utbildningar
    allaUtbildningarCta.click();
    }

@Then("the Utbildningar page URL should contain {string}")
public void urlShouldChangeToUtbildningar(String string) {
    String currentUrl = driver.getCurrentUrl();
    // Verfifierar att URL har ändrats till "iths.se/utbildningar"
    assertTrue(currentUrl.contains("iths.se/utbildningar"), "The URL has changed to the 'Alla Utbildningar' page");

    }

@DisplayName("Scenario 4")
@Given("I am navigating main page")
public void MainPageNav() throws InterruptedException{
    closeCookie();
    driver.get("https://www.iths.se");

    }
@When("I maximize window")
public void maximizeWindow() {
    driver.manage().window().maximize();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement navBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-primary-navigation")));
    }
@Then("the main menu should contain {int} links")
public void mainMenuShouldContainLinks(Integer int1) {
    List <WebElement> menuItems = driver.findElements(By.cssSelector("ul#menu-primary-navigation > li > a"));
    // Verififerar att det är 9 länkelement i huvudmenyn
    assertEquals(9, menuItems.size(), "There are 9 links in the main nav bar");

    for (WebElement link: menuItems) {
        System.out.println(link.getText());
    }
}

@DisplayName("Scenario 5")
@Given("I am on the iths.se homepage with maximized window")
public void ithsHomepageWithMaximizedWindow() throws InterruptedException {
    driver.get("https://www.iths.se");
    driver.manage().window().maximize();
    closeCookie();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement navBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-primary-navigation")));
    }

@When("I hover over {string}")
public void hoverOverString(String string) {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions hoover = new Actions(driver);
    // Använder id som locator för hitta menyn och väntar in elementet. Använder id som locator för att hitta menyn
    WebElement menuItemAnsokDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-hurduansker")));
    hoover.moveToElement(menuItemAnsokDropdown).perform();
    }

@When("I click on {string}")
public void clickOnString(String string) {
    // Använder id som locator för hitta menyn och väntar in länken. Använder id som locator för att hitta menyobjektet
    WebElement viktigaDatumLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-viktigadatum")));
    viktigaDatumLink.click();
    }

@Then("the Viktiga Datum page URL should contain {string}")
public void viktigaDatumUrlShouldContain(String expectedUrlFragment) {
// Väntar på att URL ändras till iths.se/viktiga-datum
    wait.until(ExpectedConditions.urlContains("viktiga-datum"));

    // Verifierar att URL har ändrats till iths.se/viktiga-datum
    String currentUrl = driver.getCurrentUrl();
    assertTrue(currentUrl.contains(expectedUrlFragment), "URL should contain 'viktiga-datum'" + expectedUrlFragment);

}
@And("the page title should contain {string}")
public void viktigaDatumPageTitleShouldContain(String string) {

    // Verifierar att sidan innehåller titeln Viktiga datum
    String pageTitle = driver.getTitle();
    assertTrue(pageTitle.contains("Viktiga datum"), "Page title should contain 'Viktiga datum'");
   System.out.println("Page Title: " + pageTitle);
    }

@DisplayName("Scenario 6")
@Given("I am on the iths.se main page")
public void ithsFrontPage()  {
        driver.get("https://www.iths.se/");

    }

@When("I scroll down to the page footer")
public void ithsMainPageFooter() throws InterruptedException{
    closeCookie();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'mailto:')]")));
    emailAddress = footer.getText();
    }

@Then("the email address should be {string}")
public void emailAddressShouldBe(String expectedEmail) {
    assertEquals(expectedEmail, emailAddress);
    System.out.println("Found email address: " + emailAddress);
    }

@DisplayName("Scenario 7")
@Given("I open iths.se main page")
public void ithsFrontPageFooter()  {
        driver.get("https://www.iths.se/");
    }

@When("I locate the contact section in the page footer")
public void locateContactSectionInPageFooter() throws InterruptedException {
    closeCookie();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement footerPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'callto:')]")));
    phoneNumber = footerPhone.getText();
    }

@Then("the phone number should be {string}")
public void phoneNumberShouldBe(String expectedPhoneNumber) {
    assertEquals(expectedPhoneNumber, phoneNumber);
    System.out.println("Found phoneNumber: " + phoneNumber);
    }

@DisplayName("Scenario 8")
@Given("I am navigating iths.se homepage with maximized window")
public void navigatingHomepageWithMaximizedWindow() throws InterruptedException {
    driver.get("https://www.iths.se");
    driver.manage().window().maximize();
    closeCookie();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

@When("I click on the Kontakt link")
public void clickOnKontaktLink() {
    Actions hoover = new Actions(driver);
    WebElement contactPageCta = driver.findElement(By.xpath("//*[@id=\"nav-kontakt\"]/a"));
    contactPageCta.click();
    }

@Then("the Stockholm contact information should be:")
public void stockholmContactInformationShouldBe(String docString) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement stockholmContactInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-intro--sthlm > div.text-container > div > p")));

    String stockholmContactInfo = stockholmContactInfoElement.getText();
    String expectedContactInfo = "IT-Högskolan Stockholm är beläget ett stenkast från Liljeholmens tunnelbanestation.\n\nTelefon:\n08-557 683 53\n\nBesöksadress:\nTrekantsvägen 1\n117 43 Stockholm";
    assertEquals(expectedContactInfo, stockholmContactInfo);
    System.out.println(stockholmContactInfo);
    }

@DisplayName("Scenario 9")
@Given("I am navigating main page with maximized window")
public void navigatingPageWithMaximizedWindow() throws InterruptedException {
        driver.manage().window().maximize();
        closeCookie();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
@When("I hover over the {string} menu and click on the {string} link")
public void iHoverOverTheMenuAndIOnTheLink(String arg0, String arg1) {
        Actions hover = new Actions(driver);
        WebElement menuUtbildningarDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-utbildningar")));
        hover.moveToElement(menuUtbildningarDropdown).perform();
        WebElement programmeringLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-programmering")));
        programmeringLink.click();
    }
@Then("the Programmering page URL should contain {string}")
public void programmeringPageUrlShouldContain(String string) {
        wait.until(ExpectedConditions.urlContains("/kursort/betalutbildning/"));
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Navigated to: " + currentUrl);
    }
@And("the {string} link should be visible")
public void linkShouldBeVisible(String courseLink) {
        WebElement courses = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-filter-results-6595\"]/div")));
        WebElement programmeringGrundkursLink = courses.findElement(By.linkText("Programmering grundkurs"));

    }

@When("I click on the {string} link")
public void iClickOnTheLink(String arg0) {
        WebElement courses = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-filter-results-6595\"]/div")));
        WebElement programmeringGrundkursLink = courses.findElement(By.linkText("Programmering grundkurs"));
        programmeringGrundkursLink.click();
    }
@Then("I should be taken to the Programmering course page with URL {string}")
public void i_should_be_navigated_to_the_programmering_course_page_with_url(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, currentUrl, "Expected URL to be '" + expectedUrl + "' but was: " + currentUrl);
    }
@And("the page title should be visible")
public void pageTitleShouldBe(String expectedTitle) {
        String pageTitle = driver.getTitle();
        assertEquals(expectedTitle, pageTitle, "Expected page title to be '" + expectedTitle + "' but was: " + pageTitle);
    }


@DisplayName("Scenario 10")
@Given("I have resized the window to iPhone dimensions")
public void i_have_resized_the_window_to_i_phone_pro_max_dimensions() throws InterruptedException {
    //driver.get("https://www.iths.se");
    closeCookie();
    driver.manage().window().setSize(new Dimension(430, 932));
    Thread.sleep(2000);
    }
@When("I click on the hamburger menu")
public void clickOnHamburgerMenu() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement hamburgerMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mobile-toggle\"]/i")));
    hamburgerMenu.click();
    }
@Then("the mobile menu should be visible")
public void mobileMenuShouldBeVisible() {

    Dimension windowSize = driver.manage().window().getSize();
    System.out.println("Current window size: " + windowSize);

    }


@After
public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }



}