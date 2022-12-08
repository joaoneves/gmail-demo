# gmail-demo

This project is for using Google API demonstration

## Running instructions

- Install a Postgresql docker container using postgres as default username and password. Command example of creating a custom Postgres Docker container

```
docker run -p 5432:5432 --name postgresql -e POSTGRES_PASSWORD=postgres -d postgres
```

You must also create a database named `gmail-demo`

- From develop branch, `mvn run install` to download and install the dependencies
- Run the application using the following command from terminal, assuming you have Maven and Java 8 installed and properly configured: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DclientId=<your client_id> -DclientSecret=<your client_secret>"`
