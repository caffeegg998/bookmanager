package com.megane.usermanager.dto;

import lombok.Data;

@Data
public class RatingDTO {

    private int id;
    private int rating;
    private String comment;

    private String username;

}
