CREATE TABLE IF NOT EXISTS role (
    idRole INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    dni INT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    birthDate DATE NOT NULL,
    address VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    idRole INT NOT NULL,
    isActive SMALLINT NOT NULL DEFAULT 1,
    FOREIGN KEY (idRole) REFERENCES role (idRole)
);

CREATE TABLE IF NOT EXISTS author (
    idAuthor INT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookLanguage (
    idLanguage INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS publisher (
    idPublisher INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS genre (
    idGenre INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    idBook INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    idAuthor INT NOT NULL,
    description VARCHAR(50) NOT NULL,
    publicationDate DATE NOT NULL,
    idPublisher INT NOT NULL,
    idLanguage INT NOT NULL,
    isActive SMALLINT NOT NULL DEFAULT 1,
    FOREIGN KEY (idAuthor) REFERENCES author (idAuthor),
    FOREIGN KEY (idPublisher) REFERENCES publisher (idPublisher),
    FOREIGN KEY (idLanguage) REFERENCES bookLanguage (idLanguage)
);

CREATE TABLE IF NOT EXISTS bookGenre (
    idBook INT NOT NULL,
    idGenre INT NOT NULL,
    PRIMARY KEY (idBook, idGenre),
    FOREIGN KEY (idBook) REFERENCES book (idBook),
    FOREIGN KEY (idGenre) REFERENCES genre (idGenre)
);

CREATE TABLE IF NOT EXISTS bookState (
    idState INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookCopy (
    idBookCopy INT NOT NULL,
    idBook INT NOT NULL,
    idState INT NOT NULL,
    PRIMARY KEY (idBook, idBookCopy),
    FOREIGN KEY (idBook) REFERENCES book (idBook),
    FOREIGN KEY (idState) REFERENCES bookState (idState)
);

CREATE TABLE IF NOT EXISTS loan (
    idLoan INT AUTO_INCREMENT PRIMARY KEY,
    idBook INT NOT NULL,
    idBookCopy INT NOT NULL,
    dni INT NOT NULL,
    loanDate DATE NOT NULL,
    returnDate DATE,
    limitReturnDate DATE NOT NULL,
    FOREIGN KEY (dni) REFERENCES client (dni),
    FOREIGN KEY (idBook, idBookCopy) REFERENCES bookCopy (idBook, idBookCopy)
);

INSERT IGNORE INTO author (idAuthor, name) VALUES (1, 'author1');
INSERT IGNORE INTO author (idAuthor, name) VALUES (2, 'author2');
INSERT IGNORE INTO author (idAuthor, name) VALUES (3, 'author3');

INSERT IGNORE INTO publisher (idPublisher, name) VALUES (1, 'publisher1');
INSERT IGNORE INTO bookLanguage (idLanguage, title) VALUES (1, 'spanish');

INSERT IGNORE INTO genre (idGenre, name) VALUES (1, 'aventura');
INSERT IGNORE INTO genre (idGenre, name) VALUES (2, 'magia');

INSERT IGNORE INTO book (idBook, title, idAuthor, description, publicationDate, idPublisher, idLanguage) VALUES (1,'El señor de los anillos', 1, 'Un libro de aventrua medieval', CURRENT_DATE, 1, 1);
INSERT IGNORE INTO book (idBook, title, idAuthor, description, publicationDate, idPublisher, idLanguage) VALUES (2,'Harry Potter', 2, 'Un libro de aventrua mágica', CURRENT_DATE, 1, 1);
INSERT IGNORE INTO book (idBook, title, idAuthor, description, publicationDate, idPublisher, idLanguage) VALUES (3,'Game of Thrones', 3, 'Un libro de aventura y traiciones', CURRENT_DATE, 1, 1);

INSERT IGNORE INTO bookGenre (idBook, idGenre) VALUES (1, 1);
INSERT IGNORE INTO bookGenre (idBook, idGenre) VALUES (2, 1);
INSERT IGNORE INTO bookGenre (idBook, idGenre) VALUES (2, 2);
INSERT IGNORE INTO bookGenre (idBook, idGenre) VALUES (3, 1);

INSERT IGNORE INTO bookState (idState, title) VALUES (1, 'disponible');
INSERT IGNORE INTO bookState (idState, title) VALUES (2, 'no disponible');

INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (1,1,2);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (1,2,2);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (1,3,1);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (2,1,2);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (2,2,1);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (2,3,1);
INSERT IGNORE INTO bookCopy (idBook, idBookCopy, idState) VALUES (3,1,1);

INSERT IGNORE INTO role (idRole, title) VALUES (1, 'student');
INSERT IGNORE INTO role (idRole, title) VALUES (2, 'professor');

INSERT IGNORE INTO client (dni, name, lastName, birthDate, address, email, idRole) VALUES (1, 'Ignacio', 'Oromendia', '2001-03-26', 'Calle 2201', 'igna@test.com', 1);
INSERT IGNORE INTO client (dni, name, lastName, birthDate, address, email, idRole) VALUES (2, 'Juan', 'Perez', '1990-10-25', 'Calle 121', 'jaun@test.com', 2);

INSERT IGNORE INTO loan (idLoan, idBook, idBookCopy, dni, loanDate, limitReturnDate) VALUES (1, 1, 1, 1, CURRENT_DATE, CURRENT_DATE + INTERVAL 14 DAY);
INSERT IGNORE INTO loan (idLoan, idBook, idBookCopy, dni, loanDate, limitReturnDate) VALUES (2, 2, 1, 2, CURRENT_DATE, CURRENT_DATE + INTERVAL 14 DAY);
INSERT IGNORE INTO loan (idLoan, idBook, idBookCopy, dni, loanDate, limitReturnDate) VALUES (3, 1, 2, 2, CURRENT_DATE, CURRENT_DATE + INTERVAL 14 DAY);

