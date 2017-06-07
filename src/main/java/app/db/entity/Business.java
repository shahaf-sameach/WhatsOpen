package app.db.entity;

import app.GeoPos;

public class Business implements Comparable<Business> {
    private int id;
    private String name;
    private String address;
    private String description;
    private GeoPos pos;
    private double distance;

    public Business(){}

    public Business(int id, String name, String address, String description, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.pos = new GeoPos(lat, lng);
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

    public int compareTo(Business bis) {
        if (this.distance > bis.distance)
            return 1;
        else if (this.distance < bis.distance)
            return -1;
        return 0;
    }

}