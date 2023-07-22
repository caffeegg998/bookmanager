package com.megane.usermanager.dto.kafka;

import java.util.Date;

public class StatisticDTO {
    private String message;
    private Date createdDate;

    public StatisticDTO(){

    }
    public StatisticDTO(String message, Date createdDate) {
        super();
        this.message = message;
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
