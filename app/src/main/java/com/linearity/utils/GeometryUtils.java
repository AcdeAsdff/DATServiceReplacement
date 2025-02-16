package com.linearity.utils;

public class GeometryUtils {

    public static final double EARTH_RADIUS = 6371 * 1000; // Radius of the earth,meters

    /**
     *
     * <p>What on earth?distance on earth.</p>
     * <p>Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.</p>
     * <p>
     * lat1, lon1 Start point </p>
     * <p>
     * lat2, lon2 End point </p>
     * <p>
     * el1 Start altitude in meters</p>
     * <p>
     *     el2 End altitude in meters</p>
     * @returns Distance in Meters
     */
    public static double distanceOnEarth(double lat1, double lon1, double lat2,
                                         double lon2, double el1, double el2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }
    public static double distanceOnEarth(double lat1, double lon1, double lat2,
                                         double lon2) {
        return distanceOnEarth(lat1,lon1,lat2,lon2,0,0);
    }
}
