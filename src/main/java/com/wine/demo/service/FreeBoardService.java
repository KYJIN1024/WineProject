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
	

	// freeboard(자유게시판) 글 작성하기
	public void FreeBoardWrite( FreeBoardEntity entity ) {
		
		frbreBoardRepository.save(entity);
	}
	

	// freeboard(자유게시판) 글 리스트 불러오기
	public Page< FreeBoardEntity > freeBoardList( Pageable pageable ){
				
		return frbreBoardRepository.findAll( pageable );
	}
	

	// freeboard(자유게시판) 특정 게시글 불러오기
	public FreeBoardEntity freeBoardView( Integer id ) {
		
		return frbreBoardRepository.findById( id ).get() ;
	}
	

	// freeboard(자유게시판) 특정 게시글 삭제하기
	
	public void freeBoardDelete( Integer id ) {
		
		frbreBoardRepository.deleteById(id);
	}
	
	@Autowired
    private CommentRepository commentRepository;

	 public CommentEntity saveComment(CommentEntity comment) {
	        return commentRepository.save(comment);
	    }

	    public Optional<CommentEntity> findCommentById(Long id) {
	        return commentRepository.findById(id);
	    }

	    public void updateComment(CommentEntity comment) {
	        commentRepository.save(comment);
	    }

	    public void deleteComment(Long id) {
	        commentRepository.deleteById(id);
	    }
	    
	    public List<CommentEntity> getCommentsForFreeboard(Integer frboardid) {
	        return commentRepository.findByFreeBoard_Frboardid(frboardid);
	    }
	    
	    public int getCommentCountForBoard(Integer frboardid) {
	        return commentRepository.countByFreeBoard_Frboardid( frboardid);
	    }
	
	    public List<FreeBoardEntity> getTopFreeBoardsByHits() {
	        return frbreBoardRepository.findTop5ByOrderByFrboardhitsDesc();
	    }
}
