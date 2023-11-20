<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
/* 태그 파트 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

a {
	text-decoration: none;
}

body {
	display: flex;
	flex-direction: column;
	align-items: center;
	height: 100vh;
}

ul {
	list-style: none;
}

input {
	display: block;
}

div {
	border: 1px solid black;
}

span {
	border: 1px solid black;
}

section {
	display: flex;
	flex-direction: column;
	align-items: center;
	flex: 7.8;
	width: 1280px;
	border: 1px solid black;
	padding-top: 140px; /* 헤더 높이만큼 padding-top 추가 */
	margin-bottom: 50px; /* 여분의 여백으로 풋터가 바닥에 유지되도록 설정 */
}

section::-webkit-scrollbar { /* 스크롤바 없애기 */
	display: none;
}

button {
	outline: none;
	cursor: pointer; /* 손가락모양 */
}

.menu-info-review-tab {
	display: flex;
}

.store-image {
	width: 15.6%;
	height: 28%;
}

.rating-review-minprice {
	display: flex;
}

.menu-classification-list {
	display: inline-block;
}

.menu-classification {
	display: flex;
}

.menu-info-with-btn {
	display: flex;
}

.modify-delete {
	display: flex;
}

.info-sub-tab-with-btn {
	display: flex;
}

.store-description {
	display: flex;
}

.operating-time {
	display: flex;
}

.seller-info {
	display: flex;
}

.seller-name {
	display: flex;
}

.store-address {
	display: flex;
}

.seller-regcode {
	display: flex;
}

.store-info-tab, .store-review-tab {
	display: none;
}
</style>
<script>
	$(document).ready(function() {
		// 삭제된 메뉴는 보이지 않게 처리
		$("div.menu-info-with-btn").each(function() {
		    var statusText = $(this).find("span").text();
		    if (statusText.includes("삭제")) {
		        $(this).hide();
		    }
		});
		
		
		
});

	// 메뉴/정보/리뷰 탭 영역
	
	// 메뉴 탭 영역
	// 메뉴 탭을 클릭하면 메뉴 리스트 조회, 정보와 리뷰 탭 비활
	function sellerMenu(){
		
		$(".menu-sub-tab").show();
		$(".store-info-tab, .store-review-tab").hide();
		
	}
	// + 버튼을 클릭하면 메뉴정보 입력 폼 활성화
	function addMenuBtn(){
		
        $(".menu-form, .cancel-btn").show();
        
	}
	
	// 메뉴 등록
	function addMenu() {
		
		let formData = new FormData(document.querySelector(".menu-form"));
		
		$.ajax({
			type : "POST",
			url : "${path}/sellerMenu",
			enctype : 'multipart/form-data',
			data : formData,
			processData : false, // 데이터 처리를 비활
			contentType : false, // 컨텐츠 타입을 비활
			success : function(data) {
				alert("등록되었습니다!");
				window.location.reload();
			},
			error : function() {
				alert("메뉴 등록 실패");
			}
		});
	}
	
	// 취소 버튼을 클릭시 전영역에 걸쳐서 활성/비활성화
	$(".cancel-btn").click(function() {
		$(".menu-form, .reply-form").hide();	// 입력 form 비활
		$(".active-reply-form-btn").show();	// 답글 달기 활성화
		$(this).hide();
	});
	// 메뉴 분류 수정
	function modifyMenuClassification(Code, element){
		let menuCode = Code;
		let menuClassification = $(element).closest(this).find('.menu-classification-list').val();
		
		let info = {
				menuCode : menuCode,
				menuClassification : menuClassification
		}
		
		let infos = JSON.stringify(info);
		
		$.ajax({
			type : "PUT",
			url : "${path}/updateSellerClassification",
			data : infos,
			success : function(){
				alert("수정완료");
			},
			error : function(){
				alert("실패")
			}
		});
	}
	// 메뉴 수정
	function menuModifyBtnWithoutC(Code, element){

		let menuCode = Code;
		let menuName = $(element).closest('.menu-info-with-btn').find('.menuName').val();
		let menuImage = $(element).closest('.menu-info-with-btn').find('.menuImage').val();
	    let menuContent = $(element).closest('.menu-info-with-btn').find('.menuContent').val();
	    let menuPrice = $(element).closest('.menu-info-with-btn').find('.menuPrice').val();
	    let menuStatus = $(element).closest('.menu-info-with-btn').find('.menuStatus').val();
	    
	    let info = {
		    	menuCode : menuCode,
		    	menuName : menuName,
		    	menuImage : menuImage,
		    	menuContent : menuContent,
		    	menuPrice : menuPrice,
		    	menuStatus : menuStatus
	    	}
	    
	    console.log(info);
	    
   		let infos = JSON.stringify(info);
       	
   		$.ajax({
   			type : "PUT",
   			url : "${path}/updateSellerMenu",
   			data : infos,
   			contentType : "application/json", // 필수
   			success : function() {
   				alert("변경되었습니다");
   				window.location.reload();
   			},
   			error : function() {
   				alert("error");
   			}
   		});
   		
	}
	
	// 메뉴 삭제
	function deleteMenu() {
		$.ajax({
			type: "PUT",
			url: "${path}/sellerMenu", //path Variable  ,
			success : function (data){
				window.location.reload();
			},
			error : function() {
				alert("error");
			}
		});
	}

	
	// 정보 탭 영역
	// 정보 탭을 클릭하면 가게정보 조회, 메뉴와 리뷰 탭 비활
	function storeInfo() {
		
		$(".menu-sub-tab, .store-review-tab").hide();
		$(".store-info-tab").show();
		
		$.ajax({
			type : "GET",
			url : "${path}/infoManage",
			success : function(response){
				let readStore = response.readStore;
				let readSeller = response.readSeller;
				
				// 가게 소개 정보 적용
	            $("#store-description").val(readStore.storeDescription);
	            $("#operating-time").val(readStore.operatingTime);

	            // 사업자 정보 적용
	            $("#seller-name").val(readSeller.sellerName);
	            $("#store-address").val(readStore.storeAddress);
	            $("#seller-regCode").val(readSeller.sellerRegCode);
			},
			error : function(){
				alert("실패");
			}
		});
	}
	
	// 가게정보 수정
	function modifyStoreInfo(Code, element){
		
		let storeCode = Code;
		let storeDescription = $(element).closest('.store-info-tab').find('#store-description').val();
		let operatingTime = $(element).closest('.store-info-tab').find('#operating-time').val();
	    let storeAddress = $(element).closest('.store-info-tab').find('#store-address').val();
	    
	    let storeInfo = {
	    		storeCode : storeCode,
		    	storeDescription : storeDescription,
		    	operatingTime : operatingTime,
		    	storeAddress : storeAddress
	    	}
	    
	    console.log(storeInfo);
	    
   		let infos = JSON.stringify(storeInfo);

	    console.log(infos);
	    
   		$.ajax({
   			type : "PUT",
   			url : "${path}/infoManage",
   			data : infos,
   			dataType : "text",
   			contentType : "application/json", // 필수
   			success : function() {
   				alert("변경되었습니다");
   			},
   			error : function(e) {
   				console.log(e)
   				alert("error");
   			}
   		});
	}
	
	
	// 리뷰 탭 영역
	// 리뷰 조회
	function review(storeCode){
		// 리뷰 탭을 클릭하면 리뷰 리스트 조회, 메뉴와 정보 탭 비활
		$(".menu-sub-tab, .store-info-tab").hide();
		$(".store-review-tab").show();
		
		$.ajax({
			type : "GET",
   			url : "${path}/registerAnswer",
   			success : function(  res) {
   				console.log(res);
   				alert("조회완료");
   			},
   			error : function(e) {
   				console.log(e)
   				alert("error");
   			}
		});
		
	}

</script>
</head>
<body>
	<jsp:include page="../base/sellerHeader.jsp" />
	<section id="content">
	<c:set var="storeImage" value="파일 이름을 어떻게 가져올까" />
		<div class="store-image">
			<img alt="가게 로고" src="${path}/storeImages/${storeImage}">
		</div>
		<!-- 아래 div는 추후에 선으로 대체할 예정 -->
		<div>-------------------------------------------------------------------</div>
		<div class="store-name">${readStore.storeName}</div>
		<div class="rating-review-minprice">
			<div>⭐: ${readStore.storeRating}</div>
			<div>리뷰수: ${RCount}</div>
			<div>최소주문금액: ${readStore.minOrderPrice}</div>
		</div>
		<ul class="menu-info-review-tab">
			<li class="menu-tab" onclick="sellerMenu()">메뉴</li>
			<li class="info-tab" onclick="storeInfo()">정보</li>
			<li class="review-tab" onclick="review(${readStore.storeCode})">리뷰</li>
		</ul>
		<!-- 메뉴 리스트 나오는 탭 -->
		<div class="menu-sub-tab">
			<div>
				<!-- 메뉴 카테고리 -->
				<c:forEach items="${CList}" var="classificationList">
					<span>${classificationList.menuClassification}</span>
				</c:forEach>
			</div>
			<div>
				<!-- 메뉴 리스트 -->
				<c:forEach items="${CList}" var="classificationList">
					<input type="text" class="menu-classification-list" value="${classificationList.menuClassification}">
						<button class="CModify" onclick="modifyMenuClassification(${classificationList.menuClassification}, this)">수정</button>
					<c:forEach items="${readMenuInfo}" var="menuList">
						<c:if
							test="${menuList.menuClassification eq classificationList.menuClassification}">
							<c:choose>
								<c:when
									test="${menuList.menuClassification eq classificationList.menuClassification}">
									<div class="menu-info-with-btn">
										<a href="${path}/sellerOption?menuCode=${menuList.menuCode}"><img alt="메뉴 사진" src="${path}/images/${readStore.storeImage}"></a>
										<input type="file" name="menuImageFile">
										<div>
											<input type="text" class="menuName"
												value="${menuList.menuName}"> <input type="text"
												class="menuContent" value="${menuList.menuContent}">
											<input type="text" class="menuPrice"
												value="${menuList.menuPrice}"> <span>상태 : <c:choose>
													<c:when test="${menuList.menuStatus eq 0}">판매중</c:when>
													<c:when test="${menuList.menuStatus eq 1}">매진</c:when>
													<c:when test="${menuList.menuStatus eq 2}">삭제</c:when>
												</c:choose>
											</span> <select class="menuStatus">
												<option value="0">판매중</option>
												<option value="1">매진</option>
											</select>
										</div>
										<div class="modify-delete">
											<button class="menu-modify-btn-without-c"
												onclick="menuModifyBtnWithoutC(${menuList.menuCode}, this)">수정</button>
											<button class="menu-delete-btn"
												onclick="deleteMenu()">삭제</button>
										</div>
									</div>
								</c:when>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:forEach>
			</div>
			<!-- 메뉴 등록 폼 -->
			<form class="menu-form" style="display: none;">
				<div class="menu-classification">
					<select name="menuClassification">
						<c:forEach items="${CList}" var="clist">
							<option value="${clist.menuClassification}">${clist.menuClassification}</option>
						</c:forEach>
					</select>
				</div>
				<div class="menu-info-with-btn">
					<input type="file" name="menuImageFile">
					<div>
						<input type="hidden" name="storeCode" value="${readStore.storeCode}"><br />
						<input type="text" name="menuName" placeholder="메뉴명"><br />
						<input type="text" name="menuContent" placeholder="메뉴설명"><br />
						<input type="number" name="menuPrice" placeholder="메뉴가격">
						<div></div>
						<select name="menuStatus">
							<option value="0">판매중</option>
							<option value="1">솔드아웃</option>
						</select>
					</div>
				</div>
				<button type="button" class="insert-menu-btn" onclick="addMenu()">등록하기</button>
				<button type="button" class="cancel-btn">취소하기</button>
			</form>
			<!-- 얘를 클릭하면 등록 폼이 활성화 -->
			<div class="add-menu" onclick="addMenuBtn()">+</div>
		</div>
		<!-- 가게 정보 탭 -->
		<div class="store-info-tab">
			<div class="info-sub-tab-with-btn">
				<div class="info-sub-tab">
					<div class="store-description">
						<div>가게소개</div>
						<input type="text" id="store-description"
							value="readStore.storeDescription">
					</div>
					<div class="operating-time">
						<div>운영시간</div>
						<input type="text" id="operating-time" value="">
					</div>
					<div class="seller-info">
						<div>사업자정보</div>
						<div class="seller-info-sub">
							<div class="seller-name">
								<div>대표자명</div>
								<input type="text" id="seller-name" value="" readonly>
							</div>
							<div class="store-address">
								<div>매장주소</div>
								<input type="text" id="store-address" value="">
							</div>
							<div class="seller-regcode">
								<div>사업자등록번호</div>
								<input type="text" id="seller-regCode" value="" readonly>
							</div>
						</div>
					</div>
				</div>
				<button class="store-info-modify-btn"
					onclick="modifyStoreInfo(${readStore.storeCode}, this)">수정하기</button>
			</div>
		</div>
		<!-- 리뷰 리스트 나오는 탭 -->
		<div class="store-review-tab">
			<c:forEach items="${review}" var="readReview">
				<div class="review-answer">
					<div>(닉네임)</div>
					<div>${readReview.reviewRating}</div>
					<div>주문메뉴 : (메뉴명)</div>
					<div>
						${readReview.reviewContent}
						<div>${readReview.reviewDate}</div>
						<!-- 답글 달기를 누르면 답변내용을 입력하는 폼 활성화 -->
						<form class="reply-form" style="display: none;">
							<textarea placeholder="답글 내용을 입력해주세요" rows="5" cols="30"></textarea>
							<button type="button" class="insert-answer-btn">등록</button>
						</form>
					</div>
					<button class="active-reply-form-btn">답글달기</button>
					<!-- 답글이 생기면 답글달기 버튼 사라지고 수정 삭제 버튼 생성-->
					<button class="reply-modify-btn" style="display: none">수정</button>
					<button class="reply-delete-btn" style="display: none">삭제</button>
					<button class="cancel-btn" style="display: none">취소</button>
				</div>
			</c:forEach>
		</div>
	</section>
	<jsp:include page="../base/footer.jsp" />
</body>
</html>