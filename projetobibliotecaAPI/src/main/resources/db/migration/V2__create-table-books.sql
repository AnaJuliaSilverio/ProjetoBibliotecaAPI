CREATE TABLE books (
    id_book SERIAL,
    fk_id_author int,
    isbn VARCHAR(50) NOT NULL,
    title VARCHAR(150) NOT NULL,
    genre VARCHAR(150) NOT NULL,
    realese_date DATE not null,
   	constraint book_pk primary KEY(id_book),
   	constraint fk_book_author foreign key(fk_id_author)
   	references author(id_author)
);
