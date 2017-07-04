package app.geo;

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

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "(" + this.lat + "," + this.lng + ")";
    }


    public double distance(GeoPos pos) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(pos.getLat() - this.lat);
        double lonDistance = Math.toRadians(pos.getLng() - this.lng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(pos.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

}
