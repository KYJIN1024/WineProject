package com.wine.demo.repository;

import com.wine.demo.entity.CommentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findByFreeBoard_Frboardid(Integer frboardid);
	int countByFreeBoard_Frboardid(Integer frboardid);
	
}

