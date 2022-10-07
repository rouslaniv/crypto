# Crypto App

  How to start using local docker registry:

* mvn clean install
* docker build -t localhost:5000/crypto:v2 .
* docker run --name crypto-app -p8080:8080  localhost:5000/crypto:v2
* check http://localhost:8080/swagger-ui.html

