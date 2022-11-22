package com.orange.api.cdncur.repository;

import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CdnCurCurrencyRateRepository extends JpaRepository<CdnCurCurrenсуRate,Long> {
    int countAllByRetrievedDate(LocalDate retrievedDate);

    void deleteAllByRetrievedDate(LocalDate retrievedDate);

    List<CdnCurCurrenсуRate> findByLabelOrLabelAndRetrievedDate(String labelFrom,String labelTo, LocalDate date);
}
