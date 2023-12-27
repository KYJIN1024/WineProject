   $(document).ready(function() {
	            $('#editButton').on('click', function() {
	            	$.get('/mypage-edit-modal', function(data) {
	            	    $('#editModal .modal-body').html(data);
	            	    var modalTitle = $(data).filter('title').text();
	                    $('#editModalLabel').text(modalTitle);
	            	});
	            });
	            
	            // 새롭게 추가한 코드
	            $('.loadModal').on('click', function(event) {
	                event.preventDefault();
	                var url = $(this).data('url');
	                $.get(url, function(data) {
	                    $('#editModal .modal-body').html(data);
	                    var modalTitle = $(data).filter('title').text();
	                    $('#editModalLabel').text(modalTitle);
	                    $('#editModal').modal('show');
	                });
	            });     
	            
	            $('#userPostsLink').on('click', function(event) {
	                event.preventDefault();
	                $.get('/user-posts', function(data) {
	                    // 게시글 목록을 모달에 삽입
	                    var postsList = $('#userPostsList');
	                    postsList.empty();
	                    data.forEach(post => {
	                        postsList.append('<li><a href="/community/freeboard/view?id=' + post.frboardid + '">' + post.frboardtitle + '</a></li>');
	                    });
	                    $('#postsModal').modal('show');
	                });
	            });

	            $('#userCommentsLink').on('click', function(event) {
	                event.preventDefault();
	                $.get('/user-comments', function(data) {
	                    // 댓글 목록을 모달에 삽입
	                    var commentsList = $('#userCommentsList');
	                    commentsList.empty();
	                    data.forEach(comment => {
	                    	if (comment.freeBoard && comment.freeBoard.frboardid) {
	                            commentsList.append('<li><a href="/community/freeboard/view?id=' + comment.freeBoard.frboardid + '">' + comment.content + '</a></li>');
	                        } else {
	                            // freeBoard가 없는 경우의 처리 (예: 댓글 내용만 표시)
	                            commentsList.append('<li>' + comment.content + '</li>');
	                        }
	                    });
	                    $('#commentsModal').modal('show');
	                });
	            });
	        });
	        
			
	        