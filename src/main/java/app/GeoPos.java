package app;

public class GeoPos {
    private double lat;
    private double lng;

    public GeoPos(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private GeoPos degToRad(){
        double radLat = this.lat * (Math.PI / 180);
        double radlng = this.lng * (Math.PI / 180);
        return new GeoPos(radLat, radlng);
    }

    private GeoPos radToDeg(){
        double degLat = (180 * this.lat) / Math.PI;
        double deglng = (180 * this.lng) / Math.PI;
        return new GeoPos(degLat, deglng);
    }

}
