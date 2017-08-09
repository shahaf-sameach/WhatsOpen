package app.api;

import app.config.ApiConfiguration;
import app.geo.GeoPos;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class GeoApi {

    public static GeoPos getGeoPos(String address){
        GeoPos pos = new GeoPos(32.0691989, 34.8430876);
        GeoApiContext context =  new GeoApiContext().setApiKey(ApiConfiguration.API_TOKEN);

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            pos = new GeoPos(results[0].geometry.location.lat, results[0].geometry.location.lng);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pos;
    }
}
