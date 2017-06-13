package app.db.dao;

import app.db.DbHandler;
import app.db.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private Connection conn = null;

    public List<Review> getByBusiness(int business_id) {
        List<Review> reviews = new ArrayList<Review>();
        try {
            String query = "select review.id, description, rank, username " +
                    "from review, user " +
                    "where review.bussiness = ? and review.user = user.id";


            conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, business_id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(rs.getInt("id"),
                        rs.getInt("rank"),
                        rs.getString("description"),
                        rs.getString("username")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return reviews;
    }

}