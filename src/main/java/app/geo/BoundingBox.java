package app.geo;

public class BoundingBox {
    private final double earthRadius = 6378.1;
    private double minLng;
    private double maxLng;
    private double minLat;
    private double maxLat;

    public BoundingBox(GeoPos pos, int distance){
        double radDist = distance / earthRadius;
        double radLat = degToRad(pos.getLat());
        double radLng = degToRad(pos.getLng());

        double minLat = radLat - radDist;
        double maxLat = radLat + radDist;

        double deltaLng = Math.asin(Math.sin(radDist) / Math.cos(radLat));
        double minLng = radLng - deltaLng;
        double maxLng = radLng + deltaLng;

        this.minLng = radToDeg(minLng);
        this.minLat = radToDeg(minLat);
        this.maxLng = radToDeg(maxLng);
        this.maxLat = radToDeg(maxLat);
    }

    public double getMinLat(){
        return this.minLat;
    }

    public double getMaxLat(){
        return this.maxLat;
    }

    public double getMinLng(){
        return this.minLng;
    }

    public double getMaxLng(){
        return this.maxLng;
    }

    private double degToRad(double deg){
        return deg * (Math.PI / 180);
    }

    private double radToDeg(double rad){
        return (180 * rad) / Math.PI;
    }
}
