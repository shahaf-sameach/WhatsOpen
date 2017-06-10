package app.db.entity;

import app.GeoPos;

public class Business implements Comparable<Business> {
    private int id;
    private String name;
    private String address;
    private String description;
    private GeoPos pos;
    private double distance;
    private String city;
    private String url;
    private double rank;
    private int reviewers;

    public Business(){}

    public Business(int id, String name, String address, String description, double lat, double lng, String url, String city, double rank, int count) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.pos = new GeoPos(lat, lng);
        this.url = url;
        this.city = city;
        this.rank = rank;
        this.reviewers = count;
        this.distance = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public GeoPos getPos() { return  pos;}

    public String getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDistance(GeoPos pos) {
        this.distance = this.pos.distance(pos);
    }

    public double getDistance() {
        return this.distance;
    }

    public double getRank() { return this.rank; }

    public int getReviewers() { return this.reviewers; }

    public String toString() {
        String str = String.format("%.1f",this.getDistance() / 1000) + "km " + padString(this.getName(), 20) + "  " + padString(this.getAddress(), 50)  + " " + String.format("%.1f",this.getRank()) + "/" + this.getReviewers();
        return str;
    }

    private String padString(String str, int leng) {
        if (str.length() > leng)
            return str.substring(0,leng - 3) + "...";

        for (int i = str.length(); i <= leng; i++)
            str += " ";
        return str;
    }

    public int compareTo(Business bis) {
        if (this.distance > bis.distance)
            return 1;
        else if (this.distance < bis.distance)
            return -1;
        return 0;
    }

}
