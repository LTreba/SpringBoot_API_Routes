package com.racetracker.dto;

import java.util.Collections;
import java.util.Map;

public class GeoJsonFeature {
    private String type;
    private GeoJsonGeometry geometry;
    private Map<String, Object> properties = Collections.emptyMap();

    // Getters e setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoJsonGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonGeometry geometry) {
        this.geometry = geometry;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
