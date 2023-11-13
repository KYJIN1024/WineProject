package com.wine.demo.controller;

import com.wine.demo.model.Search;
import com.wine.demo.repository.SearchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {
	
	private final SearchRepository searchRepository;

    // WineRepository를 주입하여 초기화
    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
	
	
	@GetMapping("/search")
	public String showSearchPage() {
	    return "search/search";
	}

	@GetMapping("/search/integrated")
	@ResponseBody 
	public List<Search> integratedSearch(@RequestParam String keyword) {
	    return searchRepository.findByWineNameContaining(keyword);
	}

	@GetMapping("/search/detailed")
	@ResponseBody 
	public List<Search> detailedSearch(
	        @RequestParam String ingredient,
	        @RequestParam String region,
	        @RequestParam String volume,
	        @RequestParam String degree) {
	    
	    List<Search> results = searchRepository.findAll();

	    // 원료별 검색
	    if (!ingredient.isEmpty() && !"기타".equals(ingredient)) {
	        results = results.stream()
	                .filter(search -> search.getMainIngredient().contains(ingredient))
	                .collect(Collectors.toList());
	    } else if ("기타".equals(ingredient)) {
	        results = results.stream()
	                .filter(search -> !Arrays.asList("포도", "머루", "사과", "복숭아", "복분자").contains(search.getMainIngredient()))
	                .collect(Collectors.toList());
	    }

	    // 지역별 검색
	    if (!region.isEmpty() && !"기타".equals(region)) {
	        results = results.stream()
	                .filter(search -> region.equals(search.getRegion()))
	                .collect(Collectors.toList());
	    } else if ("기타".equals(region)) {
	        results = results.stream()
	                .filter(search -> !Arrays.asList("경상북도", "충청북도", "충청남도", "경상남도", "강원도").contains(search.getRegion()))
	                .collect(Collectors.toList());
	    }

	    // 용량별 검색
	    if (!volume.isEmpty()) {
	        results = results.stream()
	                .filter(search -> search.getSpecification().contains(volume))
	                .collect(Collectors.toList());
	    }

	    // 도수별 검색
	    if (!degree.isEmpty()) {
	        switch(degree) {
	            case "10이하":
	                results = results.stream()
	                        .filter(search -> search.getAlcoholDegree() <= 10)
	                        .collect(Collectors.toList());
	                break;
	            case "10-11도":
	                results = results.stream()
	                        .filter(search -> search.getAlcoholDegree() > 10 && search.getAlcoholDegree() <= 11)
	                        .collect(Collectors.toList());
	                break;
	            case "11-12도":
	                results = results.stream()
	                        .filter(search -> search.getAlcoholDegree() > 11 && search.getAlcoholDegree() <= 12)
	                        .collect(Collectors.toList());
	                break;
	            case "12-13도":
	                results = results.stream()
	                        .filter(search -> search.getAlcoholDegree() > 12 && search.getAlcoholDegree() <= 13)
	                        .collect(Collectors.toList());
	                break;
	            case "14이상":
	                results = results.stream()
	                        .filter(search -> search.getAlcoholDegree() >= 14)
	                        .collect(Collectors.toList());
	                break;
	        }
	    }

	    return results;
	}
}