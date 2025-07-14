package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.BookState;
import com.itpatagonia.Buhoristeca.exceptions.StateNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.BookStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookStateService {

    @Autowired
    private BookStateRepository bookStateRepository;

    public BookState getAvailableState() {
        return bookStateRepository.findById(1).orElseThrow(() -> new StateNotFoundException(1));
    }

    public void assertStateExists(Integer idState) {
        if (bookStateRepository.findById(idState).isEmpty()) throw new StateNotFoundException(idState);
    }
}
