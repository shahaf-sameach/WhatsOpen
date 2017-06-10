package app.db.dao;

import app.db.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private Connection conn = null;

    public List<Review> getByBusiness(int business_id) throws Exception {
        String url = "jdbc:mysql://localhost/whatsopen";
        String userName = "whatsopen";
        String password = "1234";
        String query = "select review.id, description, rank, username " +
                "from review, user " +
                "where review.bussiness = ? and review.user = user.id";


        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(url, userName, password);
        PreparedStatement st = conn.prepareStatement(query);
        st.setInt(1, business_id);
        System.out.println(st.toString());

        ResultSet rs = st.executeQuery();

        List<Review> reviews = new ArrayList<Review>();
        while(rs.next()){
            reviews.add(new Review(rs.getInt("id"),
                                      rs.getInt("rank"),
                                      rs.getString("description"),
                                      rs.getString("username")));
        }

        rs.close();
        conn.close();
        return reviews;
    }

}