package app.db.dao;

import app.db.DbHandler;
import app.db.entity.Category;
import app.db.entity.CategoryCount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    public static List<Category> getAll() {
        List<Category> categories = new ArrayList<Category>();
        try {
            Connection conn = DbHandler.connect();
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

    public static List<Category> get(int business_id) {
        List<Category> categories = new ArrayList<Category>();
        try {
            String query = "select id, name " +
                    "from category, bussiness_category " +
                    "where category.id = bussiness_category.category and bussiness_category.bussiness = ?";


            Connection conn = DbHandler.connect();
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

    public static List<CategoryCount> getSummary(List<Integer> bussiness) {
        List<CategoryCount> count = new ArrayList<CategoryCount>();
        try {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < bussiness.size(); i++) {
                builder.append("?,");
            }

            String query = "select count(category.id) as count, category.name as category " +
                    "from bussiness, category, bussiness_category " +
                    "where bussiness.id = bussiness_category.bussiness and category.id = bussiness_category.category and bussiness.id in (" +
                    builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                    "group by category.id";

            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            int index = 1;
            for (Integer b : bussiness) {
                st.setInt(index++, b);
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count.add(new CategoryCount(rs.getInt("count"), rs.getString("category")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex){
            System.err.println(ex);
        }
        return count;
    }


}