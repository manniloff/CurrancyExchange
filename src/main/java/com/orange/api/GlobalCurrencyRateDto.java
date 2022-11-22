package com.orange.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GlobalCurrencyRateDto {
    private String label;
    private double rate;
    private LocalDate retrievedDate;
}
