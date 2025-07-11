package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Language;
import com.itpatagonia.Buhoristeca.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public Language getLanguageById(Integer idLanguage) {
        Optional<Language> language = languageRepository.findById(idLanguage);

        if (language.isEmpty()) throw new RuntimeException("El idioma con id " + idLanguage + " no existe");

        return language.get();
    }
}
