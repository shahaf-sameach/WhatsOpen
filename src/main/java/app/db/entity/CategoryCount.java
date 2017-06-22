package app.db.entity;

public class CategoryCount {
    private int count;
    private String category;

    public CategoryCount(){}

    public CategoryCount(int count, String category) {
        this.count = count;
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
