package com.example.hamiltonrent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class LodgeWebScraperUsingJsoup  extends WebSraperUsingJsoup{

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://www.lodge.co.nz/Browse-Properties?propertyType=Rental&page=1&r=948";

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

            while (true) {
                //Loop through the elements
                for (Element e : document.select("li.listing.clearfix")) {
                    //System.out.println(document.outerHtml());
                    imageURL = e.select("img").attr("abs:src");
                    title = e.select("h2 a").attr("title");
                    address = title;
                    rent = e.select("div.listingprice").text();
                    rent = rent.replaceAll("[^\\d]", "");
                    String temp = e.select("span.item").text();
                    //Format in String[]: [0]: bedroom, [1]: bathroom, [2]: car space (may be empty)
                    String[] rooms = temp.split(" ");
                    numBedroom = rooms[0];
                    numBathroom = rooms[1];
                    if (rooms.length == 3){
                        numCarSpace = rooms[2];
                    }
                    else{
                        numCarSpace = "0";
                    }
                    link = e.select("h2 a").attr("abs:href");

                    //Add the data to the list
                    Property property = new Property(imageURL, title, address, rent, numBedroom, numBathroom, numCarSpace, link);
                    data.add(property);
                }
                Element nextPage = document.select("div.searchbar.clearfix a.nextlink").first();
                if (nextPage != null){
                    //Get the url of next page
                    String nextURL = nextPage.attr("abs:href");
                    //Navigate to the next page
                    document = Jsoup.connect(nextURL).userAgent(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Chrome/33.0.1750.152 Safari/537.36")
                            .referrer("http://www.google.com").maxBodySize(0).get();
                }
                else{
                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

//    public static void main(String[] args){
//        LodgeWebScraperUsingJsoup lodgeWebScraperUsingJsoup = new LodgeWebScraperUsingJsoup();
//        List<Property> list = lodgeWebScraperUsingJsoup.getHamiltonRentResidentialData();
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
