# gmail-demo

This project is for using Google API demonstration

##### Installing instructions

- Install a Postgresql docker container using postgres as default username and password. Command example of creating a custom Postgresql Docker container

```
docker run -p 5432:5432 --name postgresql -e POSTGRES_PASSWORD=postgres -d postgres
```

You must also create a database named `gmail-demo` and run the database script to create the needed table

```
create table contact (
   resource_name varchar(255) not null,
   email varchar(255),
   name varchar(255),
   primary key (resource_name)
);
```

- From develop branch, `mvn run install` to download and install the dependencies

##### Running instructions

- Run the application using the following command from terminal, assuming you have Maven and Java 8 installed and properly configured: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DclientId=<your client_id> -DclientSecret=<your client_secret>"`

- In the interface, after the application is up, go to the internet browser and add to the address:

```
http://localhost:8083
```

- In this page, clicking on the `Import contacts` link, it will call all internal procedures to request the code of Google integration, use this code to obtain an access token, and it will import (only one page) of your personal contacts, from your Google account.

- To list all persisted contacts in the database, you can just access a GET requesting `http://localhost:8083/contacts`
