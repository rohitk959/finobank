create table if not exists T_PAYMENT
(
    ID                         uuid primary key,
    AMOUNT                     numeric(3)  not null,
    BENEFICIARY_ACCOUNT_NUMBER varchar(34) not null,
    COMMUNICATION              text,
    CREATED_AT                 timestamp   NOT NULL,
    CREATED_BY                 varchar(60) NOT NULL,
    CURRENCY                   varchar(3)  NOT NULL,
    FRAUDULENT_TXN             boolean     not null,
    GIVER_ACCOUNT_NUMBER       varchar(34) not null,
    STATUS                     varchar(10) not null
)