package com.orange.api.cdncur.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CdnCurCurrenсуRateDto {
    private String labelFrom;
    private String labelTo;
    private double rate;
    private LocalDate retrievedDate;
}
