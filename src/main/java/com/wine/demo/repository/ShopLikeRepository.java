package com.wine.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.entity.ShopEntity;
import com.wine.demo.entity.ShopLike;

@Repository
public interface ShopLikeRepository extends JpaRepository<ShopLike, Long> {
    
	  /**
     * 사용자 ID와 샵 게시글 ID를 기반으로 특정 사용자가 특정 샵 게시글에 '좋아요'를 눌렀는지 확인합니다.
     * 
     * @param shopShopboardid 샵 게시글 ID
     * @param userId 사용자 ID
     * @return '좋아요' 정보를 담은 Optional 객체
     */
    Optional<ShopLike> findByShop_ShopboardidAndUser_Id(Integer shopShopboardid, Integer userId);

    /**
     * 특정 샵 게시글에 대한 전체 '좋아요' 수를 반환합니다.
     * 
     * @param shopShopboardid 샵 게시글 ID
     * @return '좋아요'의 총 개수
     */
    int countByShop_Shopboardid(Integer shopShopboardid);
    
    /**
     * 특정 사용자가 특정 샵 게시글에 '좋아요'를 눌렀는지 여부를 반환합니다.
     * 
     * @param userId 사용자 ID
     * @param shopShopboardid 샵 게시글 ID
     * @return '좋아요' 여부
     */
    boolean existsByUserIdAndShop_shopboardid(Integer userId, Integer shopShopboardid);
    
    /**
     * 특정 사용자가 '좋아요'를 누른 샵 게시글의 목록을 반환합니다.
     * 
     * @param userId 사용자 ID
     * @return '좋아요'를 누른 샵 게시글의 목록
     */
    @Query("SELECT pl.shop FROM ShopLike pl WHERE pl.user.id = :userId")
    List<ShopEntity> findShopsByUserId(@Param("userId") Integer userId);
    
}