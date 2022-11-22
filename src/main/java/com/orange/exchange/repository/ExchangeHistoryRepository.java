package com.orange.exchange.repository;

import com.orange.exchange.model.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {
    List<ExchangeHistory> findAllByTransactionDate(LocalDateTime transactionDate);
    List<ExchangeHistory> findAllByChangeFromAndTransactionDate(String changeFrom, LocalDateTime transactionDate);
    List<ExchangeHistory> findAllByChangeToAndTransactionDate(String changeTo, LocalDateTime transactionDate);
    List<ExchangeHistory> findAllByChangeFrom(String changeFrom);
    List<ExchangeHistory> findAllByChangeTo(String changeTo);

    List<ExchangeHistory> findAllByChangeFromAndChangeTo(String changeFrom, String changeTo);

    List<ExchangeHistory> findAllByTransactionDateBetween(LocalDateTime transactionDateFrom, LocalDateTime transactionDateTo);
}
