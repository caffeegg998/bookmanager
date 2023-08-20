package com.megane.usermanager.dto;

import lombok.Data;

@Data
public class RatingStatisticsDTO {
    private long totalRatings;
    private double oneStarPercentage;
    private double twoStarPercentage;
    private double threeStarPercentage;
    private double fourStarPercentage;
    private double fiveStarPercentage;
}
