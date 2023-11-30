package com.wine.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.repository.EventBoardRepository;

@Service
public class EventBoardService {
    
    @Autowired
    private EventBoardRepository evboardRepository;

    // 글 리스트 불러오기
    public Page<EventBoardEntity> eventBoardList(Pageable pageable) {
        return evboardRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public EventBoardEntity eventBoardView(Integer id) {
        return evboardRepository.findById(id).orElse(null);
    }

    // 특정 게시글 삭제하기
    public void EventBoardDelete(Integer id) {
        evboardRepository.deleteById(id);
    }

    // 글 수정하기
    public void EventBoardUpdate(EventBoardEntity entity) {
        evboardRepository.save(entity);
    }

    // 글 작성
    public void EventBoardWrite(EventBoardEntity evbEntity) {
       
        evboardRepository.save(evbEntity);
    }
    
    // 이미지 저장
    public String saveImage(MultipartFile file) throws IOException {
        String directoryPath = "D:\\uploaded_files";
        File dir = new File(directoryPath);

        // 디렉토리 확인 및 생성
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 확장자 확인
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        // 파일 저장
        String savedFileName = UUID.randomUUID() + "." + extension;
        File targetFile = new File(dir, savedFileName);

        file.transferTo(targetFile);

        return directoryPath + "\\" + savedFileName;
    }
    
    // 태그 
    public static String stripHtmlTags(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "");
    }
    
    // 조회수가 높은 상위 5개의 게시글 출력 메서드
    public List<EventBoardEntity> getTopEventBoardsByHits() {
        return evboardRepository.findTop5ByOrderByEvboardhitsDesc();
    }
    
}