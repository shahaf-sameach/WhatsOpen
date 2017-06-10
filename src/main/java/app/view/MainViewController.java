package app.view;

import app.BoundingBox;
import app.GeoPos;
import app.api.GeoApi;
import app.db.dao.BussinessDao;
import app.db.dao.CategoryDao;
import app.db.entity.Business;
import app.db.entity.Category;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.PriorityQueue;

public class MainViewController {

    public Label topLable;
    public Label statusLable;
    public TextField searchField;
    public Button searchButton;
    public ListView<Business> mainList;
    public ListView<Category> categoriesList;
    private BusinessView businessView = new BusinessView();

    public void initialize() {
        topLable.setText("Hello " + "username");
        statusLable.setText("Ready");
        CategoryDao d = new CategoryDao();
        try {
            List<Category> categories = d.getAll();
            categoriesList.getItems().setAll(categories);
            categoriesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void searchButtonclicked(){
        searchButton.setDisable(true);
        statusLable.setText("Searching...");
        mainList.getItems().clear();
        try {
            String address = searchField.getText();
            address = "המאבק 61 גבעתיים";
            GeoPos pos = GeoApi.getGeoPos(address);
            int radius = 10;
            BoundingBox box = new BoundingBox(pos, radius);
            BussinessDao b = new BussinessDao();
            List<Category> categories = categoriesList.getSelectionModel().getSelectedItems();
            List<Business> businesses = b.get(categories, box.getMinLat(), box.getMaxLat(), box.getMinLng(), box.getMaxLng());
            System.out.println("found " + businesses.size() + " business");
            PriorityQueue<Business> queue = new PriorityQueue<Business>();

            for (Business bb : businesses) {
                bb.setDistance(pos);
                if (bb.getDistance() / 1000 <= radius)
                    queue.add(bb);
            }

            while (!queue.isEmpty()) {
                Business obj = queue.poll();
                mainList.getItems().add(obj);
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
                businessView.display(currentItemSelected);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}

