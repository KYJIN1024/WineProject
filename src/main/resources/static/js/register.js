	$(document).ready(function(){
	    $("#sendVerificationCode").click(function(){
	        var email = $("#email").val();
	        $.ajax({
	            url: '/sendVerificationEmail',
	            type: 'POST',
	           contentType: 'application/json', 
	            data: JSON.stringify({ 
	                email: email
	            }),
	            success: function(response) {
	                alert('인증번호가 이메일로 전송되었습니다. 이메일을 확인해주세요.');
	            },
	            error: function(error) {
	                alert('인증번호 전송과정에 에러가 발생했습니다.');
	            }
	         });
	    });
	    
	     $("#checkVerificationCode").click(function(){
        var email = $("#email").val();
        var verificationCode = $("#verificationCode").val();

        $.ajax({
            url: '/verifyEmail',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email, verificationCode: verificationCode }),
            success: function(response) {
				console.log("Response: ", response);
                if (response.valid) {
                    alert('인증이 완료되었습니다.');
                } else {
                    $("#verificationCodeResult").text("인증코드가 올바르지 않습니다. 인증코드를 다시 확인해주세요.").css("color", "red");
                }
            },
            error: function(xhr, status, error) {
                $("#verificationCodeResult").text("인증 과정에 오류가 발생했습니다. 다시 시도해주세요.").css("color", "red");
            }
	        });
	    });
 
            
     $("#checkUsername").click(function(){
        var username = $("#username").val();
        
        // 정규 표현식을 사용하여 아이디 유효성 검사 (6~20자 영문 및 숫자)
        var validUsername = /^[A-Za-z0-9]{6,20}$/.test(username);

        if (!validUsername) {
            // 유효하지 않은 경우, 에러 메시지 표시
            $("#usernameResult").text("사용할 수 없는 아이디입니다. 다른 아이디를 입력해주세요.").css("color", "red");
        } else {
            // 유효한 경우에만 서버에 요청
            $.ajax({ //서버에 비동기 요청
            url: '/checkUsername',
            type: 'POST',
            contentType: 'application/json', 
            data: JSON.stringify({ 
                username: username
            }),
            success: function(response) {
                if (response) {
                    $("#usernameResult").text("사용가능한 아이디입니다.");
                } else {
                    $("#usernameResult").text("사용할수 없는 아이디입니다. 다른아이디를 입력해주세요.");
                }
            },
            error: function(error) {
                alert('아이디 확인과정에 에러가 발생했습니다.');
            }
           
        });
        }
    });
    
	// 비밀번호 입력 필드와 비밀번호 재확인 입력 필드에서 포커스가 벗어났을 때의 로직
	$("#password, #confirmPassword").blur(function() {
	    var password = $("#password").val();
	    var confirmPassword = $("#confirmPassword").val();
	
	    // 비밀번호 유효성 검사
	    var validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/.test(password);
	
	    if (!validPassword) {
	        $("#passwordResult").text("비밀번호는 영문, 대/소문자, 숫자, 특수문자 포함한 8자 이상 20자 이하로 작성해주세요.").css("color", "red");
	    } else {
	        $("#passwordResult").text("");
	    }
	
	    // 비밀번호와 비밀번호 재확인이 일치하는지 검사
	    if (password !== confirmPassword) {
	        $("#passwordConfirmResult").text("비밀번호가 일치하지 않습니다.").css("color", "red");
	    } else {
	        $("#passwordConfirmResult").text("");
	    }
	});
	    
	    $("#email").blur(function() {
        var email = $(this).val();
        var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        if (!emailRegex.test(email)) {
            $("#emailResult").text("올바른 Email 양식으로 입력해 주세요.").css("color", "red");
            $("#sendVerificationCode").prop("disabled", true);
        } else {
            $("#emailResult").text(""); 
            $("#sendVerificationCode").prop("disabled", false);

            $.ajax({
                url: '/checkEmail',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ email: email }),
                success: function(response) {
                    if (response === 'false') {
                        $("#emailResult").text("이미 등록된 이메일입니다. 이메일을 다시 확인해주세요.").css("color", "red");
                        $("#sendVerificationCode").prop("disabled", true);
                    } else {
                        $("#emailResult").text("");
                        $("#sendVerificationCode").prop("disabled", false);
                    }
                },
                error: function(xhr, status, error) {
                    $("#emailResult").text(xhr.responseText).css("color", "red");
                    $("#sendVerificationCode").prop("disabled", true);
                }
            });
        }
    });
    

    $("form").submit(function(e){
        e.preventDefault();

        var username = $("#username").val();
        var password = $("#password").val();
        var passwordConfirm = $("#confirmPassword").val();
        var email = $("#email").val();
        var verificationCode = $("#verificationCode").val();

        // 회원가입 요청 전송
        $.ajax({
            url: '/register',
            type: 'POST',
            contentType: 'application/json', 
            data: JSON.stringify({ 
                username: username,
                password: password,
                passwordConfirm: passwordConfirm,
                email: email,
                verificationCode: verificationCode
            }),
            success: function(response) {
                alert('회원가입에 성공하였습니다.');
                window.location.href = "/login";
            },
            error: function(xhr, status, error) {
            try {
                var resp = JSON.parse(xhr.responseText);
                alert(resp.message || '회원가입에 실패했습니다.');
            } catch(e) {
                alert('회원가입 과정에서 오류가 발생했습니다.');
            }
           }
        });
    });
});
    