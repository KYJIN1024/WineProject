package com.wine.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.FreeBoardEntity;


@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Integer>{
		
	 // 조회수가 높은 상위 5개의 게시글을 찾는 메서드
	 List<FreeBoardEntity> findTop5ByOrderByFrboardhitsDesc();

}

