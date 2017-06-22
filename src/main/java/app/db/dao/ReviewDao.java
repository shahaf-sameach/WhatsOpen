package app.db.dao;

import app.db.DbHandler;
import app.db.entity.Review;
import app.db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {

    public static List<Review> getByBusiness(int business_id) {
        List<Review> reviews = new ArrayList<Review>();
        try {
            String query = "select review.id, description, rank, review.user, review.bussiness, user.username " +
                    "from review, user " +
                    "where review.bussiness = ? and review.user = user.id";


            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, business_id);
            System.out.println(st.toString());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(rs.getInt("id"),
                        rs.getInt("rank"),
                        rs.getString("description"),
                        rs.getInt("user"),
                        rs.getInt("bussiness"),
                        rs.getString("username")));
            }

            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return reviews;
    }

    public static Review add(Review review){
        int record_id = getMaxId();
        try {
            if (record_id == -1)
                return null;

            String query = "insert into review VALUES (?,?,?,?,?)";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, record_id + 1);
            st.setInt(2, review.getRank());
            st.setString(3, review.getDescription());
            st.setInt(4, review.getUser());
            st.setInt(5, review.getBussiness());

            if(st.executeUpdate() == 1)
                review.setId(record_id + 1);
            else
                review = null;

            st.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return review;
    }

    public static Review modify(Review review){
        try {

            String query = "update review set rank = ?, description = ? where id = ?";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, review.getRank());
            st.setString(2, review.getDescription());
            st.setInt(3, review.getId());

            if (st.executeUpdate() != 1)
                review = null;

            st.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return review;
    }

    public static boolean delete(Review review){
        boolean success = false;
        try {

            String query = "delete from review where id = ?";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, review.getId());

            if (st.executeUpdate() == 1)
                success = true;

            st.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return success;
    }

    private static int getMaxId(){
        int maxId = -1;
        try {
            String query = "select max(id) as id from review";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            rs.next();
            maxId = rs.getInt("id");
            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return maxId;
    }

}