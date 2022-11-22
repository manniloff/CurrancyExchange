package com.orange.exchange.service;

import com.orange.exchange.model.ExchangeHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeHistoryServiceTest {
    @Autowired
    private ExchangeHistoryService exchangeHistoryService;

    private List<ExchangeHistory> testData = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        testData = exchangeHistoryService.findAll();
        if(testData.isEmpty()) {
            testData = generateTestData();
        }
    }

    @Test
    public void findAll() {
        List<ExchangeHistory> expected = testData;
        List<ExchangeHistory> actual = exchangeHistoryService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void findAllByTransactionDate() {
        LocalDateTime currentDate = LocalDateTime.now().withNano(0);
        List<ExchangeHistory> expectedCurrentDate = testData.stream()
                .filter(currency -> currency.getTransactionDate().equals(currentDate)).toList();

        List<ExchangeHistory> actualCurrentDate = exchangeHistoryService.findAllByTransactionDate(currentDate);

        assertEquals(expectedCurrentDate.size(), actualCurrentDate.size());
        assertEquals(expectedCurrentDate, actualCurrentDate);
        List<ExchangeHistory> expectedYesterdayDate = testData.stream()
                .filter(currency -> currency.getTransactionDate().equals(currentDate.minusDays(1))).toList();

        List<ExchangeHistory> actualYesterdayDate = exchangeHistoryService.findAllByTransactionDate(currentDate.minusDays(1));

        assertEquals(expectedYesterdayDate.size(), actualYesterdayDate.size());
        assertEquals(expectedYesterdayDate, actualYesterdayDate);

        List<ExchangeHistory> expectedDayBeforeYesterdayDate = testData.stream()
                .filter(currency -> currency.getTransactionDate().equals(currentDate.minusDays(2))).toList();

        List<ExchangeHistory> actualDayBeforeYesterdayDate = exchangeHistoryService.findAllByTransactionDate(currentDate.minusDays(2));

        assertEquals(expectedDayBeforeYesterdayDate.size(), actualDayBeforeYesterdayDate.size());
        assertEquals(expectedDayBeforeYesterdayDate, actualDayBeforeYesterdayDate);

        //Request with no exist Transaction Date
        List<ExchangeHistory> actualNotExistDate = exchangeHistoryService.findAllByTransactionDate(currentDate.minusDays(5));

        assertEquals(List.of(), actualNotExistDate);
    }
    @Test
    public void findAllByChangeFrom() {
        List<ExchangeHistory> mdlExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("MDL"))
                .toList();

        List<ExchangeHistory> mdlActual = exchangeHistoryService.findAllByChangeFrom("MDL");

        assertEquals(mdlExpected.size(), mdlActual.size());
        assertEquals(mdlExpected, mdlActual);

        List<ExchangeHistory> usdExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("USD"))
                .toList();

        List<ExchangeHistory> usdActual = exchangeHistoryService.findAllByChangeFrom("USD");

        assertEquals(usdExpected.size(), usdActual.size());
        assertEquals(usdExpected, usdActual);

        List<ExchangeHistory> eurExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("EUR"))
                .toList();

        List<ExchangeHistory> eurActual = exchangeHistoryService.findAllByChangeFrom("EUR");

        assertEquals(eurExpected.size(), eurActual.size());
        assertEquals(eurExpected, eurActual);

        List<ExchangeHistory> rubExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("RUB"))
                .toList();

        List<ExchangeHistory> rubActual = exchangeHistoryService.findAllByChangeFrom("RUB");

        assertEquals(rubExpected.size(), rubActual.size());
        assertEquals(rubExpected, rubActual);

        //Request with no exist ChangeFrom
        List<ExchangeHistory> ronActual = exchangeHistoryService.findAllByChangeFrom("RON");

        assertEquals(List.of(), ronActual);
    }

    @Test
    public void findAllByChangeTo() {
        List<ExchangeHistory> mdlExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("MDL"))
                .toList();

        List<ExchangeHistory> mdlActual = exchangeHistoryService.findAllByChangeTo("MDL");

        assertEquals(mdlExpected.size(), mdlActual.size());
        assertEquals(mdlExpected, mdlActual);

        List<ExchangeHistory> usdExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("USD"))
                .toList();

        List<ExchangeHistory> usdActual = exchangeHistoryService.findAllByChangeTo("USD");

        assertEquals(usdExpected.size(), usdActual.size());
        assertEquals(usdExpected, usdActual);

        List<ExchangeHistory> eurExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("EUR"))
                .toList();

        List<ExchangeHistory> eurActual = exchangeHistoryService.findAllByChangeTo("EUR");

        assertEquals(eurExpected.size(), eurActual.size());
        assertEquals(eurExpected, eurActual);

        List<ExchangeHistory> rubExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("RUB"))
                .toList();

        List<ExchangeHistory> rubActual = exchangeHistoryService.findAllByChangeTo("RUB");

        assertEquals(rubExpected.size(), rubActual.size());
        assertEquals(rubExpected, rubActual);

        //Request with no exist ChangeTo
        List<ExchangeHistory> ronActual = exchangeHistoryService.findAllByChangeTo("RON");

        assertEquals(List.of(), ronActual);
    }

    @Test
    public void findAllByChangeFromTransactionDate() {
        LocalDateTime currentDate = LocalDateTime.now().withNano(0);

        List<ExchangeHistory> mdlCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("MDL") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> mdlCurrentDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("MDL", currentDate);

        assertEquals(mdlCurrentDateExpected.size(), mdlCurrentDateActual.size());
        assertEquals(mdlCurrentDateExpected, mdlCurrentDateActual);

        List<ExchangeHistory> eurCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("EUR") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> eurCurrentDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("EUR", currentDate);

        assertEquals(eurCurrentDateExpected.size(), eurCurrentDateActual.size());
        assertEquals(eurCurrentDateExpected, eurCurrentDateActual);

        List<ExchangeHistory> usdCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("USD") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> usdCurrentDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("USD", currentDate);

        assertEquals(usdCurrentDateExpected.size(), usdCurrentDateActual.size());
        assertEquals(usdCurrentDateExpected, usdCurrentDateActual);

        List<ExchangeHistory> rubCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("RUB") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> rubCurrentDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("RUB", currentDate);

        assertEquals(rubCurrentDateExpected.size(), rubCurrentDateActual.size());
        assertEquals(rubCurrentDateExpected, rubCurrentDateActual);

        List<ExchangeHistory> mdlYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("MDL") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> mdlYesterdayDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("MDL", currentDate.minusDays(1));

        assertEquals(mdlYesterdayDateExpected.size(), mdlYesterdayDateActual.size());
        assertEquals(mdlYesterdayDateExpected, mdlYesterdayDateActual);

        List<ExchangeHistory> eurYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("EUR") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> eurYesterdayDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("EUR", currentDate.minusDays(1));

        assertEquals(eurYesterdayDateExpected.size(), eurYesterdayDateActual.size());
        assertEquals(eurYesterdayDateExpected, eurYesterdayDateActual);

        List<ExchangeHistory> usdYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("USD") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> usdYesterdayDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("USD", currentDate.minusDays(1));

        assertEquals(usdYesterdayDateExpected.size(), usdYesterdayDateActual.size());
        assertEquals(usdYesterdayDateExpected, usdYesterdayDateActual);

        List<ExchangeHistory> rubYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeFrom().equals("RUB") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> rubYesterdayDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("RUB", currentDate.minusDays(1));

        assertEquals(rubYesterdayDateExpected.size(), rubYesterdayDateActual.size());
        assertEquals(rubYesterdayDateExpected, rubYesterdayDateActual);

        //Request with no exist ChangeFrom and right TransactionDate
        List<ExchangeHistory> ronWithRightDateActual = exchangeHistoryService.findAllByChangeFromTransactionDate("RON", currentDate);

        assertEquals(List.of(), ronWithRightDateActual);

        //Request with right ChangeFrom and wrong TransactionDate
        List<ExchangeHistory> ronWithRightChangeFromActual = exchangeHistoryService.findAllByChangeFromTransactionDate("RUB", currentDate.minusDays(5));

        assertEquals(List.of(), ronWithRightChangeFromActual);

        //Request with wrong ChangeFrom and wrong TransactionDate
        List<ExchangeHistory> ronWrongActual = exchangeHistoryService.findAllByChangeFromTransactionDate("RON", currentDate.minusDays(5));

        assertEquals(List.of(), ronWrongActual);
    }

    @Test
    public void findAllByChangeToTransactionDate() {
        LocalDateTime currentDate = LocalDateTime.now().withNano(0);

        List<ExchangeHistory> mdlCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("MDL") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> mdlCurrentDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("MDL", currentDate);

        assertEquals(mdlCurrentDateExpected.size(), mdlCurrentDateActual.size());
        assertEquals(mdlCurrentDateExpected, mdlCurrentDateActual);

        List<ExchangeHistory> eurCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("EUR") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> eurCurrentDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("EUR", currentDate);

        assertEquals(eurCurrentDateExpected.size(), eurCurrentDateActual.size());
        assertEquals(eurCurrentDateExpected, eurCurrentDateActual);

        List<ExchangeHistory> usdCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("USD") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> usdCurrentDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("USD", currentDate);

        assertEquals(usdCurrentDateExpected.size(), usdCurrentDateActual.size());
        assertEquals(usdCurrentDateExpected, usdCurrentDateActual);

        List<ExchangeHistory> rubCurrentDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("RUB") && currency.getTransactionDate().equals(currentDate))
                .toList();

        List<ExchangeHistory> rubCurrentDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("RUB", currentDate);

        assertEquals(rubCurrentDateExpected.size(), rubCurrentDateActual.size());
        assertEquals(rubCurrentDateExpected, rubCurrentDateActual);

        List<ExchangeHistory> mdlYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("MDL") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> mdlYesterdayDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("MDL", currentDate.minusDays(1));

        assertEquals(mdlYesterdayDateExpected.size(), mdlYesterdayDateActual.size());
        assertEquals(mdlYesterdayDateExpected, mdlYesterdayDateActual);

        List<ExchangeHistory> eurYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("EUR") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> eurYesterdayDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("EUR", currentDate.minusDays(1));

        assertEquals(eurYesterdayDateExpected.size(), eurYesterdayDateActual.size());
        assertEquals(eurYesterdayDateExpected, eurYesterdayDateActual);

        List<ExchangeHistory> usdYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("USD") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> usdYesterdayDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("USD", currentDate.minusDays(1));

        assertEquals(usdYesterdayDateExpected.size(), usdYesterdayDateActual.size());
        assertEquals(usdYesterdayDateExpected, usdYesterdayDateActual);

        List<ExchangeHistory> rubYesterdayDateExpected = testData.stream()
                .filter(currency -> currency.getChangeTo().equals("RUB") && currency.getTransactionDate().equals(currentDate.minusDays(1)))
                .toList();

        List<ExchangeHistory> rubYesterdayDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("RUB", currentDate.minusDays(1));

        assertEquals(rubYesterdayDateExpected.size(), rubYesterdayDateActual.size());
        assertEquals(rubYesterdayDateExpected, rubYesterdayDateActual);

        //Request with no exist ChangeFrom and right TransactionDate
        List<ExchangeHistory> ronWithRightDateActual = exchangeHistoryService.findAllByChangeToTransactionDate("RON", currentDate);

        assertEquals(List.of(), ronWithRightDateActual);

        //Request with right ChangeFrom and wrong TransactionDate
        List<ExchangeHistory> ronWithRightChangeToActual = exchangeHistoryService.findAllByChangeToTransactionDate("RUB", currentDate.minusDays(5));

        assertEquals(List.of(), ronWithRightChangeToActual);

        //Request with wrong ChangeFrom and wrong TransactionDate
        List<ExchangeHistory> ronWrongActual = exchangeHistoryService.findAllByChangeToTransactionDate("RON", currentDate.minusDays(5));

        assertEquals(List.of(), ronWrongActual);
    }

    @Test
    public void create() {
        ExchangeHistory expected = new ExchangeHistory();
        expected.setChangeFrom("MDL");
        expected.setChangeTo("RON");
        expected.setChangedValue(1000);
        expected.setRetrievedValue(250);
        expected.setTransactionDate(LocalDateTime.now().minusDays(1).withNano(0));

        exchangeHistoryService.create(expected);

        ExchangeHistory actual = exchangeHistoryService.findAllByChangeFrom(expected.getChangeFrom())
                .stream()
                .filter(currency -> currency.getChangeTo().equals("RON"))
                .findFirst()
                .orElse(null);

        assertEquals(expected, actual);

        ExchangeHistory secondExpected = new ExchangeHistory();
        secondExpected.setChangeFrom("RUB");
        secondExpected.setChangeTo("RON");
        secondExpected.setChangedValue(1000);
        secondExpected.setRetrievedValue(88);
        secondExpected.setTransactionDate(LocalDateTime.now().withNano(0));

        exchangeHistoryService.create(secondExpected);

        List<ExchangeHistory> actualList = exchangeHistoryService.findAll();

        testData.add(expected);
        testData.add(secondExpected);

        assertEquals(8, actualList.size());
        assertEquals(testData, actualList);
    }

    private List<ExchangeHistory> generateTestData() {
        ExchangeHistory yesterdayExchange = new ExchangeHistory();
        yesterdayExchange.setChangeFrom("MDL");
        yesterdayExchange.setChangeTo("USD");
        yesterdayExchange.setChangedValue(1000);
        yesterdayExchange.setRetrievedValue(53);
        yesterdayExchange.setTransactionDate(LocalDateTime.now().minusDays(1).withNano(0));

        exchangeHistoryService.create(yesterdayExchange);

        ExchangeHistory secondYesterdayExchange = new ExchangeHistory();
        secondYesterdayExchange.setChangeFrom("RUB");
        secondYesterdayExchange.setChangeTo("USD");
        secondYesterdayExchange.setChangedValue(1000);
        secondYesterdayExchange.setRetrievedValue(10);
        secondYesterdayExchange.setTransactionDate(LocalDateTime.now().minusDays(1).withNano(0));

        exchangeHistoryService.create(secondYesterdayExchange);

        ExchangeHistory dayBeforeYesterdayExchange = new ExchangeHistory();
        dayBeforeYesterdayExchange.setChangeFrom("USD");
        dayBeforeYesterdayExchange.setChangeTo("MDL");
        dayBeforeYesterdayExchange.setChangedValue(1000);
        dayBeforeYesterdayExchange.setRetrievedValue(19200);
        dayBeforeYesterdayExchange.setTransactionDate(LocalDateTime.now().minusDays(2).withNano(0));

        exchangeHistoryService.create(dayBeforeYesterdayExchange);

        ExchangeHistory secondDayBeforeYesterdayExchange = new ExchangeHistory();
        secondDayBeforeYesterdayExchange.setChangeFrom("USD");
        secondDayBeforeYesterdayExchange.setChangeTo("EUR");
        secondDayBeforeYesterdayExchange.setChangedValue(1000);
        secondDayBeforeYesterdayExchange.setRetrievedValue(995);
        secondDayBeforeYesterdayExchange.setTransactionDate(LocalDateTime.now().minusDays(2).withNano(0));

        exchangeHistoryService.create(secondDayBeforeYesterdayExchange);

        ExchangeHistory thirdDayBeforeYesterdayExchange = new ExchangeHistory();
        thirdDayBeforeYesterdayExchange.setChangeFrom("EUR");
        thirdDayBeforeYesterdayExchange.setChangeTo("MDL");
        thirdDayBeforeYesterdayExchange.setChangedValue(1000);
        thirdDayBeforeYesterdayExchange.setRetrievedValue(19150);
        thirdDayBeforeYesterdayExchange.setTransactionDate(LocalDateTime.now().minusDays(2).withNano(0));

        exchangeHistoryService.create(thirdDayBeforeYesterdayExchange);

        ExchangeHistory todayExchange = new ExchangeHistory();
        todayExchange.setChangeFrom("RUB");
        todayExchange.setChangeTo("EUR");
        todayExchange.setChangedValue(1000);
        todayExchange.setRetrievedValue(11);
        todayExchange.setTransactionDate(LocalDateTime.now().withNano(0));

        exchangeHistoryService.create(todayExchange);

        return List.of(yesterdayExchange, secondYesterdayExchange, dayBeforeYesterdayExchange, secondDayBeforeYesterdayExchange,
                thirdDayBeforeYesterdayExchange, todayExchange);
    }
}