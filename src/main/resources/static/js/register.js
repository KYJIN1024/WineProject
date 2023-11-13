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
	                alert('Error sending verification code.');
	            }
	         });
	    });
            
     $("#checkUsername").click(function(){
        var username = $("#username").val();
        $.ajax({
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
                alert('Error checking username.');
            }
           
        });
    });
    
	  $("form").submit(function(e){
	    e.preventDefault();
	
	    var username = $("#username").val();
	    var password = $("#password").val();
	    var passwordConfirm = $("#passwordConfirm").val();
	    var email = $("#email").val();
	    var verificationCode = $("#verificationCode").val();
	
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
	            // Redirect to login page
	            window.location.href = "/login";
	        },
	        error: function(error) {
	            alert('Registration failed.');
	        }
	    });
	});
});
    