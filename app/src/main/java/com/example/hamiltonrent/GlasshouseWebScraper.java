package com.example.hamiltonrent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GlasshouseWebScraper extends WebSraperUsingJsoup {

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://app.inspectrealestate.com.au/External/ROL/QuickWeb.aspx?AgentAccountName=glasshousePM&HidePrice=&UsePriceView=&HideAppOffer=&Sort=&HideLogo=";

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

            //Loop through the elements
            Elements elements = document.select("div.divBorder");
            for (int i = 1; i <= elements.size() - 2; i++) {
//                System.out.println(document.outerHtml());
                Element e = elements.get(i);
                imageURL = e.select("img").get(1).attr("abs:src");
                title = e.select("span.lblAddressTitle").text();
                address = title;
                rent = e.select("h3 span").text();
                rent = rent.replaceAll("[^\\d]", "");
                Elements temp = e.select("div.divInfo div.divPropInfo tbody tr span");
                String[] rooms = temp.text().split(" ");
                numBedroom = rooms[3];
                numBathroom = rooms[4];
                numCarSpace = rooms[5];

                link = e.select("div.divInspectionButton input").attr("onclick");
                link = link.substring(link.indexOf("https://"), link.indexOf("')"));
                //Add the data to the list
                Property property = new Property(imageURL, title, address, rent, numBedroom, numBathroom, numCarSpace, link);
                data.add(property);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

//    public static void main(String[] args){
//        GlasshouseWebScraper glasshouseWebScraper = new GlasshouseWebScraper();
//        List<Property> list = glasshouseWebScraper.getHamiltonRentResidentialData();
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
