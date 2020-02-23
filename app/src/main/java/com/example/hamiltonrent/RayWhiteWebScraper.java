package com.example.hamiltonrent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class RayWhiteWebScraper extends WebSraperUsingJsoup{

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://rwhamilton.co.nz/properties/for-rent?category=&" +
            "keywords=&minBaths=0&minBeds=0&minCars=0&rentPrice=&sort=updatedAt+desc&suburbPostCode=";

    @Override
    public List<Property> getHamiltonRentResidentialData() {

        //A list to store data
        List<Property> data = new ArrayList<>();

        try {
            //Get the document from the specified url
            document = Jsoup.connect(url).userAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/33.0.1750.152 Safari/537.36")
                    .referrer("http://www.google.com").maxBodySize(0).get();

            //System.out.println(document.outerHtml());   //for testing

            //Variables to store the data of property
            String imageURL;
            String title;
            String address;
            String rent;
            String numBedroom;
            String numBathroom;
            String numCarSpace;
            String link;
            String agent = "RayWhite";

            //Loop through the elements
            for (Element e : document.select("div.proplist_item")) {
                //System.out.println(document.outerHtml());
                imageURL = e.select("img").attr("abs:src");
                title = e.select("h2 a").attr("data-ev-label");
                address = title;
                rent = e.select("div.proplist_item_header div.tbc span").text();
                rent = rent.replaceAll("[^\\d]", "");
                Elements temp = e.select("ul li");
                String[] rooms = temp.text().split(" ");
                if (rooms.length == 4){
                    numBedroom = rooms[0];
                    numBathroom = rooms[2];
                    numCarSpace = "0";
                }
                else if (rooms.length == 6){
                    numBedroom = rooms[0];
                    numBathroom = rooms[2];
                    numCarSpace = rooms[4];
                }
                else {
                    numBedroom = "-1";
                    numBathroom = "-1";
                    numCarSpace = "-1";
                }
                link = e.select("h2 a").attr("abs:href");

                //Add the data to the list
                Property property = new Property(imageURL, title, address, rent, numBedroom, numBathroom, numCarSpace, link, agent);
                data.add(property);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

//    public static void main(String[] args){
//        RayWhiteWebScraper rayWhiteWebScraper = new RayWhiteWebScraper();
//        List<Property> list = rayWhiteWebScraper.getHamiltonRentResidentialData();
////        List<Property> lowToHigh = lodgeWebScraperUsingJsoup.sortByRentLowToHigh(list);
////        List<Property> highToLow = lodgeWebScraperUsingJsoup.sortByRentHighToLow(list);
//        for (Property p : list){
//            System.out.println(p.getImageURL());
//            System.out.println(p.getTitle());
//            System.out.println(p.getAddress());
//            System.out.println(p.getRent());
//            System.out.println("Bedrooms: " + p.getNumBedroom() + "     Bathrooms: " + p.getNumBathroom() + "     Car space: " + p.getNumCarSpace());
//            System.out.println(p.getlink());
//            System.out.println("---------------");
//        }
//        System.out.println(list.size());
////        for (Property p : lowToHigh){
////            System.out.println(p.getTitle());
////            System.out.println(p.getAddress());
////            System.out.println(p.getRent());
////            System.out.println("Bedrooms: " + p.getNumBedroom() + "     Bathrooms: " + p.getNumBathroom() + "     Car space: " + p.getNumCarSpace());
////            System.out.println(p.getlink());
////            System.out.println("---------------");
////        }
////        for (Property p : highToLow){
////            System.out.println(p.getTitle());
////            System.out.println(p.getAddress());
////            System.out.println(p.getRent());
////            System.out.println("Bedrooms: " + p.getNumBedroom() + "     Bathrooms: " + p.getNumBathroom() + "     Car space: " + p.getNumCarSpace());
////            System.out.println(p.getlink());
////            System.out.println("---------------");
////        }
//    }
}
