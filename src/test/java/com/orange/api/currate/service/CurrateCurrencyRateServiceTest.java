package com.orange.api.currate.service;

import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.currate.model.CurrateCurrencyRate;
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
public class CurrateCurrencyRateServiceTest {
    @Autowired
    private CurrateCurrencyRateService currateCurrencyRateService;

    @BeforeEach
    public void beforeEach() {
        currateCurrencyRateService.clear();
    }

    @Test
    void create() {
        //Check if data were inserted in db
        List<CurrateCurrencyRate> expected = generateTestData();

        List<CurrateCurrencyRate> actual = currateCurrencyRateService.getRate();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));

        //Check if we try to insert the same data to db it will be updated
        generateTestData();
        List<CurrateCurrencyRate> repeatActual = currateCurrencyRateService.getRate();

        assertEquals(3,repeatActual.size());
    }

    @Test
    void exists() {
        LocalDate currantDay = LocalDate.now();
        generateTestData();

        //Check if currency rate for current date already exists
        boolean expected = currateCurrencyRateService.exists(currantDay);

        assertTrue(expected);

        //Check if currency rate exists for yesterday
        boolean yesterdayExpected = currateCurrencyRateService.exists(currantDay.minusDays(1));

        assertFalse(yesterdayExpected);

        //Check if currency rate exists for tomorrow
        boolean tomorrowExpected = currateCurrencyRateService.exists(currantDay.plusDays(1));

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

        Optional<GlobalCurrencyRateDto> mdlUsdActual = currateCurrencyRateService.getCurrencyRateInfo(mdlUsdRateInfo);

        assertTrue(mdlUsdActual.isPresent());
        assertEquals(19.20,mdlUsdActual.get().getRate());

        //Check rate from MDL to EUR
        CurrencyRateInfo mdlEurRateInfo = new CurrencyRateInfo();
        mdlEurRateInfo.setCurrencyFrom("MDL");
        mdlEurRateInfo.setCurrencyTo("EUR");
        mdlEurRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> mdlEurActual = currateCurrencyRateService.getCurrencyRateInfo(mdlEurRateInfo);

        assertTrue(mdlEurActual.isPresent());
        assertEquals(19.15,mdlEurActual.get().getRate());

        //Check rate from MDL to USD
        CurrencyRateInfo mdlRubRateInfo = new CurrencyRateInfo();
        mdlRubRateInfo.setCurrencyFrom("MDL");
        mdlRubRateInfo.setCurrencyTo("RUB");
        mdlRubRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> mdlRubActual = currateCurrencyRateService.getCurrencyRateInfo(mdlRubRateInfo);

        assertTrue(mdlRubActual.isPresent());
        assertEquals(0.31,mdlRubActual.get().getRate());

        //Check if try to check wrong currency rate
        CurrencyRateInfo wrongRateInfo = new CurrencyRateInfo();
        wrongRateInfo.setCurrencyFrom("AAA");
        wrongRateInfo.setCurrencyTo("BBB");
        wrongRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> wrongActual = currateCurrencyRateService.getCurrencyRateInfo(wrongRateInfo);

        assertFalse(wrongActual.isPresent());
    }

    private List<CurrateCurrencyRate> generateTestData() {
        LocalDate toDate = LocalDate.now();
        List<CurrateCurrencyRate> expected = new ArrayList<>();
        CurrateCurrencyRate firstCurrency = new CurrateCurrencyRate();
        firstCurrency.setLabel("MDLUSD");
        firstCurrency.setRate(19.20);
        firstCurrency.setRetrievedDate(toDate);
        expected.add(firstCurrency);

        CurrateCurrencyRate secondCurrency = new CurrateCurrencyRate();
        secondCurrency.setLabel("MDLEUR");
        secondCurrency.setRate(19.15);
        secondCurrency.setRetrievedDate(toDate);
        expected.add(secondCurrency);

        CurrateCurrencyRate thirdCurrency = new CurrateCurrencyRate();
        thirdCurrency.setLabel("MDLRUB");
        thirdCurrency.setRate(0.31);
        thirdCurrency.setRetrievedDate(toDate);
        expected.add(thirdCurrency);


        currateCurrencyRateService.create(expected);
        return expected;
    }
}
