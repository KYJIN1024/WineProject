	
	function redirectToSearch() {
        // 체크박스에서 선택된 모든 값을 가져옵니다.
        let ingredients = [];
        document.querySelectorAll('input[name="ingredient"]:checked').forEach(element => {
            ingredients.push(element.value);
        });
        let regions = [];
        document.querySelectorAll('input[name="region"]:checked').forEach(element => {
            regions.push(element.value);
        });
        let volumes = [];
        document.querySelectorAll('input[name="volume"]:checked').forEach(element => {
            volumes.push(element.value);
        });
        let degrees = [];
        document.querySelectorAll('input[name="degree"]:checked').forEach(element => {
            degrees.push(element.value);
        });

        // 쿼리 파라미터를 만듭니다. 배열의 값을 쉼표로 구분하여 전달합니다.
        let queryParams = `?ingredient=${ingredients.join(",")}&region=${regions.join(",")}&volume=${volumes.join(",")}&degree=${degrees.join(",")}`;

        // search.html 페이지로 리다이렉트합니다.
        window.location.href = `/search${queryParams}`;
    }
    
    function validateForm(event) {
        // Prevent the form from submitting
        event.preventDefault();

        // Get form elements
        var companyName = document.querySelector('input[placeholder="업체명"]').value;
        var email = document.querySelector('input[placeholder="이메일"]').value;
        var service = document.querySelector('.form-select').value;
        var content = document.querySelector('textarea[placeholder="내용"]').value;

        // Check if any field is empty
        if (!companyName.trim()) {
            alert("업체명을 입력하세요.");
            return;
        }

        // Check if email is valid
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (!emailPattern.test(email)) {
            alert("유효한 이메일 주소를 입력하세요.");
            return;
        }

        // Check if service is selected
        if (service === "서비스를 선택하세요") {
            alert("서비스를 선택하세요.");
            return;
        }

        // Check content length
        if (content.trim().length < 10) {
            alert("내용은 10자 이상 입력해주세요.");
            return;
        }

        alert("정상적으로 등록되었습니다. 등록하신 이메일로 연락드리겠습니다.");
       
    }
   
    
    
    
    