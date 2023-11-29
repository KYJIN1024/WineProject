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
	 

    public List<ProducerEntity> getAllProducerBoards() {
        return producerBoardRepository.findAll();
    }

    public Optional<ProducerEntity> getProducerBoardById(Integer id) {
        return producerBoardRepository.findById(id);
    }

    public ProducerEntity saveProducerBoard(ProducerEntity producerEntity) {
        return producerBoardRepository.save(producerEntity);
    }


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
    
    public boolean hasUserLiked(Integer userId, Integer producerId) {
        return producerLikeRepository.existsByUserIdAndProducer_Pdboardid(userId, producerId);
    }

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

    public void updateProducerBoard(Integer id, ProducerEntity updatedProducer) {
        Optional<ProducerEntity> producerOpt = producerBoardRepository.findById(id);

        if (producerOpt.isPresent()) {
            ProducerEntity existingProducer = producerOpt.get();
            
            // Update the fields that you want
            existingProducer.setPdboardtitle(updatedProducer.getPdboardtitle());
            existingProducer.setPdboardcontent(updatedProducer.getPdboardcontent());
            // ... (you can continue updating other fields if needed)

            producerBoardRepository.save(existingProducer);
        } else {
            throw new RuntimeException("Producer not found!");
        }
    }
    
    public void deleteProducerBoard(Integer id) {
    	producerBoardRepository.deleteById(id);
    }
    
    public Page<ProducerEntity> getAllProducerBoards(Pageable pageable) {
        return producerBoardRepository.findAll(pageable);
    }
    
    
    
    
    
}