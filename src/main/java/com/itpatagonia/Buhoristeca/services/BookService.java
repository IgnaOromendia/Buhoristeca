package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.spi.LocaleServiceProvider;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> getAllLoanedBooks(Integer idRole, LocalDate startDate, LocalDate endDate) {
        List<Book> loanedBooks = bookRepository.findBooksWithLoans(idRole, startDate, endDate);

        if (loanedBooks == null)
            throw new RuntimeException("Error en la búsqueda de libros prestados");

        return loanedBooks.stream().map(Book::convertToBookDto).toList();
    }

}
