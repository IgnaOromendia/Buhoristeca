package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Language;
import com.itpatagonia.Buhoristeca.exceptions.LanguageNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public Language getLanguageById(Integer idLanguage) {
        return languageRepository.findById(idLanguage).orElseThrow(() -> new LanguageNotFoundException(idLanguage));
    }
}
