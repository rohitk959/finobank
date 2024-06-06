create table if not exists BANK_ACCOUNT
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

create table if not exists ACCOUNT_BALANCE
(
    ID                uuid PRIMARY KEY,
    AMOUNT            numeric(3)  NOT NULL,
    CURRENCY_ISO_CODE varchar(3)  NOT NULL,
    PARENT            uuid,
    TYPE              varchar(30) not null,
    CREATED_AT        timestamp   NOT NULL,
    CREATED_BY        varchar(60) NOT NULL
)