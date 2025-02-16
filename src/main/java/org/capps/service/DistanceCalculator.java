package org.capps.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371; // Earth's radius in kilometers

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS_KM * c;
    }

    public double calculateCost(double distance, double costPerKm) {
        return distance * costPerKm;
    }

    public double calculateCostBasedOnDistance(double lat1, double lon1, double lat2, double lon2, double costPerKm) {
        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        return calculateCost(distance, costPerKm);
    }
}
