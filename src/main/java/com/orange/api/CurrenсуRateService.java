package com.orange.api;

import com.orange.util.dto.CurrencyRateInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrenсуRateService<T,E> {
    void create(List<T> currencyRates);

    boolean exists(LocalDate retrievedDate);

    Optional<E> getCurrencyRateInfo(CurrencyRateInfo data);

    List<T> getRate();
}
