package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Author;
import com.itpatagonia.Buhoristeca.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(Integer idAuthor) {
        Optional<Author> author = authorRepository.findById(idAuthor);

        if (author.isEmpty()) throw new RuntimeException("El autor con id " + idAuthor + " no existe");

        return author.get();
    }

}
