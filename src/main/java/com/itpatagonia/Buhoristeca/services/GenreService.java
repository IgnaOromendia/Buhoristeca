package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Genre;
import com.itpatagonia.Buhoristeca.exceptions.GenresNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Set<Genre> getGenresByIds(Set<Integer> genresIds) {
        if (genresIds.isEmpty()) return new HashSet<>();

        List<Genre> genres = genreRepository.findAllById(genresIds);

        if (genres.isEmpty()) throw new GenresNotFoundException(genresIds);

        return new HashSet<>(genres);
    }
}
