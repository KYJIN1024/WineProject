package com.wine.demo.repository;

import com.wine.demo.entity.CommentEntity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	
	// 특정 게시글 ID에 해당하는 모든 댓글을 조회하는 메서드
	List<CommentEntity> findByFreeBoard_Frboardid(Integer frboardid);
	
	// 특정 게시글 ID에 해당하는 댓글 수를 카운트하는 메서드
	int countByFreeBoard_Frboardid(Integer frboardid);
	
	// 특정 게시글과 연관된 모든 댓글을 삭제하는 메서드
	@Transactional
	@Modifying
	@Query("DELETE FROM CommentEntity c WHERE c.freeBoard.frboardid = :frboardid")
	void deleteByFreeBoardId(Integer frboardid);
}
	


