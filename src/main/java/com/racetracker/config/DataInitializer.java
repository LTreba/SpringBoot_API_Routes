package com.racetracker.config;

import com.racetracker.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RouteService routeService;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String[] FILES = {
        "classpath:routes/Aldeia.geojson",
        "classpath:routes/Interlagos.geojson",
        "classpath:routes/NelsonPiquet.geojson",
        "classpath:routes/Piracicaba.geojson",
        "classpath:routes/SantaCruz.geojson",
        "classpath:routes/Taruma.geojson"
    };

    @Override
    public void run(String... args) throws Exception {
        for (String filePath : FILES) {
            Resource resource = resourceLoader.getResource(filePath);
            try (InputStream inputStream = resource.getInputStream()) {
                routeService.processGeoJsonMocked(inputStream);
                System.out.println("Rota carregada: " + filePath);
            } catch (Exception e) {
                System.err.println("Erro ao carregar " + filePath + ": " + e.getMessage());
            }
        }
    }
}
