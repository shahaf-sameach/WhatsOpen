package app.db.dao;

import app.db.DbHandler;
import app.db.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private Connection conn = null;

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<Category>();
        try {
            conn = DbHandler.connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id, name from category");

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex){
            System.err.println(ex);
        }
        return categories;
    }

    public List<Category> get(int business_id) {
        List<Category> categories = new ArrayList<Category>();
        try {
            String query = "select id, name " +
                    "from category, bussiness_category " +
                    "where category.id = bussiness_category.category and bussiness_category.bussiness = ?";


            conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, business_id);
            System.out.println(st.toString());

            ResultSet rs = st.executeQuery();


            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return categories;
    }


}