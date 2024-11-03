
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.locators.RelativeLocator.with;



public class SeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    static void setuo(){
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origin=*");
        option.addArguments("incognito");
        driver = new ChromeDriver(option);
        //option.addArguments("--disable-popup-blocking");
        option.addArguments("--disable-notifications");
        //System.setProperty("webdriver.chrome.driver",
             //   "/Users/jon/Desktop/chromedriver-mac-x64/chromedriver");
    }
    // Eftersom jag fick stora problem med att cookie-consent störde övriga tester så valde jag att lägga den så den körs inför varje testkörning.
    public void closeCookie() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Väntar i 5 sekunder
        try {
            // Väntar på att cookie-knappen ska visas
            WebElement cookieButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Cookie dialog not found or already closed.");
        }
    }

    @BeforeEach
    public void navigate(){
        driver.get("https://www.iths.se");
    }

    @Test
    @DisplayName("Test 1 - Confirm website load and title")
    public void checkWebsiteTitle(){
        String websiteTitle = driver.getTitle();
       //System.out.println("titeln på ITHS.se: " + websiteTitle);
        assertEquals("IT-Högskolan – Här startar din IT-karriär!", websiteTitle);
    }

    @Test
    @DisplayName("Test 3 - Click link - Stockholm")
    public void clickStockholmButton() throws InterruptedException {
        closeCookie();
        WebElement stockholmCta = driver.findElement(By.cssSelector("a.btn.btn--dark[href='https://www.iths.se/kursort/stockholm/']"));
        stockholmCta.click();
        // Använder CSS selector som Locator
        String currentUrl = driver.getCurrentUrl();
        // Verfifierar att URL har ändrats till "iths.se/utbildningar"
        assertTrue(currentUrl.contains("iths.se/kursort/stockholm"), "The URL has changed to the Stockholm page");
       // String stockholmUrl = driver.getTitle("IT-utbildningar Stockholm");
    }

    @Test
    @DisplayName("Test 2 - Click link - Alla Utbildningar")
    public void clickUtbildnigarButton() throws InterruptedException {

        closeCookie();
        WebElement allaUtbildningarCta = driver.findElement(By.cssSelector("a.btn.btn--dark[href='https://www.iths.se/utbildningar/']"));
        // Klickar på länken till iths.se/utbildningar
        allaUtbildningarCta.click();
        // Använder CSS selector som Locator
        String currentUrl = driver.getCurrentUrl();
        // Verfifierar att URL har ändrats till "iths.se/utbildningar"
        assertTrue(currentUrl.contains("iths.se/utbildningar"), "The URL has changed to the 'Alla Utbildningar' page");

    }

    @Test
    @DisplayName("Test 4 - Check number of links in main menu")
    public void checkNumberOfMenuLinks() throws InterruptedException {
        // För att se alla länkar måste jag först maximera fönstret
        driver.manage().window().maximize();
        // Väntar 5 sekunder för att säkertställa att menyn laddas
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement navBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-primary-navigation"))); // Adjust locator if necessary
        // Listar alla element i huvumenyn som ligger under "menu-primary-navigation"
        List <WebElement> menuItems = driver.findElements(By.cssSelector("ul#menu-primary-navigation > li > a"));
        // Verififerar att det är 9 länkelement i huvudmenyn
        assertEquals(9, menuItems.size(), "There are 9 links in the main nav bar");

        for (WebElement link: menuItems) {
            System.out.println(link.getText());
        }
    }

    @Test
    @DisplayName("Test 5 - Verify dropdown, navigate to Viktiga datum and verify page title")
    public void navigateToViktigaDatum() throws InterruptedException{
        // För att länken ska bli synlig måste jag först maximera fönstret
        driver.manage().window().maximize();
        // Vänta på att dropdown ska bli synlig
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Skapar en actions för att hoovra musen över Hur Du Ansöker
        Actions hoover = new Actions(driver);
        // Använder id som locator för hitta menyn och väntar in elementet. Använder id som locator för att hitta menyn
        WebElement menuItemAnsokDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-hurduansker")));
        hoover.moveToElement(menuItemAnsokDropdown).perform();

        // Använder id som locator för hitta menyn och väntar in länken. Använder id som locator för att hitta menyobjektet
        WebElement viktigaDatumLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-viktigadatum")));

        viktigaDatumLink.click();

        // Väntar på att URL ändras till iths.se/viktiga-datum
        wait.until(ExpectedConditions.urlContains("viktiga-datum"));

        // Verifierar att URL har ändrats till iths.se/viktiga-datum
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("viktiga-datum"), "URL should contain 'viktiga-datum'");

        // Verifierar att sidan innehåller titeln Viktiga datum
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("Viktiga datum"), "Page title should contain 'Viktiga datum'");

        System.out.println("Navigated to: " + currentUrl);
        System.out.println("Page Title: " + pageTitle);
    }

    @Test
    @DisplayName("Test 6 - Verify iths email")
    public void checkEmailAddress (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //Använder Xpath som locator för footer container
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'mailto:')]")));
        String emailAddress = footer.getText();
        String expectedEmail = "info@iths.se";
        assertEquals(expectedEmail, emailAddress);
        System.out.println(emailAddress);

    }

    @Test
    @DisplayName("Test 7 - Verify phone number to Göteborg")
    public void checkPhoneNumber (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //Använder Xpath som locator för footer container
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'callto:')]")));
        String phoneNumber = footer.getText();
        String expectedPhoneNumber = "031-790 42 55";
        assertEquals(expectedPhoneNumber, phoneNumber);
        System.out.println(phoneNumber);

    }

    @Test
    @DisplayName("Test 8 - Navigate to Kontakt page and verify Stockholm contact info")
    public void navigateToContactPage() throws InterruptedException {
        // För att länken ska bli synlig måste jag först maximera fönstret
        driver.manage().window().maximize();
        // Vänta på att dropdown ska bli synlig
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Skapar en actions för att hoovra musen över Kontakt
        Actions hoover = new Actions(driver);
        // Använder xpath som locator för hitta Kontakt-sidan i menyn och klickar på länken
        WebElement contactPageCta = driver.findElement(By.xpath("//*[@id=\"nav-kontakt\"]/a"));
        contactPageCta.click();

        // Väntar på att sidan ska ladda och använder CSS Selector som locator
        WebElement stockholmContactInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-intro--sthlm > div.text-container > div > p")));

        String stockholmContactInfo = stockholmContactInfoElement.getText();
        // Adressen och den övriga texten ligger i samma <p> så jag var osäker på hur jag kunde få fram endast adressen. Istället fick jag verifiera all text i rutan Kontakt Stockholm
        String expectedContactInfo = "IT-Högskolan Stockholm är beläget ett stenkast från Liljeholmens tunnelbanestation.\n\nTelefon:\n08-557 683 53\n\nBesöksadress:\nTrekantsvägen 1\n117 43 Stockholm";
        assertEquals(expectedContactInfo, stockholmContactInfo);
        System.out.println(stockholmContactInfo);

    }

    @Test
    @DisplayName("Test 9 - Navigate to Utbildningar > Programmering > Coursepage")
    public void verifyCourseContent() throws InterruptedException {
        // För att länken ska bli synlig måste jag först maximera fönstret
        driver.manage().window().maximize();
        // Vänta på att dropdown ska bli synlig
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Skapar en actions för att hoovra musen över Utbildningar
        Actions hoover = new Actions(driver);
        // Använder id som locator för hitta menyn och väntar in elementet. Använder id som locator för att hitta menyn
        WebElement menuUtbildningarDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-utbildningar")));
        hoover.moveToElement(menuUtbildningarDropdown).perform();

        // Använder id som locator för hitta menyn och väntar in länken. Använder id som locator för att hitta menyobjektet
        WebElement programmeringLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-programmering")));

        programmeringLink.click();

        // Väntar på att URL ändras till iths.se/kursort/betalutbildning
        wait.until(ExpectedConditions.urlContains("/kursort/betalutbildning/"));

        // Verifierar att URL har ändrats till iths.se/viktiga-datum
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/kursort/betalutbildning/"), "URL should contain 'kursort/betalutbildning'");

        System.out.println("Navigated to: " + currentUrl);

        // Väntar på att kurserna på sidan är synliga
        WebElement courses = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-filter-results-6595\"]/div")));
        // Väljer Programmering grundkurs
        WebElement programmeringGrundkursLink = courses.findElement(By.linkText("Programmering grundkurs"));

        programmeringGrundkursLink.click();
        // Verifierar att jag kommit till kurssidan
        String expectedUrl = "https://www.iths.se/utbildningar/programmering-grundkurs/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        System.out.println("Navigated to: " + expectedUrl);

        //Verifierar titeln på sidan
        String pageTitle = driver.getTitle();
        assertEquals("Programmering – Utbildning distans | IT-Högskolan", pageTitle, "Page title should be 'Programmering – Utbildning distans | IT-Högskolan'");

    }

    @Test
    @DisplayName("Test 10 - Test hamburger menu with iPhone 14 Pro Max dimensions")
    public void testMobileResponsiveness(){
        // Ändrar skärmstorlek till iPhone 14 Pro Max
        driver.manage().window().setSize(new Dimension(430, 932));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Använder xpath som selector-metod
        WebElement hamburgerMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mobile-toggle\"]")));
        hamburgerMenu.click();
    }

    @AfterAll
    static void teardown(){
        driver.quit();
    }

}
