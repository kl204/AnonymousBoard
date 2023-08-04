<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.oneJo.board.model.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>익명 게시판</title>
<style>
/* 전체 페이지 스타일 */
body {
	background-color: #f5f6f7;
	font-family: "Noto Sans", sans-serif;
}

/* 게시판 스타일 */
.board-container {
	max-width: 800px;
	margin: 0 auto;
	padding: 20px;
	background-color: #ffffff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* 제목 스타일 */
.board-title {
	text-align: center;
	font-size: 24px;
	margin-bottom: 20px;
}

/* 검색창 스타일 */
.search-box {
	margin-bottom: 20px;
	text-align: right;
}

.search-box input[type="text"] {
	padding: 5px;
	border: 2px solid #ff8c00;
	border-radius: 5px;
	font-size: 13px;
	width: 150px;
}

.search-box button {
	padding: 5px;
	border: 2px solid #ff8c00;
	border-radius: 5px;
	background-color: #ff8c00;
	color: #ffffff;
	font-size: 13px;
	cursor: pointer;
}

.search-box select {
	padding: 5px;
	border: 2px solid #ff8c00;
	border-radius: 5px;
	font-size: 13px;
	margin-right: 5px;
}

/* 테이블 스타일 */
table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	text-align: left;
	border-bottom: 1px solid #e4e6e8;
}

th {
	background-color: #f7f8fa;
}

/* new 태그 스타일 */
.new-tag {
	color: #ff8c00;
	font-weight: bold;
}

/* 글쓰기 버튼 스타일 */
.write-button {
	background-color: #ff8c00;
	color: #ffffff;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
}

/* 페이지네이션 스타일 */
.pagination {
	display: flex;
	justify-content: center;
	margin-top: 10px;
}

.pagination .page-link {
	background-color: #e4e6e8;
	color: #333333;
	padding: 5px 10px;
	border: none;
	border-radius: 5px;
	font-size: 14px;
	margin: 0 5px;
	cursor: pointer;
}

.pagination .page-link.active {
	background-color: #ff8c00;
	color: #ffffff;
}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	 function handleKeyPress(event) {
        if (event.keyCode === 13) { // 13은 엔터 키의 keyCode입니다.
          event.preventDefault(); // 기본 엔터 동작을 막습니다.
          document.getElementById("searchButton").click(); // 검색 버튼을 클릭합니다.
        }
      }
</script>
</head>
<body>
	<%
	ArrayList<BoardVO> list = (ArrayList<BoardVO>) request.getAttribute("list");
	String search = (String) request.getParameter("search");
	String sortType = (String) request.getParameter("sortType");
	String category = (String) request.getParameter("category");
	
	boolean isSearch = search != null;
	boolean isSortType = sortType != null;
	boolean isCategory = category != null;
	%>
	<div class="board-container">
		<h1 class="board-title">익명 게시판</h1>

		<!-- 검색창 -->
		<div class="search-box">
		
			<select id="sortType">
				<option value="latest"
					<%= sortType == null || sortType.equals("latest") ? "selected" : "" %>>작성시간</option>
				<option value="views" <%=isSortType && sortType.equals("views") ? "selected" : "" %>>조회수</option>
				<option value="likes" <%= isSortType && sortType.equals("likes") ? "selected" : "" %>>좋아요수</option>
			</select> 
			<select id="category">
				<option value="all"
					<%= category == null || category.equals("all") ? "selected" : "" %>>전체</option>
				<option value="공지" <%=isCategory && category.equals("공지") ? "selected" : "" %>>공지</option>
				<option value="투표" <%=isCategory && category.equals("투표") ? "selected" : "" %>>투표</option>
				<option value="일반글" <%=isCategory && category.equals("일반글") ? "selected" : "" %>>일반글</option>
				
			</select>
			<input type="text" placeholder="게시글 검색" id="search" value="<%=isSearch ? request.getParameter("search") : ""%>" />
				<button id="searchButton">검색</button>

		</div>

		<!-- 게시판 리스트 -->
		<table>
			<tr>
				<th>카테고리</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>작성시간</th>
				<th>조회수</th>
				<th>좋아요수</th>
			</tr>
			<%
			for (BoardVO board : list) {
			%>
			<tr>
				<td><%=board.getCategory()%></td>
				<td><a href="<%=board.getSeq()%>"><%=board.getTitle()%></a></td>
				<td><%=board.getWriter()%></td>
				<td><%=new SimpleDateFormat("yyyy-MM-dd HH시mm분").format(board.getCreated_at())%></td>
				<td><%=board.getViews()%></td>
				<td><%=board.getLike_count()%></td>
			</tr>
			<%
			}
			%>

		</table>

		<!-- 페이지네이션 -->
		<div class="pagination">
			<c:if test="${paging.prevPageNo != 0}">
				<a href="./?page=${paging.firstPageNo}">
					<button class="page-link"> << </button>
				</a>
				<a href="./?page=${paging.prevPageNo}">
					<button class="page-link">&lt;</button>
				</a>
			</c:if>
			<c:forEach begin="${paging.startPageNo}" end="${paging.endPageNo}"
				var="page">
				<a href="./?page=${page}">
					<button class="page-link ${page == paging.pageNo ? 'active' : ''}">${page}</button>
				</a>
			</c:forEach>
			<c:if test="${paging.nextPageNo != 0}">
				<a href="./?page=${paging.nextPageNo}">
					<button class="page-link">&gt;</button>
				</a>
				<a href="./?page=${paging.finalPageNo}">
					<button class="page-link"> >> </button>
				</a>
			</c:if>
		</div>
		<!-- 글쓰기 버튼 -->
		<button class="write-button" id="write-button">글쓰기</button>
	</div>
	<script>
		// 현재 페이지의 경로를 기준으로 상대 경로로 페이지 이동
		$("#write-button").click(function() {
			window.location.href = "create";
		});

		$(document)
				.ready(
						function() {
							// searchButton 클릭 시 실행되는 함수
							$("#searchButton")
									.click(
											function() {
												var searchValue = $("#search")
														.val();
												var sortTypeValue = $(
														"#sortType").val();
												var categoryValue = $(
														"#category").val();

												var url = window.location.href
														.split("?")[0]; // 현재 페이지 URL에서 쿼리스트링 제거

												if (searchValue !== "") {
													url += "?search="
															+ encodeURIComponent(searchValue);
												}

												if (sortTypeValue !== "") {
													url += (url.indexOf("?") === -1 ? "?"
															: "&")
															+ "sortType="
															+ encodeURIComponent(sortTypeValue);
												}

												if (categoryValue !== "") {
													url += (url.indexOf("?") === -1 ? "?"
															: "&")
															+ "category="
															+ encodeURIComponent(categoryValue);
												}

												window.location.href = url; // URL로 페이지 다시 로드
											});

							// sortType 변경 시 실행되는 함수
							$("#sortType").change(function() {
								$("#searchButton").click(); // searchButton 클릭 이벤트 호출
							});

							// category 변경 시 실행되는 함수
							$("#category").change(function() {
								$("#searchButton").click(); // searchButton 클릭 이벤트 호출
							});
						});
	</script>
</body>
</html>
