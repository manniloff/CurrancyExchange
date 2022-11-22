package com.orange.api.currate.repository;

import com.orange.api.currate.model.CurrateCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrateCurrencyRateRepository extends JpaRepository<CurrateCurrencyRate, Long> {
    int countAllByRetrievedDate(LocalDate retrievedDate);

    void deleteAllByRetrievedDate(LocalDate retrievedDate);

    Optional<CurrateCurrencyRate> findByLabelContainsAndRetrievedDate(String label, LocalDate date);
}
