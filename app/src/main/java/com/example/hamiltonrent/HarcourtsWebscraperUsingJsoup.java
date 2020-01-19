package com.example.hamiltonrent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class HarcourtsWebscraperUsingJsoup {

    private static Document document;
    //The url of the website that we want to scrape
    private static final String url = "https://harcourts.co.nz/Property/Rentals?location=25015&minbed=1&data=%7B%22region%22%3A22003%2C%22city%22%3A25015%7D";

    public List<Property> getHamiltonRentResidentialData() {

        //A list to store data
        List<Property> data = new ArrayList<>();

        try {
            //Get the document from the specified url
            document = Jsoup.connect(url).userAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").referrer("http://www.google.com").maxBodySize(0).get();

            //System.out.println(document.outerHtml());   //for testing

            //Find the number of pages that it has
            int numOfPages = findNumberOfPages(url);

            String title;
            String address;
            String rent;
            String numBedroom;
            String numBathroom;
            String numCarSpace;
            String link;

            for (int i = 1; i <= numOfPages; i++) {
                //Loop through the elements
                for (Element e : document.select("div.search-item-content")) {
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
                    Property property = new Property(title, address, rent, numBedroom, numBathroom, numCarSpace, link);
                    data.add(property);
                }
                Element nextPage = document.select("div.next-pagination a").first();
                //Get the url of next page
                String nextURL = nextPage.attr("abs:href");
                //Navigate to the next page
                document = Jsoup.connect(nextURL).userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").referrer("http://www.google.com").maxBodySize(0).get();;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static int findNumberOfPages(String url){
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

//    public static void main(String[] args){
//        HarcourtsWebscraperUsingJsoup harcourtsWebscraperUsingJsoup = new HarcourtsWebscraperUsingJsoup();
//        List<Property> list = harcourtsWebscraperUsingJsoup.getHamiltonRentResidentialData();
//        for (Property p : list){
//            System.out.println(p.getTitle());
//            System.out.println(p.getAddress());
//            System.out.println(p.getRent());
//            System.out.println("Bedrooms: " + p.getNumBedroom() + "     Bathrooms: " + p.getNumBathroom() + "     Car space: " + p.getNumCarSpace());
//            System.out.println(p.getlink());
//            System.out.println("---------------");
//        }
//    }
}
