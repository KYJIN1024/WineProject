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
import com.wine.demo.entity.ShopEntity;
import com.wine.demo.model.Search;
import com.wine.demo.model.User;
import com.wine.demo.repository.ShopBoardRepository;
import com.wine.demo.service.ProducerBoardService;
import com.wine.demo.service.SearchService;
import com.wine.demo.service.ShopBoardService;
import com.wine.demo.service.UserService;

@Controller
@RequestMapping("/partners/shop")

public class ShopboardController {
	
	@Autowired
    private ShopBoardRepository shopBoardRepository;
	
	@Autowired
	private ShopBoardService shopBoardService;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/list")
	public String partnersShopBoardList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
	    PageRequest pageable = PageRequest.of(page, 8); // 10은 한 페이지에 보여줄 항목의 수입니다. 원하는대로 조정할 수 있습니다.
	    Page<ShopEntity> shopPage = shopBoardService.getAllShopBoards(pageable);
	    
	    model.addAttribute("shops", shopPage.getContent());
	    model.addAttribute("page", shopPage);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);


	    return "winepartners/partnersshopboardlist";
	}
	
	// 게시글 작성 페이지를 보여주는 메서드
	@GetMapping("/write")
	public String showWriteForm() {
	    return "winepartners/partnersshopboardwrite";
	}

	// 게시글 데이터를 처리하고 데이터베이스에 저장하는 메서드
	@PostMapping("/writedo")
	public String saveShopBoard(ShopEntity shopEntity) {
	    shopBoardRepository.save(shopEntity);
	    return "redirect:partners/shop/list"; // 게시글 저장 후 목록 페이지로 리디렉션
	}
	
	
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
	    // 이미지 저장 로직
	    String imageUrl = shopBoardService.saveImage(file); 

	    return ResponseEntity.ok(Map.of(
	        "uploaded", 1,  // CKEditor에서 요구하는 응답 형식을 준수합니다.
	        "fileName", file.getOriginalFilename(),
	        "url", imageUrl
	    ));
	}
	
	@GetMapping("/view/{id}")
	public String partnersShopBoardView(@PathVariable("id") Integer id, Model model) {
	    Optional<ShopEntity> shopOpt = shopBoardRepository.findById(id);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);
	    
	    if (shopOpt.isPresent()) {
	        model.addAttribute("shopboard", shopOpt.get());  
	        return "winepartners/partnersshopboardview";
	    } else {
	        // 게시글이 존재하지 않을 경우 처리 (예: 오류 페이지로 리디렉션)
	        return "redirect:/partners/shop/list";
	    }
	}

	@GetMapping("/modify/{id}")
	public String showModifyForm(@PathVariable("id") Integer id, Model model) {
	    Optional<ShopEntity> shopOpt = shopBoardRepository.findById(id);

	    if (shopOpt.isPresent()) {
	        model.addAttribute("shopboard", shopOpt.get());
	        return "winepartners/partnersshopboardmodify";
	    } else {
	        return "redirect:/partners/shop/list";
	    }
	}

	@PostMapping("/update/{id}")
	public String updateShopBoard(@PathVariable("id") Integer id, ShopEntity shopEntity) {
	    shopBoardService.updateShopBoard(id, shopEntity);
	    return "redirect:/partners/shop/view/" + id;
	}
	
	@GetMapping("/delete")
	public String deleteshopboard(@RequestParam Integer id) {
		shopBoardService.deleteShopBoard(id);
	    return "redirect:/partners/shop/list";
	}
	
	@PostMapping("/like/{id}")
	@ResponseBody
	public ResponseEntity<?> increaseLike(@PathVariable("id") Integer id) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        System.out.println("User is not logged in.");
	        return ResponseEntity.badRequest().body("{\"error\": \"로그인이 필요합니다.\"}");
	    }
	    
	    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
	    String username = principal.getUsername();  // 스프링 시큐리티에서 제공하는 User 객체에서 사용자 이름을 가져옵니다.
	    System.out.println("Logged in user: " + username);
	    
	    // DB에서 사용자의 전체 정보 가져오기
	    User user = userService.findByUsername(username);  // 이 부분은 실제 구현에 따라 변경될 수 있습니다.
	    
	    if (user == null) {
	        return ResponseEntity.badRequest().body("{\"error\": \"로그인된 사용자 정보를 찾을 수 없습니다.\"}");
	    }
	    
	    if (!shopBoardRepository.existsById(id)) {
	        return ResponseEntity.notFound().build();
	    }

	    if (shopBoardService.hasUserLiked(user.getId(), id)) {
	    	 Map<String, Object> response = new HashMap<>();
	         response.put("error", "이미 좋아요를 눌렀습니다.");
	         return ResponseEntity.badRequest().body(response);
	    }

	    shopBoardService.likeShop(user.getId(), id);
	    ShopEntity shop = shopBoardRepository.findById(id).orElseThrow();
	    
	    // 좋아요가 성공적으로 적용되었다는 응답을 추가하거나 다른 적절한 응답을 반환해야 합니다.
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "좋아요가 성공적으로 적용되었습니다.");
	    response.put("likes", shop.getShopboardlikes());  // 현재 좋아요 수 추가
	    return ResponseEntity.ok(response);
	}
	
	
	
}
