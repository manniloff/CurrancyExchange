package com.orange.exchange.service.cndcur;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.exchange.dto.GlobalExchangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CdnCurExchangeServiceTest {

    @Autowired
    private CdnCurExchangeService cdnCurExchangeService;

    @Autowired
    private CurrenсуRateService<CdnCurCurrenсуRate, GlobalCurrencyRateDto> currenсуRateService;

    @Test
    void exchangeCurrency() {
        generateTestData();
        DecimalFormat doubleFormat = new DecimalFormat("#.##");

        GlobalExchangeDto mdlUsdGlobalExchange = new GlobalExchangeDto();
        mdlUsdGlobalExchange.setFrom("MDL");
        mdlUsdGlobalExchange.setTo("USD");
        mdlUsdGlobalExchange.setCount(1920);

        double actualMdlUsdResult = cdnCurExchangeService.exchangeCurrency(mdlUsdGlobalExchange);

        assertEquals(100, actualMdlUsdResult);

        GlobalExchangeDto mdlEurGlobalExchange = new GlobalExchangeDto();
        mdlEurGlobalExchange.setFrom("MDL");
        mdlEurGlobalExchange.setTo("EUR");
        mdlEurGlobalExchange.setCount(100 * (19.20/1.01));

        double actualMdlEurResult = cdnCurExchangeService.exchangeCurrency(mdlEurGlobalExchange);

        assertEquals(100, actualMdlEurResult);

        GlobalExchangeDto rubMdlGlobalExchange = new GlobalExchangeDto();
        rubMdlGlobalExchange.setFrom("RUB");
        rubMdlGlobalExchange.setTo("MDL");
        rubMdlGlobalExchange.setCount(100);

        double actualRubMdlResult = cdnCurExchangeService.exchangeCurrency(rubMdlGlobalExchange);

        assertEquals( Double.parseDouble(doubleFormat.format(100 * (19.20/58.76))), actualRubMdlResult);

        GlobalExchangeDto eurMdlGlobalExchange = new GlobalExchangeDto();
        eurMdlGlobalExchange.setFrom("EUR");
        eurMdlGlobalExchange.setTo("MDL");
        eurMdlGlobalExchange.setCount(100);

        double actualEurMdlResult = cdnCurExchangeService.exchangeCurrency(eurMdlGlobalExchange);

        assertEquals( Double.parseDouble(doubleFormat.format(100 * (19.20/1.01))), actualEurMdlResult);

        GlobalExchangeDto eurRubGlobalExchange = new GlobalExchangeDto();
        eurRubGlobalExchange.setFrom("EUR");
        eurRubGlobalExchange.setTo("RUB");
        eurRubGlobalExchange.setCount(100);

        double actualEurRubResult = cdnCurExchangeService.exchangeCurrency(eurRubGlobalExchange);

        assertEquals(Double.parseDouble(doubleFormat.format(100 * (58.76/1.01))), actualEurRubResult);

        //Wrong Data
        GlobalExchangeDto eurRonGlobalExchange = new GlobalExchangeDto();
        eurRonGlobalExchange.setFrom("EUR");
        eurRonGlobalExchange.setTo("RON");
        eurRonGlobalExchange.setCount(100);

        double actualEurRonResult = cdnCurExchangeService.exchangeCurrency(eurRonGlobalExchange);

        assertEquals(0,actualEurRonResult);
    }

    private void generateTestData() {
        LocalDate currentDate= LocalDate.now();
        CdnCurCurrenсуRate firstCdnCur = new CdnCurCurrenсуRate();
        firstCdnCur.setLabel("MDL");
        firstCdnCur.setRate(19.20);
        firstCdnCur.setRetrievedDate(currentDate);

        CdnCurCurrenсуRate secondCdnCur = new CdnCurCurrenсуRate();
        secondCdnCur.setLabel("EUR");
        secondCdnCur.setRate(1.01);
        secondCdnCur.setRetrievedDate(currentDate);

        CdnCurCurrenсуRate thirdCdnCur = new CdnCurCurrenсуRate();
        thirdCdnCur.setLabel("RUB");
        thirdCdnCur.setRate(58.76);
        thirdCdnCur.setRetrievedDate(currentDate);

        currenсуRateService.create(List.of(firstCdnCur,secondCdnCur,thirdCdnCur));
    }
}