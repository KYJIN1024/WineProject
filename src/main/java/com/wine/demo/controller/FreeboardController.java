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
	    public String commFreeBoardList( Model model , @PageableDefault( page = 0, size = 10, sort = "frboardid",  //첫페이지를0으로 설정하고 페이지당 10개의 게시글을 출력, frboardid필드를 기준으로 내림차순
	    								direction=Sort.Direction.DESC ) Pageable pageable) {
			Page<FreeBoardEntity> list = frbService.freeBoardList(pageable);
			int nowpage = list.getPageable().getPageNumber() + 1; //현재페이지번호
			int startpage = Math.max( nowpage - 3 , 1); //시작페이지번호
			int endpage = Math.min( nowpage+3 , list.getTotalPages()); //종료페이지번호

			model.addAttribute("nowpage", nowpage);
			model.addAttribute("startpage", startpage);
			model.addAttribute("endpage", endpage);
			model.addAttribute("list", frbService.freeBoardList( pageable ));
			
			
			//1. 현재 페이지 번호 (nowpage): 사용자가 현재 보고 있는 페이지 번호입니다. 이는 페이지네이션 컨트롤에서 '현재 페이지'를 강조하는 데 사용됩니다.
			//getPageNumber() 메소드는 현재 페이지의 인덱스를 반환합니다. 일반적으로 페이지 인덱스는 0부터 시작하므로, 사용자 친화적인 표시를 위해 1을 더해줍니다.
			//시작 페이지 번호 (startpage) 및 종료 페이지 번호 (endpage): 이들은 페이지네이션 바에서 표시할 페이지 범위를 결정합니다. 
			//예를 들어, 사용자가 5번 페이지를 보고 있다면, 시작 페이지는 2, 종료 페이지는 8로 설정될 수 있습니다. 
			//이렇게 하면 사용자는 현재 페이지 주변의 페이지로 쉽게 이동할 수 있습니다.
			//startpage는 현재 페이지에서 3을 뺀 값이나, 최소 1로 설정합니다. endpage는 현재 페이지에서 3을 더한 값이나, 최대 페이지 수로 설정합니다.
			//게시글 목록 (list): 현재 페이지에 표시될 게시글 목록입니다. 
			//freeBoardList(pageable) 메서드는 Pageable 객체에 따라 필요한 페이지의 게시글 목록을 반환합니다.
			
			return "community/commfreeboardlist";
	    }
		
		// 게시글 작성 페이지을 표시
		@GetMapping("/write")
		   public String commFreeBoardWriteForm(Model model, Principal principal) {
			 
			   // 현재 로그인한 사용자의 이름을 가져옴
			   String username = principal.getName();
	
			   // 모델에 사용자 이름 추가
			   model.addAttribute("user", username);
			   
			   return "community/commfreeboardwrite";
		   }
		
		// 작성된 게시글을 처리하고 데이터베이스에 저장
		@PostMapping("/writedo")
		   public String commFreeBoardWritedo( FreeBoardEntity frbEntity,  Model model) {
			LocalDateTime LDTime = LocalDateTime.now(); // LocalDateTime을 호출하여 현재시간을 가져옴 
			frbEntity.setFrboardcreatetime(LDTime); // 게시글 엔티티의 생성시간을 현재시간으로 설정함
			frbEntity.setFrboardhits(0); // 조회수를 0으로 설정함
			
			frbService.FreeBoardWrite( frbEntity ); //서비스의 해당 메서드에 게시글 엔티티를 전달하여 데이터베이스에 저장
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
		    CommentEntity existingComment = frbService.findCommentById(commentId).orElse(null); //해당id의 댓글내용을 찾습니다.
		    if (existingComment == null) {
		        return ResponseEntity.notFound().build(); // 댓글이 없는 경우 404 에러 반환
		    }
		    existingComment.setContent(payload.get("content")); // 찾은 댓글의 내용을 새로운 내용으로 업데이트 합니다.
		    frbService.updateComment(existingComment); // 업데이트된 댓글을 저장합니다.
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
		    frbService.deleteComment(commentId);   // 존재한다면 해당댓글을 삭제합니다.
		    return "redirect:/community/freeboard/view?id=" + boardId;
			}
	
	}