package com.orange.api.currate.service;

import com.orange.api.GlobalCurrencyRateDto;
import com.orange.api.currate.model.CurrateCurrencyRate;
import com.orange.api.currate.repository.CurrateCurrencyRateRepository;
import com.orange.api.CurrenсуRateService;
import com.orange.util.dto.CurrencyRateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class CurrateCurrencyRateService implements CurrenсуRateService<CurrateCurrencyRate,GlobalCurrencyRateDto> {
    private final CurrateCurrencyRateRepository currateCurrencyRateRepository;

    @Override
    @Transactional
    public void create(List<CurrateCurrencyRate> currencyRates) {
        LocalDate currentDate = LocalDate.now();
        if(exists(currentDate)){
            currateCurrencyRateRepository.deleteAllByRetrievedDate(currentDate);
        }
        currateCurrencyRateRepository.saveAll(currencyRates);
    }

    @Override
    public boolean exists(LocalDate retrievedDate) {
        return currateCurrencyRateRepository.countAllByRetrievedDate(retrievedDate) > 0;
    }

    @Override
    public Optional<GlobalCurrencyRateDto> getCurrencyRateInfo(CurrencyRateInfo data) {
        Optional<CurrateCurrencyRate> getCurrencyRateInfo = currateCurrencyRateRepository
                .findByLabelContainsAndRetrievedDate(data.getCurrencyFrom() + data.getCurrencyTo(), data.getDate());

        if(getCurrencyRateInfo.isPresent()) {
            GlobalCurrencyRateDto currateCurrencyRateDto = new GlobalCurrencyRateDto();
            currateCurrencyRateDto.setLabel(getCurrencyRateInfo.get().getLabel());
            currateCurrencyRateDto.setRate(getCurrencyRateInfo.get().getRate());
            currateCurrencyRateDto.setRetrievedDate(getCurrencyRateInfo.get().getRetrievedDate());

            return Optional.of(currateCurrencyRateDto);
        }
        return Optional.empty();
    }

    @Override
    public List<CurrateCurrencyRate> getRate() {
        return currateCurrencyRateRepository.findAll();
    }

    public void clear() {
        currateCurrencyRateRepository.deleteAll();
    }
}
