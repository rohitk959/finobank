create table if not exists T_USER
(
    ID       uuid PRIMARY KEY,
    USERNAME varchar(100) NOT NULL,
    ADDRESS  text         NOT NULL
)