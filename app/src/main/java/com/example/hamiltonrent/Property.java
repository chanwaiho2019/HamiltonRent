package com.example.hamiltonrent;

public class Property {

    private String title;
    private String address;
    private float rent;
    private int numBedroom;
    private int numBathroom;
    private int numCarSpace;
    private String link;

    /**
     * Initialize a Property object
     * @param title The title of property
     * @param address The address of property
     * @param rent The rent of property
     * @param link The link for detail description of property
     */
    public Property(String title, String address, String rent, String numBedroom, String numBathroom, String numCarSpace, String link){
        this.title = title;
        this.address = address;
        this.rent = Float.parseFloat(rent);
        this.numBedroom = Integer.parseInt(numBedroom);
        this.numBathroom = Integer.parseInt(numBathroom);
        this.numCarSpace = Integer.parseInt(numCarSpace);
        this.link = link;
    }

    /**
     * Get the title of property
     * @return The title of property
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Get the address of property
     * @return The address of property
     */
    public String getAddress(){
        return this.address;
    }

    /**
     * Get the rent of property
     * @return The rent of property
     */
    public float getRent(){
        return this.rent;
    }

    /**
     * Get the bedroom number of property
     * @return The bedroom number of property
     */
    public int getNumBedroom(){
        return this.numBedroom;
    }

    /**
     * Get the bathroom number of property
     * @return The bathroom number of property
     */
    public int getNumBathroom(){
        return this.numBathroom;
    }

    /**
     * Get the car space number of property
     * @return The car space number of property
     */
    public int getNumCarSpace(){
        return this.numCarSpace;
    }

    /**
     * Get the link of property
     * @return The link of property
     */
    public String getlink(){
        return this.link;
    }

}
