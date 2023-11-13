package com.wine.demo.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name="eventboard")
public class EventBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evboardid;
    private String evboardevfee;
    private String evboardevname;
    private String evboardevperiod;
    private String evboardevplace;
    private String evboardlink;
    private String evboardphonenumber;
    private String evboardimage;
    
    @CreationTimestamp
    private LocalDateTime evboardcreatetime;
    
    private String evboardevcontent;
    private String evboardevopentime;
    private Integer evboardhits;
    private String evboardupdatetime;
    private String evboardwriter;
    private String filename;
    private String imagefilename;
    
    @Column(name = "file_path")
    private String filePath; 
    
    public String getFirstImageUrl() {
    	if (this.evboardevcontent == null || this.evboardevcontent.trim().isEmpty()) 
            return null;
        
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(this.evboardevcontent);
        if (matcher.find()) {
            String imageUrl = matcher.group(1);
            return imageUrl != null && !imageUrl.trim().isEmpty() ? imageUrl : null;
        }
        return null;  // 이미지 URL이 없는 경우
    }

    public String getPlainTextContent() {
        if (this.evboardevcontent == null) return "";
        
        String plainText = this.evboardevcontent.replaceAll("<[^>]+>", "").trim();
        
        // 40자 이상이면 40자까지만 잘라서 반환
        return plainText.length() > 40 ? plainText.substring(0, 40) + "..." : plainText;
    }
    
    public String getFormattedCreationTime() {
        if (this.evboardcreatetime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.evboardcreatetime.format(formatter);
    }
    
}