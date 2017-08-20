package app.config;

public class DatabaseConfiguration {

    private static String URL = "jdbc:mysql://localhost/dmw04db";
    private static String USERNAME = "dmw04";
    private static String PASSWORD = "Df26q";

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
