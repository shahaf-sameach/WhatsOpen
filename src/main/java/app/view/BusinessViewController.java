package app.view;

import app.db.dao.CategoryDao;
import app.db.dao.ReviewDao;
import app.db.entity.Business;
import app.db.entity.Category;
import app.db.entity.Review;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class BusinessViewController {

    public Button okButton;
    public Button cancelButton;
    public Label addressLabel, distanceLabel, posLabel, cityLabel, rankLabel;
    public Hyperlink urlLink;
    public ListView<Category> categoryList;
    public VBox reviewsVBox;


    public void setView(Business business){
        addressLabel.setText(business.getAddress());
        distanceLabel.setText(String.format("%.1f",business.getDistance() / 1000) + "km");
        posLabel.setText("(" + Double.toString(business.getPos().getLat()) + "," + Double.toString(business.getPos().getLng()) + ")");
        cityLabel.setText(business.getCity());
        urlLink.setText(business.getUrl());
        rankLabel.setText(String.format("%.1f",business.getRank()) + " out of " + business.getReviewers() + " reviewers");
        categoryList.getItems().setAll(CategoryDao.get(business.getId()));

        List<Review> reviews = ReviewDao.getByBusiness(business.getId());
        for(Review review : reviews){
            VBox vbox = new VBox();
            HBox hBox = new HBox();
            Label rank = new Label(Integer.toString(review.getRank()));
            rank.setFont(new Font("Arial", 40));

            Label reviewL = new Label(review.getDescription());
            reviewL.setFont(Font.font("Arial", FontPosture.ITALIC, 16));

            hBox.getChildren().addAll(rank, reviewL);
            Label reviewer = new Label("By " + review.getUser());
            reviewer.setAlignment(Pos.BOTTOM_RIGHT);
            vbox.getChildren().addAll(hBox, reviewer);
            reviewsVBox.getChildren().add(vbox);
        }
    }

    public void okButtonClicked(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void cancelButtonClicked(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void urlLinkClicked(){
        urlLink.setVisited(true);
    }

}
