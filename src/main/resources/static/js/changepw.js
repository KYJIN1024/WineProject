$(document).ready(function(){
    // 비밀번호 입력 필드에서 포커스가 벗어났을 때 유효성 검사 실행
    $("#password").blur(function() {
        validatePassword();
    });

    // 비밀번호 재확인 입력 필드에서 포커스가 벗어났을 때 비밀번호 일치 여부 검사
    $("#confirmPassword").blur(function() {
        validatePasswordConfirmation();
    });

    // 폼이 서밋되기 전에 실행될 함수
    $("form").submit(function(e){
        e.preventDefault(); // 기본 서밋 이벤트 방지
        
        // 비밀번호 유효성 검사와 비밀번호 일치 여부 확인
        if (validatePassword() && validatePasswordConfirmation()) {
            // 비밀번호 변경을 위한 AJAX 요청
            $.ajax({
                url: $(this).attr("action"), // 폼의 action 속성을 사용
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded', 
                data: $(this).serialize(), // 폼 데이터 직렬화하여 전송
                success: function(response) {
                    alert('비밀번호 변경에 성공하였습니다.');
                    window.location.href = "/login"; // 비밀번호 변경 성공 시 로그인 페이지로 리다이렉션
                },
                error: function(error) {
                    alert('비밀번호 변경에 실패했습니다.');
                }
            });
        } else {
            // 에러가 있으므로 서밋 방지
            return false;
        }
    });
});

// 비밀번호 유효성 검사 함수
function validatePassword() {
    var password = $("#password").val();
    var validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/.test(password);
    if (!validPassword) {
        // 유효하지 않은 경우, 에러 메시지 표시
        $("#passwordResult").text("비밀번호는 영문, 대/소문자, 숫자, 특수문자 포함한 8자 이상 20자 이하로 작성해주세요.").css("color", "red");
    } else {
        // 유효한 경우, 에러 메시지 제거
        $("#passwordResult").text("");
    }
    return validPassword;
}

// 비밀번호 재확인 검사 함수
function validatePasswordConfirmation() {
    var password = $("#password").val();
    var passwordConfirm = $("#confirmPassword").val();
    if (password !== passwordConfirm) {
        // 비밀번호가 일치하지 않는 경우, 에러 메시지 표시
        $("#passwordConfirmResult").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        return false;
    } else {
        // 비밀번호가 일치하는 경우, 에러 메시지 제거
        $("#passwordConfirmResult").text("");
        return true;
    }
}