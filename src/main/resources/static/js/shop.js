// likePost 함수: 사용자가 게시글을 좋아요 클릭 시 호출되는 함수
function likePost(shopId) {
    // 사용자가 로그인했는지 확인
    if (!isUserLoggedIn()) {
        showLoginModal();
        return;
    }
    
    // 서버에 좋아요 요청을 보냄
    fetch(`/partners/shop/like/${shopId}`, {
        method: 'POST'
    })
    .then(response => {
        if (!response.ok) {
        return response.json().then(errorData => {
            if (errorData.error) {
                throw new Error(errorData.error); // 서버에서 반환한 에러 메시지를 사용
            } else {
                throw new Error('Server Error');
            }
        });
    }
        return response.json();
    })
   .then(data => {
        // 좋아요 카운트를 업데이트
        const likesElement = document.querySelector(`#likes-count-${shopId}`);
        likesElement.textContent = data.likes;
    })
    .catch(error => {
        console.error('Error liking post:', error);
        alert(error.message); // 수정된 부분: 서버에서 반환된 에러 메시지를 보여줍니다.
    });
}

// isUserLoggedIn 함수: 사용자가 로그인했는지 확인하는 함수
function isUserLoggedIn() {
    // 로그인 상태를 나타내는 요소가 문서에 존재하는지 확인
    return !!document.querySelector('.loggedin-status');
}

// showLoginModal 함수: 로그인이 필요하다는 모달을 보여주는 함수
function showLoginModal() {
    alert('로그인이 필요합니다.');
}

// 글작성 버튼 클릭 이벤트 핸들러
function handleWriteButtonClick() {
    // 사용자가 로그인했는지 확인
    if (!isUserLoggedIn()) {
        alert('글 작성을 하려면 로그인해주세요.');
        window.location.href = '/login'; // 로그인 페이지로 리다이렉션
        return;
    }

    // 로그인 상태라면 글 작성 페이지로 이동
    window.location.href = '/partners/shop/write';
}

// 이벤트 리스너를 글작성 버튼에 연결
document.querySelector('.write-button').addEventListener('click', handleWriteButtonClick);



