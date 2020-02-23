package com.example.hamiltonrent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WaikatoREWebScraper extends WebSraperUsingJsoup{
    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://www.wre.co.nz/shop/Property+Listings/x_cat/00319.html";

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
            //Variables to store the data of property
            String imageURL;
            String title;
            String address;
            String rent;
            String numBedroom;
            String numBathroom;
            String numCarSpace;
            String link;
            String agent = "Waikato Real Estate";

            int currentPage = 1;
            // Find the number of pages
            int numPages = findNumberOfPages(url);

            for (int i = currentPage; i <= numPages; i++) {
                //Loop through the elements
                for (Element e : document.select("article.product-card")) {
                    imageURL = e.select("img").attr("abs:src");
                    title = e.select("h4").text();
                    link = e.select("h4 a").attr("abs:href");
                    rent = e.select("span").text();
                    String[] rents = rent.split(" ");
                    rent = rents[0];
                    rent = rent.replaceAll("\\$", "");
                    String temp = e.select("ul li").text();
                    //Format in String[]: [0]: bedroom, [1]: bathroom, [2]: car space
                    String[] rooms = temp.split(" ");
                    numBedroom = rooms[0];
                    numBathroom = rooms[1];
                    numCarSpace = rooms[2];

                    // Have to look up the address by using the link
                    Document tempDoc = Jsoup.connect(link).userAgent(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Chrome/33.0.1750.152 Safari/537.36")
                            .referrer("http://www.google.com")
                            .maxBodySize(0).get();
                    Element elTemp = tempDoc.select("article.summary").first();
                    address = elTemp.select("p.description").text();

                    //Add the data to the list
                    Property property = new Property(imageURL, title, address, rent, numBedroom, numBathroom, numCarSpace, link, agent);
                    data.add(property);
                }
                //Proceed to the next page
                currentPage++;
                //Send a post form to the website so that we can visit the next page
                Connection.Response res = Jsoup.connect(url)
                        .data("cat_code", "00319", "path", "133", "page", ""+currentPage, "cid", "61")
                        .method(Connection.Method.POST)
                        .execute();
                document = res.parse();
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
            List<Element> elements = document.select("aside.load-page.drop-select ul li");
            num = elements.size();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

//    public static void main(String[] args){
//        WaikatoREWebScraper waikatoREWebScraperUsingJsoup = new WaikatoREWebScraper();
//        List<Property> list = waikatoREWebScraperUsingJsoup.getHamiltonRentResidentialData();
////        List<Property> lowToHigh = waikatoREWebScraperUsingJsoup.sortByRentLowToHigh(list);
////        List<Property> highToLow = waikatoREWebScraperUsingJsoup.sortByRentHighToLow(list);
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
//    }
}
