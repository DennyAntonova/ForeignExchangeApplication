package com.example.ForeignExchangeApplication;
import com.example.ForeignExchangeApplication.service.ForeignExchangeImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ForeignExchangeApplicationTests {
	@Autowired
	private ForeignExchangeImp foreignExchangeImp;

	@Test
	public void whenGetExchangeRateCalledWithValidRequestItShouldReturnValidResult() throws Exception {
		String sourceCurrency = "EUR";
		String targetCurrency = "USD";

		assertNotNull(foreignExchangeImp.getExchangeRate(sourceCurrency, targetCurrency));
	}

	@Test
	public void whenGetExchangeRateCalledWithNotValidRequestItShouldReturnException() {
		String sourceCurrency = "sdf";
		String targetCurrency = "iuy";

		Exception exception = assertThrows(Exception.class, () -> {
			foreignExchangeImp.getExchangeRate(sourceCurrency, targetCurrency);
		});
		String errorMessage = "error occurred";
		assertTrue(exception.getMessage().contains(errorMessage));
	}

	@Test
	public void whenDoConversionCalledWithValidRequestItShouldReturnValidResult() throws Exception {
		Integer sourceAmount = 10;
		String sourceCurrency = "EUR";
		String targetCurrency = "USD";

		assertNotNull(foreignExchangeImp.doConversion(Double.valueOf(sourceAmount), sourceCurrency, targetCurrency));
	}

	@Test
	public void whenDoConversionCalledWithNotValidRequestItShouldReturnException() throws Exception {
		Integer sourceAmount = 10;
		String sourceCurrency = "gfd";
		String targetCurrency = "yte";

		Exception exception = assertThrows(Exception.class, () -> {
			foreignExchangeImp.doConversion(Double.valueOf(sourceAmount), sourceCurrency, targetCurrency);
		});
		assertEquals(exception.getClass(),(Exception.class));

		Exception exception2 = assertThrows(Exception.class, () -> {
			foreignExchangeImp.saveConversion(10.0, null, null, null);
		});
		assertEquals(exception2.getClass(), (Exception.class));

	}

	@Test
	public void whenGetConversionListCalledWithValidRequestItShouldReturnValidResult() throws Exception {
		Long transactionId = 10L;
		Date transactionDate = new Date();
		Pageable pageable = null;

		assertNotNull(foreignExchangeImp.getConversionList(transactionId, transactionDate, null));

		transactionId = null;
		transactionDate = new Date();


		assertNotNull(foreignExchangeImp.getConversionList(transactionId, transactionDate, null));

		transactionId = 10L;
		transactionDate = null;


		assertNotNull(foreignExchangeImp.getConversionList(transactionId, null, null));
	}

	@Test
	public void whenGetConversionListCalledWithNotValidRequestItShouldReturnException() {
		Exception exception = assertThrows(Exception.class, () -> {
			foreignExchangeImp.getConversionList(null, null, null);
		});
		assertEquals(exception.getClass(), UnexpectedRollbackException.class);
	}
}