package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
