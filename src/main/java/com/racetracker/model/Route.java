package com.racetracker.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "route_coordinates", joinColumns = @JoinColumn(name = "route_id"))
    private List<Coordinate> coordinates;
    
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return(this.name);
    }
    
    public void setCoordinates(List<Coordinate> coordinates) {
    	this.coordinates = coordinates;
    }
    
    public List<Coordinate> getCoordinates(){
    	return(coordinates);
    }
}
