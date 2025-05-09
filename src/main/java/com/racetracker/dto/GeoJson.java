package com.racetracker.dto;

import java.util.List;

public class GeoJson {
    private String type;
    private List<GeoJsonFeature> features;

    // Getters e setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GeoJsonFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<GeoJsonFeature> features) {
        this.features = features;
    }
}