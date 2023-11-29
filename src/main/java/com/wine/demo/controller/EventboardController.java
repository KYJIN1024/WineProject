package com.wine.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.EventBoardEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.service.EventBoardService;

@Controller
@RequestMapping("/community/eventboard")
public class EventboardController {
	
	@Autowired
	private EventBoardService evbService;
	
	 // 행사 게시판 목록
	@GetMapping("/list")
	public String commeventBoardList(Model model, @PageableDefault(page = 0, size = 8, sort = "evboardid", direction = Sort.Direction.DESC) Pageable pageable) {
	    Page<EventBoardEntity> list = evbService.eventBoardList(pageable);
	    int nowpage = list.getPageable().getPageNumber() + 1;
	    int startpage = Math.max(nowpage - 3, 1);
	    int endpage = Math.min(nowpage + 3, list.getTotalPages());
	
	    model.addAttribute("nowpage", nowpage);
	    model.addAttribute("startpage", startpage);
	    model.addAttribute("endpage", endpage);
	    model.addAttribute("list", list);
	    
	    return "community/commeventboardlist";
	}
	
	 // 행사 게시글 작성
	@GetMapping("/write")
	public String commeventBoardWriteForm(Model model) {
	    model.addAttribute("user", "코와사 편집팀");
	    return "community/commeventboardwrite";
	}
	
	// 행사 게시글을 저장
	@PostMapping("/writedo")
	public String commeventBoardWritedo(EventBoardEntity evbEntity) throws Exception {
	 evbService.EventBoardWrite(evbEntity);
	  return "redirect:list";
	}
	
	 // 게시글 조회
	@GetMapping("/view")
	public String eventboardView(Model model, @RequestParam(required = false) Integer id) {
	    // 1. id 파라미터가 없는 경우의 처리
	    if (id == null) {
	        model.addAttribute("error", "게시글 ID가 제공되지 않았습니다.");
	        return "error";  // 에러 페이지로 리다이렉트 
	    }

	    // 2. 게시글 조회
	    EventBoardEntity evbTemp = evbService.eventBoardView(id);

	    // 3. 게시글이 존재하지 않는 경우의 처리
	    if (evbTemp == null) {
	        model.addAttribute("error", "해당 ID의 게시글이 존재하지 않습니다.");
	        return "error";  
	    }

	    model.addAttribute("eventboard", evbTemp);
	    
	    // 4. 조회수 높은 게시글 목록 조회
	    List<EventBoardEntity> topBoards = evbService.getTopEventBoardsByHits();
	    //System.out.println("Top Boards Size: " + topBoards.size());
	    model.addAttribute("topBoards", topBoards);

	    return "community/commeventboardview"; 
	}
	    
	// 게시글 삭제
	@PostMapping("/delete")
	public String eventboardDelete(@RequestParam Integer id) {
	    evbService.EventBoardDelete(id);
	    return "redirect:list";
	}
	
	 // 게시글 수정
	@GetMapping("/modify/{id}")
	public String eventboardModify(@PathVariable("id") Integer id, Model model) {
	    model.addAttribute("eventboard", evbService.eventBoardView(id));
	    return "community/commeventboardmodify";
	}
	
	 // 게시글 수정 처리 
	@PostMapping("/update/{id}")
	public String eventboardUpdate(@PathVariable("id") Integer id, EventBoardEntity evbEntity, MultipartFile file) throws Exception {
	    EventBoardEntity boardTemp = evbService.eventBoardView(id);
	    boardTemp.setEvboardevname(evbEntity.getEvboardevname());
	    evbService.EventBoardUpdate(boardTemp);
	    return "redirect:/community/eventboard/view?id=" + id;
	}
	
	// 이미지 업로드 처리 
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
	    // 이미지 저장 로직
	    String imageUrl = evbService.saveImage(file); 

	    return ResponseEntity.ok(Map.of(
	        "uploaded", 1,  
	        "fileName", file.getOriginalFilename(),
	        "url", imageUrl
	    ));
	}
	
	
	// 이미지 저장 
	public String saveImage(MultipartFile file) throws IOException {
	    String directoryPath = "/static/uploaded_images";  
	    String absolutePath = "/var/uploaded_files";

	    File dir = new File(absolutePath);

	    // 디렉토리 확인 및 생성
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    // 파일 확장자 확인
	    String originalFileName = file.getOriginalFilename();
	    String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

	    // 파일 저장
	    String savedFileName = UUID.randomUUID() + "." + extension;
	    File targetFile = new File(dir, savedFileName);

	    file.transferTo(targetFile);

	    return directoryPath + "/" + savedFileName;  
	}
	
}

