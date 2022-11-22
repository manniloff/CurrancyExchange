package com.orange.api.currate.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrateCurrency {
    private String status;
    private String message;
    private List<String> data;
}
