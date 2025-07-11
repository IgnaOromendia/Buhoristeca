package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.BookState;
import com.itpatagonia.Buhoristeca.repositories.BookStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookStateService {

    @Autowired
    private BookStateRepository bookStateRepository;

    public BookState getAvailableState() {
        Optional<BookState> state = bookStateRepository.findById(1);

        if (state.isEmpty()) throw new RuntimeException("Error en la búsqueda del estado disponible");

        return state.get();
    }

    public void assertStateExists(Integer idState) {
        if (bookStateRepository.findById(idState).isEmpty()) throw new RuntimeException("El estado con id " + idState + " no existe");
    }
}
