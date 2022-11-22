package com.orange.util.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrencyRateInfo {
    private String currencyFrom;
    private String currencyTo;
    private LocalDate date;
}
