package com.pld.velociraptor.tools;

/**
 * Created by Thibault on 30/04/2016.
 */
public class VeloFilter {
    private Integer limit;
    private Integer minDistance;
    private Integer maxDistance;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minElevation;
    private Integer maxElevation;

    public VeloFilter(Integer limit, Integer minDistance, Integer maxDistance, Integer minPrice, Integer maxPrice, Integer minElevation, Integer maxElevation) {
        this.limit = limit;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;
    }

    public Integer getMaxElevation() {
        return maxElevation;
    }

    public void setMaxElevation(Integer maxElevation) {
        this.maxElevation = maxElevation;
    }

    public Integer getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Integer minDistance) {
        this.minDistance = minDistance;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinElevation() {
        return minElevation;
    }

    public void setMinElevation(Integer minElevation) {
        this.minElevation = minElevation;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
