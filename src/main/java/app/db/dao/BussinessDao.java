package app.db.dao;

import app.db.DbHandler;
import app.db.entity.Business;
import app.db.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BussinessDao {

    public static List<String> getAll()  {
        List<String> categories = new ArrayList<String>();
        try {
            Connection conn = DbHandler.connect();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select name from bussiness");


            while (rs.next()) {
                String record = rs.getString("name");
                categories.add(record);
            }

            rs.close();
            conn.close();
        } catch (Exception ex){
            System.err.println(ex);
        }
        return categories;
    }

    public static List<Business> get(List<Category> categories, double minLat, double maxLat, double minLng, double maxLng) {
        List<Business> businesses = new ArrayList<Business>();

        try {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < categories.size(); i++) {
                builder.append("?,");
            }

            String query = "select b.id, b.name, b.address, b.description, b.lat, b.lng, b.url, city.name as city, avg(rank) as rank, count(rank) as reviewers " +
                    "from bussiness b " +
                    "LEFT JOIN bussiness_category on bussiness_category.bussiness = b.id " +
                    "LEFT JOIN category on category.id = bussiness_category.category " +
                    "LEFT JOIN city on city.id = b.city " +
                    "LEFT JOIN review on review.bussiness = b.id " +
                    "where category.id in (" + builder.deleteCharAt(builder.length() - 1).toString() + ") and b.lat between ? and ? and b.lng between ? and ? and review.bussiness = b.id " +
                    "group by review.bussiness";

            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            int index = 1;
            for (Category c : categories) {
                st.setInt(index++, c.getId());
            }

            st.setDouble(index++, minLat);
            st.setDouble(index++, maxLat);
            st.setDouble(index++, minLng);
            st.setDouble(index++, maxLng);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                businesses.add(new Business(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getDouble("lat"),
                        rs.getDouble("lng"),
                        rs.getString("url"),
                        rs.getString("city"),
                        rs.getDouble("rank"),
                        rs.getInt("reviewers")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex){
            System.err.println(ex);
        }
        return businesses;
    }
}
