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

import com.wine.demo.entity.ShopLike;
import com.wine.demo.entity.ShopEntity;
import com.wine.demo.model.User;
import com.wine.demo.repository.ShopBoardRepository;
import com.wine.demo.repository.ShopLikeRepository;
import com.wine.demo.repository.UserRepository;

@Service
public class ShopBoardService {
	
	@Autowired
    private ShopBoardRepository shopBoardRepository;

	@Autowired
	private ShopLikeRepository shopLikeRepository;
	 
	@Autowired
	 private UserRepository userRepository; 

	public List<ShopEntity> getAllShopBoards() {
	    return shopBoardRepository.findAll();
	}

	 public Page<ShopEntity> getAllShopBoards(Pageable pageable) {
	    return shopBoardRepository.findAll(pageable);
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
	 
	 public void updateShopBoard(Integer id, ShopEntity updatedShop) {
	        Optional<ShopEntity> shopOpt = shopBoardRepository.findById(id);

	        if (shopOpt.isPresent()) {
	            ShopEntity existingShop = shopOpt.get();
	            
	            // Update the fields that you want
	            existingShop.setShopboardtitle(updatedShop.getShopboardtitle());
	            existingShop.setShopboardcontent(updatedShop.getShopboardcontent());
	            // ... (you can continue updating other fields if needed)

	            shopBoardRepository.save(existingShop);
	        } else {
	            throw new RuntimeException("Shop not found!");
	        }
	    }
	    
	    public void deleteShopBoard(Integer id) {
	    	shopBoardRepository.deleteById(id);
	    }
	    
	    public boolean hasUserLiked(Integer userId, Integer shopId) {
	        return shopLikeRepository.existsByUserIdAndShop_shopboardid(userId, shopId);
	    }

	    public void likeShop(Integer userId, Integer shopId) {
	        if (!hasUserLiked(userId, shopId)) {
	            ShopLike like = new ShopLike();

	            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")); 
	            ShopEntity shop = shopBoardRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found!")); 

	            like.setUser(user);
	            like.setShop(shop);

	            shopLikeRepository.save(like);
	            
	            shop.like();  // 게시글의 좋아요 수 증가
	            shopBoardRepository.save(shop);
	        }
	    }

	    
	    
	    
	    
	
}
