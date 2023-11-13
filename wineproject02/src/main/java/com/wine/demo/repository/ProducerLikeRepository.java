package com.wine.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.entity.ProducerLike;

@Repository
public interface ProducerLikeRepository extends JpaRepository<ProducerLike, Long> {
    
    // 사용자가 특정 게시글에 좋아요를 눌렀는지 확인하는 메서드
    Optional<ProducerLike> findByProducer_PdboardidAndUser_Id(Integer producerPdboardid, Integer userId);

    // 특정 게시글의 전체 좋아요 수를 가져오는 메서드
    int countByProducer_Pdboardid(Integer producerPdboardid);
    
    boolean existsByUserIdAndProducer_Pdboardid(Integer userId, Integer producerPdboardid);
    
    @Query("SELECT pl.producer FROM ProducerLike pl WHERE pl.user.id = :userId")
    List<ProducerEntity> findProducersByUserId(@Param("userId") Integer userId);
    
}