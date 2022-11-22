package com.orange.exchange.service.currate;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.currate.model.CurrateCurrencyRate;
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
public class CurrateExchangeService implements GlobalExchangeService {
    private final CurrenсуRateService<CurrateCurrencyRate, GlobalCurrencyRateDto> currateCurrencyRateService;
    private final ExchangeHistoryService exchangeHistoryService;

    @Override
    public double exchangeCurrency(GlobalExchangeDto exchange) {
        DecimalFormat doubleFormat = new DecimalFormat("#.##");

        CurrencyRateInfo currencyRateInfo = new CurrencyRateInfo();
        currencyRateInfo.setCurrencyFrom(exchange.getFrom());
        currencyRateInfo.setCurrencyTo(exchange.getTo());
        currencyRateInfo.setDate(LocalDate.now());

        Optional<GlobalCurrencyRateDto> currencyRate = currateCurrencyRateService.getCurrencyRateInfo(currencyRateInfo);

        double retrievedValue = currencyRate.map(globalCurrencyRateDto ->
                Double.parseDouble(doubleFormat.format(exchange.getCount() * currencyRate.get().getRate()))).orElse(0.0);

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
