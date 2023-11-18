package com.wine.demo.repository;

import com.wine.demo.entity.CommentEntity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findByFreeBoard_Frboardid(Integer frboardid);
	int countByFreeBoard_Frboardid(Integer frboardid);
	
	 @Transactional
	    @Modifying
	    @Query("DELETE FROM CommentEntity c WHERE c.freeBoard.frboardid = :frboardid")
	    void deleteByFreeBoardId(Integer frboardid);
}
	


