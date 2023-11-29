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
    
	  /**
     * 사용자 ID와 생산자 게시글 ID를 기반으로 특정 생산자 게시글에 대한 사용자의 '좋아요' 존재 여부를 확인합니다.
     * @param producerPdboardid 생산자 게시글 ID
     * @param userId 사용자 ID
     * @return 사용자가 해당 생산자 게시글에 '좋아요'를 했다면 해당 엔터티를 포함하는 Optional, 아니면 빈 Optional
     */
    Optional<ProducerLike> findByProducer_PdboardidAndUser_Id(Integer producerPdboardid, Integer userId);

   
    /**
     * 특정 생산자 게시글에 대한 총 '좋아요' 수를 반환합니다.
     * @param producerPdboardid 생산자 게시글 ID
     * @return 해당 생산자 게시글의 '좋아요' 수
     */
    int countByProducer_Pdboardid(Integer producerPdboardid);
    
    /**
     * 특정 사용자가 특정 생산자 게시글에 '좋아요'를 했는지 여부를 확인합니다.
     * @param userId 사용자 ID
     * @param producerPdboardid 생산자 게시글 ID
     * @return '좋아요' 여부
     */
    boolean existsByUserIdAndProducer_Pdboardid(Integer userId, Integer producerPdboardid);
    
    /**
     * 특정 사용자가 '좋아요'를 누른 모든 생산자 게시글을 조회합니다.
     * @param userId 사용자 ID
     * @return '좋아요'를 누른 생산자 게시글 목록
     */
    @Query("SELECT pl.producer FROM ProducerLike pl WHERE pl.user.id = :userId")
    List<ProducerEntity> findProducersByUserId(@Param("userId") Integer userId);
    
}