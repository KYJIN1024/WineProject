package com.wine.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               
@NoArgsConstructor  
@AllArgsConstructor 
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