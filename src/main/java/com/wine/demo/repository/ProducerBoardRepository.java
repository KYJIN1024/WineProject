package com.wine.demo.repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.ProducerEntity;

@Repository
public interface ProducerBoardRepository extends JpaRepository<ProducerEntity, Integer>, PagingAndSortingRepository<ProducerEntity, Integer> {
}
