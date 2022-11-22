package com.orange.api.currate.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GlobalCurrateCurrancyRate {
    private String status;
    private String message;
    private Map<String, Double> data;
}
