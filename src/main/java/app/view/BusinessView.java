package app.view;

import app.db.entity.Business;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BusinessView {

    private Stage window = new Stage();

    public BusinessView(){
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
    }

    public void display(Business business) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("business_layout.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        window.setTitle(business.getName());
        window.setScene(new Scene(root, 600, 400));
        BusinessViewController controller = fxmlLoader.<BusinessViewController>getController();
        controller.setView(business);
        window.showAndWait();
    }


}
