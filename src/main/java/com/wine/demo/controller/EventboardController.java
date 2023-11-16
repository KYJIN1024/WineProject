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
	
	@GetMapping("/write")
	public String commeventBoardWriteForm(Model model) {
	    model.addAttribute("user", "코와사 편집팀");
	    return "community/commeventboardwrite";
	}
	
	@PostMapping("/writedo")
	public String commeventBoardWritedo(EventBoardEntity evbEntity) throws Exception {
	 evbService.EventBoardWrite(evbEntity);
	  return "redirect:community/eventboard/list";
	}
	
	@GetMapping("/view")
	public String eventboardView(Model model, @RequestParam(required = false) Integer id) {
	    // 1. id 파라미터가 없는 경우의 처리
	    if (id == null) {
	        model.addAttribute("error", "게시글 ID가 제공되지 않았습니다.");
	        return "error";  // 에러 페이지로 리다이렉트 (실제 경로는 프로젝트에 따라 다를 수 있습니다.)
	    }
	    
	    EventBoardEntity evbTemp = evbService.eventBoardView(id);

	    // 2. 게시글이 존재하지 않는 경우의 처리
	    if (evbTemp == null) {
	        model.addAttribute("error", "해당 ID의 게시글이 존재하지 않습니다.");
	        return "error";  // 에러 페이지로 리다이렉트 (실제 경로는 프로젝트에 따라 다를 수 있습니다.)
	    }

	    model.addAttribute("eventboard", evbTemp);
	    
	    List<EventBoardEntity> topBoards = evbService.getTopEventBoardsByHits();
	    System.out.println("Top Boards Size: " + topBoards.size());
        model.addAttribute("topBoards", topBoards);

	    return "community/commeventboardview";
	}
	
	@GetMapping("/delete")
	public String eventboardDelete(@RequestParam Integer id) {
	    evbService.EventBoardDelete(id);
	    return "redirect:community/eventboard/list";
	}
	
	@GetMapping("/modify/{id}")
	public String eventboardModify(@PathVariable("id") Integer id, Model model) {
	    model.addAttribute("eventboard", evbService.eventBoardView(id));
	    return "community/commeventboardmodify";
	}
	
	@PostMapping("/update/{id}")
	public String eventboardUpdate(@PathVariable("id") Integer id, EventBoardEntity evbEntity, MultipartFile file) throws Exception {
	    EventBoardEntity boardTemp = evbService.eventBoardView(id);
	    boardTemp.setEvboardevname(evbEntity.getEvboardevname());
	    evbService.EventBoardUpdate(boardTemp);
	    return "redirect:community/eventboard/list";
	}
	
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
	    // 이미지 저장 로직
	    String imageUrl = evbService.saveImage(file); 

	    return ResponseEntity.ok(Map.of(
	        "uploaded", 1,  // CKEditor에서 요구하는 응답 형식을 준수합니다.
	        "fileName", file.getOriginalFilename(),
	        "url", imageUrl
	    ));
	}

	public String saveImage(MultipartFile file) throws IOException {
	    String directoryPath = "/static/uploaded_images";  // 웹에서 접근 가능한 경로로 수정
	    String absolutePath = "D:\\uploaded_files";  // 실제 서버에서 파일이 저장될 절대 경로

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

	    return directoryPath + "/" + savedFileName;  // 웹에서 접근 가능한 URL 형식으로 반환
	}
	
}

