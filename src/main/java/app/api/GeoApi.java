package app.api;

import app.GeoPos;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public class GeoApi {
    private GeoApiContext context = null;

    public GeoApi(){
        this.context = new GeoApiContext().setApiKey("AIzaSyBSb9j5Dl8FnAdNraCMkN-l0rKsKrHsaWg");
    }

    public GeoPos getGeoPos(String address){
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GeoPos(results[0].geometry.location.lat, results[0].geometry.location.lng);
    }

    public static void main(String[] args) {
        GeoApi api = new GeoApi();
        GeoPos pos = api.getGeoPos("המאבק 64 גבעתיים");
        System.out.println(pos);
    }
}
