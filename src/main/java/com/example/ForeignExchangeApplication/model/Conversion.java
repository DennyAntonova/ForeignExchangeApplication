package com.example.ForeignExchangeApplication.model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table ( name = "conversion" )
public class Conversion  {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    @Column ( name = "transaction_id" )
    private Long transactionId;

    @Column ( name = "source_amount" )
    private Double sourceAmount;

    @Column ( name = "source_currency" )
    private String sourceCurrency;

    @Column ( name = "target_currency" )
    private String targetCurrency;

    @Column ( name = "transaction_date" )
    @Temporal ( TemporalType.DATE )
    private Date transactionDate;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Conversion() {
    }
}

