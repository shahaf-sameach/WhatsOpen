package app.db.entity;

public class Review {
    private int id;
    private int rank;
    private String description;
    private User user;
    private int bussiness;

    public Review(){}

    public Review(int id, int rank, String description, User user, int bussiness) {
        this.id = id;
        this.rank = rank;
        this.description = description;
        this.user = user;
        this.bussiness = bussiness;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBussiness() {
        return bussiness;
    }

    public void setBussiness(int bussiness) {
        this.bussiness = bussiness;
    }

    public String toString() {
        return this.rank + " : " + this.description + " - " + "by " + this.user.getUserName();
    }

}
