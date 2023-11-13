package com.wine.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.JobLike;

@Repository
public interface JobLikeRepository extends JpaRepository<JobLike, Long> {
    
    // 사용자가 특정 게시글에 좋아요를 눌렀는지 확인하는 메서드
    Optional<JobLike> findByJob_JobboardidAndUser_Id(Integer jobJobboardid, Integer userId);

    // 특정 게시글의 전체 좋아요 수를 가져오는 메서드
    int countByJob_Jobboardid(Integer jobJobboardid);
    
    boolean existsByUserIdAndJob_jobboardid(Integer userId, Integer jobJobboardid);
}