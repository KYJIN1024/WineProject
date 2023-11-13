package com.wine.demo.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="job")
public class JobEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobboardid;
    private String jobboardcontent;
    private String jobboardtitle;
    private String jobboardsectors;
    private Integer jobboardlikes;
    private String jobboardwriter;
    private String jobboardregistrationdate;
    private String jobboardregion;
    private String jobboardcompanyname;
   
    
    public String getFirstImageUrl() {
    	if (this.jobboardcontent == null || this.jobboardcontent.trim().isEmpty()) 
            return null;
        
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(this.jobboardcontent);
        if (matcher.find()) {
            String imageUrl = matcher.group(1);
            return imageUrl != null && !imageUrl.trim().isEmpty() ? imageUrl : null;
        }
        return null;  // 이미지 URL이 없는 경우
    }

    public String getPlainTextContent() {
        if (this.jobboardcontent == null) return "";
        
        String plainText = this.jobboardcontent.replaceAll("<[^>]+>", "").trim();
        
        // 40자 이상이면 40자까지만 잘라서 반환
        return plainText.length() > 40 ? plainText.substring(0, 40) + "..." : plainText;
    }
    
    public void like() {
        this.jobboardlikes += 1;
    }
    
    

}
