package com.megane.usermanager.controller;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.entity.Rating;
import com.megane.usermanager.service.interf.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book-ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/statistics")
    public RatingStatisticsDTO getRatingStatistics(@RequestParam int bookId) {

        return ratingService.calculateRatingStatistics(bookId);
    }
    @PostMapping("/{bookId}")
    public ResponseDTO<RatingDTO> create(@AuthenticationPrincipal CurrentUser user,@PathVariable int bookId, @RequestBody @Valid RatingDTO ratingDTO) {
        int userId = user.getId();
        ratingService.addOrUpdateRatingForUser(bookId,userId,ratingDTO);
        ratingDTO.setUsername(user.getUsername());
        return ResponseDTO.<RatingDTO>builder()
                .status(200)
                .msg("ok")
                .data(ratingDTO)
                .build();
    }
}
