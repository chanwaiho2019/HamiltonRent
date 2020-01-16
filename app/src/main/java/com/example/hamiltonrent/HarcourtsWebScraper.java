package com.example.hamiltonrent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HarcourtsWebScraper {

    //The web driver used for scraping
    public static WebDriver driver = null;

    public List<String[]> getHamiltonRentResidentialData() {
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
        driver.get("https://harcourts.co.nz/Property/Rentals?location=25015&data=%7B%22region%22%3A22003%2C%22city%22%3A25015%7D");

        //Store the data in variables (Rent - Residential)
        String title;
        String address;
        String cost;
        String link;
        List<String[]> list = new ArrayList<>();


        //Locate web elements
        List<WebElement> webElements = driver.findElements(By.cssSelector("div.search-item-content"));
        for (WebElement we : webElements){
            title = we.findElement(By.cssSelector("a")).getText();
            address = we.findElement(By.cssSelector("address")).getText();
            cost = we.findElement(By.cssSelector("p span")).getText();
            link = we.findElement(By.cssSelector("a")).getAttribute("href");
            list.add(new String[]{title, address, cost, link});
            //list.add(title + " " + address + " " + cost + " " + link);
        }
        return list;
    }

    public static void main(String[] args) {
        HarcourtsWebScraper lb = new HarcourtsWebScraper();
        List<String[]> list = lb.getHamiltonRentResidentialData();
        for (String[] s : list){
            System.out.println(s[0]);
            System.out.println(s[1]);
            System.out.println(s[2]);
            System.out.println(s[3]);
            System.out.println("---------------");
        }
    }
}
