# atm_machine

## Run the app locally

1. Run docker-compose up -d to start mysql instance.
2. Run mvn spring-boot:run on terminal to start the application.
3. Start the application also using IntellJ run feature.
4. Swagger Api doc : http://localhost:8080/swagger-ui.html

## To Run app as docker image

1. docker build -t <app-name> .
2. docker run -p 8080:8080  <app-name>
