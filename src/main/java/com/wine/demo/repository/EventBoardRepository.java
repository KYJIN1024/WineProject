package com.wine.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.entity.FreeBoardEntity;

@Repository
public interface EventBoardRepository extends JpaRepository<EventBoardEntity, Integer>{
	// 조회수가 높은 상위 5개의 이벤트 게시글을 찾는 메서드
	List<EventBoardEntity> findTop5ByOrderByEvboardhitsDesc();
}
