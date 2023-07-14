package com.wine.demo.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.repository.EventBoardRepository;

@Service
public class EventBoardService {
	
	
	@Autowired
	private EventBoardRepository evboardRepository;
	
	

	// eventboard 글 작성하기
	public void EventBoardWrite( EventBoardEntity entity, MultipartFile file) throws Exception{
		
		String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
		
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = uuid + file.getOriginalFilename();
		
		File saveFile = new File(projectPath, fileName );
		
		file.transferTo( saveFile );
		entity.setFilename( fileName );
		entity.setFilepath( "/files/" + fileName);

		evboardRepository.save( entity );
	}
	

	// eventboard 글 리스트 불러오기
	public Page< EventBoardEntity > eventBoardList( Pageable pageable ){
				
		return evboardRepository.findAll( pageable );
	}
	

	//  eventboard 특정 게시글 불러오기
	public EventBoardEntity eventBoardView( Integer id ) {
		
		return evboardRepository.findById( id ).get() ;
	}
	

	//  eventboard 특정 게시글 삭제하기
	
	public void EventBoardDelete( Integer id ) {
		
		evboardRepository.deleteById(id);
	}
	
	// eventboard 글 수정하기
	
	public void EventBoardUpdate(EventBoardEntity entity) {
		
		evboardRepository.save( entity );
	}


}
