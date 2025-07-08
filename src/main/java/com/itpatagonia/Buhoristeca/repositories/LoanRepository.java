package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Loan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Integer> {


    @Query(value = """
            SELECT *
            FROM loan
            WHERE dni = :idClient AND returnDate IS NULL;
            """, nativeQuery = true)
    Loan findActiveLoanToClientWithId(@Param("idClient") Integer idClient);

    @Query(value = """
            SELECT idLoan
            FROM loan
            WHERE dni = :idClient AND idBook = :idBook AND idBookCopy = :idBookCopy AND returnDate IS NULL;
            """, nativeQuery = true)
    Integer findIdLoanBy(@Param("idClient") Integer idClient,
                         @Param("idBook") Integer idBook,
                         @Param("idBookCopy") Integer idBookCopy);
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE loan
            SET returnDate = CURRENT_DATE
            WHERE idLoan = :idLoan
            """, nativeQuery = true)
    void updateReturnDateOfLoanWithId(@Param("idLoan") Integer idLoan);
}
