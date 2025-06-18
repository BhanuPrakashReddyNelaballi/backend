--Book

INSERT INTO lib.book (book_id,title, author, genre, isbn, year_published, available_copies) VALUES
(1,'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', '9780743273565', 1925, 5),
(2,'To Kill a Mockingbird', 'Harper Lee', 'Fiction', '9780061120084', 1960, 3),
(3,'1984', 'George Orwell', 'Dystopian', '9780451524935', 1949, 4),
(4,'Pride and Prejudice', 'Jane Austen', 'Romance', '9780141439518', 1813, 7),
(5,'The Catcher in the Rye', 'J.D. Salinger', 'Fiction', '9780316769488', 1951, 6),
(6,'Moby-Dick', 'Herman Melville', 'Adventure', '9780142437247', 1851, 2),
(7,'War and Peace', 'Leo Tolstoy', 'Historical', '9780199232765', 1869, 3),
(8,'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', '9780345339683', 1937, 8),
(9,'Crime and Punishment', 'Fyodor Dostoevsky', 'Psychological', '9780679734505', 1866, 5),
(10,'Brave New World', 'Aldous Huxley', 'Science Fiction', '9780060850524', 1932, 4);

--Member

INSERT INTO lib.member (member_id,name, email, phone, address, membership_status, password, user_role) VALUES
(1,'Alice Johnson', 'alice@example.com', '9876543210', '123 Street, City', 'ACTIVE', '$2a$10$RQi5agPK.EQuPgYc7RSsYOsll.GdaA7oMGammkKZPml0OzfTD9Doe', 'MEMBER'),
(2,'Bob Smith', 'bob@example.com', '8765432109', '456 Avenue, Town', 'ACTIVE', '$2a$10$hkezUf7augJTJWzPgf9EJOScd995ZYrYZvERP6lIc3wTiIjZozetq', 'MEMBER'),
(3,'Charlie Brown', 'charlie@example.com', '7654321098', '789 Lane, Village', 'ACTIVE', '$2a$10$ey8i6KbGIvWr3Y3O9aViDOwHJNIDs4nYNlvWGzvuDmclLfgDie/36', 'MEMBER'),
(4,'David Miller', 'david@example.com', '6543210987', '1011 Road, County', 'ACTIVE', '$2a$10$qZ83LpZEz.Ln0GOibBK3o.FcKlV0FNtEE.2nOL/npd9YbHWQ0WNei', 'ADMIN'),
(5,'Ella Davis', 'ella@example.com', '5432109876', '1213 Street, City', 'ACTIVE', '$2a$10$pIdf4SA/e6JubO2cQnGhg.MdV/4AHttXqi1T2rzI.526HEa20SQwa', 'MEMBER'),
(6,'Frank Wilson', 'frank@example.com', '4321098765', '1415 Avenue, Town', 'ACTIVE', '$2a$10$vd4VwhqroXF4fronBv9PVuZbc2ulThcQiyStqWYJzkuPlNflPtxeu', 'MEMBER'),
(7,'Grace Lee', 'grace@example.com', '3210987654', '1617 Lane, Village', 'ACTIVE', '$2a$10$mgGX2UVkpX89DMxI3KHPPuyKC2G5/AYtB94DbG33AzH1I1E0Ljvd.', 'MEMBER'),
(8,'Henry Clark', 'henry@example.com', '2109876543', '1819 Road, County', 'ACTIVE', '$2a$10$3REyYekN47gmcdoEjM8xQOy.HsM6ACu9WlMBQBaZ6FmJkGJJcBf/m', 'ADMIN'),
(9,'Isabella White', 'isabella@example.com', '1098765432', '2021 Street, City', 'ACTIVE', '$2a$10$X/qccEHU.K7P9wubxtE/Lu847JYpWsorhTVaGv.c3WDVZJ8eMm6E.', 'MEMBER'),
(10,'Jack Thomas', 'jack@example.com', '0987654321', '2223 Avenue, Town', 'ACTIVE', '$2a$10$aq9miE29Z2xluExWLn.lReO9nWDSGplsybKKPDRzvungPsRJalQ06', 'MEMBER');

--Borrowing Transaction

INSERT INTO lib.borrowing_transaction (transactionid,book_id, member_id, borrow_date, return_date, status) VALUES
(1,1, 1, '2025-06-01', null, 'BORROWED'),
(2,2, 2, '2025-05-02', null, 'BORROWED'),
(3,3, 3, '2025-05-15', null, 'BORROWED'),
(4,4, 3, '2025-05-25', '2025-06-10', 'RETURNED'),
(5,5, 5, '2025-05-05', null, 'BORROWED'),
(6,6, 6, '2025-06-06', '2025-06-20', 'RETURNED'),
(7,7, 7, '2025-05-07', null, 'BORROWED'),
(8,7,5,'2025-05-19',null,'BORROWED'),
(9,8, 6, '2025-06-08', '2025-06-22', 'RETURNED'),
(10,9, 9, '2025-05-30', '2025-06-14', 'RETURNED'),
(11,10, 10, '2025-06-09', '2025-06-23', 'RETURNED');


--fine

INSERT INTO lib.Fine (amount, fineid, last_payment_date,transaction_date, member_id, status) VALUES
(9.0, 1,null, '2025-06-03', 2, 'PENDING'),
(2.5, 2, null,'2025-06-03', 3, 'PENDING'),
(7.5, 3, null,'2025-06-03', 5, 'PENDING'),
(6.5, 4, null,'2025-06-03', 7, 'PENDING'),
(0.5, 5, null,'2025-06-03', 5, 'PENDING');


--Notification

INSERT INTO lib.Notification (dateSent, notificationid,member_id, message) VALUES
('2025-06-03', 1, 2, 'Book ID: 2 (To Kill a Mockingbird) - Your book is overdue. Please return it or pay the overdue fine to keep using the book.'),
('2025-06-03', 2, 3, 'Book ID: 3 (1984) - Your book is overdue. Please return it or pay the overdue fine to keep using the book.'),
('2025-06-03', 3, 5, 'Book ID: 5 (The Catcher in the Rye) - Your book is overdue. Please return it or pay the overdue fine to keep using the book.'),
('2025-06-03', 4, 7, 'Book ID: 7 (War and Peace) - Your book is overdue. Please return it or pay the overdue fine to keep using the book.'),
('2025-06-03', 5, 5, 'Book ID: 7 (War and Peace) - Your book is overdue. Please return it or pay the overdue fine to keep using the book.');
