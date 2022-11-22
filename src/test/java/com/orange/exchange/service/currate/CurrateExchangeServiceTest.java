package com.orange.exchange.service.currate;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.currate.model.CurrateCurrencyRate;
import com.orange.exchange.dto.GlobalExchangeDto;
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
class CurrateExchangeServiceTest {

    @Autowired
    private CurrateExchangeService currateExchangeService;

    @Autowired
    private CurrenсуRateService<CurrateCurrencyRate, GlobalCurrencyRateDto> currenсуRateService;

    @Test
    void exchangeCurrency() {
        generateTestData();
        DecimalFormat doubleFormat = new DecimalFormat("#.##");

        GlobalExchangeDto mdlUsdGlobalExchange = new GlobalExchangeDto();
        mdlUsdGlobalExchange.setFrom("MDL");
        mdlUsdGlobalExchange.setTo("USD");
        mdlUsdGlobalExchange.setCount(1920);

        double actualMdlUsdResult = currateExchangeService.exchangeCurrency(mdlUsdGlobalExchange);

        assertEquals(100, Math.round(actualMdlUsdResult));

        GlobalExchangeDto mdlEurGlobalExchange = new GlobalExchangeDto();
        mdlEurGlobalExchange.setFrom("MDL");
        mdlEurGlobalExchange.setTo("EUR");
        mdlEurGlobalExchange.setCount(100);

        double actualMdlEurResult = currateExchangeService.exchangeCurrency(mdlEurGlobalExchange);

        assertEquals(Double.parseDouble(doubleFormat.format(100 * 0.052)), actualMdlEurResult);

        GlobalExchangeDto rubMdlGlobalExchange = new GlobalExchangeDto();
        rubMdlGlobalExchange.setFrom("RUB");
        rubMdlGlobalExchange.setTo("MDL");
        rubMdlGlobalExchange.setCount(100);

        double actualRubMdlResult = currateExchangeService.exchangeCurrency(rubMdlGlobalExchange);

        assertEquals(Double.parseDouble(doubleFormat.format(100 * 0.31)), actualRubMdlResult);

        GlobalExchangeDto eurMdlGlobalExchange = new GlobalExchangeDto();
        eurMdlGlobalExchange.setFrom("EUR");
        eurMdlGlobalExchange.setTo("MDL");
        eurMdlGlobalExchange.setCount(100);

        double actualEurMdlResult = currateExchangeService.exchangeCurrency(eurMdlGlobalExchange);

        assertEquals( Double.parseDouble(doubleFormat.format(100 * 19.15)), actualEurMdlResult);

        GlobalExchangeDto eurRubGlobalExchange = new GlobalExchangeDto();
        eurRubGlobalExchange.setFrom("EUR");
        eurRubGlobalExchange.setTo("RUB");
        eurRubGlobalExchange.setCount(100);

        double actualEurRubResult = currateExchangeService.exchangeCurrency(eurRubGlobalExchange);

        assertEquals(Double.parseDouble(doubleFormat.format(100 * 60)), actualEurRubResult);

        //Wrong Data
        GlobalExchangeDto eurRonGlobalExchange = new GlobalExchangeDto();
        eurRonGlobalExchange.setFrom("EUR");
        eurRonGlobalExchange.setTo("RON");
        eurRonGlobalExchange.setCount(100);

        double actualEurRonResult = currateExchangeService.exchangeCurrency(eurRonGlobalExchange);

        assertEquals(0,actualEurRonResult);
    }

    private void generateTestData() {
        LocalDate currentDate= LocalDate.now();
        CurrateCurrencyRate firstCurrate = new CurrateCurrencyRate();
        firstCurrate.setLabel("MDLUSD");
        firstCurrate.setRate(0.052);
        firstCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate secondCurrate = new CurrateCurrencyRate();
        secondCurrate.setLabel("EURUSD");
        secondCurrate.setRate(1.01);
        secondCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate thirdCurrate = new CurrateCurrencyRate();
        thirdCurrate.setLabel("RUBUSD");
        thirdCurrate.setRate(58.76);
        thirdCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate fourthCurrate = new CurrateCurrencyRate();
        fourthCurrate.setLabel("MDLEUR");
        fourthCurrate.setRate(0.052);
        fourthCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate fifthCurrate = new CurrateCurrencyRate();
        fifthCurrate.setLabel("RUBMDL");
        fifthCurrate.setRate(0.31);
        fifthCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate sixthCurrate = new CurrateCurrencyRate();
        sixthCurrate.setLabel("EURMDL");
        sixthCurrate.setRate(19.15);
        sixthCurrate.setRetrievedDate(currentDate);

        CurrateCurrencyRate seventhCurrate = new CurrateCurrencyRate();
        seventhCurrate.setLabel("EURRUB");
        seventhCurrate.setRate(60);
        seventhCurrate.setRetrievedDate(currentDate);

        currenсуRateService.create(List.of(firstCurrate,secondCurrate,thirdCurrate, fourthCurrate, fifthCurrate, sixthCurrate, seventhCurrate));
    }
}