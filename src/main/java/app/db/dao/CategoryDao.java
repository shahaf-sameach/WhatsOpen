package app.db.dao;

import app.db.entity.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private Connection conn = null;

    public List<Category> getAll() throws Exception {
        String url = "jdbc:mysql://localhost/whatsopen";
        String userName = "whatsopen";
        String password = "1234";
        String query = "select id, name from category";

        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(url, userName, password);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        List<Category> categories = new ArrayList<Category>();
        while(rs.next()){
            String name = rs.getString("name");
            int id = rs.getInt("id");
            categories.add(new Category(id, name));
        }

        rs.close();
        conn.close();
        return categories;
    }


}