curl -X GET http://localhost:8080/

curl -X PUT -H "Content-Type: application/json" -d '{"name":"mkyong","email":"abc@gmail.com"}' http://localhost:8080/users/100

curl -X POST -H 'Content-Type: application/json ' -d '{"FirstName": "Joe","LastName": "Soap"}' http://localhost:8080/users/100

curl -X POST -H 'Content-Type: application/xml' -d '<Person><FirstName>Joe</FirstName><LastName>Soap</LastName></Person>' http://localhost:8080/users/100

curl -X POST -H 'Content-Type: text' -d 'Hello World! from post request' http://localhost:8080/

curl -X POST -H 'Content-Type: html' -d '<html><body><h1>Hello from Habrahabr</h1></body></html>' http://localhost:8080/

curl -X DELETE http://localhost:8080/users/100