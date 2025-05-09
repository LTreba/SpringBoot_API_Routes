package com.racetracker.dto;

import java.util.List;

public class RouteGeoJsonListResponse {
    private List<RouteGeoJsonResponse> pistas;

    public List<RouteGeoJsonResponse> getPistas() {
        return pistas;
    }

    public void setPistas(List<RouteGeoJsonResponse> pistas) {
        this.pistas = pistas;
    }
}
