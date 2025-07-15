package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Publisher;
import com.itpatagonia.Buhoristeca.exceptions.PublisherNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher getPublisherById(Integer idPublisher) {
        return publisherRepository.findById(idPublisher).orElseThrow(() -> new PublisherNotFoundException(idPublisher));
    }
}
