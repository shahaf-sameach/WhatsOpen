package app.view;

import app.db.dao.CategoryDao;
import app.db.dao.ReviewDao;
import app.db.entity.Business;
import app.db.entity.Category;
import app.db.entity.Review;
import app.db.entity.User;

import com.google.maps.model.PlaceDetails;
import com.sun.org.apache.regexp.internal.RE;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.util.List;


public class BusinessController {

    @FXML
    private Button okButton, cancelButton;
    @FXML
    private Label addressLabel, distanceLabel, posLabel, cityLabel, rankLabel;
    @FXML
    private Hyperlink urlLink;
    @FXML
    private ListView<Category> categoryList;
    @FXML
    private ListView<Review> reviewListView;

    private Business bussiness;
    private User user;
    private ReviewView reviewView = new ReviewView();
    private Review userReview;


    public void setView(){
        addressLabel.setText(bussiness.getAddress());
        distanceLabel.setText(String.format("%.1f",bussiness.getDistance() / 1000) + "km");
        posLabel.setText("(" + Double.toString(bussiness.getPos().getLat()) + "," + Double.toString(bussiness.getPos().getLng()) + ")");
        cityLabel.setText(bussiness.getCity());
        urlLink.setText(bussiness.getUrl());
        rankLabel.setText(String.format("%.1f",bussiness.getRank()) + " out of " + bussiness.getReviewers() + " reviewers");
        categoryList.getItems().setAll(CategoryDao.get(bussiness.getId()));

        List<Review> reviews = ReviewDao.getByBusiness(bussiness.getId());
        userReview = null;
        for(Review review : reviews){
            if (review.getUser() == user.getId()) {
                userReview = review;
            }
        }

        reviewListView.getItems().setAll(reviews);

        if (userReview != null)
            okButton.setText("Modify");
        else
            okButton.setText("Add");
    }

    public void okButtonClicked(){
        try {
            reviewView.display(user, bussiness, userReview);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cancelButtonClicked(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setBussiness(Business bussiness) {
        this.bussiness = bussiness;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void urlLinkClicked(){
        System.out.println("url clicked");
    }

}
