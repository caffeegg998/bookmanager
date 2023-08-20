package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.RatingDTO;
import com.megane.usermanager.dto.RatingStatisticsDTO;
import com.megane.usermanager.dto.StaffDTO;
import com.megane.usermanager.entity.Book;
import com.megane.usermanager.entity.Rating;
import com.megane.usermanager.entity.Staff;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.BookRepo;
import com.megane.usermanager.repo.RatingRepo;
import com.megane.usermanager.repo.UserRepo;
import com.megane.usermanager.service.interf.RatingService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    BookRepo bookRepo;

    @Autowired
    UserRepo userRepo;
    @Override
    public RatingStatisticsDTO calculateRatingStatistics(int bookId) {
        long totalRatings = ratingRepo.countRatingByBookId(bookId);

        // Lấy số lượng đánh giá cho mỗi số sao
        long oneStarCount = ratingRepo.countByRatingAndBookId(1, bookId);
        long twoStarCount = ratingRepo.countByRatingAndBookId(2, bookId);
        long threeStarCount = ratingRepo.countByRatingAndBookId(3, bookId);
        long fourStarCount = ratingRepo.countByRatingAndBookId(4, bookId);
        long fiveStarCount = ratingRepo.countByRatingAndBookId(5, bookId);

        RatingStatisticsDTO statistics = new RatingStatisticsDTO();
        statistics.setTotalRatings(totalRatings);
        statistics.setOneStarPercentage((double) oneStarCount / totalRatings * 100);
        statistics.setTwoStarPercentage((double) twoStarCount / totalRatings * 100);
        statistics.setThreeStarPercentage((double) threeStarCount / totalRatings * 100);
        statistics.setFourStarPercentage((double) fourStarCount / totalRatings * 100);
        statistics.setFiveStarPercentage((double) fiveStarCount / totalRatings * 100);

        return statistics;
    }

    @Override
    public void addRating(Integer bookId,RatingDTO ratingDTO) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Rating rating = new ModelMapper().map(ratingDTO,Rating.class);
        rating.setBook(book);
        ratingRepo.save(rating);
    }

    @Override
    public void addOrUpdateRatingForUser(Integer bookId, Integer userId, RatingDTO ratingDTO) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // Kiểm tra xem người dùng có tồn tại không
        User user = userRepo.findById(userId)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        // Kiểm tra xem người dùng đã có đánh giá cho cuốn sách chưa

        Rating existingRating = ratingRepo.findByUserAndBook(user, book);

        Rating rating = new ModelMapper().map(ratingDTO,Rating.class);

        if (existingRating == null) {
            // Nếu người dùng chưa có đánh giá, thêm đánh giá mới
            rating.setBook(book);
            rating.setUser(user);
            ratingRepo.save(rating);
        } else {
            // Nếu người dùng đã có đánh giá, cập nhật đánh giá hiện có
            existingRating.setRating(rating.getRating());
            ratingRepo.save(existingRating);
        }
    }


}
