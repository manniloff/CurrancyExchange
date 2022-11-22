package com.orange.api.currate.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currate_currency_rate")
public class CurrateCurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;

    private String label;

    private double rate;

    private LocalDate retrievedDate;
}
