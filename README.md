curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
 "asset": "TST",
 "price": 10,
 "amount": 20.0,
"direction" : "SELL"
}'


