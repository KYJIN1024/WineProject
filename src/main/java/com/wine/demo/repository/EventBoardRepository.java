package com.wine.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.entity.FreeBoardEntity;

@Repository
public interface EventBoardRepository extends JpaRepository<EventBoardEntity, Integer>{

	List<EventBoardEntity> findTop5ByOrderByEvboardhitsDesc();
}
