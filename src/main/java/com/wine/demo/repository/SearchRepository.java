package com.wine.demo.repository;

import com.wine.demo.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
	 List<Search> findByWineNameContaining(String keyword);
	 List<Search> findByMainIngredientContaining(String ingredient);
	 List<Search> findBySpecificationContaining(String volume);
	 
	 @Query("SELECT s FROM Search s WHERE s.mainIngredient NOT LIKE %?1% AND s.mainIngredient NOT LIKE %?2% AND s.mainIngredient NOT LIKE %?3% AND s.mainIngredient NOT LIKE %?4% AND s.mainIngredient NOT LIKE %?5%")
	    List<Search> findOthersExcluding(String keyword1, String keyword2, String keyword3, String keyword4, String keyword5);

	    @Query("SELECT s FROM Search s WHERE s.mainIngredient LIKE %?1% AND s.region LIKE %?2%")
	    List<Search> findByMainIngredientAndRegionContaining(String ingredient, String region);

	    @Query("SELECT s FROM Search s WHERE s.region NOT LIKE %?1% AND s.region NOT LIKE %?2% AND s.region NOT LIKE %?3% AND s.region NOT LIKE %?4% AND s.region NOT LIKE %?5%")
	    List<Search> findOthersExcludingRegions(String region1, String region2, String region3, String region4, String region5);
	    
	    @Query("SELECT s FROM Search s WHERE s.id IN (SELECT MIN(s2.id) FROM Search s2 GROUP BY s2.manufacturer, s2.region) ORDER BY s.id ASC")
	    List<Search> findDistinctByManufacturerAndRegion();
	    
	}
