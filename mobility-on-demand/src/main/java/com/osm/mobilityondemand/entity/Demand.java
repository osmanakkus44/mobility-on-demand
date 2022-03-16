package com.osm.mobilityondemand.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Demand {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String pickUpLocation;
    private String dropOffLocation;
    private Date pickUpTime;
    private Date dropOffTime;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Demand() {
    }

    public Demand(String pickUpLocation, String dropOffLocation, Date pickUpTime, Date dropOffTime, Car car, User user) {
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
        this.pickUpTime = pickUpTime;
        this.dropOffTime = dropOffTime;
        this.car = car;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Date getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(Date dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return Objects.equals(id, demand.id) &&
                Objects.equals(pickUpLocation, demand.pickUpLocation) &&
                Objects.equals(dropOffLocation, demand.dropOffLocation) &&
                Objects.equals(pickUpTime, demand.pickUpTime) &&
                Objects.equals(dropOffTime, demand.dropOffTime) &&
                Objects.equals(car, demand.car) &&
                Objects.equals(user, demand.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pickUpLocation, dropOffLocation, pickUpTime, dropOffTime, car, user);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Demand{");
        sb.append("id=").append(id);
        sb.append(", pickUpLocation='").append(pickUpLocation).append('\'');
        sb.append(", dropOffLocation='").append(dropOffLocation).append('\'');
        sb.append(", pickUpTime=").append(pickUpTime);
        sb.append(", dropOffTime=").append(dropOffTime);
        sb.append(", car=").append(car);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
