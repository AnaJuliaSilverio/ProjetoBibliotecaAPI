
ALTER TABLE books
DROP CONSTRAINT IF EXISTS fk_book_author;

ALTER TABLE books
ADD CONSTRAINT fk_book_author
FOREIGN KEY (fk_id_author)
REFERENCES author(id_author)
ON DELETE CASCADE;


ALTER TABLE loan
DROP CONSTRAINT IF EXISTS fk_loan_book;

ALTER TABLE loan
ADD CONSTRAINT fk_loan_book
FOREIGN KEY (fk_id_book)
REFERENCES books(id_book)
ON DELETE CASCADE;
