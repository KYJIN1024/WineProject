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

   
    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
	
	// 와인검색 페이지 
	@GetMapping("/search")
	public String showSearchPage() {
	    return "search/search";
	}

	//통합검색 
	@GetMapping("/search/integrated")
	@ResponseBody 
	public List<Search> integratedSearch(@RequestParam String keyword) {
	    return searchRepository.findByWineNameContaining(keyword);
	}
	
	//상세검색 
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
	    	List<String> ingredientList = Arrays.asList(ingredient.split(","));
	        results = results.stream()
	        		.filter(search -> ingredientList.stream().anyMatch(ing -> search.getMainIngredient().contains(ing)))
	                .collect(Collectors.toList());
	    } else if ("기타".equals(ingredient)) {
	        results = results.stream()
	                .filter(search -> !Arrays.asList("포도", "머루", "사과", "복숭아", "복분자").contains(search.getMainIngredient()))
	                .collect(Collectors.toList());
	    }

	    // 지역별 검색
	    if (!region.isEmpty() && !"기타".equals(region)) {
	    	List<String> regionList = Arrays.asList(region.split(","));
	        results = results.stream()
	        		.filter(search -> regionList.stream().anyMatch(reg -> reg.equals(search.getRegion())))
	                .collect(Collectors.toList());
	    } else if ("기타".equals(region)) {
	        results = results.stream()
	                .filter(search -> !Arrays.asList("경상북도", "충청북도", "충청남도", "경상남도", "강원도").contains(search.getRegion()))
	                .collect(Collectors.toList());
	    }

	    // 용량별 검색
	    if (!volume.isEmpty()) {
	    	List<String> volumeList = Arrays.asList(volume.split(","));
	        results = results.stream()
	        		.filter(search -> volumeList.stream().anyMatch(vol -> search.getSpecification().contains(vol)))
	                .collect(Collectors.toList());
	    }

	    // 도수별 검색
	    if (!degree.isEmpty()) {
	    	 List<String> degreeList = Arrays.asList(degree.split(","));
	    	 results = results.stream()
	                 .filter(search -> {
	                     double alcohol = search.getAlcoholDegree();
	                     return degreeList.stream().anyMatch(deg -> {
	                         switch(deg) {
	                             case "10이하":
	                                 return alcohol <= 10;
	                             case "10-11도":
	                                 return alcohol > 10 && alcohol <= 11;
	                             case "11-12도":
	                                 return alcohol > 11 && alcohol <= 12;
	                             case "12-13도":
	                                 return alcohol > 12 && alcohol <= 13;
	                             case "14이상":
	                                 return alcohol >= 14;
	                             default:
	                                 return false;
	                         }
	                     });
	                 })
	                 .collect(Collectors.toList());
	     }

	    return results;
	}
}