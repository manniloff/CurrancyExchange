package com.orange.api.currate.service;

import com.orange.api.GlobalCurrencyRate;
import com.orange.api.currate.dto.CurrateCurrency;
import com.orange.api.currate.dto.GlobalCurrateCurrancyRate;
import com.orange.util.json.JsonService;
import com.orange.api.currate.model.CurrateCurrencyRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Primary
@Service
@RequiredArgsConstructor
public class GlobalCurrateCurrencyRateService implements GlobalCurrencyRate {
    private final JsonService jsonService;
    private final CurrateCurrencyRateService currateCurrencyRateService;

    @Value("${currate.currency.api.key}")
    private String currencyApiKey;
    @Value("${currate.currency.url}")
    private String currencyUrl;
    @Value("${currate.currency.rate.url}")
    private String rateUrl;

    @Override
    public void getCurrencyRate() throws IOException {
        List<String> currencyInfo = getCurrencyInfo();
        Map<String, Double> currencyRate = getCurrencyRate(currencyInfo);

        List<CurrateCurrencyRate> currateCurrencyRates = currencyRate.entrySet()
                .stream()
                .map(currency -> {
                    CurrateCurrencyRate currateCurrencyRate = new CurrateCurrencyRate();
                    currateCurrencyRate.setLabel(currency.getKey());
                    currateCurrencyRate.setRate(currency.getValue());
                    currateCurrencyRate.setRetrievedDate(LocalDate.now());

                    return currateCurrencyRate;
                })
                .toList();

        currateCurrencyRateService.create(currateCurrencyRates);
    }

    private List<String> getCurrencyInfo() throws IOException {
        String finalCurrencyUrl = currencyUrl + "?get=currency_list&key=" + currencyApiKey;
        URL currencyRateUrl = new URL(finalCurrencyUrl);
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

            CurrateCurrency currencies = jsonService.fromJson(response.toString(), CurrateCurrency.class);

            return currencies.getData();
        }
        return List.of();
    }

    private Map<String, Double> getCurrencyRate(List<String> currencies) throws IOException {
        String finalRateUrl = rateUrl + "?get=rates&pairs=" + String.join(",", currencies) + "&key=" + currencyApiKey;
        URL currencyRateUrl = new URL(finalRateUrl);
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

            GlobalCurrateCurrancyRate rateInfo = jsonService.fromJson(response.toString(), GlobalCurrateCurrancyRate.class);

            return rateInfo.getData();
        }
        return null;
    }
}
