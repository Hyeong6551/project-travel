$(document).ready(function() {
    let currentSort = 'id,desc';
    let currentKeyword = '';
    let currentMinPrice = '';
    let currentMaxPrice = '';


    // 쿼리 파라미터에서 keyword 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const keywordParam = urlParams.get('keyword');
    if (keywordParam) {
        currentKeyword = keywordParam;
        $('#searchKeyword').val(currentKeyword);
    }

    function loadPackages(url, sort = 'id,desc') {
        $('#package-list').empty();
        let requestUrl = url;

        // URL 파라미터 처리
        const params = new URLSearchParams();
        if (sort) params.append('sort', sort);
        if (currentMinPrice) params.append('minPrice', currentMinPrice);
        if (currentMaxPrice) params.append('maxPrice', currentMaxPrice);

        // 검색어가 있는 경우 정렬 파라미터 추가
        if (url.includes('/search')) {
            requestUrl += (url.includes('?') ? '&' : '?') + params.toString();
        } else if (url.startsWith('/packages')) {
            requestUrl += '?' + params.toString();
        }

        $.ajax({
            url: requestUrl,
            method: 'GET',
            success: function(response) {
                const packages = response.content ? response.content : response;
                if (packages.length === 0) {
                    $('#package-list').append('<div class="col-12 text-center">검색 결과가 없습니다.</div>');
                    return;
                }
                packages.forEach(function(pkg) {
                    $('#package-list').append(`
              <div class="col-md-6 col-lg-4">
                <a href="/package/detail/${pkg.id}" style="text-decoration: none; color: inherit;">
                  <div class="package-card card h-100">
                    <div class="position-relative">
                      <img src="${pkg.imageUrl}" class="card-img-top" alt="${pkg.title}">
                      <div class="price-tag">
                        ${pkg.price.toLocaleString()}원
                      </div>
                    </div>
                    <div class="card-body">
                      <h5 class="package-title">${pkg.title}</h5>
                      <div class="price-highlight">
                        ${pkg.price.toLocaleString()}원
                      </div>
                      <p class="card-text text-muted">${pkg.description}</p>
                      <div class="package-features">
                        <div class="mb-2">
                          <i class="fas fa-plane feature-icon"></i>
                          ${pkg.date}
                        </div>
                       
                      </div>
                    </div>
                  </div>
                </a>
              </div>
            `);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('패키지 목록 불러오기 실패');
            }
        });
    }

    // 가격 필터 이벤트 핸들러
    function applyPriceFilter() {
        currentMinPrice = $('#minPrice').val();
        currentMaxPrice = $('#maxPrice').val();

        if (currentMinPrice && currentMaxPrice && parseInt(currentMinPrice) > parseInt(currentMaxPrice)) {
            alert('최소 가격이 최대 가격보다 클 수 없습니다.');
            return;
        }

        let requestUrl = `/packages/search?`;
        if (currentKeyword.trim() !== '') {
            requestUrl += `keyword=${encodeURIComponent(currentKeyword)}&`;
        }
        loadPackages(requestUrl, currentSort); // 가격조건은 loadPackages 내부에서 항상 추가됨
        updatePriceRangeText();
    }

    function updatePriceRangeText() {
        let text = '';
        if (currentMinPrice && currentMaxPrice) {
            text = `${parseInt(currentMinPrice).toLocaleString()}원 ~ ${parseInt(currentMaxPrice).toLocaleString()}원`;
        } else if (currentMinPrice) {
            text = `${parseInt(currentMinPrice).toLocaleString()}원 이상`;
        } else if (currentMaxPrice) {
            text = `${parseInt(currentMaxPrice).toLocaleString()}원 이하`;
        }
        $('#priceRangeText').text(text);
    }

    // 필터 초기화
    $('#resetFilter').click(function(e) {
        e.preventDefault();
        $('#minPrice').val('');
        $('#maxPrice').val('');
        currentMinPrice = '';
        currentMaxPrice = '';
        $('#priceRangeText').text('');
        loadPackages('/packages', currentSort);
    });

    // 가격 입력 이벤트
    let priceTimer;
    $('.price-input').on('input', function() {
        clearTimeout(priceTimer);
        priceTimer = setTimeout(applyPriceFilter, 500); // 500ms 디바운스
    });

    // 초기 로드
    if (currentKeyword.trim() !== '') {
        loadPackages(`/packages/search?keyword=${encodeURIComponent(currentKeyword)}`, currentSort);
    } else {
        loadPackages('/packages', currentSort);
    }

    // 정렬 버튼 클릭
    $('.sort-btn').click(function() {
        $('.sort-btn').removeClass('active');
        $(this).addClass('active');
        currentSort = $(this).data('sort');

        if (currentKeyword.trim() !== '') {
            loadPackages(`/packages/search?keyword=${encodeURIComponent(currentKeyword)}`, currentSort);
        } else {
            loadPackages('/packages', currentSort);
        }
    });

    // 검색 버튼
    $('#searchBtn').click(function() {
        const keyword = $('#searchKeyword').val();
        currentKeyword = keyword;

        if (keyword.trim() === '') {
            loadPackages('/packages', currentSort);
        } else {
            loadPackages(`/packages/search?keyword=${encodeURIComponent(keyword)}`, currentSort);
        }
    });

    // Enter 키
    $('#searchKeyword').keypress(function(e) {
        if (e.which == 13) {
            $('#searchBtn').click();
        }
    });
});