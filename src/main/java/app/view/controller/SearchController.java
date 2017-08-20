package app.view.controller;

import app.constants.UserStatus;
import app.geo.BoundingBox;
import app.geo.GeoPos;
import app.api.GeoApi;
import app.db.dao.BussinessDao;
import app.db.dao.CategoryDao;
import app.db.entity.Business;
import app.db.entity.Category;
import app.db.entity.CategoryCount;
import app.db.entity.User;
import app.view.BusinessView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SearchController {

    @FXML
    private Label topLable, statusLable;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Business> mainList;
    @FXML
    private ListView<Category> categoriesList;
    @FXML
    private ComboBox<String> radiusComboBox;
    @FXML
    private TextArea summaryTextArea;

    private BusinessView businessView = new BusinessView();
    private User user;

    public void initialize() {
        topLable.setText("");
        statusLable.setText("Ready");
        try {
            List<Category> categories = CategoryDao.getAll();
            categoriesList.getItems().setAll(categories);
            categoriesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception ex) {
            System.err.println(ex);
        }

        radiusComboBox.getItems().addAll("10km","15km","20km","25km","30km");
        radiusComboBox.getSelectionModel().select(1);
        searchField.setText("המאבק 61 גבעתיים");
    }

    public void searchButtonclicked(){
        searchButton.setDisable(true);
        statusLable.setText("Searching...");
        mainList.getItems().clear();
        try {
            String address = searchField.getText();
            GeoPos pos = GeoApi.getGeoPos(address);
            int radius = Integer.parseInt(radiusComboBox.getSelectionModel().getSelectedItem().substring(0,2));
            BoundingBox box = new BoundingBox(pos, radius);
            List<Category> categories = categoriesList.getSelectionModel().getSelectedItems();
            List<Business> businesses = BussinessDao.get(categories, box.getMinLat(), box.getMaxLat(), box.getMinLng(), box.getMaxLng());
            PriorityQueue<Business> queue = new PriorityQueue<Business>();
            List<Integer> bussinessId = new ArrayList<Integer>();

            for (Business bb : businesses) {
                bb.setDistance(pos);
                if (bb.getDistance() / 1000 <= radius) {
                    bussinessId.add(bb.getId());
                    queue.add(bb);
                }
            }

            summaryTextArea.setText("Found total of " + queue.size() + " businesses");

            while (!queue.isEmpty()) {
                Business obj = queue.poll();
                mainList.getItems().add(obj);
            }

            List<CategoryCount> count = CategoryDao.getSummary(bussinessId);

            if (count.size() > 0) {
                String summary = "";
                for (CategoryCount c : count){
                    summary = summary + c.getCategory() + "(" + c.getCount() + ")" + " | ";
                }
                summary = summaryTextArea.getText() + " :\n" + summary.substring(0,summary.length() - 2);
                summaryTextArea.setText(summary);
            }

            statusLable.setText("Ready");
        }catch (Exception ex){
            System.err.println(ex);
            statusLable.setText("Error occurred, please try again");
        } finally {
            searchButton.setDisable(false);
        }
    }

    public void mainListClicked(MouseEvent click){
        if (click.getClickCount() == 2) {
            Business currentItemSelected = mainList.getSelectionModel().getSelectedItem();
            try {
                businessView.display(user, currentItemSelected);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setUser(User user, UserStatus status){
        this.user = user;
        if (status == UserStatus.NewUser)
            topLable.setText("Hello " + user.getUserName());
        else
            topLable.setText("Welcom back " + user.getUserName());
    }

}

