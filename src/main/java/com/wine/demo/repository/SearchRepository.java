package com.wine.demo.repository;

import com.wine.demo.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
	
	 // 와인 이름에 주어진 키워드가 포함된 검색 결과를 반환하는 메서드
	 List<Search> findByWineNameContaining(String keyword);
	 
	 // 원료에 주어진 원료가 포함된 검색 결과를 반환하는 메서드
	 List<Search> findByMainIngredientContaining(String ingredient);
	 
	 // 용량에 주어진 값이 포함된 검색 결과를 반환하는 메서드
	 List<Search> findBySpecificationContaining(String volume);
	 
	 // 주어진 키워드들을 제외한 다른 원료들을 포함하는 검색 결과를 반환하는 메서드 (이달의추천와인)
	 @Query("SELECT s FROM Search s WHERE s.mainIngredient NOT LIKE %?1% AND s.mainIngredient NOT LIKE %?2% AND s.mainIngredient NOT LIKE %?3% AND s.mainIngredient NOT LIKE %?4% AND s.mainIngredient NOT LIKE %?5%")
	 List<Search> findOthersExcluding(String keyword1, String keyword2, String keyword3, String keyword4, String keyword5);

	 // 주어진 원료와 지역을 모두 포함하는 검색 결과를 반환하는 메서드 (이달의추천와인)
	 @Query("SELECT s FROM Search s WHERE s.mainIngredient LIKE %?1% AND s.region LIKE %?2%")
	 List<Search> findByMainIngredientAndRegionContaining(String ingredient, String region);

	 // 주어진 지역들을 제외한 다른 지역들을 포함하는 검색 결과를 반환하는 메서드 (이달의추천와인)
	 @Query("SELECT s FROM Search s WHERE s.region NOT LIKE %?1% AND s.region NOT LIKE %?2% AND s.region NOT LIKE %?3% AND s.region NOT LIKE %?4% AND s.region NOT LIKE %?5%")
	 List<Search> findOthersExcludingRegions(String region1, String region2, String region3, String region4, String region5);
	  
	 // 제조사와 지역을 기준으로 중복 없이 검색 결과를 반환하는 메서드 (이달의추천와인)
	 @Query("SELECT s FROM Search s WHERE s.id IN (SELECT MIN(s2.id) FROM Search s2 GROUP BY s2.manufacturer, s2.region) ORDER BY s.id ASC")
	 List<Search> findDistinctByManufacturerAndRegion();
	    
	}
