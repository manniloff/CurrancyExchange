package com.orange.exchange.controller;

import com.orange.exchange.dto.GlobalExchangeDto;
import com.orange.exchange.service.cndcur.CdnCurExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange/cdncur/")
@RequiredArgsConstructor
public class CdnCurExchangeController {
    private final CdnCurExchangeService cdnCurExchangeService;

    @PostMapping(value = {"","/"}, produces = "application/json")
    public ResponseEntity<?> getExchange(@RequestBody GlobalExchangeDto globalExchange) {
        return ResponseEntity.ok(cdnCurExchangeService.exchangeCurrency(globalExchange));
    }
}
