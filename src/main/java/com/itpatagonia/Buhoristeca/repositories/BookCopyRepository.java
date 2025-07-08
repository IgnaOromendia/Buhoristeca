package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.BookCopy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {

    @Query(value = """
            SELECT bc.*
            FROM bookCopy bc
            JOIN book b ON b.idBook = bc.idBook
            WHERE bc.idState = 1
            LIMIT 1;
            """, nativeQuery = true)
    BookCopy findAvailableCopyWithIdBook(Integer idBook);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE bookCopy
            SET idState = 2
            WHERE idBook = :idBook AND idBookCopy = :idBookCopy
            """, nativeQuery = true)
    void updateStateToNotAvailableOfCopyWithId(@Param("idBook") Integer idBook,
                                               @Param("idBookCopy") Integer idBookCopy);
}
