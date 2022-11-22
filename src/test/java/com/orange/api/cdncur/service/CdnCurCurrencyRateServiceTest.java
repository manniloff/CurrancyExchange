package com.orange.api.cdncur.service;

import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.util.dto.CurrencyRateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CdnCurCurrencyRateServiceTest {

    @Autowired
    private CdnCurCurrencyRateService cdnCurCurrencyRateService;

    @BeforeEach
    public void beforeEach() {
        cdnCurCurrencyRateService.clear();
    }

    @Test
    void create() {
        //Check if data were inserted in db
        List<CdnCurCurrenсуRate> expected = generateTestData();

        List<CdnCurCurrenсуRate> actual = cdnCurCurrencyRateService.getRate();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));

        //Check if we try to insert the same data to db it will be updated
        cdnCurCurrencyRateService.create(expected);
        List<CdnCurCurrenсуRate> repeatActual = cdnCurCurrencyRateService.getRate();

        assertEquals(3, repeatActual.size());
    }

    @Test
    void exists() {
        LocalDate currantDay = LocalDate.now();
        generateTestData();

        //Check if currency rate for current date already exists
        boolean expected = cdnCurCurrencyRateService.exists(currantDay);

        assertTrue(expected);

        //Check if currency rate exists for yesterday
        boolean yesterdayExpected = cdnCurCurrencyRateService.exists(currantDay.minusDays(1));

        assertFalse(yesterdayExpected);

        //Check if currency rate exists for tomorrow
        boolean tomorrowExpected = cdnCurCurrencyRateService.exists(currantDay.plusDays(1));

        assertFalse(tomorrowExpected);
    }

    @Test
    void getCurrencyRateInfo() {
        generateTestData();
        //Check rate from MDL to USD
        CurrencyRateInfo mdlUsdRateInfo = new CurrencyRateInfo();
        mdlUsdRateInfo.setCurrencyFrom("MDL");
        mdlUsdRateInfo.setCurrencyTo("USD");
        mdlUsdRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> mdlUsdActual = cdnCurCurrencyRateService.getCurrencyRateInfo(mdlUsdRateInfo);

        assertTrue(mdlUsdActual.isPresent());
        assertEquals(19.20, mdlUsdActual.get().getRate());

        //Check rate from MDL to EUR
        CurrencyRateInfo mdlEurRateInfo = new CurrencyRateInfo();
        mdlEurRateInfo.setCurrencyFrom("MDL");
        mdlEurRateInfo.setCurrencyTo("EUR");
        mdlEurRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> mdlEurActual = cdnCurCurrencyRateService.getCurrencyRateInfo(mdlEurRateInfo);

        assertTrue(mdlEurActual.isPresent());
        assertEquals(19.20/1.01, mdlEurActual.get().getRate());

        //Check rate from MDL to RUB
        CurrencyRateInfo mdlRubRateInfo = new CurrencyRateInfo();
        mdlRubRateInfo.setCurrencyFrom("MDL");
        mdlRubRateInfo.setCurrencyTo("RUB");
        mdlRubRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> mdlRubActual = cdnCurCurrencyRateService.getCurrencyRateInfo(mdlRubRateInfo);

        assertTrue(mdlRubActual.isPresent());
        assertEquals(19.20/58.76, mdlRubActual.get().getRate());

        //Check if try to check wrong currency rate
        CurrencyRateInfo wrongRateInfo = new CurrencyRateInfo();
        wrongRateInfo.setCurrencyFrom("AAA");
        wrongRateInfo.setCurrencyTo("BBB");
        wrongRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> wrongActual = cdnCurCurrencyRateService.getCurrencyRateInfo(wrongRateInfo);

        assertFalse(wrongActual.isPresent());

        //Check rate from RUB to MDL
        CurrencyRateInfo rubMdlRateInfo = new CurrencyRateInfo();
        rubMdlRateInfo.setCurrencyFrom("RUB");
        rubMdlRateInfo.setCurrencyTo("MDL");
        rubMdlRateInfo.setDate(LocalDate.now());

        assertTrue(mdlEurActual.isPresent());
        Optional<GlobalCurrencyRateDto> rubMdlActual = cdnCurCurrencyRateService.getCurrencyRateInfo(rubMdlRateInfo);

        assertTrue(rubMdlActual.isPresent());
        assertEquals(58.76/19.20, rubMdlActual.get().getRate());

        //Check rate from EUR to MDL
        CurrencyRateInfo eurMdlRateInfo = new CurrencyRateInfo();
        eurMdlRateInfo.setCurrencyFrom("EUR");
        eurMdlRateInfo.setCurrencyTo("MDL");
        eurMdlRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> eurMdlActual = cdnCurCurrencyRateService.getCurrencyRateInfo(eurMdlRateInfo);

        assertTrue(eurMdlActual.isPresent());
        assertEquals(1.01/19.20, eurMdlActual.get().getRate());
    }

    private List<CdnCurCurrenсуRate> generateTestData() {
        LocalDate toDate = LocalDate.now();
        List<CdnCurCurrenсуRate> cdnCurCurrenсуRates = new ArrayList<>();

        CdnCurCurrenсуRate firstCurrencyRate = new CdnCurCurrenсуRate();
        firstCurrencyRate.setLabel("MDL");
        firstCurrencyRate.setRate(19.20);
        firstCurrencyRate.setRetrievedDate(toDate);
        cdnCurCurrenсуRates.add(firstCurrencyRate);

        CdnCurCurrenсуRate secondCurrencyRate = new CdnCurCurrenсуRate();
        secondCurrencyRate.setLabel("EUR");
        secondCurrencyRate.setRate(1.01);
        secondCurrencyRate.setRetrievedDate(toDate);
        cdnCurCurrenсуRates.add(secondCurrencyRate);

        CdnCurCurrenсуRate thirdCurrencyRate = new CdnCurCurrenсуRate();
        thirdCurrencyRate.setLabel("RUB");
        thirdCurrencyRate.setRate(58.76);
        thirdCurrencyRate.setRetrievedDate(toDate);
        cdnCurCurrenсуRates.add(thirdCurrencyRate);

        cdnCurCurrencyRateService.create(cdnCurCurrenсуRates);

        return cdnCurCurrenсуRates;
    }
}
