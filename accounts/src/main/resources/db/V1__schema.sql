create table if not exists ACCOUNT
(
    ID             uuid PRIMARY KEY,
    ACCOUNT_NAME   varchar(100) NOT NULL,
    ACCOUNT_NUMBER varchar(34)  NOT NULL,
    BALANCES       uuid[]       not null,
    STATUS         varchar(10)  not null,
    USERS          uuid[]       NOT NULL,
    CREATED_AT     timestamp    NOT NULL,
    CREATED_BY     varchar(60)  NOT NULL,
    UPDATED_AT     timestamp   DEFAULT NULL,
    UPDATED_BY     varchar(60) DEFAULT NULL
);

create table if not exists BALANCE
(
    ID         uuid PRIMARY KEY,
    ACCOUNT_ID uuid        not null,
    AMOUNT     numeric(3)  NOT NULL,
    CURRENCY   varchar(3)  NOT NULL,
    PARENT     uuid,
    TYPE       varchar(30) not null,
    CREATED_AT timestamp   NOT NULL,
    CREATED_BY varchar(60) NOT NULL,
    CONSTRAINT FK_BALANCE_ACCOUNT_ID
        FOREIGN KEY (ACCOUNT_ID)
            REFERENCES ACCOUNT (ID)
)