package app.view;

import app.db.entity.Business;
import app.db.entity.Review;
import app.db.entity.User;
import app.view.controller.ReviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ReviewView {

    private Stage window = new Stage();

    public ReviewView(){
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
    }

    public void display(User user, Business business, Review review) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("review_layout.fxml"));
        Parent root = fxmlLoader.load();
        window.setTitle(business.getName());
        window.setScene(new Scene(root, 434, 382));

        ReviewController controller = fxmlLoader.getController();
        controller.setBussiness(business);
        controller.setUser(user);
        controller.setReview(review);
        controller.setView();

        window.showAndWait();
    }


}
