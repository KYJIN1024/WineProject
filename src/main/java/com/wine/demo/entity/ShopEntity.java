package com.wine.demo.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wine.demo.model.User;

import lombok.Data;

@Entity
@Data
@Table(name="shop")
public class ShopEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopboardid;
    private String shopboardcontent;
    private String shopboardtitle;
    private String shopboardlink;
    private Integer shopboardlikes;
    private String shopboardwriter;
    private String shopboardregister;
    private String shopboardphone;
    
    // User 엔티티와의 연결을 나타내는 필드와 어노테이션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    
    
    public String getFirstImageUrl() {
    	if (this.shopboardcontent == null || this.shopboardcontent.trim().isEmpty()) 
            return null;
        
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(this.shopboardcontent);
        if (matcher.find()) {
            String imageUrl = matcher.group(1);
            return imageUrl != null && !imageUrl.trim().isEmpty() ? imageUrl : null;
        }
        return null;  // 이미지 URL이 없는 경우
    }

    public String getPlainTextContent() {
        if (this.shopboardcontent == null) return "";
        
        String plainText = this.shopboardcontent.replaceAll("<[^>]+>", "").trim();
        
        // 40자 이상이면 40자까지만 잘라서 반환
        return plainText.length() > 40 ? plainText.substring(0, 40) + "..." : plainText;
    }
    
    public void like() {
        this.shopboardlikes += 1;
    }
    
    

}
