package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.PDFBook;
import com.itpatagonia.Buhoristeca.entities.PDFBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PDFBookRepository extends JpaRepository<PDFBook, PDFBookId> {

    @Query(value = """
            SELECT idPdfBook
            FROM pdfBook
            WHERE idBook = :idBook
            ORDER BY idPdfBook DESC
            LIMIT 1;
            """, nativeQuery = true)
    Integer getLastPDFId(@Param("idBook") Integer idBook);
}
