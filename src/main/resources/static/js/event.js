  $(document).ready(function() {
				    $('#delete-post-form').on('submit', function(event) {
				        var confirmed = confirm('게시글을 삭제하시겠습니까?'); 
				        if (!confirmed) {
				            event.preventDefault(); 
				        }
				    });
				}); 