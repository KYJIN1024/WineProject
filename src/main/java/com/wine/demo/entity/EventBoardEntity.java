package com.wine.demo.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.wine.demo.model.User;

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
    
 // User 엔티티와의 연결을 나타내는 필드와 어노테이션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    
    
    // 게시글의 첫 번째 이미지 URL을 추출
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
    
    // HTML 태그를 제외한 순수 텍스트 내용을 반환
    public String getPlainTextContent() {
        if (this.evboardevcontent == null) return "";
        
        String plainText = this.evboardevcontent.replaceAll("<[^>]+>", "").trim();
        
        // 40자 이상이면 40자까지만 잘라서 반환
        return plainText.length() > 40 ? plainText.substring(0, 40) + "..." : plainText;
    }
    
    // 생성 시간을 "yyyy-MM-dd" 형식으로 포맷팅하여 반환
    public String getFormattedCreationTime() {
        if (this.evboardcreatetime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.evboardcreatetime.format(formatter);
    }
    
}