package com.orange.exchange.service.cndcur;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.exchange.dto.GlobalExchangeDto;
import com.orange.exchange.model.ExchangeHistory;
import com.orange.exchange.service.ExchangeHistoryService;
import com.orange.exchange.service.GlobalExchangeService;
import com.orange.util.dto.CurrencyRateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CdnCurExchangeService implements GlobalExchangeService {
    private final CurrenсуRateService<CdnCurCurrenсуRate, GlobalCurrencyRateDto> cdnCurCurrencyRateService;
    private final ExchangeHistoryService exchangeHistoryService;

    @Override
    public double exchangeCurrency(GlobalExchangeDto exchange) {
        DecimalFormat doubleFormat = new DecimalFormat("#.##");

        CurrencyRateInfo currencyRateInfo = new CurrencyRateInfo();
        currencyRateInfo.setCurrencyFrom(exchange.getFrom());
        currencyRateInfo.setCurrencyTo(exchange.getTo());
        currencyRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> currencyRate = cdnCurCurrencyRateService.getCurrencyRateInfo(currencyRateInfo);

        double retrievedValue = currencyRate.map(globalCurrencyRateDto ->
                Double.parseDouble(doubleFormat.format(exchange.getCount() / globalCurrencyRateDto.getRate()))).orElse(0.0);

        ExchangeHistory exchangeHistory = new ExchangeHistory();
        exchangeHistory.setChangeFrom(exchange.getFrom());
        exchangeHistory.setChangeTo(exchange.getTo());
        exchangeHistory.setChangedValue(exchange.getCount());
        exchangeHistory.setRetrievedValue(retrievedValue);
        exchangeHistory.setTransactionDate(LocalDateTime.now().withNano(0));

        exchangeHistoryService.create(exchangeHistory);

        return retrievedValue;
    }
}
