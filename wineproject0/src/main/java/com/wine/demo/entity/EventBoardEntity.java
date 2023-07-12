package com.wine.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="eventboard")
public class EventBoardEntity {

	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer evboardid;
	
	
	private String evboardwriter;
	private Integer evboardhits;
	private String evboardcreatetime;
	private String evboardupdatetime;
	private String evboardevname;
	private String evboardevplace;
	private String evboardevfee;
	private String evboardevperiod;
	private String evboardevopentime;
	private String evboardevcontent;
	private String filename;
	private String filepath;
	
}
