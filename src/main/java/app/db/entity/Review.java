package app.db.entity;

import org.apache.commons.lang.WordUtils;

public class Review {
    private int id;
    private int rank;
    private String description;
    private String user;

    public Review(){}

    public Review(int id, int rank, String description, String user) {
        this.id = id;
        this.rank = rank;
        this.description = description;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
