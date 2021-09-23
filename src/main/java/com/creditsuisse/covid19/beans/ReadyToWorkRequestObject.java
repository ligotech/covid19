package com.creditsuisse.covid19.beans;

import javax.validation.constraints.NotNull;

public class ReadyToWorkRequestObject {

    @NotNull(message = "latitude value is required")
    private Double latitude;
    @NotNull(message = "longitude value is required")
    private Double longitude;
    @NotNull(message = "radius value is required")
    private Double radius;
    @NotNull(message = "population value is required")
    private Long population;
    @NotNull(message = "threshold value is required")
    private Double threshold;
    @NotNull
    private Integer date;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
