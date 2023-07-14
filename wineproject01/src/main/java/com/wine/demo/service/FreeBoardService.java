package com.wine.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wine.demo.entity.FreeBoardEntity;
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
}
