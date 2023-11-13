package com.wine.demo.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="freeboard")
public class FreeBoardEntity
{
	
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Integer frboardid;

	private String frboardwriter;
	private String frboardtitle;
	private String frboardcontent;
	private Integer frboardhits;
	
	@Column(name = "frboardcreatetime")
	private LocalDateTime frboardcreatetime;
	 
	@Column(name = "frboardupdatetime")
	private LocalDateTime frboardupdatetime;
	
	private String tags;
	
}
