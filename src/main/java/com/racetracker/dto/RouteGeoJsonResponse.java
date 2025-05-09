package com.racetracker.dto;

public class RouteGeoJsonResponse {
    private String nome;
    private GeoJson geojson;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GeoJson getGeojson() {
        return geojson;
    }

    public void setGeojson(GeoJson geojson) {
        this.geojson = geojson;
    }
}