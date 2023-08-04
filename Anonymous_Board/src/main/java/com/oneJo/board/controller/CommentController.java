package com.oneJo.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oneJo.board.model.dto.CommentDTO;
import com.oneJo.board.model.dto.Paging;
import com.oneJo.board.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private CommentService commentService;
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	public CommentController(CommentService commentService){
		this.commentService = commentService;
	}
	
    // 게시글의 댓글 목록 조회 - 페이징 처리 
	@RequestMapping(value="/list/{boardSeq}", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> selectAllByBoardSeq(@PathVariable(value = "boardSeq") int boardSeq,
			@RequestParam(value = "page", required = false,defaultValue = "1") int page){	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		 
		// 페이징 처리
		Paging paging = new Paging();
		paging.setPageSize(5);	// 한 페이지당 보여줄 댓글 수 
		paging.setPageNo(page);	// 현재 요청 페이지 
		paging.setTotalCount(commentService.countCommentByBoard(boardSeq));// 해당 게시물의 댓글 총갯수(대댓글 제외)
		
		ArrayList<CommentDTO> comments =  commentService.selectAllByBoardSeq(boardSeq,paging);
		resultMap.put("paging", paging);
		resultMap.put("comments", comments);
		return resultMap;
	}
	
	//댓글 수정
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateComment(@ModelAttribute CommentDTO commentDTO) {
		boolean result = commentService.updateComment(commentDTO);
		return result;	// 수정 결과 반환
	}
	
	// 댓글 비밀번호 일치 여부
	@RequestMapping(value="/checkPw", method = RequestMethod.POST)
	@ResponseBody
	public int checkPw(@ModelAttribute CommentDTO commentDTO) {
		boolean result = commentService.checkPw(commentDTO);
		System.out.println(result);
		return result?1:0;
	}
	
	// 댓글 좋아요 추가
	@RequestMapping(value = "/like/{commentSeq}", method = RequestMethod.PUT)
	@ResponseBody
	public void likeBoard(@PathVariable("commentSeq") int seq) {
		Boolean result = commentService.likeIncreace(seq);
	}
	
	// 댓글 싫어요 추가
	@RequestMapping(value = "/hate/{commentSeq}", method = RequestMethod.PUT)
	@ResponseBody
	public void hateBoard(@PathVariable("commentSeq") int seq) {
		Boolean result = commentService.hateIncreace(seq);
	}
	
	// 댓글 등록
	@RequestMapping(value= "/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveComment(@ModelAttribute CommentDTO comment) {
		boolean result = commentService.addComment(comment);
	}
	
	// 댓글 삭제
	@RequestMapping(value= "/delete/{commentSeq}", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteComment(@PathVariable int commentSeq) {
		boolean result = commentService.deleteComment(commentSeq);
		return result;
	}
}
