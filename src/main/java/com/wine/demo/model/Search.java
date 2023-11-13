package com.wine.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Getter, Setter, toString, equalsAndHashCode를 모두 포함
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드 값을 인자로 받는 생성자
@Entity
public class Search {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "wine_name")
	    private String wineName;
	    
	    @Column(name = "alcohol_degree")
	    private Integer alcoholDegree;
	    
	    private String specification;
	    
	    @Column(name = "main_ingredient")
	    private String mainIngredient;
	    
	    private String manufacturer;
	    
	    @Column(name = "image_url")
	    private String imageUrl;
	    
	    private String region;
	    
	}