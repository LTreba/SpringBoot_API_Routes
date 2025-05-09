package com.racetracker.dto;

import java.util.List;

public class GeoJsonGeometry {
    private String type;
    private List<List<Double>> coordinates;

    // Getters e setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }
}
