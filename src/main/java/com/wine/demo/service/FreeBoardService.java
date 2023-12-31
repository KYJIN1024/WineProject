package com.wine.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wine.demo.entity.CommentEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.repository.CommentRepository;
import com.wine.demo.repository.FreeBoardRepository;


@Service
public class FreeBoardService {
	
	@Autowired
	private FreeBoardRepository frbreBoardRepository;
	
	@Autowired
    private CommentRepository commentRepository;
	

	// 글 작성하기
	public void FreeBoardWrite( FreeBoardEntity entity ) {
		frbreBoardRepository.save(entity);
	}
	

	//  글 리스트 불러오기
	public Page< FreeBoardEntity > freeBoardList( Pageable pageable ){	
		return frbreBoardRepository.findAll( pageable );
	}
	

	// 글 상세보기 불러오기
	public FreeBoardEntity freeBoardView( Integer id ) {
		return frbreBoardRepository.findById( id ).get() ;
	}
	
	// 글 삭제하기
	public void freeBoardDelete( Integer id ) {
		// 먼저 해당 게시글과 연관된 모든 댓글 삭제
        commentRepository.deleteByFreeBoardId(id);
        // 그 후 게시글 삭제
        frbreBoardRepository.deleteById(id);
	}
	
		// 댓글저장
		 public CommentEntity saveComment(CommentEntity comment) {
		        return commentRepository.save(comment);
		    }
	 	// 댓글 찾기
	    public Optional<CommentEntity> findCommentById(Long id) {
	        return commentRepository.findById(id);
	    }
	    // 댓글 업데이트
	    public void updateComment(CommentEntity comment) {
	        commentRepository.save(comment);
	    }
	    // 댓글 삭제
	    public void deleteComment(Long id) {
	        commentRepository.deleteById(id);
	    }
	    // 특정 게시글의 댓글 목록 가져오기
	    public List<CommentEntity> getCommentsForFreeboard(Integer frboardid) {
	        return commentRepository.findByFreeBoard_Frboardid(frboardid);
	    }
	    // 게시글의 댓글 수 가져오기
	    public int getCommentCountForBoard(Integer frboardid) {
	        return commentRepository.countByFreeBoard_Frboardid( frboardid);
	    }
	    // 조회수가 높은 상위 5개 게시글 가져오기
	    public List<FreeBoardEntity> getTopFreeBoardsByHits() {
	        return frbreBoardRepository.findTop5ByOrderByFrboardhitsDesc();
	    }
}
