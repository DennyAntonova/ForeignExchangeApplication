package com.example.ForeignExchangeApplication.dto;

public class ConversionDTO {
    private Long transactionId;

    private Double amountInTargetCurrency;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public void setAmountInTargetCurrency(Double amountInTargetCurrency) {
        this.amountInTargetCurrency = amountInTargetCurrency;
    }
}
