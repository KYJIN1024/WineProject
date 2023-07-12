package com.wine.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.FreeBoardEntity;


@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Integer>{

}
