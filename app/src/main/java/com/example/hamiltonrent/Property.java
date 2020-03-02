package com.example.hamiltonrent;

public class Property {

    private String imageURL;
    private String title;
    private String address;
    private float rent;
    private int numBedroom;     // -1 means they are not mentioned in the website
    private int numBathroom;    // -1 means they are not mentioned in the website
    private int numCarSpace;    // -1 means they are not mentioned in the website
    private String link;
    private String agent;

    /**
     * Initialize a Property object
     * @param title The title of property
     * @param address The address of property
     * @param rent The rent of property
     * @param link The link for detail description of property
     */
    public Property(String imageURL, String title, String address, String rent, String numBedroom, String numBathroom, String numCarSpace, String link, String agent){
        this.imageURL = imageURL;
        this.title = title;
        this.address = address;
        this.rent = Float.parseFloat(rent);
        this.numBedroom = Integer.parseInt(numBedroom);
        this.numBathroom = Integer.parseInt(numBathroom);
        this.numCarSpace = Integer.parseInt(numCarSpace);
        this.link = link;
        this.agent = agent;
    }

    /**
     * Get the image URL of property
     * @return The image URL of property
     */
    public String getImageURL(){
        return this.imageURL;
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

    /**
     * Get the agent of property
     * @return The agent of property
     */
    public String getAgent() {
        return this.agent;
    }

}
