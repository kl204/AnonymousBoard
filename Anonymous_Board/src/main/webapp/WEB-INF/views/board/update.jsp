<%@page import="com.oneJo.board.model.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>게시글 수정</title>
    <style>
      /* 전체 페이지 스타일 */
      body {
        background-color: #f5f6f7;
        font-family: "Noto Sans", sans-serif;
      }

      /* 수정 페이지 스타일 */
      .edit-container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      /* 제목 스타일 */
      .edit-title {
        text-align: center;
        font-size: 24px;
        margin-bottom: 20px;
      }

      /* 입력 란 스타일 */
      .input-field {
        margin-bottom: 20px;
      }

      .input-field label {
        display: block;
        margin-bottom: 5px;
      }

      .input-field input[type="text"],
      .input-field textarea {
        width: 95%;
        padding: 10px;
        border: 1px solid #e4e6e8;
        border-radius: 5px;
        resize: none;
      }

      /* 카테고리 선택 스타일 */
      .category-field select {
        width: 99%;
        padding: 10px;
        border: 1px solid #e4e6e8;
        border-radius: 5px;
      }

      /* 로그인 필드 스타일 */
      .login-field {
        margin-top: 10px;
        display: flex;
        justify-content: flex-end;
        margin-bottom: 20px;
      }

      .login-field input[type="text"],
      .login-field input[type="password"] {
        width: 30%;
        padding: 10px;
        border: 1px solid #e4e6e8;
        border-radius: 5px;
      }

      /* 글 수정 버튼 스타일 */
      .submit-button {
        background-color: #ff8c00;
        color: #ffffff;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
      }
      
      #buttons {
	display: flex;
	justify-content: space-between;
}
    </style>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  </head>
  <body>
  <%
  BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
  System.out.println( "getCategory: " + boardVO.getCategory() == "일반글");
  
  %>
    <div class="edit-container">
      <h1 class="edit-title">게시글 수정</h1>

<input type="hidden" id="seq" value="<%=boardVO.getSeq()%>"/>
      <!-- 제목 입력 란 -->
      <div class="input-field">
        <label for="title">제목</label>
        <input type="text" id="title" placeholder="제목을 입력하세요" value=<%=boardVO.getTitle() %> />
      </div>

      <!-- 본문 입력 란 -->
      <div class="input-field">
        <label for="content">본문</label>
        <textarea
          id="content"
          rows="8"
          placeholder="본문을 입력하세요"
        ><%=boardVO.getContent() %></textarea>
      </div>

      <!-- 카테고리 선택 -->
      <div class="category-field">
        <label for="category">카테고리</label>
        <select id="category">
          <option value="공지" <%=boardVO.getCategory() == "공지" ? "selected" :"" %>>공지</option>
          <option value="투표" <%=boardVO.getCategory() == "투표" ? "selected" :"" %>>투표</option>
          <option value="일반글" <%=boardVO.getCategory() == "일반글" ? "selected" :"" %>>일반글</option>
        </select>
      </div>

      <div class="login-field">
        <input type="password" id="pw" placeholder="임시 PW" required />
      </div>

      <div id="buttons">
			<button class="submit-button" id="go-to-list">뒤로 가기</button>
			<button class="submit-button" id="submit-button">글 수정</button>
		</div>
    </div>
    <script>
      $(document).ready(function () {
    	  $("#submit-button").click(function () {
              // 필수 입력값 검사
              let seq = $("#seq").val();
              let title = $("#title").val();
              let content = $("#content").val();
              let category = $("#category").val();
              let pw = $("#pw").val();

              if (pw.length < 1) {
            	  alert("비밀번호를 입력해주세요");
              }
              else if (title.length < 1) {
                alert("제목을 적어주세요");
              } else if (content.length < 1) {
                alert("본문을 적어주세요");
              } else {
                let jsonData = {
                  seq: parseInt(seq),
                  title,
                  pw,
                  content,
                  category
                };
                console.log(jsonData);
                $.ajax({
                    url: "../update",
                    type: "PUT",
                    contentType: "application/json",
                    data: JSON.stringify(jsonData),
                    success: function (response) {
                      // 요청이 성공한 경우, '/board'로 페이지 이동
                      window.location.href = "../";
                    },
                    error: function (error) {
                      // 에러 처리
                      console.error(error);
                      alert("게시글 수정에 실패했습니다.");
                    },
                  });
              }
            });

        $('#go-to-list').click(function() {
        	window.location.href = "../";
        })
      });
    </script>
  </body>
</html>
