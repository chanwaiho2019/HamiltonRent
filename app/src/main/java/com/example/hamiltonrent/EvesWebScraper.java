package com.example.hamiltonrent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class EvesWebScraper extends WebSraperUsingJsoup {

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://www.evesrentals.co.nz/search/district/Hamilton/60/?";

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

            int currentPage = 1;
            while (true) {
                //Loop through the elements
                for (Element e : document.select("div.col-xs-12.col-sm-6.col-md-4.listing.listing-grid")) {
                    imageURL = e.select("div").attr("style");
                    imageURL = imageURL.substring( imageURL.indexOf("https://"), imageURL.indexOf("')"));
                    //title = e.select("div.listing-heading").text();
                    //address = e.select("p span").text() + " " + e.select("p b").text() + " Hamilton";
                    link = e.select("a").attr("abs:href");
                    rent = e.select("div.listing-price").text();
                    rent = rent.replaceAll("[^\\d]", "");
                    String temp = e.select("ul li").text();
                    //Format in String[]: [0]: bedroom, [1]: bathroom, [2]: car space
                    String[] rooms = temp.split(" ");
                    numBedroom = rooms[0];
                    numBathroom = rooms[1];
                    if (rooms.length > 4){
                        numCarSpace = rooms[2];
                    }
                    else{
                        numCarSpace = "0";
                    }

                    // Have to look up the title and address by using the link
                    Document tempDoc = Jsoup.connect(link).userAgent(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Chrome/33.0.1750.152 Safari/537.36")
                            .referrer("http://www.google.com")
                            .maxBodySize(0).get();
                    Element elTitle = tempDoc.select("div.col-xs-12.detail-image-block h3").first();
                    title = elTitle.text();
                    title = title.substring(0, title.indexOf("$"));
                    Elements elAddress = tempDoc.select("div.panel-body.property-info div.col-xs-12.col-sm-8.col-md-9 p");
                    address = elAddress.get(2).text();

                    //Add the data to the list
                    Property property = new Property(imageURL, title, address, rent, numBedroom, numBathroom, numCarSpace, link);
                    data.add(property);
                }
                //Proceed to the next page
                Element elNextPage = document.select("div.col-md-4.no-padding p a").get(1);
                //Get the url of next page
                String nextURL = elNextPage.attr("abs:href");
                //System.out.println(nextURL);
                if (Integer.parseInt(nextURL.charAt(nextURL.length() - 1) + "") > currentPage){
                    //Navigate to the next page
                    document = Jsoup.connect(nextURL).userAgent(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Chrome/33.0.1750.152 Safari/537.36")
                            .referrer("http://www.google.com").maxBodySize(0).get();
                    currentPage++;
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
//        EvesWebScraper evesWebScraper = new EvesWebScraper();
//        List<Property> list = evesWebScraper.getHamiltonRentResidentialData();
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
