
# Foreign Exchange

Developed Foreign Exchange APIs with Spring Boot and MySQL database.
 Currencies are taken from https://v6.exchangerate-api.com


API Reference

GET Exchange Rate API

/api/exchangeRate
* sourceCurrency - type String
* targetCurrency - type String

Post Conversion API
/api/doConversion

* sourceAmount - type Double
* sourceCurrency - type String
* targetCurrency - type String

POST Conversion API that returns the amount in target currency.

GET Conversion List
/api/getConversionList

* date - the format should be 'yyyy-MM-dd'
or 
* transactionId - type Long

GET All Conversion records.


