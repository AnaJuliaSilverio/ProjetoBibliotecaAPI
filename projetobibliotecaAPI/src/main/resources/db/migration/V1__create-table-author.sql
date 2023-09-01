CREATE TABLE author (
    id_author SERIAL,
    cpf VARCHAR(11) NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
   	constraint author_pk primary KEY(id_author)
);
