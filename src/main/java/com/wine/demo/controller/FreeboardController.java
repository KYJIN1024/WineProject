package com.wine.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wine.demo.entity.CommentEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.service.FreeBoardService;

@Controller
@RequestMapping("/community/freeboard")
public class FreeboardController {
	 @Autowired
	    private FreeBoardService frbService;

	 	// 게시판 목록 페이지 표시, Pagination을 사용하여 게시글을 페이지별로 나눔
		@GetMapping("/list")
	    public String commFreeBoardList( Model model , @PageableDefault( page = 0, size = 10, sort = "frboardid", 
	    								direction=Sort.Direction.DESC ) Pageable pageable) {
			Page<FreeBoardEntity> list = frbService.freeBoardList(pageable);
			int nowpage = list.getPageable().getPageNumber() + 1;
			int startpage = Math.max( nowpage - 3 , 1);
			int endpage = Math.min( nowpage+3 , list.getTotalPages());

			model.addAttribute("nowpage", nowpage);
			model.addAttribute("startpage", startpage);
			model.addAttribute("endpage", endpage);
			model.addAttribute("list", frbService.freeBoardList( pageable ));
			
			Map<Integer, Integer> commentCounts = new HashMap<>();
		    for (FreeBoardEntity board : list) {
		        commentCounts.put(board.getFrboardid(), frbService.getCommentCountForBoard(board.getFrboardid()));
		    }
		    model.addAttribute("commentCounts", commentCounts);
		    
		    List<FreeBoardEntity> topBoards = frbService.getTopFreeBoardsByHits();
	        model.addAttribute("topBoards", topBoards);
			
			return "community/commfreeboardlist";
	    }
		
		// 게시글 작성 페이지을 표시
		@GetMapping("/write")
		   public String commFreeBoardWriteForm(Model model, Principal principal) {
			 
			   // 현재 로그인한 사용자의 이름을 가져옴
			   String username = principal.getName();
	
			   // 모델에 사용자 이름 추가
			   model.addAttribute("user", username);
			
			
			   List<FreeBoardEntity> topBoards = frbService.getTopFreeBoardsByHits();
		       model.addAttribute("topBoards", topBoards);
			   
			   return "community/commfreeboardwrite";
		   }
		
		// 작성된 게시글을 처리하고 데이터베이스에 저장
		@PostMapping("/writedo")
		   public String commFreeBoardWritedo( FreeBoardEntity frbEntity,  Model model) {
			LocalDateTime LDTime = LocalDateTime.now();
			frbEntity.setFrboardcreatetime(LDTime);
			frbEntity.setFrboardhits(0);
			
			frbService.FreeBoardWrite( frbEntity );
			return "redirect:list";
		   }
		
		// 특정 게시글을 보여주는 페이지(댓글 및 조회수 처리를 포함)
		@GetMapping( "/view" ) 
			public String boardView( Model model , Integer id, FreeBoardEntity frbEntity) {
				FreeBoardEntity frbTemp = frbService.freeBoardView(id);
				if(frbTemp.getFrboardhits() == null) {
					int hits = 0 ;
					frbTemp.setFrboardhits( hits );
				}else {
					int hits = frbTemp.getFrboardhits() + 1 ;
					frbTemp.setFrboardhits( hits );
				}
				
				frbService.FreeBoardWrite( frbTemp );
				model.addAttribute("comment", new CommentEntity());
				model.addAttribute( "freeboard", frbService.freeBoardView(id));
				
				List<CommentEntity> comments = frbService.getCommentsForFreeboard(id);
			    model.addAttribute("comments", comments);
			    
			    // 댓글의 날짜 및 시간 포맷
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    List<String> formattedDates = comments.stream()
			    									  .map(comment -> comment.getTimestamp().toLocalDate().format(formatter))
			                                          .collect(Collectors.toList());
			    model.addAttribute("formattedDates", formattedDates);
			    
				
			    List<FreeBoardEntity> topBoards = frbService.getTopFreeBoardsByHits();
			   // System.out.println("Top Boards Size: " + topBoards.size());
		        model.addAttribute("topBoards", topBoards);
				
				return "community/commfreeboardview";
			}
	
		 // 게시글 삭제
		@PostMapping( "/delete" ) 
		public String boardDelete( @RequestParam("id") Integer id) {
				
				frbService.freeBoardDelete(id);
				return "redirect:list";
			}	
	
		// 게시글 수정 
		@GetMapping( "/modify/{id}" ) 
			public String boardModify( @PathVariable( "id" ) Integer id, Model model) {
				
				model.addAttribute("freeboard", frbService.freeBoardView(id));
				return "community/commfreeboardmodify";
			}
		
		// 수정된 게시글을 처리하고 데이터베이스에 업데이트
		@PostMapping( "/update/{id}" ) 
			public String boardUpdate( @PathVariable( "id" ) Integer id, @ModelAttribute FreeBoardEntity frbEntity) {
				
				FreeBoardEntity boardTemp = frbService.freeBoardView(id);
				boardTemp.setFrboardtitle(frbEntity.getFrboardtitle());
				boardTemp.setFrboardcontent(frbEntity.getFrboardcontent());
			
				LocalDateTime LDTime = LocalDateTime.now();
				boardTemp.setFrboardupdatetime(LDTime);
				
				boardTemp.setTags(frbEntity.getTags());
				frbService.FreeBoardWrite(boardTemp);
				return "redirect:/community/freeboard/view?id=" + id;
				
	
			}
		
		// 댓글 추가 (로그인 필요)
		@PostMapping("/{frboardid}/addComment")
		public String addComment(@PathVariable Integer frboardid, CommentEntity comment, Principal principal) {
		    FreeBoardEntity board = frbService.freeBoardView(frboardid);
		    if (board == null) {
		     
		        return "redirect:/community/freeboard/list";
		    }
		    comment.setFreeBoard(board);
		    comment.setWriter(principal.getName()); 
		    comment.setTimestamp(LocalDateTime.now()); 
		    frbService.saveComment(comment);
		    return "redirect:/community/freeboard/view?id=" + frboardid;
		}
		
		// 댓글 업데이트
		@PostMapping("/comment/{commentId}/update")
		public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody Map<String, String> payload) {
		    CommentEntity existingComment = frbService.findCommentById(commentId).orElse(null);
		    if (existingComment == null) {
		        return ResponseEntity.notFound().build(); // 댓글이 없는 경우 404 에러 반환
		    }
		    existingComment.setContent(payload.get("content"));
		    frbService.updateComment(existingComment);
		    return ResponseEntity.ok().build(); // 성공적으로 업데이트한 경우 OK 상태 코드 반환
		}
		
		// 댓글 삭제
		@PostMapping("/comment/{commentId}/delete")
		public String deleteComment(@PathVariable Long commentId) {
		    CommentEntity comment = frbService.findCommentById(commentId).orElse(null);
		    if (comment == null) {
		        return "redirect:community/freeboard/list";
		    }
		    Integer boardId = comment.getFreeBoard().getFrboardid();
		    frbService.deleteComment(commentId);
		    return "redirect:/community/freeboard/view?id=" + boardId;
			}
	
	}