$(function() {
    // 페이지가 로딩될 때 쿠키에서 아이디를 가져와 입력 필드에 채워넣는 코드
    var rememberedId = getCookie("remembered_id");
    if (rememberedId) {
        $("#username").val(rememberedId);
        $("#remember-id").prop("checked", true);
    }

    // 로그인 폼이 제출될 때 "아이디 기억하기" 체크박스가 선택되어 있다면 쿠키에 아이디를 저장하는 코드
    $("#loginForm").submit(function(e) {
        if ($("#remember-id").is(":checked")) {
            setCookie("remembered_id", $("#username").val(), 30);
        } else {
            deleteCookie("remembered_id");
        }
    });
});

// 쿠키를 가져오는 함수
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

// 쿠키를 설정하는 함수
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

// 쿠키를 삭제하는 함수
function deleteCookie(cname) {
    document.cookie = cname + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}