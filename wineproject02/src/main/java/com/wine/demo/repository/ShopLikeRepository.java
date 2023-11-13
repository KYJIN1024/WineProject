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
    
    // 사용자가 특정 게시글에 좋아요를 눌렀는지 확인하는 메서드
    Optional<ShopLike> findByShop_ShopboardidAndUser_Id(Integer shopShopboardid, Integer userId);

    // 특정 게시글의 전체 좋아요 수를 가져오는 메서드
    int countByShop_Shopboardid(Integer shopShopboardid);
    
    boolean existsByUserIdAndShop_shopboardid(Integer userId, Integer shopShopboardid);
    
    @Query("SELECT pl.shop FROM ShopLike pl WHERE pl.user.id = :userId")
    List<ShopEntity> findShopsByUserId(@Param("userId") Integer userId);
    
}