package com.example.hamiltonrent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HarcourtsWebscraperUsingJsoup extends WebSraperUsingJsoup{

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://harcourts.co.nz/Property/Rentals?location=25015&minbed=1&data=%7B%22region%22%3A22003%2C%22city%22%3A25015%7D";

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

            //Find the number of pages that it has
            int numOfPages = findNumberOfPages(url);
            for (int i = 1; i <= numOfPages; i++) {
                //Loop through the elements
                for (Element e : document.select("div.search-item-container")) {
                    imageURL = e.select("div.swiper-lazy").attr("data-background");
                    title = e.select("a").text();
                    address = e.select("address").text();
                    rent = e.select("p span").text();
                    rent = rent.replaceAll("[^\\d]", "");
                    String temp = e.select("span.hc-text").text();
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
                Element nextPage = document.select("div.next-pagination a").first();
                //Get the url of next page
                String nextURL = nextPage.attr("abs:href");
                //Navigate to the next page
                document = Jsoup.connect(nextURL).userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/33.0.1750.152 Safari/537.36")
                        .referrer("http://www.google.com").maxBodySize(0).get();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public int findNumberOfPages(String url){
        int num = 0;
        try {
            document = Jsoup.connect(url).userAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .referrer("http://www.google.com").maxBodySize(0).get();
            List<Element> elements = document.select("div.page-list a");
            for (Element e : elements){
                num = Integer.parseInt(e.text());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }



//    public static void main(String[] args){
//        HarcourtsWebscraperUsingJsoup harcourtsWebscraperUsingJsoup = new HarcourtsWebscraperUsingJsoup();
//        List<Property> list = harcourtsWebscraperUsingJsoup.getHamiltonRentResidentialData();
////        List<Property> lowToHigh = harcourtsWebscraperUsingJsoup.sortByRentLowToHigh(list);
////        List<Property> highToLow = harcourtsWebscraperUsingJsoup.sortByRentHighToLow(list);
//        for (Property p : list){
//            System.out.println(p.getImageURL());
//            System.out.println(p.getTitle());
//            System.out.println(p.getAddress());
//            System.out.println(p.getRent());
//            System.out.println("Bedrooms: " + p.getNumBedroom() + "     Bathrooms: " + p.getNumBathroom() + "     Car space: " + p.getNumCarSpace());
//            System.out.println(p.getlink());
//            System.out.println("---------------");
//        }
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
