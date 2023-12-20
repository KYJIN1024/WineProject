function updateComment(commentId) {
	 var commentContentDiv = $('#comment-content-' + commentId);
	 var editForm = commentContentDiv.find('.edit-form');
	 var currentContent = commentContentDiv.find('.comment-text').data('content');
			
	 // 기존 댓글 내용 숨김, 편집 폼 표시
	 commentContentDiv.find('.comment-text').hide();
	 editForm.removeClass('d-none');
			
	 // 편집 폼 내 텍스트에리어에 현재 내용 적용
	 editForm.find('.comment-edit-text').val(currentContent);
			
	 // 저장 버튼 이벤트 핸들러
	 editForm.find('.save-edit').off('click').on('click', function() {
	 	   var newContent = editForm.find('.comment-edit-text').val();
		           // AJAX 요청을 통한 댓글 업데이트
	               if (newContent && newContent !== currentContent) {
			                $.ajax({
			                    url: `/community/freeboard/comment/` + commentId + `/update`,
			                    type: 'POST',
			                    contentType: 'application/json',
			                    data: JSON.stringify({ content: newContent }),
			                    success: function(response) {
			                        // 성공 시 페이지에서 댓글 내용 업데이트
			                        commentContentDiv.find('.comment-text').text(newContent).show();
			                        editForm.addClass('d-none');
			                    },
			                    error: function(error) {
			                        console.error('Error updating comment:', error);
			                    }
			                });
			            } else {
			                commentContentDiv.find('.comment-text').show();
			                editForm.addClass('d-none');
			            }
			        });
			
			        // 취소 버튼 이벤트 핸들러
			        editForm.find('.cancel-edit').off('click').on('click', function() {
			            commentContentDiv.find('.comment-text').show();
			            editForm.addClass('d-none');
			        });
			    }
			
			    // AJAX를 사용하여 댓글 삭제 처리
 function deleteComment(commentId) {
	  if (confirm('댓글을 삭제하시겠습니까?')) {
			  $.ajax({
				       url: `/community/freeboard/comment/` + commentId + `/delete`,
				       type: 'POST',
				       success: function(result) {
				                $('#comment-' + commentId).remove();
				            },
				       error: function(error) {
				               alert("댓글 삭제에 실패했습니다.");
				            }
				        });
				    }
				}
			
			    // 댓글 수정 및 삭제 버튼 이벤트 바인딩
			    $(document).on('click', '.edit-comment', function() {
			        var commentId = $(this).data('commentId');
			        updateComment(commentId);
			    });
			
			    $(document).on('click', '.delete-comment', function() {
			        var commentId = $(this).data('commentId');
			        deleteComment(commentId);
			    });
			    
			    
			   $(document).ready(function() {
				    $('#delete-post-form').on('submit', function(event) {
				        var confirmed = confirm('게시글을 삭제하시겠습니까?'); 
				        if (!confirmed) {
				            event.preventDefault(); 
				        }
				    });
				}); 
								
// isUserLoggedIn 함수: 사용자가 로그인했는지 확인하는 함수
function isUserLoggedIn() {
    // 로그인 상태를 나타내는 요소가 문서에 존재하는지 확인

    return !!document.querySelector('.loggedin-status');
}

// 글작성 버튼 클릭 이벤트 핸들러
function handleWriteButtonClick(event) {
    console.log("Write button clicked");
    event.preventDefault(); // 기본 이벤트 동작을 방지

    // 로그인 상태 확인
    if (!isUserLoggedIn()) {
        // 로그인하지 않았다면 알림창 표시
        alert('글 작성을 하려면 로그인해주세요.');

        // 알림창 확인 후 로그인 페이지로 이동
        window.setTimeout(function() {
            window.location.href = '/login';
        }, 500); // 500ms 후에 페이지 이동
        return;
    }

    // 로그인 상태라면 글 작성 페이지로 이동
    window.location.href = '/community/freeboard/write';
}

// 이벤트 리스너를 글작성 버튼에 연결
document.addEventListener('DOMContentLoaded', function() {
    var writeButton = document.querySelector('.write-button');
    if (writeButton) {
        writeButton.addEventListener('click', handleWriteButtonClick);
    }
});						

			   