CREATE TABLE loan (
    id_loan SERIAL,
    fk_id_book int,
    loan_date DATE not null,
    return_date DATE not null,
    status VARCHAR(80) not null,
   	constraint loan_pk primary KEY(id_loan),
   	constraint fk_loan_book foreign key(fk_id_book)
   	references books(id_book),
   	CONSTRAINT check_status
   	CHECK (status IN ('devolvido', 'alugado'))
);
