package com.wine.demo.entity;


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
	private String frboardcreatetime;
	private String frboardupdatetime;
	
}
