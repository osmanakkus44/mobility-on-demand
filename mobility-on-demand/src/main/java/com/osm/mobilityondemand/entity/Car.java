package com.osm.mobilityondemand.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({ "demand" })
public class Car {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String model;
    private String engine;
    private String infotainmentSystem;
    private String interiorDesign;
    private String currentLocation;

    @OneToOne(mappedBy = "car")
    private Demand demand;

    public Car() {

    }

    public Car(String model, String engine, String infotainmentSystem, String interiorDesign, String currentLocation) {
        this.model = model;
        this.engine = engine;
        this.infotainmentSystem = infotainmentSystem;
        this.interiorDesign = interiorDesign;
        this.currentLocation = currentLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getInfotainmentSystem() {
        return infotainmentSystem;
    }

    public void setInfotainmentSystem(String infotainmentSystem) {
        this.infotainmentSystem = infotainmentSystem;
    }

    public String getInteriorDesign() {
        return interiorDesign;
    }

    public void setInteriorDesign(String interiorDesign) {
        this.interiorDesign = interiorDesign;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return  Objects.equals(id, car.id) &&
                Objects.equals(model, car.model) &&
                Objects.equals(engine, car.engine) &&
                Objects.equals(infotainmentSystem, car.infotainmentSystem) &&
                Objects.equals(interiorDesign, car.interiorDesign) &&
                Objects.equals(currentLocation, car.currentLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, engine, infotainmentSystem, interiorDesign, currentLocation);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("id=").append(id);
        sb.append(", model='").append(model).append('\'');
        sb.append(", engine='").append(engine).append('\'');
        sb.append(", infotainmentSystem='").append(infotainmentSystem).append('\'');
        sb.append(", interiorDesign='").append(interiorDesign).append('\'');
        sb.append(", currentLocation='").append(currentLocation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
