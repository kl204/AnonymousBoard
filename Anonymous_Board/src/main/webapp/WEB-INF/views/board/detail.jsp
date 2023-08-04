<%@page import="java.util.Date"%> <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%> <%@page
import="com.oneJo.board.model.vo.BoardVO"%> <%@page
import="com.oneJo.board.model.dto.CommentDTO"%> <%@page
import="com.oneJo.board.model.dto.Paging"%> <%@page
import="java.util.ArrayList"%> <%@ page language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <title>상세페이지</title>
    <style>
      /* 테이블 스타일 */
      .tbody {
        display: flex;
        flex-direction: column;
        width: 50px;
      }

      /* 전체 페이지 스타일 */
      body {
        background-color: #f5f6f7;
        font-family: "Noto Sans", sans-serif;
      }

      /* 상세페이지 스타일 */
      .detail-container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      /* 타이틀 스타일 */
      .detail-title {
        text-align: center;
        font-size: 28px;
        margin-bottom: 20px;
        color: #333333;
      }

      /* 글 정보 스타일 */
      .post-info {
        margin-bottom: 20px;
      }

      .post-info .title {
        font-size: 22px;
        font-weight: bold;
        color: #333333;
      }

      .post-info .author {
        font-size: 14px;
        color: #888888;
      }

      /* 본문 스타일 */
      .content {
        min-height: 300px;
        padding: 10px;
        border: 1px solid #e4e6e8;
        border-radius: 8px;
        background-color: #f7f8fa;
        margin-bottom: 20px;
        color: #333333;
      }

      /* 버튼 스타일 */
      .button {
        background-color: #ff8c00;
        color: #ffffff;
        padding: 8px 16px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }

      .button:hover {
        background-color: #ff9800;
      }

      /* 댓글 스타일 */
      .commentTr {
        background-color: #f7f9fc;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin-bottom: 10px;
      }

      .coCommentTr {
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
        margin-bottom: 6px;
      }

      .commentTr td,
      .coCommentTr td {
        padding: 10px;
        border-bottom: 1px solid #f1f3f5;
      }

      .comment-buttons {
        display: flex;
        align-items: center;
      }

      .comment-buttons button {
        margin-left: 5px;
      }

      .comment-buttons .button {
        background-color: #ffeb3b;
        color: #000000;
        border: none;
        padding: 8px;
        border-radius: 5px;
        font-size: 14px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }

      .comment-buttons .button:hover {
        background-color: #ffc107;
      }

      .comment-buttons .like-button {
        background-color: #42a5f5;
        color: #ffffff;
      }

      .comment-buttons .dislike-button {
        background-color: #e57373;
        color: #ffffff;
      }
      
       .comment-buttons .none-button {
        visibility: hidden;
        margin-left:10px;
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

      /* 리스트로 돌아가기 버튼 스타일 */
      .list-button {
        background-color: #ff8c00;
        color: #ffffff;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
      }

      /* 댓글 작성 창 스타일 */
      .comment-input-container {
        margin-top: 10px;
        padding: 10px;
        border: 1px solid #e4e6e8;
        border-radius: 5px;
        background-color: #f7f8fa;
        display: none;
      }

      .comment-input-container input[type="text"],
      .comment-input-container input[type="password"] {
        width: 100%;
        padding: 5px;
        margin-bottom: 10px;
      }

      .comment-input-container textarea {
        width: 100%;
        height: 80px;
        resize: none;
        padding: 5px;
        margin-bottom: 10px;
      }

      #contentHeaderContainer {
        display: flex;
        flex-direction: row;
        align-items: start;
        padding: 10px 0px;
      }

      #titleContainer {
        display: flex;
        flex-direction: column;
        flex: 10;
      }

      #optionContainer,
      #deleteContainer {
        flex: 10;
        display: flex;
        justify-content: flex-end;
        flex-direction: row;
        align-self: flex-end;
        margin-bottom: 20px;
        height: 20px;
        gap: 5px;
      }

      #optionContainer > button,
      #deleteContainer > button {
        border: 0px;
        background-color: transparent;
        gap: 5px;
        font-size: 15px;
      }

      #editButton,
      #cancelButton {
        color: #d4d4d4fe;
      }

      #deleteButton,
      #sendButton {
        color: #ff8888fe;
      }
    </style>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  </head>
  <body>
    <% BoardVO boardVO = (BoardVO) request.getAttribute("boardVO"); Timestamp
    createdAt = boardVO.getCreated_at(); Date formattedDate = new
    Date(createdAt.getTime()); SimpleDateFormat sdf = new
    SimpleDateFormat("yyyy-MM-dd"); // 원하는 날짜 형식으로 포맷 설정 String
    String date2 = sdf.format(formattedDate); int likeCount =
    boardVO.getLike_count(); int hateCount = boardVO.getHate_count(); %>
    <div class="detail-container">
      <h1 class="detail-title">상세페이지</h1>

      <!-- 글 정보 -->
      <div id="contentHeaderContainer">
        <div class="post-info" id="titleContainer">
          <div class="title"><%=boardVO.getTitle()%></div>
          <div class="author"><%=boardVO.getWriter()%> | <%=date2%></div>
        </div>
        <div id="optionContainer">
          <button id="editButton">수정</button>
          <button id="deleteButton">삭제</button>
        </div>
        <div id="deleteContainer" style="display: none">
          pw:
          <input type="password" placeholder="Input password...." id="del_pw" />
          <button id="sendButton">전송</button>
          <button id="cancelButton">취소</button>
        </div>
      </div>

      <!-- 본문 -->
      <div class="content"><%=boardVO.getContent()%></div>

      <!-- 좋아요, 싫어요 버튼 -->
      <div
        class="button-container"
        style="display: flex; justify-content: center"
      >
        <button class="button like-button" id="like-button">
          좋아요 (<%=likeCount%>)
        </button>
        <button class="button dislike-button" id="hate-button">
          싫어요 (<%=hateCount%>)
        </button>
      </div>

      <!-- 댓글 작성 버튼 -->
      <button class="button addComment" style="justify-content: end">
        댓글 작성
      </button>

      <!-- 댓글 입력 폼 -->
      <div class="comment-input-container">
        <input
          type="text"
          placeholder="닉네임"
          name="writer"
          id="comment-writer"
        />
        <input
          type="password"
          placeholder="비밀번호"
          name="pw"
          id="comment-pw"
        />
        <textarea
          placeholder="댓글 내용"
          name="content"
          id="comment-content"
        ></textarea>
        <button class="button" onclick="saveComment()">댓글 작성</button>
      </div>

      <!-- 댓글 -->
      <div class="comment-container">
        <div class="comment">
          <table class="comment-info" id="commentTable"></table>
        </div>

        <!-- 페이지네이션 -->
        <div class="pagination"></div>

        <!-- 리스트로 돌아가기 버튼 -->
        <button class="list-button" id="list-button">리스트로 돌아가기</button>
      </div>
    </div>

    <script type="text/javascript">
                   // ======= 게시글 ========
                 let editButton = document.getElementById("editButton");
                 let deleteButton = document.getElementById("deleteButton");
                 let cancelButtonDoc = document.getElementById("cancelButton");
                 let sendButtonDoc = document.getElementById("sendButton");
                 let delPwInputDoc = document.getElementById("del_pw");
                 let optionContainerDoc = document.getElementById("optionContainer");
                 let deleteContainerDoc = document.getElementById("deleteContainer");

                 editButton.addEventListener("click", ()=>{
                   let temp = window.location.pathname.split('/');
                   let seq = temp[temp.length-1];
                   let url = "./update/"+seq
                   window.location.href = url
                 })

                 deleteButton.addEventListener("click", ()=>{
                   optionContainerDoc.style.display = "none";
                   deleteContainerDoc.style.display = "flex";
                   deleteContainerDoc.style.justifySelf = "end";
                 })

                 cancelButtonDoc.addEventListener("click", ()=>{
                   optionContainerDoc.style.display = "flex";
                   deleteContainerDoc.style.display = "none";
                   optionContainerDoc.style.justifySelf = "end";
                 })

                 sendButtonDoc.addEventListener("click", ()=>{
                   let temp = window.location.pathname.split('/');
                   let seq = temp[temp.length-1];
                   $.ajax({
                     url: './'+seq,
                     method: "DELETE",
                     data: {pw: delPwInputDoc.value},
                     success: function (data) {
                   	  console.log("성공");
                       window.location.href="./"
                     },
                     error: function (err) {
                      alert("비밀번호가 틀렸습니다!!")
                       console.error(err);
                       console.log("에러");
                     }
                   });
                 })

                 // 게시글 좋아요 버튼 클릭 시
                 $("#like-button").click(function () {
                     var seq = <%=boardVO.getSeq()%>;
                     $.ajax({
                       url: "like/"+ seq,
                       method: "PUT",
                       success: function (data) {
                         // 성공 시 좋아요 수 증가
                         var likeCountElement = $("#like-button");
                         var currentCount = parseInt(likeCountElement.text().split("(")[1].split(")")[0]);
                         likeCountElement.text("좋아요 (" + (currentCount + 1) + ")");
                       },
                       error: function (err) {
                         console.log(err);
                       },
                     });
                   });

                // 게시글 싫어요 버튼 클릭 시
                   $("#hate-button").click(function () {
                     var seq = <%=boardVO.getSeq()%>;
                     $.ajax({
                       url: "hate/"+ seq,
                       method: "PUT",
                       success: function (data) {
                         // 성공 시 싫어요 수 증가
                         var hateCountElement = $("#hate-button");
                         var currentCount = parseInt(hateCountElement.text().split("(")[1].split(")")[0]);
                         hateCountElement.text("싫어요 (" + (currentCount + 1) + ")");
                       },
                       error: function (err) {
                         console.log(err);
                       },
                     });
                   });

                   // ========= 댓글 =========

                   // 댓글 작성 버튼 클릭 시 댓글 작성 창 토글
                   const addCommentButton = document.querySelector(".addComment");
                   const commentInputContainer = document.querySelector(".comment-input-container");

                   const pagination = document.querySelector(".pagination"); // 페이지네이션 div

                   // 페이지 로드 시 댓글 조회
                   window.onload = function(){
                     movePage(1);
                   };

                   // 댓글 등록 버튼 클릭 시
                   addCommentButton.addEventListener("click", () => {
                   	 if (commentInputContainer.style.display === "none") {
                   		    commentInputContainer.style.display = "block";
                   		  } else {
                   		    commentInputContainer.style.display = "none";
                   		  }
                   });

                   // 리스트로 버튼 클릭 시
                   $("#list-button").click(function () {
                     window.location.href = "./";
                   });

                   // 댓글 페이지네이션 로드
                   function movePage(page) {
                     $.ajax({
                       url: './comment/list/<%=boardVO.getSeq()%>?page='+page,
                       method: "GET",
                       success: function (data) {
                       	console.log(data);
                       	// 댓글 생성
                         	createComment(data);
                      		// 페이지네이션 버튼 생성
                         	createPagination(data);
                       },
                       error: function (err) {
                         console.error(err);
                         console.log("에러");
                       }
                     });
                   }

                   // 페이지네이션 생성
                   function createPagination(data){
                     pagination.innerHTML = ""; // 기존 페이지네이션 초기화
                     // 이전 페이지 버튼
                     if (data.paging.prevPageNo !== 0) {
                       const prevPageButton = createPageButton(data.paging.prevPageNo, "<");
                       pagination.appendChild(prevPageButton);
                     }
                     // 페이지 버튼
                     for (let i = data.paging.startPageNo; i <= data.paging.endPageNo; i++) {
                       const pageButton = createPageButton(i, i);
                       if (i === data.paging.pageNo)
                         pageButton.classList.add("active");
                       pagination.appendChild(pageButton);
                     }
                     // 다음 페이지 버튼
                     if (data.paging.nextPageNo !== 0) {
                       const nextPageButton = createPageButton(data.paging.nextPageNo, ">");
                       pagination.appendChild(nextPageButton);
                     }
                     // 맨 처음 페이지로 가는 버튼
                     const firstPageButton = createPageButton(data.paging.firstPageNo, "<<");
                     pagination.prepend(firstPageButton);
                     // 맨 마지막 페이지로 가는 버튼
                     const finalPageButton = createPageButton(data.paging.finalPageNo, ">>");
                     pagination.appendChild(finalPageButton);
                   }

                   // 페이지 버튼 생성 함수
                   function createPageButton(page, text) {
                     const button = document.createElement("button");
                     button.classList.add("page-link");
                     button.textContent = text;
                     button.addEventListener("click", () => movePage(page));
                     return button;
                   }

             // 댓글 등록
             function saveComment(){
               //입력된 댓글 값
               let writer = document.getElementById("comment-writer");
               let pw = document.getElementById("comment-pw");
               let content = document.getElementById("comment-content");
               $.ajax({
                 url: '/board/comment/save',
                 method: "POST",
                 data: {
                   "writer":writer.value ,
                   "pw" : pw.value,
                   "content" :content.value ,
                   "board_seq" : '<%=boardVO.getSeq()%>'
                 },
                 success: function (data) {
                   // 입력된 댓글 값 초기화
                   writer.value = "";
                   pw.value="";
                   content.value="";
                   // 댓글 입력 창 숨기기
                   commentInputContainer.style.display = "none";
                   // 댓글 리랜더링
                   movePage(1);
                 },
                 error: function (err) {
                   console.log("에러");
                   console.error(err);
                 }
               });
             }

             // 댓글 생성
             function createComment(data){
               var seq = <%=boardVO.getSeq()%>;
               let commentsArray = data.comments;
               let commentSize = commentsArray.length;
               var comment = '';
               
               for(let i= 0 ; i < commentSize; i++){
             	  //comments 데이터
                 let cocommentDto = commentsArray[i].cocomments;
                 let writer = commentsArray[i].writer;
                 let publishDate = commentsArray[i].created_at;
                 const date = new Date(publishDate);
                 const formattedDate = date.toLocaleDateString();
                 let commentSeq =commentsArray[i].seq;
                 let content = commentsArray[i].content;
                 let like = commentsArray[i].like;
                 let hate = commentsArray[i].hate;
                 let updateComments = 1;
                 let deleteComments = 0;
           
         		// 댓글 생성
                 comment +=
                         '<tr>' +
                         '<td style="padding-left: 10px">' +
                         formattedDate + ' <br> ' + writer + '<br>' +
                         '</td>' + '<td style="padding-left: 30px">' + content +'</td>' +
                         '<td class="comment-buttons" id=comment-' +commentSeq +'>'+
                         	'<button class="button like-button" style="margin-left: 30px" id="like-button'+commentSeq+'" onclick="commentLike('+commentSeq+')">좋아요 <span id="like-count'+commentSeq+'">' + like + '</span></button>' +
                         	'<button class="button dislike-button" style="margin-left: 5px" id="hate-button'+commentSeq+'" onclick="commentHate('+commentSeq+')">싫어요 <span id="hate-count'+commentSeq+'">' + hate + '</span></button>' +
                         	'<button class="button addCocomment" onclick="createCocoment(' +commentSeq+ ')" style="margin-left: 5px">댓글 작성</button>' +
                         	'<div id="optionContainer-'+commentSeq+'">'+
         						'<button class="editButton-comment" onclick="updateClickOption(\'' + writer + '\', \'' + content + '\', ' + commentSeq + ')">수정</button>'+
         						'<button class="deleteButton-comment" onclick="deleteClickOption(\'' + writer + '\', \'' + content + '\', ' + commentSeq + ')">삭제</button>'+
         					'</div>'+
                         '</tr>';
         		    // 대댓글 생성
                 if(cocommentDto!=null){	  
                   let cocommentSize = cocommentDto.length;
                   for(let j= 0 ; j < cocommentSize; j++){
                 	//cocomment 해당 데이터
                     let writercoco = cocommentDto[j].writer;
                     let publishDatecoco = cocommentDto[j].created_at;
                     const date = new Date(publishDatecoco);
                     const formattedDates = date.toLocaleDateString();
                     let contentcoco = cocommentDto[j].content;
                     let likecoco = cocommentDto[j].like;
                     let hatecoco = cocommentDto[j].hate;
                     let cocommentSeq =cocommentDto[j].seq;
                     let updateCocomments = 1;
                     let deleteCocomments = 0;
                     
                  	// 대댓글 생성
                     comment +=
                     	'<tr>' +
                         '<td style="padding-left: 10px">' +
                         formattedDates + ' <br> ' + writercoco + '<br>' +
                         '</td>' + '<td style="padding-left: 30px">' + contentcoco +'</td>' +
                         '<td class="comment-buttons" id=comment-' +cocommentSeq +'>'+
                         	'<button class="button like-button" style="margin-left: 30px" id="like-button'+cocommentSeq+'" onclick="commentLike('+cocommentSeq+')">좋아요 <span id="like-count'+cocommentSeq+'">' + like + '</span></button>' +
                         	'<button class="button dislike-button" style="margin-left: 5px" id="hate-button'+cocommentSeq+'" onclick="commentHate('+cocommentSeq+')">싫어요 <span id="hate-count'+cocommentSeq+'">' + hate + '</span></button>' +
                         	'<button class="button none-button" ><span>없는댓글</span></button>' +
                        	
                         	'<div id="optionContainer-'+cocommentSeq+'">'+
         						'<button class="editButton-comment" onclick="updateClickOption(\'' + writercoco + '\', \'' + contentcoco + '\', ' + cocommentSeq + ')">수정</button>'+
         						'<button class="deleteButton-comment" onclick="deleteClickOption(' + cocommentSeq + ')">삭제</button>'+
         					'</div>'+
                         '</tr>';
                   }// for
                 }//if
               }// for
               document.getElementById("commentTable").innerHTML = comment;
             }

          // 대댓글 입력창 생성
             function createCocoment(commentSeq) {
               var parentDiv = document.getElementById("comment-"+commentSeq);
               var childDiv = document.createElement('div');
               childDiv.innerHTML = 
                 '<input type="text" placeholder="닉네임" name="writer" id="cocomment-writer" >'+
                 '<input type="password" placeholder="비밀번호" name="pw" id="cocomment-pw">'+
                 '<textarea placeholder="댓글 내용" name="content" id="cocomment-content"></textarea>'+
                 '<button class="button" onclick="saveCocomment('+commentSeq+')">댓글 작성</button>';
               parentDiv.appendChild(childDiv);
             }
          
             //대댓글 등록
             function saveCocomment(commentSeq){
               //입력된 댓글 값
               let writer = document.getElementById("cocomment-writer");
               let pw = document.getElementById("cocomment-pw");
               let content = document.getElementById("cocomment-content");
               console.log(commentSeq);
               $.ajax({
                 url: '/board/comment/save',
                 method: "POST",
                 data: {
                   "writer":writer.value ,
                   "pw" : pw.value,
                   "content" :content.value ,
                   "board_seq" : '<%=boardVO.getSeq()%>',
                   "comment_seq" : commentSeq
                 },
                 success: function (data) {
                   console.log("성공");
                   movePage(1);
                 },
                 error: function (err) {
                   console.error(err);
                   console.log("에러");
                 }
               });
             }
             
             // 댓글 좋아요 클릭 시
            	function commentLike(commentSeq){
                 $.ajax({
                   url: "/board/comment/like/"+ commentSeq,
                   method: "PUT",
                   success: function (data) {
                     // 성공 시 좋아요 수 증가
                     var likeCountElement = $("#like-count"+commentSeq);
                     var currentCount = parseInt(likeCountElement.text());
                     likeCountElement.text(currentCount + 1);
                   },
                   error: function (err) {
                     console.log(err);
                   },
                 });
             }
           	// 댓글 싫어요 클릭 시
               function commentHate(commentSeq){
                 $.ajax({
                   url: "./comment/hate/"+ commentSeq,
                   method: "PUT",
                   success: function (data) {
                 	// 성공 시 싫어요 수 증가
                       var hateCountElement = $("#hate-count"+commentSeq);
                       var currentCount = parseInt(hateCountElement.text());
                       hateCountElement.text(currentCount + 1);
                   },
                   error: function (err) {
                     console.log(err);
                   },
                 });
               }  
		
// 수정 버튼 클릭 시 비밀번호 입력 창 생성
function updateClickOption(writer, content, commentSeq){
		    
		    let updateCheck = 1;
		    
		    console.log("updateClickOption : "+commentSeq);
		    console.log("writer : " + writer);
		    console.log("content : "+content);
		    
    var parentDiv = document.getElementById("comment-"+commentSeq);
    var childDiv = document.createElement('div');
    childDiv.innerHTML = 
    	'<div id="deleteContainer-'+commentSeq+'" style="display: none;">'+
		'pw: <input type="password" placeholder="Input password...." id="input-pw-'+commentSeq+'" />'+
		'<button class="sendButtonPw" onclick="chckeCommentPw(\'' + writer + '\', \'' + content + '\',\'' + updateCheck + '\', ' + commentSeq + ')">전송</button>'+
		'<button class="cancelButtonPw" onclick="optionCancle('+commentSeq+')">취소</button>'+
	'</div>'+
'</td>';
    parentDiv.appendChild(childDiv);
		    
    
			let optionContainerComm = document.getElementById("optionContainer-"+commentSeq);	// 수정 및 삭제 버튼
    		let deleteContainerComm = document.getElementById("deleteContainer-"+commentSeq);	// 비밀번호 입력창
		    

		    optionContainerComm.style.display = "none";
		    deleteContainerComm.style.display = "flex";
		    deleteContainerComm.style.justifySelf = "end";
		}
	
// 삭제 버튼 클릭 시 비밀번호 입력 창 생성
function deleteClickOption(writer, content, commentSeq){
		    
		    let deleteCheck = 0;
		    
		    console.log("updateClickOption : "+commentSeq);

    var parentDiv = document.getElementById("comment-"+commentSeq);
    var childDiv = document.createElement('div');
    childDiv.innerHTML = 
    	'<div id="deleteContainer-'+commentSeq+'" style="display: none;">'+
		'pw: <input type="password" placeholder="Input password...." id="input-pw-'+commentSeq+'" />'+
		'<button class="sendButtonPw" onclick="chckeCommentPw(\'' + writer + '\', \'' + content + '\',\'' + deleteCheck + '\', ' + commentSeq + ')">전송</button>'+
		'<button class="cancelButtonPw" onclick="optionCancle('+commentSeq+')">취소</button>'+
	'</div>'+
'</td>';
    parentDiv.appendChild(childDiv);
		    
    
			let optionContainerComm = document.getElementById("optionContainer-"+commentSeq);	// 수정 및 삭제 버튼
    		let deleteContainerComm = document.getElementById("deleteContainer-"+commentSeq);	// 비밀번호 입력창
		    

		    optionContainerComm.style.display = "none";
		    deleteContainerComm.style.display = "flex";
		    deleteContainerComm.style.justifySelf = "end";
		}
	
	
		// 취소 버튼 클릭 시
function optionCancle(commentSeq){
			let optionContainerComm = document.getElementById("optionContainer-"+commentSeq);	// 수정 및 삭제 버튼
		    let deleteContainerComm = document.getElementById("deleteContainer-"+commentSeq);	// 비밀번호 입력창

		    optionContainerComm.style.display = "flex";
		    deleteContainerComm.style.display = "none";
		    optionContainerComm.style.justifySelf = "end";
		}
		
  	// 댓글 작성자 비밀번호 확인
  	function chckeCommentPw(writer, content, type, commentSeq){
  		console.log("writer : "+ writer +"content : "+content +"commentSeq : "+commentSeq + " type : " + type);
  		
  		let inputPw = document.getElementById("input-pw-"+commentSeq).value;	// 입력된 비밀번호	
  		console.log("inputPW : " + inputPw);
  		
  		
  		$.ajax({
            url: "/board/comment/checkPw",
            method: "POST",
            data: {
            	"seq": commentSeq,
            	"pw": inputPw,
            },
            success: function (data) {	
            	
            	let commentPw = data;
            	let commentCheck = 0;
            	console.log("commentPw : "+commentPw);
            	console.log("type : " +type);
            	
            	if(commentPw === 1){
            		if(type == 0){
            			alert("비밀번호가 일치합니다.");
             	 		deleteComment(commentSeq);
            		}else if(type == 1){
            			alert("비밀번호가 일치합니다.");
            			updateCommentView(writer, content, commentSeq);
            		}
            		
            	else{
            		alert("비밀번호가 일치하지 않습니다.");
            	}
            	}
            },
            error: function (err) {	
              console.log(err);
            },
          });
  	}
  	
  	// 댓글 삭제 요청
  	function deleteComment(commentSeq){
  		$.ajax({
            url: "/board/comment/delete/"+commentSeq,
            method: "POST",
            success: function (data) {
          		movePage(1);
            },
            error: function (err) {
              console.log(err);
            },
          });
  	}
  	
    //댓글 수정 뷰
    function updateCommentView(writer, content, commentSeq){
 
    	
        var parentDiv = document.getElementById("comment-"+commentSeq);
        var childDiv = document.createElement('div');
        childDiv.innerHTML = 
          '<input type="text" placeholder="'+ writer +'" name="writer" id="update-comment-writer" >'+
          '<input type="password" placeholder="비밀번호" name="pw" id="update-comment-pw">'+
          '<textarea placeholder="' + content + '" name="content" id="update-comment-content"></textarea>'+
          '<button class="button" onclick="updateComment('+commentSeq+')">댓글수정</button>';
        parentDiv.appendChild(childDiv);  	

    }
    
    //댓글 수정
    function updateComment(commentSeq){
    	
        let updateWriter = document.getElementById("update-comment-writer");
        let updatePw = document.getElementById("update-comment-pw");
        let updateContent = document.getElementById("update-comment-content");
        
        console.log("updateWriter : "+updateWriter.value);
        console.log("updatePw : "+updatePw.value);
        console.log("updateContent : "+updateContent.value);
        console.log("commentSeq : "+commentSeq);
        
        $.ajax({
          url: '/board/comment/update',
          method: "POST",
          data: {
            "writer":updateWriter.value ,
            "pw" : updatePw.value,
            "content" :updateContent.value ,
            "seq" : commentSeq
          },
          success: function (data) {
     		console.log(data);
            movePage(1);
          },
          error: function (err) {
            console.log("에러");
            console.error(err);
          }
        });
      }
     	
  	
  	
  	
  </script>
</body>

</html>
