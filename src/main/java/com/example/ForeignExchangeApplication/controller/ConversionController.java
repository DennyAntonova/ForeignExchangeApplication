package com.example.ForeignExchangeApplication.controller;

import com.example.ForeignExchangeApplication.dto.ConversionDTO;
import com.example.ForeignExchangeApplication.service.ForeignExchangeService;
import com.example.ForeignExchangeApplication.model.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping ("/api")
public class ConversionController {
    @Autowired
    private ForeignExchangeService foreignExchangeService;

    @GetMapping(value = "/exchangeRate", consumes = MediaType.ALL_VALUE)
    public Double getExchangeRate(@RequestParam(value = "sourceCurrency") String sourceCurrency,
                                  @RequestParam(value = "targetCurrency") String targetCurrency) throws Exception {
        return this.foreignExchangeService.getExchangeRate(sourceCurrency, targetCurrency);
    }

    @PostMapping(value = "/doConversion", consumes = MediaType.ALL_VALUE)
    public ConversionDTO doConversion(@RequestParam(value = "sourceAmount") Double sourceAmount,
                                      @RequestParam(value = "sourceCurrency") String sourceCurrency,
                                      @RequestParam(value = "targetCurrency") String targetCurrency) throws Exception {

        return this.foreignExchangeService.doConversion(sourceAmount, sourceCurrency, targetCurrency);
    }

    @GetMapping(value = "/getConversionList", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<Conversion>> getConversionList(@RequestParam(value = "transactionId", required = false)                                                                          Long transactionId, @RequestParam(value = "transactionDate", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date transactionDate, Pageable pageable) throws Exception {
        Page<Conversion> page = this.foreignExchangeService.getConversionList(transactionId, transactionDate, pageable);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("All elements", String.valueOf(page.getTotalElements()));
        return new ResponseEntity<>(page.getContent(), httpHeaders, HttpStatus.OK);
    }
}
