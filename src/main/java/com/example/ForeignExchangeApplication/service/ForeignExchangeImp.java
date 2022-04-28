package com.example.ForeignExchangeApplication.service;
import com.example.ForeignExchangeApplication.dto.ConversionDTO;
import com.example.ForeignExchangeApplication.model.ForeignExchangeExceptionMessages;
import com.example.ForeignExchangeApplication.model.Conversion;
import com.example.ForeignExchangeApplication.repository.ConversionRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@Transactional
public class ForeignExchangeImp implements ForeignExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignExchangeImp.class);

    @Value ("${exchangerateapi.url}")
    private String exchangerateapiUrl;

    @Value("${exchangerateapi.accessKey}")
    private String exchangerateapiAccessKey;

    @Autowired
    private ConversionRepository conversionRepository;

    public ForeignExchangeImp() {
    }

    @Override
    public Double getExchangeRate(String sourceCurrency, String targetCurrency) throws Exception {
        return getCurrencyFromExternalApi(sourceCurrency, targetCurrency);
    }

    @Override
    public ConversionDTO doConversion(Double sourceAmount, String sourceCurrency, String targetCurrency) throws Exception {
        sourceCurrency = sourceCurrency.toUpperCase();
        targetCurrency = targetCurrency.toUpperCase();
        Double exchangeRate = getCurrencyFromExternalApi(sourceCurrency, targetCurrency);
        return saveConversion(exchangeRate, sourceAmount, sourceCurrency, targetCurrency);
    }

    public ConversionDTO saveConversion(Double exchangeRate, Double sourceAmount, String sourceCurrency,
                                        String targetCurrency) throws Exception {
        ConversionDTO conversionDTO = new ConversionDTO();
        Conversion conversion = new Conversion();
        try {
            if (exchangeRate != null) {
                Double amountInTargetCurrency = exchangeRate * sourceAmount;
                conversion.setTransactionDate(new Date());
                conversion.setSourceAmount(sourceAmount);
                conversion.setSourceCurrency(sourceCurrency);
                conversion.setTargetCurrency(targetCurrency);
                conversion = this.conversionRepository.save(conversion);
                conversionDTO.setTransactionId(conversion.getTransactionId());
                conversionDTO.setAmountInTargetCurrency(amountInTargetCurrency);
            }
        } catch (Exception e) {
            String errorMessage = ForeignExchangeExceptionMessages.SAVE_CONVERSION_TO_DB_ERROR.getDescription() + e.getMessage();
            LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        return conversionDTO;
    }

    @Override
    public Page<Conversion> getConversionList(Long transactionId, Date transactionDate, Pageable pageable) throws Exception {
        Page<Conversion> conversionTransactionPage = null;
        try {
            if (transactionId != null && transactionDate != null) {
                conversionTransactionPage = this.conversionRepository.findConversionTransactionsByTransactionIdAndTransactionDateIsGreaterThanEqual(transactionId, transactionDate, pageable);
            } else if (transactionId != null && transactionDate == null) {
                conversionTransactionPage = this.conversionRepository.findConversionTransactionsByTransactionId(transactionId, pageable);
            } else if (transactionId == null && transactionDate != null) {
                conversionTransactionPage = this.conversionRepository.findConversionTransactionsByTransactionDateIsGreaterThanEqual(transactionDate, pageable);
            } else {
                conversionTransactionPage = this.conversionRepository.findAll(pageable);
            }
        } catch (Exception e) {
            String errorMessage = ForeignExchangeExceptionMessages.GET_CONVERSION_LIST_ERROR.getDescription() + e.getMessage();
            LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        return conversionTransactionPage;
    }

    public Double getCurrencyFromExternalApi(String sourceCurrency, String targetCurrency) throws Exception {
        sourceCurrency = sourceCurrency.toUpperCase();
        targetCurrency = targetCurrency.toUpperCase();
        Double result;
        try {
            String urlStr = String.format(exchangerateapiUrl + "/%s/latest/%s", exchangerateapiAccessKey, sourceCurrency);
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();

            if (jsonObject.get("result").getAsString().equals("success")) {
                result = jsonObject.get("conversion_rates").getAsJsonObject().get(targetCurrency).getAsDouble();
            } else {
                String errorMessage = ForeignExchangeExceptionMessages.EXTERNAL_API_CONNECTION_ERROR.getDescription();
                LOGGER.error(errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = ForeignExchangeExceptionMessages.UNKNOWN_EXCEPTION_ERROR.getDescription() + e.getMessage();
            LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        return result;
    }
}
