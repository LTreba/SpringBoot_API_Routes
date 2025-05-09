package com.racetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetracker.dto.GeoJson;
import com.racetracker.dto.GeoJsonFeature;
import com.racetracker.dto.GeoJsonGeometry;
import com.racetracker.dto.RouteGeoJsonListResponse;
import com.racetracker.dto.RouteGeoJsonResponse;
import com.racetracker.model.Coordinate;
import com.racetracker.model.Route;
import com.racetracker.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    private Integer count = 0;
    
    private List<String> pistas = Arrays.asList(
    	    "Aldeia", "Interlagos", "NelsonPiquet", "Piracibaca", "SantaCruz", "Taruma"
    	);

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public RouteGeoJsonListResponse getAllRoutesGeoJson() {
        List<Route> routes = routeRepository.findAll();
        List<RouteGeoJsonResponse> response = new ArrayList<>();

        for (Route route : routes) {
            RouteGeoJsonResponse routeGeoJson = new RouteGeoJsonResponse();
            routeGeoJson.setNome(route.getName());
            routeGeoJson.setGeojson(createGeoJson(route));
            response.add(routeGeoJson);
        }

        RouteGeoJsonListResponse geoJsonListResponse = new RouteGeoJsonListResponse();
        geoJsonListResponse.setPistas(response);

        return geoJsonListResponse;
    }

    private GeoJson createGeoJson(Route route) {
        GeoJson geoJson = new GeoJson();
        geoJson.setType("FeatureCollection");

        List<GeoJsonFeature> features = new ArrayList<>();
        GeoJsonFeature feature = new GeoJsonFeature();
        feature.setType("Feature");

        GeoJsonGeometry geometry = new GeoJsonGeometry();
        geometry.setType("LineString");

        List<List<Double>> coordinates = new ArrayList<>();
        for (Coordinate coord : route.getCoordinates()) {
            List<Double> coordinate = new ArrayList<>();
            coordinate.add(coord.getLongitude());
            coordinate.add(coord.getLatitude());
            coordinates.add(coordinate);
        }

        geometry.setCoordinates(coordinates);
        feature.setGeometry(geometry);
        features.add(feature);

        geoJson.setFeatures(features);
        return geoJson;
    }
    
    public void processGeoJsonMocked(InputStream inputStream) throws Exception {
        GeoJson geoJson = objectMapper.readValue(inputStream, GeoJson.class);

        if (geoJson.getFeatures() == null || geoJson.getFeatures().isEmpty()) {
            throw new IllegalArgumentException("GeoJSON não contém features.");
        }

        GeoJsonFeature feature = geoJson.getFeatures().get(0);
        GeoJsonGeometry geometry = feature.getGeometry();

        if (!"LineString".equalsIgnoreCase(geometry.getType())) {
            throw new IllegalArgumentException("A geometria precisa ser do tipo LineString.");
        }

        List<Coordinate> coordinates = new ArrayList<>();
        for (List<Double> coord : geometry.getCoordinates()) {
            if (coord.size() >= 2) {
                coordinates.add(new Coordinate(coord.get(0), coord.get(1)));
            }
        }
        Route route = new Route();
        route.setName(pistas.get(count++));
        route.setCoordinates(coordinates);

        routeRepository.save(route);
    }
    
    public void processGeoJson(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            GeoJson geoJson = objectMapper.readValue(inputStream, GeoJson.class);

            if (geoJson.getFeatures() == null || geoJson.getFeatures().isEmpty()) {
                throw new IllegalArgumentException("GeoJSON não contém features.");
            }

            GeoJsonFeature feature = geoJson.getFeatures().get(0);
            GeoJsonGeometry geometry = feature.getGeometry();

            if (!"LineString".equalsIgnoreCase(geometry.getType())) {
                throw new IllegalArgumentException("A geometria precisa ser do tipo LineString.");
            }

            List<Coordinate> coordinates = new ArrayList<>();
            for (List<Double> coord : geometry.getCoordinates()) {
                if (coord.size() >= 2) {
                    coordinates.add(new Coordinate(coord.get(0), coord.get(1)));
                }
            }

            Route route = new Route();
            route.setName("Rota do Arquivo"); 
            route.setCoordinates(coordinates);

            routeRepository.save(route);
        }
    }


}
