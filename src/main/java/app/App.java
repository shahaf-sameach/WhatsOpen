package app;

import app.db.dao.BussinessDao;
import app.db.dao.CategoryDao;

import java.util.List;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("hello world");

        CategoryDao c = new CategoryDao();
        List<String> categories = c.getAll();

        for(String category : categories)
            System.out.println(category);

        System.out.println("----");

        BussinessDao b = new BussinessDao();
        List<String> businesses = b.getAll();

        for(String bussiness : businesses)
            System.out.println(bussiness);

    }
}
