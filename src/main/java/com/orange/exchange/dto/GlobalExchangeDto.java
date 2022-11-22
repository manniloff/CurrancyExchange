package com.orange.exchange.dto;

import lombok.Data;

@Data
public class GlobalExchangeDto {
    private String from;
    private String to;
    private double count;
}
