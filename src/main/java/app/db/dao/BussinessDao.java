package app.db.dao;

import app.db.entity.Business;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BussinessDao {
    private Connection conn = null;

    public List<String> getAll() throws Exception {
        String url = "jdbc:mysql://localhost/whatsopen";
        String userName = "whatsopen";
        String password = "1234";
        String query = "select name from bussiness";

        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(url, userName, password);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        List<String> categories = new ArrayList<String>();
        while(rs.next()){
            String record = rs.getString("name");
            categories.add(record);
        }

        rs.close();
        conn.close();
        return categories;
    }

    public List<Business> get(List<String> categories, double minLat, double maxLat, double minLng, double maxLng) throws Exception {
        String url = "jdbc:mysql://localhost/whatsopen";
        String userName = "whatsopen";
        String password = "1234";
        StringBuilder builder = new StringBuilder();

        for( int i = 0 ; i < categories.size(); i++ ) {
            builder.append("?,");
        }

        String query = "select b.id, b.name, b.address, b.description, b.lat, b.lng from bussiness b " +
                       "LEFT join bussiness_category on bussiness_category.bussiness = b.id " +
                       "LEFT join category on category.id = bussiness_category.category " +
                       "where category.name in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ") and b.lat between ? and ? and b.lng between ? and ?";

        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(url, userName, password);
        PreparedStatement st = conn.prepareStatement(query);

        int index = 1;
        for( String o : categories ) {
            st.setString(  index++, o ); // or whatever it applies
        }

        st.setDouble(index++, minLat);
        st.setDouble(index++, maxLat);
        st.setDouble(index++, minLng);
        st.setDouble(index++, maxLng);
        System.out.println(st.toString());
        ResultSet rs = st.executeQuery();

        List<Business> businesses = new ArrayList<Business>();
        while(rs.next()){
            businesses.add(new Business(rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("address"),
                                        rs.getString("description"),
                                        rs.getDouble("lat"),
                                        rs.getDouble("lng")));
        }

        rs.close();
        conn.close();
        return businesses;
    }

    public static void main(String[] args) throws Exception {
        BussinessDao b = new BussinessDao();
        List<String> categories = new ArrayList<String>();
        categories.add("food");
        categories.add("atm");
        List<Business> businesses = b.get(categories, 32.2 , 33 , 34.98 , 35);
        System.out.println("found " + businesses.size() + "business");
        for(Business bb : businesses){
            System.out.println(bb.getName());
        }
    }
}
