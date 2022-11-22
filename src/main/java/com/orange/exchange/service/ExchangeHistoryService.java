package com.orange.exchange.service;

import com.orange.exchange.model.ExchangeHistory;
import com.orange.exchange.repository.ExchangeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeHistoryService {
    private final ExchangeHistoryRepository exchangeHistoryRepository;

    public List<ExchangeHistory> findAll() {
        return exchangeHistoryRepository.findAll();
    }

    public List<ExchangeHistory> findAllByTransactionDate(LocalDateTime transactionDate) {
        return exchangeHistoryRepository.findAllByTransactionDate(transactionDate);
    }

    public List<ExchangeHistory> findAllByChangeFrom(String changeFrom) {
        return exchangeHistoryRepository.findAllByChangeFrom(changeFrom);
    }

    public List<ExchangeHistory> findAllByChangeTo(String changeTo) {
        return exchangeHistoryRepository.findAllByChangeTo(changeTo);
    }

    public List<ExchangeHistory> findAllByChangeFromTransactionDate(String changeFrom, LocalDateTime transactionDate) {
        return exchangeHistoryRepository.findAllByChangeFromAndTransactionDate(changeFrom, transactionDate);
    }

    public List<ExchangeHistory> findAllByChangeToTransactionDate(String changeTo, LocalDateTime transactionDate) {
        return exchangeHistoryRepository.findAllByChangeToAndTransactionDate(changeTo, transactionDate);
    }

    public List<ExchangeHistory> findAllByChangeFromTo(String exchangeFrom, String exchangeTo) {
        return exchangeHistoryRepository.findAllByChangeFromAndChangeTo(exchangeFrom,exchangeTo);
    }

    public List<ExchangeHistory> findAllByPeriod(LocalDateTime transactionDateFrom, LocalDateTime transactionDateTo) {
        return exchangeHistoryRepository.findAllByTransactionDateBetween(transactionDateFrom, transactionDateTo);
    }

    public void create(ExchangeHistory exchangeHistory) {
        exchangeHistoryRepository.save(exchangeHistory);
    }

    public void clear() {
        exchangeHistoryRepository.deleteAll();
    }
}
