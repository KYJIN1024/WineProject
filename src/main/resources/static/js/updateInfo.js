$(document).ready(function() {

    $('#updateEmailForm').on('submit', function(e) {
        e.preventDefault();

        var formData = {
            email: $('#email').val()
        };

        $.ajax({
            url: '/update-email',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(response) {
                if (response.success) {
                    alert('이메일이 성공적으로 업데이트되었습니다.');
                } else {
                    alert('오류 발생: ' + response.message);
                }
            },
            error: function(error) {
                alert('서버 오류가 발생했습니다. 다시 시도해 주세요.');
            }
        });
    });

    $('#updatePasswordForm').on('submit', function(e) {
        e.preventDefault();

        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();

        if (newPassword !== confirmPassword) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        if (!confirm('정말로 비밀번호를 업데이트하시겠습니까?')) {
            return;
        }

        var formData = {
            email: $('#email').val(),
            newPassword: newPassword,
            currentPassword: $('#currentPassword').val(),
            confirmPassword: confirmPassword
        };

        $.ajax({
            url: '/update-password',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(response) {
                if (response.success) {
                    alert('비밀번호가 성공적으로 업데이트되었습니다.');
                } else {
                    alert('오류 발생: ' + response.message);
                }
            },
            error: function(error) {
                alert('서버 오류가 발생했습니다. 다시 시도해 주세요.');
            }
        });
    });
    
// 이메일 유효성 검사 함수
	function isValidEmail(email) {
	    var re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	    return re.test(email);
	}
	
	$('#updateEmailForm').on('submit', function(e) {
	    e.preventDefault();
	
	    var email = $('#email').val();
	
	    if (!isValidEmail(email)) {
	        alert('유효하지 않은 이메일 형식입니다.');
	        return;
	    }
	
	    var formData = {
	        email: email
	    };
	
	    // AJAX 요청 코드 (API endpoint는 /update-email로 설정)
	});
	
	$('#updatePasswordForm').on('submit', function(e) {
	    e.preventDefault();
	
	    var newPassword = $('#newPassword').val();
	    var confirmPassword = $('#confirmPassword').val();
	
	    if (newPassword !== confirmPassword) {
	        alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
	        return;
	    }
	
	    var formData = {
	        email: $('#email').val(),
	        newPassword: newPassword
	    };
	
	    // AJAX 요청 코드 (API endpoint는 /update-password로 설정)
		});
    
});