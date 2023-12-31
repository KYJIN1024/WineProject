package com.wine.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.model.Search;
import com.wine.demo.model.User;
import com.wine.demo.repository.ProducerBoardRepository;
import com.wine.demo.service.ProducerBoardService;
import com.wine.demo.service.SearchService;
import com.wine.demo.service.UserService;

@Controller
@RequestMapping("/partners/producer")
public class ProdboardController {
	
	@Autowired
    private ProducerBoardRepository producerBoardRepository;
	
	@Autowired
	private ProducerBoardService pdbService;
	
	@Autowired
	private ProducerBoardService producerBoardService;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private UserService userService;
	
	// 게시판 목록 페이지 표시, Pagination을 사용하여 게시글을 페이지별로 나눔
	@GetMapping("/list")
	public String partnersProdBoardList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
	    PageRequest pageable = PageRequest.of(page, 8); // 10은 한 페이지에 보여줄 항목의 수입니다. 원하는대로 조정할 수 있습니다.
	    Page<ProducerEntity> producerPage = producerBoardService.getAllProducerBoards(pageable);
	    
	    model.addAttribute("producers", producerPage.getContent());
	    model.addAttribute("page", producerPage);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);

	    return "winepartners/partnersprodboardlist";
	}
	
	// 게시글 조회
	@GetMapping("/view/{id}")
	public String partnersProdBoardView(@PathVariable("id") Integer id, Model model) {
	    Optional<ProducerEntity> producerOpt = producerBoardRepository.findById(id);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);
	    
	    if (producerOpt.isPresent()) {
	        model.addAttribute("producerboard", producerOpt.get());  
	        return "winepartners/partnersprodboardview";
	    } else {
	        return "redirect:/partners/producer/list";
	    }
	}
	
	
	// 게시글 작성 
	@GetMapping("/write")
	public String showWriteForm() {
	    return "winepartners/partnersprodboardwrite";
	}

	// 게시글 데이터를 처리하고 데이터베이스에 저장하는 메서드
	@PostMapping("/writedo")
	public String saveProducerBoard(ProducerEntity producerEntity) {
	    producerBoardRepository.save(producerEntity);
	    return "redirect:/partners/producer/list"; 
	}
	
	// 이미지 업로드
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
	    // 이미지 저장 로직
	    String imageUrl = pdbService.saveImage(file); 

	    return ResponseEntity.ok(Map.of(
	        "uploaded", 1,  
	        "fileName", file.getOriginalFilename(),
	        "url", imageUrl
	    ));
	}
	
	//좋아요버튼 메서드
	@PostMapping("/like/{id}")
	@ResponseBody
	public ResponseEntity<?> increaseLike(@PathVariable("id") Integer id) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        //System.out.println("User is not logged in.");
	        return ResponseEntity.badRequest().body("{\"error\": \"로그인이 필요합니다.\"}");
	    }
	    
	    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
	    String username = principal.getUsername();  
	   // System.out.println("Logged in user: " + username);
	    
	    // DB에서 사용자의 전체 정보 가져오기
	    User user = userService.findByUsername(username); 
	    
	    if (user == null) {
	        return ResponseEntity.badRequest().body("{\"error\": \"로그인된 사용자 정보를 찾을 수 없습니다.\"}");
	    }
	    
	    if (!producerBoardRepository.existsById(id)) {
	        return ResponseEntity.notFound().build();
	    }

	    if (producerBoardService.hasUserLiked(user.getId(), id)) {
	    	 Map<String, Object> response = new HashMap<>();
	         response.put("error", "이미 좋아요를 눌렀습니다.");
	         return ResponseEntity.badRequest().body(response);
	    }

	    producerBoardService.likeProducer(user.getId(), id);
	    ProducerEntity producer = producerBoardRepository.findById(id).orElseThrow();
	    
	    // 좋아요가 성공적으로 적용되었다는 응답 반환
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "좋아요가 성공적으로 적용되었습니다.");
	    response.put("likes", producer.getPdboardlikes()); 
	    return ResponseEntity.ok(response);
	}
	
	// 게시글 수정
	@GetMapping("/modify/{id}")
	public String showModifyForm(@PathVariable("id") Integer id, Model model) {
	    Optional<ProducerEntity> producerOpt = producerBoardRepository.findById(id);

	    if (producerOpt.isPresent()) {
	        model.addAttribute("producerboard", producerOpt.get());
	        return "winepartners/partnersprodboardmodify";
	    } else {
	        return "redirect:/partners/producer/list";
	    }
	}

	// 게시글 수정 처리
	@PostMapping("/update/{id}")
	public String updateProducerBoard(@PathVariable("id") Integer id, ProducerEntity producerEntity) {
	    producerBoardService.updateProducerBoard(id, producerEntity);
	    return "redirect:/partners/producer/view/" + id;
	}
	
	// 게시글 삭제
	@GetMapping("/delete")
	public String deleteproducerboard(@RequestParam Integer id) {
	    pdbService.deleteProducerBoard(id);
	    return "redirect:/partners/producer/list";
	}
	
	
}
	
	
	

