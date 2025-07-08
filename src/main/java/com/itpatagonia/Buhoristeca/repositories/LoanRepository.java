package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Integer> {


    @Query(value = """
            SELECT *
            FROM loan
            WHERE dni = :idClient AND returnDate IS NULL;
            """, nativeQuery = true)
    Loan findActiveLoanToClientWithId(@Param("idClient") Integer idClient);
}
