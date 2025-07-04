package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = """
        SELECT b.*
        FROM book b
        JOIN loan l on b.idBook = l.idBook
        JOIN client c on c.dni = l.dni
        WHERE c.idRole = :idRole AND :from <= l.loanDate AND l.loanDate <= :to
        GROUP BY b.idBook;
        """, nativeQuery = true)
    List<Book> findBooksWithLoansByRole(@Param("idRole") Integer idRole,
                                        @Param("from") LocalDate fromDate,
                                        @Param("to") LocalDate toDate);

    @Query(value = """
            WITH availableCopies AS (
            	SELECT idBook, COUNT(*) AS amount
                FROM bookCopy
                WHERE idState = 1
                GROUP BY idBook
            )
            SELECT b.title, COUNT(*) AS amountOfCopies, ac.amount AS amountOfAvailableCopies
            FROM bookCopy bc
            JOIN availableCopies ac ON ac.idBook = bc.idBook
            JOIN book b ON b.idBook = bc.idBook
            GROUP BY bc.idBook;
            """, nativeQuery = true)
    List<BookCopiesAmountProjection> findAllCopiesAmount();

    @Query(value = """
            WITH availableCopies AS (
            	SELECT idBook, COUNT(*) AS amount
                FROM bookCopy
                WHERE idState = 1
                GROUP BY idBook
            )
            SELECT b.title, COUNT(*) AS amountOfCopies, ac.amount AS amountOfAvailableCopies
            FROM bookCopy bc
            JOIN availableCopies ac ON ac.idBook = bc.idBook
            JOIN book b ON b.idBook = bc.idBook
            WHERE bc.idBook = :idBook
            """, nativeQuery = true)
    BookCopiesAmountProjection findCopiesAmountByIdBook(@Param("idBook") Integer idBook);

    @Query(value = """
            WITH loanedBooks AS (
            	SELECT b.idBook
                FROM book b
                JOIN loan l ON l.idBook = b.idBook
                GROUP BY b.idBook
            )
            SELECT b.*
            FROM book b
            WHERE b.idBook NOT IN (SELECT * FROM loanedBooks)
            """, nativeQuery = true)
    List<Book> findBooksWithNoLoans();

    @Query(value = """
            WITH loanedBooks AS (
            	SELECT b.idBook
                FROM book b
                JOIN loan l ON l.idBook = b.idBook
                WHERE :from <= l.loanDate AND l.loanDate <= :to
                GROUP BY b.idBook
            )
            SELECT b.*
            FROM book b
            WHERE b.idBook NOT IN (SELECT * FROM loanedBooks)
            """, nativeQuery = true)
    List<Book> findBooksWithNoLoansBetween(@Param("from") LocalDate startDate,
                                           @Param("to") LocalDate endDate);

    @Query(value = """
        SELECT b.*
        FROM book b
        JOIN loan l on b.idBook = l.idBook
        JOIN client c on c.dni = l.dni
        WHERE c.dni = :idClient
        GROUP BY b.idBook;
        """, nativeQuery = true)
    List<Book> findBooksLoanedToClientWithId(@Param("idClient") Integer idClient);
}
