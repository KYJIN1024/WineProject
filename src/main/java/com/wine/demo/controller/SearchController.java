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

	//와인명검
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
	    
	    // if구문: 사용자가 지정한 조건을 기반으로 와인을 검색하고자 할때 실행됩니다.
	    // 원료별 검색  
	    if (!ingredient.isEmpty()) { //  user가 원료를 선택한경우(파라미터가 비어있지않은 경우) 조건이 실행됩니다.
	    	results = filterByIngredient(results, ingredient);  
	    }

	    // 지역별 검색
	    if (!region.isEmpty()) {  //  user가 지역을 선택한경우(파라미터가 비어있지않은 경우) 조건이 실행됩니다.
	        results = filterByRegion(results, region);
	    }

	    // 용량별 검색
	    if (!volume.isEmpty()) { //  user가 용량을 선택한경우(파라미터가 비어있지않은 경우) 조건이 실행됩니다.
	        results = filterByVolume(results, volume); 
	    }

	    // 도수별 검색
	    if (!degree.isEmpty()) { //  user가 도수를 선택한경우(파라미터가 비어있지않은 경우) 조건이 실행됩니다.
	        results = filterByDegree(results, degree); 
	    }

	    return results;
	}
	    
	// 원료기반 필터링을 수행합니다.
	private List<Search> filterByIngredient(List<Search> results, String ingredient) { //filterByIngredient 메서드는 사용자가 지정한 원료에 따라 와인을 필터링합니다.
		List<String> ingredientList = Arrays.asList(ingredient.split(",")); //사용자가 입력한 원료 문자열을 쉼표(,)로 분리하여 배열로 변환합니다.
		if (!"기타".equals(ingredient)) { //"기타".equals(ingredient)가 false인 경우 (사용자가 구체적인 원료를 선택한 경우)
			return results.stream()
			        .filter(search -> containsAnyIngredient(search, ingredientList)) //containsAnyIngredient 메서드를 사용하여, 전체와인리스트(search)중에서 사용자가 선택한 사용자가 선택한원료가 있는 와인을 필터링합니다.
			        .collect(Collectors.toList());  //true로 반환된 list를 모아서 반환합니다
		} else { //"기타".equals(ingredient)가 true인 경우 (사용자가 '기타'를 선택한 경우)
			return results.stream() 
			        .filter(search -> !Arrays.asList("포도", "머루", "사과", "복숭아", "복분자").contains(search.getMainIngredient())) //전체와인리스트(search)중에서 "포도", "머루", "사과", "복숭아", "복분자"를 제외한 다른 원료로 만든 와인을 필터링합니다.
			        .collect(Collectors.toList());  //true로 반환된 list를 모아서 반환합니다
		}
	}
	// 전체와인리스트 중 user가 선택한 원료 목록중 하나 이상을 포함하는지 확인
	private boolean containsAnyIngredient(Search search, List<String> ingredientList) { 
	    return ingredientList.stream().anyMatch(ingredient -> search.getMainIngredient().contains(ingredient)); //anyMatch 어떤 원료라도 주어진 조건(contains(ingredient)을 만족하면 true를 반환
	}													 // 주어진 와인 객체의 mainIngredient 필드가 해당 원료를 포함하고 있는지 검사합니다.

	private List<Search> filterByRegion(List<Search> results, String region) {
		List<String> regionList = Arrays.asList(region.split(","));
		if (!"기타".equals(region)) {
			return results.stream()
			        .filter(search -> regionList.contains(search.getRegion()))
			        .collect(Collectors.toList());
		} else {
			return results.stream()
			        .filter(search -> !Arrays.asList("경상북도", "충청북도", "충청남도", "경상남도", "강원도").contains(search.getRegion()))
			        .collect(Collectors.toList());
		}
	}

	private List<Search> filterByVolume(List<Search> results, String volume) {
		List<String> volumeList = Arrays.asList(volume.split(","));
		return results.stream()
		        .filter(search -> volumeList.stream().anyMatch(vol -> search.getSpecification().contains(vol)))
		        .collect(Collectors.toList());
	}

	private List<Search> filterByDegree(List<Search> results, String degree) {
		List<String> degreeList = Arrays.asList(degree.split(","));
		return results.stream()
		        .filter(search -> matchesDegree(search, degreeList))
		        .collect(Collectors.toList());
	}

	private boolean matchesDegree(Search search, List<String> degreeList) {
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
	}
}