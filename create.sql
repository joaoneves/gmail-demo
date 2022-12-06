
    create table contact (
       code varchar(255) not null,
        email varchar(255) not null,
        name varchar(255) not null,
        primary key (code, email)
    );
