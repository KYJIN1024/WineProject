package com.wine.demo.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.*;

import com.wine.demo.model.User;

import lombok.Data;

@Entity
@Data
@Table(name="producer")
public class ProducerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pdboardid;
    private String pdboardcontent;
    private String pdboardtitle;
    private String pdboardlink;
    private Integer pdboardlikes;
    private String pdboardwriter;
    private String pdboardregion;
    
    // User 엔티티와의 연결을 나타내는 필드와 어노테이션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    

    public String getFirstImageUrl() {
    	if (this.pdboardcontent == null || this.pdboardcontent.trim().isEmpty()) 
            return null;
        
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(this.pdboardcontent);
        if (matcher.find()) {
            String imageUrl = matcher.group(1);
            return imageUrl != null && !imageUrl.trim().isEmpty() ? imageUrl : null;
        }
        return null;  // 이미지 URL이 없는 경우
    }

    public String getPlainTextContent() {
        if (this.pdboardcontent == null) return "";
        
        String plainText = this.pdboardcontent.replaceAll("<[^>]+>", "").trim();
        
        // 40자 이상이면 40자까지만 잘라서 반환
        return plainText.length() > 40 ? plainText.substring(0, 40) + "..." : plainText;
    }
    
    public void like() {
        this.pdboardlikes += 1;
    }
    
    
}
