package com.orange.api.currate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDto {
    private String label;
    private double rate;
    private LocalDate retrievedDate;
}
