To use with local docker repository:

1. mvn clean install
2. docker build -t localhost:5000/crypto:v2 .
3. docker run --name crypto-app -p8080:8080  localhost:5000/crypto:v2
4. open http://localhost:8080/swagger-ui.html
