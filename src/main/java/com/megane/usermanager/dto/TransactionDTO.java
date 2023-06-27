package com.megane.usermanager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {
    private Long transactionId;
    private CustomerDTO customer;
    private BookDTO book;
    private Date transactionDate;
    private double amount;
    private int quantity;

    // Constructors, getters, and setters

    // ...
}

