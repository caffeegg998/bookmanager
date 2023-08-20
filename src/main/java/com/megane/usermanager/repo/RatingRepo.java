package com.megane.usermanager.repo;

import com.megane.usermanager.entity.Book;
import com.megane.usermanager.entity.Rating;
import com.megane.usermanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Rating, Integer> {

    long countRatingByBookId(int rating);

    Rating findByUserAndBook(User user, Book book);

    long countByRatingAndBookId(int rating, int bookId);
}
