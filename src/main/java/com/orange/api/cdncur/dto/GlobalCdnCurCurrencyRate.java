package com.orange.api.cdncur.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class GlobalCdnCurCurrencyRate {
    private String table;
    private Map<String, Double> rates;
    private LocalDateTime lastUpdate;
}
