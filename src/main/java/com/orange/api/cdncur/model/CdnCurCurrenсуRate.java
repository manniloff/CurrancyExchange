package com.orange.api.cdncur.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cdn_cur_currency_rate")
public class CdnCurCurrenсуRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;

    private String label;

    private double rate;

    private LocalDate retrievedDate;
}
