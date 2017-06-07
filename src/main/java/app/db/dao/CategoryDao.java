package app.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private Connection conn = null;

    public List<String> getAll() throws Exception {
        String url = "jdbc:mysql://localhost/whatsopen";
        String userName = "whatsopen";
        String password = "1234";
        String query = "select name from category";

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
}