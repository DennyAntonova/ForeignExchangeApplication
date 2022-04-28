package com.example.ForeignExchangeApplication.service;

import com.example.ForeignExchangeApplication.dto.ConversionDTO;
import com.example.ForeignExchangeApplication.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ForeignExchangeService {
    Double getExchangeRate(String sourceCurrency, String targetCurrency) throws Exception;

    ConversionDTO doConversion(Double sourceAmount, String sourceCurrency, String targetCurrency) throws Exception;

    Page<Conversion> getConversionList(Long transactionId, Date transactionDate, Pageable pageable)
            throws Exception;

}
