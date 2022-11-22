package com.orange.api.controller;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRate;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.api.currate.model.CurrateCurrencyRate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rate")
@RequiredArgsConstructor
public class CurrencyRateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRateController.class);
    private final GlobalCurrencyRate globalCurrencyRate;
    private final CurrenсуRateService<CdnCurCurrenсуRate, GlobalCurrencyRateDto> cdnCurRateService;

    private final CurrenсуRateService<CurrateCurrencyRate, GlobalCurrencyRateDto> currateRateService;

    @GetMapping(value = "/cdncur", produces = "application/json")
    ResponseEntity<?> getCdnCurRate() {
        return ResponseEntity.ok(cdnCurRateService.getRate());
    }

    @GetMapping(value = "/currate", produces = "application/json")
    ResponseEntity<?> getCurrateRate() {
        return ResponseEntity.ok(currateRateService.getRate());
    }

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> updateCurrencyRate() {
        try {
            LOGGER.info("Updating currency rate");
            globalCurrencyRate.getCurrencyRate();
            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            LOGGER.error("Error on getting update fro currency rate!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
