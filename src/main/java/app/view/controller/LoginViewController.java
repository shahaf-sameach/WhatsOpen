package app.view.controller;

import app.db.dao.UserDao;
import app.db.entity.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginViewController {

    @FXML
    private Label infoLabel;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passField;

    private Scene nextScene;
    private SearchController searchController;

    public void loginButtonClicked(){
        String userName = userNameTextField.getText().toLowerCase();

        if (isPasswordEmpty()){
            infoLabel.setText("Password cannot be empty");
            return;
        }

        User user = UserDao.get(userName, getHashPassword());

        if (user != null){
            switchScene(user);
        } else {
            infoLabel.setText("Wrong username or password");
        }
    }

    public void signUpButtonClicked(){
        String userName = userNameTextField.getText().toLowerCase();

        if (isPasswordEmpty()){
            infoLabel.setText("Password cannot be empty");
            return;
        }
        
        User user = UserDao.create(userName, getHashPassword());

        if (user != null){
            switchScene(user);
        } else {
            infoLabel.setText("username already exist");
        }
    }

    private void switchScene(User user){
        searchController.setUser(user);
        Stage primaryStage = (Stage) infoLabel.getScene().getWindow();
        primaryStage.setScene(nextScene);
    }

    private String getHashPassword() {
        return DigestUtils.md5Hex(passField.getText());
    }

    private boolean isPasswordEmpty(){
        return passField.getText().equals("");
    }

    public void setNextScene(Scene scene){
        this.nextScene = scene;
    }

    public void setController(SearchController controller) {
        this.searchController = controller;
    }

}

