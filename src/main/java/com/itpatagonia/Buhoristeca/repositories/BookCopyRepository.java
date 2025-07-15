package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.BookCopyId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, BookCopyId> {

    @Query(value = """
            SELECT bc.*
            FROM bookCopy bc
            JOIN book b ON b.idBook = bc.idBook
            WHERE bc.idState = 1 AND bc.idBook = :idBook
            LIMIT 1;
            """, nativeQuery = true)
    BookCopy findAvailableCopyWithIdBook(@Param("idBook") Integer idBook);

    @Query(value = """
            SELECT idBookCopy
            FROM bookCopy
            WHERE idBook = :idBook
            ORDER BY idBookCopy DESC
            LIMIT 1
            """, nativeQuery = true)
    Integer getLastIdBookCopy(@Param("idBook") Integer idBook);

    @Query(value = """
            SELECT *
            FROM bookCopy
            WHERE idBook = :idBook AND idBookCopy = :idBookCopy
            """, nativeQuery = true)
    BookCopy findByBookCopyId(@Param("idBook") Integer idBook,
                              @Param("idBookCopy") Integer idBookCOpy);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE bookCopy
            SET idState = :idState
            WHERE idBook = :idBook AND idBookCopy = :idBookCopy
            """, nativeQuery = true)
    void updateState(@Param("idBook") Integer idBook,
                     @Param("idBookCopy") Integer idBookCopy,
                     @Param("idState") Integer idState);
}
