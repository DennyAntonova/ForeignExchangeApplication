package com.example.ForeignExchangeApplication.repository;

import com.example.ForeignExchangeApplication.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    Page<Conversion> findConversionTransactionsByTransactionDateIsGreaterThanEqual
            (Date transactionDate, Pageable pageable);

    Page<Conversion> findConversionTransactionsByTransactionId(Long transactionId, Pageable pageable);

    Page<Conversion> findConversionTransactionsByTransactionIdAndTransactionDateIsGreaterThanEqual
            (Long transactionId, Date transactionDate, Pageable pageable);

    Page<Conversion> findAll(Pageable pageable);
}
