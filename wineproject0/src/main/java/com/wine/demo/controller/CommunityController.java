package com.wine.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.service.EventBoardService;
import com.wine.demo.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
@RequestMapping("/community")

public class CommunityController {
	
	//private final FreeBoardService boardService ;
	@Autowired
	private FreeBoardService frbService;
	@Autowired
	private EventBoardService evbService;
	
	
	///freeboard Controller
	@GetMapping("/freeboard/list")
    public String commFreeBoardList( Model model , @PageableDefault( page = 0, size = 10, sort = "frboardid", direction=Sort.Direction.DESC ) Pageable pageable) {
		
	
		Page<FreeBoardEntity> list = frbService.freeBoardList(pageable);
		int nowpage = list.getPageable().getPageNumber() + 1;
		int startpage = Math.max( nowpage - 3 , 1);
		int endpage = Math.min( nowpage+3 , list.getTotalPages());

		model.addAttribute("nowpage", nowpage);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);
		model.addAttribute("list", frbService.freeBoardList( pageable ));
        
		return "/community/commfreeboardlist";
    }
   
@GetMapping("/freeboard/write")
   public String commFreeBoardWriteForm(Model model) {

	   model.addAttribute("user", "testuser");
	   
	   
	   return "/community/commfreeboardwrite";
   }

@PostMapping("/freeboard/writedo")
   public String commFreeBoardWritedo( FreeBoardEntity frbEntity,  Model model) {
	

	LocalDateTime LDTime = LocalDateTime.now();
	String now = LDTime.toString().substring(2, 16).replace("T", " ");
	frbEntity.setFrboardcreatetime(now);
	frbEntity.setFrboardhits(0);
	
	frbService.FreeBoardWrite( frbEntity );

	   return "redirect:/community/freeboard/list";
   }

@GetMapping( "/freeboard/view" ) 
	public String boardView( Model model , Integer id, FreeBoardEntity frbEntity) {
		FreeBoardEntity frbTemp = frbService.freeBoardView(id);
		if(frbTemp.getFrboardhits() == null) {
			int hits = 0 ;
			frbTemp.setFrboardhits( hits );
		}else {
			int hits = frbTemp.getFrboardhits() + 1 ;
			frbTemp.setFrboardhits( hits );
		}
		
		if(frbTemp.getFrboardupdatetime() == null) {
			frbTemp.setFrboardupdatetime("수정되지 않은 글");
		}
		
		
		frbService.FreeBoardWrite( frbTemp );
		model.addAttribute( "freeboard", frbService.freeBoardView(id));
		return "/community/commfreeboardview";
	}

@GetMapping( "/freeboard/delete" ) 
	public String boardDelete( Integer id) {
		
		frbService.freeBoardDelete(id);
		return "redirect:/community/freeboard/list";
	}	


@GetMapping( "/freeboard/modify/{id}" ) 
	public String boardModify( @PathVariable( "id" ) Integer id, Model model) {
		
		
		model.addAttribute("freeboard", frbService.freeBoardView(id));
		return "/community/commfreeboardmodify";
	}

@PostMapping( "/freeboard/update/{id}" ) 
	public String boardUpdate( @PathVariable( "id" ) Integer id, FreeBoardEntity frbEntity) {
		
		FreeBoardEntity boardTemp = frbService.freeBoardView(id);
		boardTemp.setFrboardtitle(frbEntity.getFrboardtitle());
		boardTemp.setFrboardcontent(frbEntity.getFrboardcontent());
	
		LocalDateTime LDTime = LocalDateTime.now();
		String now = LDTime.toString().substring(2, 16).replace("T", " ");
		boardTemp.setFrboardupdatetime(now);
		
	
		frbService.FreeBoardWrite( boardTemp );
		return "redirect:/community/freeboard/list";
	}





	/////eventboard Controller
@GetMapping("/eventboard/list")
	public String commeventBoardList( Model model , @PageableDefault( page = 0, size = 4, sort = "evboardid", direction=Sort.Direction.DESC ) Pageable pageable) {
		
	
		Page<EventBoardEntity> list = evbService.eventBoardList(pageable);
		int nowpage = list.getPageable().getPageNumber() + 1;
		int startpage = Math.max( nowpage - 3 , 1);
		int endpage = Math.min( nowpage+3 , list.getTotalPages());
	
		model.addAttribute("nowpage", nowpage);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);
		
		model.addAttribute("list", evbService.eventBoardList( pageable ));
	    
		return "/community/commeventboardlist";
	}

@GetMapping("/eventboard/write")
	public String commeventBoardWriteForm(Model model) {
	
	   model.addAttribute("user", "testuser");
	   
	   
	   return "/community/commeventboardwrite";
	}

@PostMapping("/eventboard/writedo")
	public String commeventBoardWritedo( EventBoardEntity evbEntity, MultipartFile file) throws Exception{
	
		
		LocalDateTime LDTime = LocalDateTime.now();
		String now = LDTime.toString().substring(2, 16).replace("T", " ");
		evbEntity.setEvboardcreatetime(now);
		evbEntity.setEvboardhits(0);
		
		evbService.EventBoardWrite( evbEntity, file );
	
	   return "redirect:/community/eventboard/list";
	}

@GetMapping( "/eventboard/view" ) 
	public String eventboardView( Model model , Integer id, EventBoardEntity evbEntity){
		EventBoardEntity evbTemp = evbService.eventBoardView(id);
		if(evbTemp.getEvboardhits() == null) {
			int hits = 0 ;
			evbTemp.setEvboardhits( hits );
		}else {
			int hits = evbTemp.getEvboardhits() + 1 ;
			evbTemp.setEvboardhits( hits );
		}
		
		if(evbTemp.getEvboardupdatetime() == null) {
			evbTemp.setEvboardupdatetime("수정되지 않은 글");
		}
		evbService.EventBoardUpdate( evbTemp );
			model.addAttribute( "eventboard", evbService.eventBoardView(id));
			
		return "/community/commeventboardview";
	}

@GetMapping( "/eventboard/delete" ) 
	public String eventboardDelete( Integer id) {
		
		evbService.EventBoardDelete(id);
		return "redirect:/community/eventboard/list";
	}	


@GetMapping( "/eventboard/modify/{id}" ) 
	public String eventboardModify( @PathVariable( "id" ) Integer id, Model model) {
		
		
		model.addAttribute("eventboard", evbService.eventBoardView(id));
		return "/community/commeventboardmodify";
	}

@PostMapping( "/eventboard/update/{id}" ) 
	public String eventboardUpdate( @PathVariable( "id" ) Integer id, EventBoardEntity evbEntity, MultipartFile file) throws Exception{
	
		EventBoardEntity boardTemp = evbService.eventBoardView(id);
		boardTemp.setEvboardevname(evbEntity.getEvboardevname());
		boardTemp.setEvboardevcontent(evbEntity.getEvboardevcontent());
		
		LocalDateTime LDTime = LocalDateTime.now();
		String now = LDTime.toString().substring(2, 16).replace("T", " ");
		boardTemp.setEvboardupdatetime(now);
		
		
		evbService.EventBoardWrite( boardTemp , file);
		return "redirect:/community/eventboard/list";
	}
	   
	   

	  
}
