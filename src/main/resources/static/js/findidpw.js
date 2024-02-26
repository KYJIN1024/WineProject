$(function () {
  $("#register-link").click(function () {
    $("#login-box").hide();
    $("#register-box").show();
  });
  $("#login-link").click(function () {
    $("#login-box").show();
    $("#register-box").hide();
  });
  $("#forgot-link").click(function () {
    $("#login-box").hide();
    $("#forgot-box").show();
  });
  $("#back-link").click(function () {
    $("#login-box").show();
    $("#forgot-box").hide();
  });
});

	$(document).ready(function() {
	    // 이메일 형식을 검증하는 정규 표현식
	    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	
	    // form 제출 이벤트 처리
	    $("#forgot-form").submit(function(event) {
	        var email = $("input[name=email]").val();
	
	        // 이메일 형식 검증
	        if (!emailRegex.test(email)) {
	            // 이메일 형식이 올바르지 않은 경우
	            alert("올바른 Email양식으로 입력해 주세요");
	            event.preventDefault(); // form 제출을 중단
	        }
	        // 이메일 형식이 올바른 경우 form 제출을 계속 진행
	    });
});