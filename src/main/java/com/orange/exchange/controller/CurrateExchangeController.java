package com.orange.exchange.controller;

import com.orange.exchange.dto.GlobalExchangeDto;
import com.orange.exchange.service.currate.CurrateExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange/currate")
@RequiredArgsConstructor
public class CurrateExchangeController {
    private final CurrateExchangeService currateExchangeService;

    @PostMapping(value = {"","/"}, produces = "application/json")
    public ResponseEntity<?> getExchange(@RequestBody  GlobalExchangeDto globalExchange) {
        return ResponseEntity.ok(currateExchangeService.exchangeCurrency(globalExchange));
    }
}
