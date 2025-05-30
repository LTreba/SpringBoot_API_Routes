package com.racetracker.controller;

import com.racetracker.dto.RouteGeoJsonListResponse;
import com.racetracker.model.Route;
import com.racetracker.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public ResponseEntity<RouteGeoJsonListResponse> getAllRoutes() {
        RouteGeoJsonListResponse geoJsonResponse = routeService.getAllRoutesGeoJson();
        return ResponseEntity.ok(geoJsonResponse);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadGeoJson(@RequestParam("file") MultipartFile file) {
        try {
            routeService.processGeoJson(file);
            return ResponseEntity.ok("Rotas salvas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.ok("Rota deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Rota não encontrada com ID: " + id);
        }
    }

}