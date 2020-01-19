package com.example.hamiltonrent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HarcourtsWebScraper {

    //The URL of Harcourts (Rent - Residential)
    public static final String url = "https://harcourts.co.nz/Property/Rentals?location=25015&minbed=1&data=%7B%22region%22%3A22003%2C%22city%22%3A25015%7D";

    //The web driver used for scraping
    public static WebDriver driver = null;

    /**
     * A method to get all the residential properties for rent from Harcourts website
     * @return A list of Property objects.
     */
    public List<Property> getHamiltonRentResidentialData() {
        //Allow access the memory location of chrome driver
        System.setProperty("webdriver.chrome.driver", "app/libs/chromedriver");

        //Set the chrome browser to headless
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        //Launch the chrome browser
        driver = new ChromeDriver(options);
        //Give it 15 seconds to load
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        //Connect to the url
        driver.get(url);
        //Find the number of pages that it has
        int numOfPages = findNumberOfPages(url);
        //System.out.println(numOfPages);

        //Store the data in variables (Rent - Residential)
        String title;
        String address;
        String rent;
        String numOfBedroom;
        String link;
        List<Property> list = new ArrayList<>();

        for (int i = 1; i <= numOfPages; i++){
            //Locate web elements
            List<WebElement> webElements = driver.findElements(By.cssSelector("div.search-item-content"));
            for (WebElement we : webElements){
                title = we.findElement(By.cssSelector("a")).getText();
                address = we.findElement(By.cssSelector("address")).getText();
                rent = we.findElement(By.cssSelector("p span")).getText();
                rent = rent.replaceAll("[^\\d]", "");
                numOfBedroom = we.findElement(By.cssSelector("span.hc-text")).getText();
                link = we.findElement(By.cssSelector("a")).getAttribute("href");
                //Create a Property object and add to the list
//                Property property = new Property(title, address, rent, numOfBedroom, link);
//                list.add(property);
            }
            //Locate the web element for the url of next page
            WebElement nextPage = driver.findElement(By.cssSelector("div.next-pagination a"));
            //Get the url of next page
            String nextURL = nextPage.getAttribute("href");
            //Navigate to the next page
            driver.get(nextURL);
        }
        //System.out.println(list.size());    //Print out the number of search results
        return list;
    }

    /**
     * A method to find the total number of pages for result
     * @param url The url to check
     * @return The total number of pages
     */
    public static int findNumberOfPages(String url){
        //Navigate to the original url
        driver.get(url);
        int num = 0;
        List<WebElement> webElements = driver.findElements(By.cssSelector("div.page-list a"));
        for (WebElement we : webElements){
            try{
                num = Integer.parseInt(we.getText());
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return num;
    }

    /**
     * Get the list of properties by specified number of bedrooms
     * @param list The full list of properties
     * @param bedroomNum The number of bedrooms that you want to specify
     * @return The list of properties with the number of bedrooms that you specified
     */
    public List<Property> getByBedroomNum(List<Property> list, int bedroomNum){
        List<Property> temp = new ArrayList<>();
        for (Property property : list){
            if (property.getNumBedroom() == bedroomNum){
                temp.add(property);
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        HarcourtsWebScraper lb = new HarcourtsWebScraper();
        List<Property> list = lb.getHamiltonRentResidentialData();
        for (Property p : list){
            System.out.println(p.getTitle());
            System.out.println(p.getAddress());
            System.out.println(p.getRent());
            System.out.println("Number of bedrooms: " + p.getNumBedroom());
            System.out.println(p.getlink());
            System.out.println("---------------");
        }

    }
}
