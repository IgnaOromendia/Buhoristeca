package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Author;
import com.itpatagonia.Buhoristeca.exceptions.AuthorNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(Integer idAuthor) {
        return authorRepository.findById(idAuthor).orElseThrow(() -> new AuthorNotFoundException(idAuthor));
    }

}
