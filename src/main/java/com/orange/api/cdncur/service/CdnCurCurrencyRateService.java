package com.orange.api.cdncur.service;

import com.orange.api.CurrenсуRateService;
import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.cdncur.model.CdnCurCurrenсуRate;
import com.orange.api.cdncur.repository.CdnCurCurrencyRateRepository;
import com.orange.util.dto.CurrencyRateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CdnCurCurrencyRateService implements CurrenсуRateService<CdnCurCurrenсуRate, GlobalCurrencyRateDto> {
    private final CdnCurCurrencyRateRepository cdnCurCurrenсуRateRepository;

    @Override
    @Transactional
    public void create(List<CdnCurCurrenсуRate> currencyRates) {
        LocalDate currentDate = LocalDate.now();
        if (exists(currentDate)) {
            cdnCurCurrenсуRateRepository.deleteAllByRetrievedDate(currentDate);
        }
        cdnCurCurrenсуRateRepository.saveAll(currencyRates);
    }

    @Override
    public boolean exists(LocalDate retrievedDate) {
        return cdnCurCurrenсуRateRepository.countAllByRetrievedDate(retrievedDate) > 0;
    }

    @Override
    public Optional<GlobalCurrencyRateDto> getCurrencyRateInfo(CurrencyRateInfo data) {
        //TODO check method to change currency
        List<CdnCurCurrenсуRate> currencyRateList = cdnCurCurrenсуRateRepository.findByLabelOrLabelAndRetrievedDate(
                data.getCurrencyFrom(), data.getCurrencyTo(), data.getDate());

        Optional<CdnCurCurrenсуRate> changeFrom = currencyRateList.stream()
                .filter(currencyInfo -> Objects.equals(currencyInfo.getLabel(), data.getCurrencyFrom()))
                .findFirst();
        Optional<CdnCurCurrenсуRate> changeTo = currencyRateList.stream()
                .filter(currencyInfo -> Objects.equals(currencyInfo.getLabel(), data.getCurrencyTo()))
                .findFirst();

        String label;
        double value;
        if (data.getCurrencyTo().equals("USD")) {
            label = changeFrom.get().getLabel() + "USD";
            value = changeFrom.get().getRate();

            GlobalCurrencyRateDto cdnCurCurrencyRateDto = new GlobalCurrencyRateDto();
            cdnCurCurrencyRateDto.setLabel(label);
            cdnCurCurrencyRateDto.setRate(value);
            cdnCurCurrencyRateDto.setRetrievedDate(data.getDate());
            return Optional.of(cdnCurCurrencyRateDto);
        } else {
            if (changeFrom.isPresent() && changeTo.isPresent()) {
                label = changeFrom.get().getLabel() + changeTo.get().getLabel();
                value = changeFrom.get().getRate() / changeTo.get().getRate();

                GlobalCurrencyRateDto cdnCurCurrencyRateDto = new GlobalCurrencyRateDto();
                cdnCurCurrencyRateDto.setLabel(label);
                cdnCurCurrencyRateDto.setRate(value);
                cdnCurCurrencyRateDto.setRetrievedDate(data.getDate());
                return Optional.of(cdnCurCurrencyRateDto);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CdnCurCurrenсуRate> getRate() {
        return cdnCurCurrenсуRateRepository.findAll();
    }

    public void clear() {
        cdnCurCurrenсуRateRepository.deleteAll();
    }
}
