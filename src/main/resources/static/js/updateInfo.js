$(document).ready(function() {
    $('#updatePasswordForm').on('submit', function(e) {
        e.preventDefault(); // 폼의 기본 제출 동작을 방지

        // 폼 필드에서 값 가져오기
        var currentPassword = $('#currentPassword').val();
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();
        
         // 비밀번호 검증 패턴
        var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

        // 새 비밀번호 검사
        if (!passwordPattern.test(newPassword)) {
            alert('비밀번호는 영문, 대/소문자, 숫자, 특수문자 포함한 8자 이상 20자 이하로 작성해주세요.');
            return; // 비밀번호 규칙에 맞지 않으면 여기서 함수 실행을 중단합니다.
        }

        // 새 비밀번호와 비밀번호 확인이 일치하는지 검증
        if (newPassword !== confirmPassword) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return; // 함수 실행을 여기서 중단
        }

        // 사용자에게 비밀번호 변경을 최종적으로 확인받음
        if (!confirm('정말로 비밀번호를 업데이트하시겠습니까?')) {
            return; // 사용자가 취소한 경우, 함수 실행을 여기서 중단
        }

        // 서버에 전송할 formData 객체 생성
        var formData = {
            currentPassword: currentPassword,
            newPassword: newPassword,
           
        };

        // AJAX를 사용하여 서버에 비밀번호 변경 요청 전송
        $.ajax({
            url: '/update-password', // 요청을 보낼 서버의 URL 주소
            type: 'POST', // HTTP 요청 방식
            data: JSON.stringify(formData), // JSON 형식의 문자열로 변환
            contentType: 'application/json', // 요청의 Content-Type
            success: function(response) {
                // 요청이 성공적으로 처리되었을 때 실행될 함수
                alert(response.message); // 성공 메시지를 알림으로 표시합니다.
                window.location.href = '/mypage';
            },
            error: function(xhr) {
                // 요청 처리 중 오류가 발생했을 때 실행될 함수
                var response = JSON.parse(xhr.responseText); // 서버로부터의 응답 텍스트를 JSON 객체로 변환합니다.
                alert(response.message); // 변환된 객체에서 메시지를 추출하여 알림으로 표시합니다.
            }
        });
    });
});