package com.orange.api.cdncur.service;

import com.orange.api.GlobalCurrencyRate;
import com.orange.api.cdncur.dto.GlobalCdnCurCurrencyRate;
import com.orange.util.json.JsonService;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.api.CurrenсуRateService;
import com.orange.util.exception.RetrieveCurrencyRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalCdnCurCurrencyRateService implements GlobalCurrencyRate {
    private final JsonService jsonService;
    private final CurrenсуRateService currencyRateService;
    @Value("${cdn.cur.currency.rate.url}")
    private String rateUrl;

    @Override
    public void getCurrencyRate() throws IOException {
        URL currencyRateUrl = new URL(rateUrl);
        HttpURLConnection connection = (HttpURLConnection) currencyRateUrl.openConnection();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            GlobalCdnCurCurrencyRate globalCurrencyRate = jsonService.fromJson(response.toString(), GlobalCdnCurCurrencyRate.class);
            List<CdnCurCurrenсуRate> externalCurrencyRate = globalCurrencyRate.getRates()
                    .entrySet()
                    .stream()
                    .map(rate -> {
                        CdnCurCurrenсуRate currenсуRate = new CdnCurCurrenсуRate();
                        currenсуRate.setLabel(rate.getKey());
                        currenсуRate.setRate(rate.getValue());
                        currenсуRate.setRetrievedDate(LocalDate.now());

                        return currenсуRate;
                    }).toList();

            currencyRateService.create(externalCurrencyRate);

        } else {
            throw new RetrieveCurrencyRateException(HttpStatus.NO_CONTENT.toString());
        }
    }
}
