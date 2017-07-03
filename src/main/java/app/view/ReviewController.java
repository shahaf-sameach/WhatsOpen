package app.view;

import app.db.dao.ReviewDao;
import app.db.entity.Business;
import app.db.entity.Review;
import app.db.entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class ReviewController {

    @FXML
    private Label charNumLabel, statusLable;
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private Slider rankSlider;
    @FXML
    private Button addButton, cancelButton, deleteButton;

    private Business bussiness;
    private User user;
    private Review review;

    public void initialize() {
        // Listen for Slider value changes
        rankSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                rankSlider.setValue(newValue.intValue());
            }
        });

        reviewTextArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                charNumLabel.setText(newValue.length() + " chars");
            }
        });
    }


    public void setView(){
        if (review != null){
            reviewTextArea.setText(review.getDescription());
            rankSlider.setValue(review.getRank());
        } else {
            addButton.setText("Add");
            deleteButton.setVisible(false);
            deleteButton.setDisable(true);
        }
    }

    public void addButtonClicked(){
        if (addButton.getText().equals("Add")){
            review = new Review(1, (int)rankSlider.getValue(), getDescription(), user, bussiness.getId());
            review = ReviewDao.add(review);
        } else {
            review.setRank((int)rankSlider.getValue());
            review.setDescription(getDescription());
            review = ReviewDao.modify(review);
        }

        if (review != null) {
            statusLable.setText("Successfully Update DB");
        } else {
            statusLable.setText("An Error Occurred");
        }
        closeWindow();
    }

    public void cancelButtonClicked(){
        closeWindow();
    }

    private void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void deleteButtonClicked(){
        if(ReviewDao.delete(review))
            statusLable.setText("Successfully Update DB");
        else
            statusLable.setText("An Error Occurred");

        reviewTextArea.setText("");
        rankSlider.setValue(3);
        deleteButton.setDisable(true);
        deleteButton.setVisible(false);
        addButton.setText("Add");
    }

    public void setBussiness(Business bussiness) {
        this.bussiness = bussiness;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReview(Review review) { this.review = review; }

    private String getDescription(){
        String text = reviewTextArea.getText();
        if (text.length() > 140) {
            return text.substring(0,137) + "...";
        }
        return text;
    }

}
