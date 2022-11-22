package com.orange.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exchange_history")
public class ExchangeHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String changeFrom;

    private double changedValue;

    private String changeTo;

    private double retrievedValue;

    private LocalDateTime transactionDate;
}