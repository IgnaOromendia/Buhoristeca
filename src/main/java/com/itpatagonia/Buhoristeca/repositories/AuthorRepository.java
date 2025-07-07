package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
