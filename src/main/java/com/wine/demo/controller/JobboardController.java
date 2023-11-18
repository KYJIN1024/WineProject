package com.wine.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.JobEntity;
import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.model.Search;
import com.wine.demo.repository.JobBoardRepository;
import com.wine.demo.service.ProducerBoardService;
import com.wine.demo.service.SearchService;
import com.wine.demo.service.JobBoardService;

@Controller
@RequestMapping("/partners/job")

public class JobboardController {
	
	@Autowired
    private JobBoardRepository jobBoardRepository;
	
	@Autowired
	private JobBoardService jobBoardService;
	
	@Autowired
	private SearchService searchService;
	
	
	@GetMapping("/list")
	public String partnersJobBoardList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
	    PageRequest pageable = PageRequest.of(page, 8); // 10은 한 페이지에 보여줄 항목의 수입니다. 원하는대로 조정할 수 있습니다.
	    Page<JobEntity> jobPage = jobBoardService.getAllJobBoards(pageable);
	    
	    model.addAttribute("jobs", jobPage.getContent());
	    model.addAttribute("page", jobPage);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);


	    return "winepartners/partnersjobboardlist";
	}
	
	// 게시글 작성 페이지를 보여주는 메서드
	@GetMapping("/write")
	public String showWriteForm() {
	    return "winepartners/partnersjobboardwrite";
	}

	// 게시글 데이터를 처리하고 데이터베이스에 저장하는 메서드
	@PostMapping("/writedo")
	public String saveJobBoard(JobEntity jobEntity) {
		jobBoardRepository.save(jobEntity);
	    return "redirect:/partners/job/list"; // 게시글 저장 후 목록 페이지로 리디렉션
	}
	
	
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
	    // 이미지 저장 로직
	    String imageUrl = jobBoardService.saveImage(file); 

	    return ResponseEntity.ok(Map.of(
	        "uploaded", 1,  // CKEditor에서 요구하는 응답 형식을 준수합니다.
	        "fileName", file.getOriginalFilename(),
	        "url", imageUrl
	    ));
	}
	
	@GetMapping("/view/{id}")
	public String partnersJobBoardView(@PathVariable("id") Integer id, Model model) {
	    Optional<JobEntity> jobOpt = jobBoardRepository.findById(id);
	    
	    List<Search> searches = searchService.findDistinctByManufacturerAndRegion();
	    model.addAttribute("searches", searches);
	    
	    if (jobOpt.isPresent()) {
	        model.addAttribute("jobboard", jobOpt.get());  
	        return "winepartners/partnersjobboardview";
	    } else {
	        // 게시글이 존재하지 않을 경우 처리 (예: 오류 페이지로 리디렉션)
	        return "redirect:/partners/job/list";
	    }
	}

	@GetMapping("/modify/{id}")
	public String showModifyForm(@PathVariable("id") Integer id, Model model) {
	    Optional<JobEntity> jobOpt = jobBoardRepository.findById(id);

	    if (jobOpt.isPresent()) {
	        model.addAttribute("jobboard", jobOpt.get());
	        return "winepartners/partnersjobboardmodify";
	    } else {
	        return "redirect:/partners/job/list";
	    }
	}

	@PostMapping("/update/{id}")
	public String updateJobBoard(@PathVariable("id") Integer id, JobEntity jobEntity) {
		jobBoardService.updateJobBoard(id, jobEntity);
	    return "redirect:/partners/job/view/" + id;
	}
	
	@GetMapping("/delete")
	public String deletejobboard(@RequestParam Integer id) {
		jobBoardService.deleteJobBoard(id);
	    return "redirect:/partners/job/list";
	}
	
	
	
	
	
}
