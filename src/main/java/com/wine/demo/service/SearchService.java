package com.wine.demo.service;

import com.wine.demo.model.Search;
import com.wine.demo.repository.SearchRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final SearchRepository searchRepository;
    private String lastShownManufacturer = null;
    private String lastShownRegion = null;

    
    

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Transactional(readOnly = true)
    public List<Search> searchByKeyword(String keyword) {
        return searchRepository.findByWineNameContaining(keyword);
    }

    @Transactional(readOnly = true)
    public List<Search> searchByIngredient(String ingredient) {
        return searchRepository.findByMainIngredientContaining(ingredient);
    }
    
    @Transactional(readOnly = true)
    public List<Search> findAll() {
        return searchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Search> findDistinctByManufacturerAndRegion() {
        List<Search> distinctList = searchRepository.findDistinctByManufacturerAndRegion();
        return distinctList.size() > 4 ? distinctList.subList(0, 4) : distinctList;
    }
    
}