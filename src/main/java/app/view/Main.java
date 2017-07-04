package app.view;

import app.view.controller.LoginViewController;
import app.view.controller.SearchController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getClassLoader().getResource("login_layout.fxml"));
        Parent loginPane = loginPaneLoader.load();
        Scene loginScene = new Scene(loginPane, 350, 200);

        FXMLLoader searchPaneLoader = new FXMLLoader(getClass().getClassLoader().getResource("search_layout.fxml"));
        Parent searchPane = searchPaneLoader.load();
        Scene searchScene = new Scene(searchPane, 720, 576);

        LoginViewController loginPaneController = (LoginViewController) loginPaneLoader.getController();
        loginPaneController.setNextScene(searchScene);
        loginPaneController.setController((SearchController) searchPaneLoader.getController());

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("What's Open");
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
