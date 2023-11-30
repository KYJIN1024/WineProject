package com.wine.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.entity.ProducerLike;
import com.wine.demo.model.User;
import com.wine.demo.repository.ProducerBoardRepository;
import com.wine.demo.repository.ProducerLikeRepository;
import com.wine.demo.repository.UserRepository;

@Service
public class ProducerBoardService {

	@Autowired
	private ProducerBoardRepository producerBoardRepository;

	@Autowired
	private ProducerLikeRepository producerLikeRepository;
	 
	@Autowired
	private UserRepository userRepository; 
	 
	// 모든 게시물을 가져오는 메서드
    public List<ProducerEntity> getAllProducerBoards() {
        return producerBoardRepository.findAll();
    }
    
    // 특정 id의 게시물을 가져오는 메서드
    public Optional<ProducerEntity> getProducerBoardById(Integer id) {
        return producerBoardRepository.findById(id);
    }
    
    // 새로운 게시물을 저장하는 메서드
    public ProducerEntity saveProducerBoard(ProducerEntity producerEntity) {
        return producerBoardRepository.save(producerEntity);
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
    
    // 사용자가 게시물에 좋아요를 눌렀는지 확인하는 메서드
    public boolean hasUserLiked(Integer userId, Integer producerId) {
        return producerLikeRepository.existsByUserIdAndProducer_Pdboardid(userId, producerId);
    }
    
    // 사용자가 게시물에 좋아요를 누르는 기능을 처리하는 메서드
    public void likeProducer(Integer userId, Integer producerId) {
        if (!hasUserLiked(userId, producerId)) {
            ProducerLike like = new ProducerLike();

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")); 
            ProducerEntity producer = producerBoardRepository.findById(producerId).orElseThrow(() -> new RuntimeException("Producer not found!")); 

            like.setUser(user);
            like.setProducer(producer);

            producerLikeRepository.save(like);
            
            producer.like();  // 게시글의 좋아요 수 증가
            producerBoardRepository.save(producer);
        }
    }
    
    // 특정 생산자 게시물을 업데이트하는 메서드
    public void updateProducerBoard(Integer id, ProducerEntity updatedProducer) {
        Optional<ProducerEntity> producerOpt = producerBoardRepository.findById(id);

        if (producerOpt.isPresent()) {
            ProducerEntity existingProducer = producerOpt.get();
            existingProducer.setPdboardtitle(updatedProducer.getPdboardtitle());
            existingProducer.setPdboardcontent(updatedProducer.getPdboardcontent());
  

            producerBoardRepository.save(existingProducer);
        } else {
            throw new RuntimeException("Producer not found!");
        }
    }
    
    // 게시글 삭제
    public void deleteProducerBoard(Integer id) {
    	producerBoardRepository.deleteById(id);
    }
    
    // 페이지 정보에 따라 게시물을 가져오는 메서드
    public Page<ProducerEntity> getAllProducerBoards(Pageable pageable) {
        return producerBoardRepository.findAll(pageable);
    }
    
}