package app.db.dao;

import app.db.DbHandler;
import app.db.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public static User get(String userName, String password) {
        User user = null;
        try {
            String query = "select * from user where username = ? and password = ?";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, userName);
            st.setString(2, password);
            System.out.println(st.toString());

            ResultSet rs = st.executeQuery();
            rs.next();
            user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));

            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return user;
    }

    public static User create(String userName, String password) {
        User user = null;

        if (isExist(userName))
            return null;

        try {
            int id = getMaxId() + 1;
            if (id == 0)
                throw new Exception("can't get user max id");

            String query = "insert into user VALUES (?,?,?)";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, id);
            st.setString(2, userName);
            st.setString(3, password);

            int affectedRows = st.executeUpdate();
            user = new User(id, userName, password);

            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return user;
    }

    private static boolean isExist(String userName) {
        boolean flag = true;
        try {
            String query = "select username from user where username = ?";
            Connection conn = DbHandler.connect();
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst() )
                flag = false;

            rs.close();
            conn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return flag;
    }

    private static int getMaxId(){
        int maxId = -1;
        try {
            String query = "select max(id) as id from user";
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