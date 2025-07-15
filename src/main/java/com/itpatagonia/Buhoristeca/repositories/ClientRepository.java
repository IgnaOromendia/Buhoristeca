package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = """
            SELECT *
            FROM client
            WHERE isActive = 1;
            """, nativeQuery = true)
    List<Client> findActive();

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE client
            SET isActive = :status
            WHERE dni = :idClient
            """, nativeQuery = true)
    void updateStatus(@Param("idClient") Integer idClient,
                      @Param("status") Integer statusToSet);
}
