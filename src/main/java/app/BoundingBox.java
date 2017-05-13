package app;

public class BoundingBox {
    private final double earthRadius = 6378.1;
    private GeoPos center;
    private GeoPos LeftUp;
    private GeoPos LeftDown;
    private GeoPos RightDown;
    private GeoPos RightUp;

    public void getBounds(double lat, double lng, int distance){
        double radDist = distance / earthRadius;
        double radLat = degToRad(lat);
        double radLng = degToRad(lng);

        double minLat = radLat - radDist;
        double maxLat = radLat + radDist;

        double deltaLng = Math.asin(Math.sin(radDist) / Math.cos(radLat));
        double minLng = radLng - deltaLng;
        double maxLng = radLng + deltaLng;

        minLng = radToDeg(minLng);
        minLat = radToDeg(minLat);
        maxLng = radToDeg(maxLng);
        maxLat = radToDeg(maxLat);
    }

    private double degToRad(double deg){
        return deg * (Math.PI / 180);
    }

    private double radToDeg(double rad){
        return (180 * rad) / Math.PI;
    }
}
