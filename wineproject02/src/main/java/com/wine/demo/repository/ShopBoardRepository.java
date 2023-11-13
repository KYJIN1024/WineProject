package com.wine.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.entity.ShopEntity;

@Repository
public interface ShopBoardRepository extends JpaRepository<ShopEntity, Integer>, PagingAndSortingRepository<ShopEntity, Integer> {
}
