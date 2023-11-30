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
				
			   