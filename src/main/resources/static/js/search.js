function displayResults(data) {
    let resultsDiv = document.getElementById('searchResults');
    resultsDiv.innerHTML = ''; // Clear previous results
    
    // Check if there are no results
    if (data.length === 0) {
        resultsDiv.innerHTML = '<p>검색된 데이터가 없습니다.</p>';
        return;
    }
    
     // Display the count of search results at the top
    let countDiv = document.createElement('div');
    countDiv.innerHTML = `<h4>검색결과 총 ${data.length}건이 검색되었습니다.</h4>`;
    countDiv.style.margin = "20px 0";
    resultsDiv.appendChild(countDiv);
    
    data.forEach(item => {
        // Create a new result container
        let resultContainer = document.createElement('div');
        resultContainer.classList.add('container', 'mt-6', 'mb-6');

        let rowDiv = document.createElement('div');
        rowDiv.classList.add('d-flex', 'justify-content-center', 'row');

        let colDiv = document.createElement('div');
        colDiv.classList.add('col-md-10');

        let innerRowDiv = document.createElement('div');
        innerRowDiv.classList.add('row', 'p-2', 'bg-white', 'border', 'rounded');

	    // Image section
	    let imageColDiv = document.createElement('div');
	    imageColDiv.classList.add('col-md-3', 'mt-1');
	
	    let imageDiv = document.createElement('div');
	    imageDiv.style.backgroundImage = `url(${item.imageUrl})`;
	    imageDiv.style.width = '136.24px';
	    imageDiv.style.height = '203.33px';
	    imageDiv.style.backgroundSize = 'contain';
	    imageDiv.style.backgroundRepeat = 'no-repeat';
	    imageDiv.style.backgroundPosition = 'center center';
        imageColDiv.appendChild(imageDiv);
        
        innerRowDiv.appendChild(imageColDiv);

        // Details section
        let detailsColDiv = document.createElement('div');
        detailsColDiv.classList.add('col-md-6', 'mt-1');
        detailsColDiv.innerHTML = `
            <h5>${item.wineName}</h5>
            <div class="d-flex flex-row">
                <div class="ratings mr-2"><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></div><span>310</span>
            </div>
            <div class="mt-1 mb-1 spec-1">
                <span>제조사: ${item.manufacturer}</span></br>
                <span class="dot"></span>
                <span>주원료: ${item.mainIngredient}</span>
            </div>
            <div class="mt-1 mb-1 spec-1">
                <span>규격: ${item.specification}</span></br>
                <span class="dot"></span>
                <span>도수: ${item.alcoholDegree}</span>
            </div>
        `;

        innerRowDiv.appendChild(imageColDiv);
        innerRowDiv.appendChild(detailsColDiv);
        colDiv.appendChild(innerRowDiv);
        rowDiv.appendChild(colDiv);
        resultContainer.appendChild(rowDiv);

        resultsDiv.appendChild(resultContainer);
    });
}
function integratedSearch() {
    let keyword = document.getElementById('integratedSearchInput').value;
    fetch(`/search/integrated?keyword=${keyword}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
            document.getElementById('resultCount').textContent = data.length;
        })
        .catch(error => console.error('Error fetching search results:', error));
}

function fetchSearchResults(ingredient, region, volume, degree) {
    fetch(`/search/detailed?ingredient=${ingredient}&region=${region}&volume=${volume}&degree=${degree}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
            document.getElementById('resultCount').textContent = data.length;
        })
        .catch(error => console.error('Error fetching search results:', error));
}

window.onload = function() {
    // URL의 query parameters를 읽습니다.
    let urlParams = new URLSearchParams(window.location.search);
    let ingredient = urlParams.get('ingredient');
    let region = urlParams.get('region');
    let volume = urlParams.get('volume');
    let degree = urlParams.get('degree');

    fetchSearchResults(ingredient, region, volume, degree);
}

function detailedSearch(event) {
	event.preventDefault();
	
    let ingredientSelect = document.querySelector('select[name="ingredient"]');
    let ingredient = ingredientSelect.options[ingredientSelect.selectedIndex].value;

    let regionSelect = document.querySelector('select[name="region"]');
    let region = regionSelect.options[regionSelect.selectedIndex].value;

    let volumeSelect = document.querySelector('select[name="volume"]');
    let volume = volumeSelect.options[volumeSelect.selectedIndex].value;

    let degreeSelect = document.querySelector('select[name="degree"]');
    let degree = degreeSelect.options[degreeSelect.selectedIndex].value;
    
    fetchSearchResults(ingredient, region, volume, degree);
}



