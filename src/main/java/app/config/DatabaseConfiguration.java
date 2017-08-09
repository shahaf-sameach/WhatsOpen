package app.config;

public class DatabaseConfiguration {

    private static String URL = "jdbc:mysql://localhost/whatsopen";
    private static String USERNAME = "whatsopen";
    private static String PASSWORD = "1234";

    public static void setConf(String url, String username, String password){
        if (url != null)
            URL = url;

        if (username != null)
            USERNAME = username;

        if (password != null)
            PASSWORD = password;
    }

    public static String getUrl(){
        return URL;
    }

    public static String getUsername(){
        return USERNAME;
    }

    public static String getPassword(){
        return PASSWORD;
    }
}
