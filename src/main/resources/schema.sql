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

CREATE TABLE IF NOT EXISTS bookCopy (
    idBookCopy INT NOT NULL,
    idBook INT NOT NULL,
    state VARCHAR(30) NOT NULL DEFAULT 'Disponible',
    PRIMARY KEY (idBook, idBookCopy),
    FOREIGN KEY (idBook) REFERENCES book (idBook)
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

