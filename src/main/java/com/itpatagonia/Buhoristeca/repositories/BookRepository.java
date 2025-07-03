package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Book;
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
    List<Book> findBooksWithLoans(@Param("idRole") Integer idRole,
                                  @Param("from") LocalDate fromDate,
                                  @Param("to") LocalDate toDate);


}
