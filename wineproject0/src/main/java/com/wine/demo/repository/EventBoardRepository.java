package com.wine.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.EventBoardEntity;

@Repository
public interface EventBoardRepository extends JpaRepository<EventBoardEntity, Integer>{

}
