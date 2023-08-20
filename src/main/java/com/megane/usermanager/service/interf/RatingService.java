package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.RatingDTO;
import com.megane.usermanager.dto.RatingStatisticsDTO;
import com.megane.usermanager.dto.StaffDTO;
import com.megane.usermanager.entity.Rating;

public interface RatingService {
    public RatingStatisticsDTO calculateRatingStatistics(int bookId);
    public void addRating(Integer bookId,RatingDTO rating);
    public void addOrUpdateRatingForUser(Integer bookId, Integer userId, RatingDTO ratingDTO);

}
