package app;

import app.api.GeoApi;
import app.db.dao.BussinessDao;
import app.db.entity.Business;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("hello world");

        String address = "המאבק 61 גבעתיים";
        GeoApi api = new GeoApi();
        GeoPos pos = api.getGeoPos(address);

        BoundingBox box = new BoundingBox(pos, 10);

        BussinessDao b = new BussinessDao();
        List<String> categories = new ArrayList<String>();
        categories.add("food");
        categories.add("atm");
        List<Business> businesses = b.get(categories, box.getMinLat() , box.getMaxLat() , box.getMinLng() , box.getMaxLng());
        System.out.println("found " + businesses.size() + "business");
        PriorityQueue<Business> queue = new PriorityQueue<Business>();

        for(Business bb : businesses){
            bb.setDistance(pos);
            queue.add(bb);
        }

        while(!queue.isEmpty()) {
            Business obj = queue.poll();
            System.out.println(obj.getName() + " : " + obj.getDistance());
        }

    }
}
