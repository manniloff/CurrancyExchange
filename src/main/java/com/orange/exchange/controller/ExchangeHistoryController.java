package com.orange.exchange.controller;

import com.orange.exchange.service.ExchangeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/exchange/history")
@RequiredArgsConstructor
public class ExchangeHistoryController {
    private final ExchangeHistoryService exchangeHistoryService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(exchangeHistoryService.findAll());
    }

    @GetMapping(value = "/from/{exchangeFrom}", produces = "application/json")
    public ResponseEntity<?> getByExchangeFrom(@PathVariable String exchangeFrom) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByChangeFrom(exchangeFrom));
    }

    @GetMapping(value = "/to/{exchangeTo}", produces = "application/json")
    public ResponseEntity<?> getByExchangeTo(@PathVariable String exchangeTo) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByChangeTo(exchangeTo));
    }

    @GetMapping(value = "/from/date", produces = "application/json")
    public ResponseEntity<?> getByExchangeFromAndTransactionDate(@RequestParam String exchangeFrom,
                                                                 @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime transactionDate) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByChangeFromTransactionDate(exchangeFrom, transactionDate));
    }

    @GetMapping(value = "/to/date", produces = "application/json")
    public ResponseEntity<?> getByExchangeToAndTransactionDate(@RequestParam String exchangeTo,
                                                               @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime transactionDate) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByChangeToTransactionDate(exchangeTo, transactionDate));
    }

    @GetMapping(value = "/transaction/date", produces = "application/json")
    public ResponseEntity<?> getByTransactionDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime transactionDate) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByTransactionDate(transactionDate));
    }

    @GetMapping(value = "/fromto", produces = "application/json")
    public ResponseEntity<?> getByExchangeFromTo(@RequestParam String exchangeFrom, @RequestParam String exchangeTo) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByChangeFromTo(exchangeFrom, exchangeTo));
    }

    @GetMapping(value = "/period", produces = "application/json")
    public ResponseEntity<?> getByTransactionDates(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime transactionDateFrom,
                                                  @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime transactionDateTo) {
        return ResponseEntity.ok(exchangeHistoryService.findAllByPeriod(transactionDateFrom, transactionDateTo));
    }
}
