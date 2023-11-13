package com.wine.demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
	
	public static String getTimeAgo(LocalDateTime past) {
		
		if (past == null) {
		        return "날짜 정보 없음";  // 적절한 기본값을 반환하거나 예외를 발생시킵니다.
		    }
		
		
		
        Duration duration = Duration.between(past, LocalDateTime.now());
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "초 전";
        } else if (seconds < 3600) {
            return seconds / 60 + "분 전";
        } else if (seconds < 86400) {
            return seconds / 3600 + "시간 전";
        } else {
            return seconds / 86400 + "일 전";
        }
    }

}
