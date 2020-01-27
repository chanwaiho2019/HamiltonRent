package com.example.hamiltonrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class WebSraperUsingJsoup {

    /**
     * A method to get the data of all Hamilton residential properties for rent.
     * @return The list of Hamilton residential properties for rent.
     */
    public abstract List<Property> getHamiltonRentResidentialData();

    /**
     * Get the list of properties by specified number of bedrooms
     * @param properties The full list of properties
     * @param bedroomNum The number of bedrooms that you want to specify
     * @return The list of properties with the number of bedrooms that you specified
     */
    public List<Property> getByBedroomNum(List<Property> properties, int bedroomNum){
        List<Property> temp = new ArrayList<>();
        for (Property property : properties){
            if (property.getNumBedroom() == bedroomNum){
                temp.add(property);
            }
        }
        return temp;
    }

    /**
     * Sort the list of properties from low to high rent.
     * @param properties The list of properties to be sorted
     * @return Sorted list
     */
    public List<Property> sortByRentLowToHigh(List<Property> properties){
        Collections.sort(properties, new Comparator<Property>() {
            @Override
            public int compare(Property p1, Property p2) {
                return Float.compare(p1.getRent(), p2.getRent());
            }
        });
        return properties;
    }

    /**
     * Sort the list of properties from High to low rent.
     * @param properties The list of properties to be sorted
     * @return Sorted list
     */
    public List<Property> sortByRentHighToLow(List<Property> properties){
        List<Property> temp = new ArrayList<>();
        //Sort the list from low to high rent
        List<Property> lowToHighRent = sortByRentLowToHigh(properties);
        //Reverse the list
        for (int i = lowToHighRent.size() - 1; i >= 0; i--){
            temp.add(lowToHighRent.get(i));
        }
        return temp;
    }


}
