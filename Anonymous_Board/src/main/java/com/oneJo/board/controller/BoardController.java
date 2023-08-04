package com.oneJo.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oneJo.board.model.dto.Paging;
import com.oneJo.board.model.vo.BoardVO;
import com.oneJo.board.service.BoardService;

@Controller
public class BoardController {

	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insertBoard(@RequestBody BoardVO boardVO, HttpServletResponse response) {
		Boolean result = boardService.createBoard(boardVO);
		System.out.println("result: " + result);

		if (result) {
			response.setStatus(HttpServletResponse.SC_OK); // 성공 상태 설정
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패 상태 설정
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void updateBoard(@RequestBody BoardVO boardVO, HttpServletResponse response) {
		System.out.println("update: ");
		Boolean result = boardService.updateBoard(boardVO);
		System.out.println("result: " + result);
		
		if (result) {
			response.setStatus(HttpServletResponse.SC_OK); // 성공 상태 설정
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패 상태 설정
		}
	}

	@RequestMapping(value = "/update/{seq}", method = RequestMethod.GET)
	public ModelAndView updateView(@PathVariable("seq") int seq) {
		ModelAndView mav = new ModelAndView("board/update");
		BoardVO boardVO = boardService.searchBoard(seq);
		mav.addObject(boardVO);
		
		return mav;
	}

	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public void deleteBoard(@PathVariable("seq") int seq, HttpServletResponse response, @RequestBody String pw) {
	
		System.out.println("delete: " + seq + "   pw: " + pw);
		String pwArr = pw.split("=")[1];
		System.out.println(pwArr);
		
		Boolean result = boardService.deleteBoard(seq, pwArr);
		System.out.println("result: " + result);

		if (result) {
			response.setStatus(HttpServletResponse.SC_OK); // 성공 상태 설정
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패 상태 설정
		}
	}

	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public ModelAndView searchBoard(@PathVariable("seq") int seq, HttpServletResponse response) throws IOException {
	    BoardVO boardVO = boardService.searchBoard(seq);
	    
	    ModelAndView mav = new ModelAndView("/board/detail");
		mav.addObject("boardVO", boardVO);
		return mav;
	    

	}
	


	@RequestMapping(value = "/like/{seq}", method = RequestMethod.PUT)
	public void likeBoard(@PathVariable("seq") int seq, HttpServletResponse response) {
		Boolean result = boardService.likeIncreace(seq);
		System.out.println("result: " + result);

		if (result) {
			response.setStatus(HttpServletResponse.SC_OK); // 성공 상태 설정
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패 상태 설정
		}
	}

	@RequestMapping(value = "/hate/{seq}", method = RequestMethod.PUT)
	public void hateBoard(@PathVariable("seq") int seq, HttpServletResponse response) {
		Boolean result = boardService.hateIncreace(seq);
		System.out.println("result: " + result);

		if (result) {
			response.setStatus(HttpServletResponse.SC_OK); // 성공 상태 설정
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패 상태 설정
		}
	}

	// 게시판 리스트
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public ModelAndView listView(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sortType", required = false, defaultValue = "latest") String sortType,
			@RequestParam(value = "category", required = false, defaultValue = "all") String category,
			@RequestParam(value = "search", required = false, defaultValue = "") String search) {
		ModelAndView mav = new ModelAndView();
		
		//페이징 처리
		Paging paging = new Paging();
		paging.setPageSize(5);
		paging.setPageNo(page);
		paging.setTotalCount(boardService.boardCount());
		
		ArrayList<BoardVO> list = boardService.selectAllBoard(sortType, category, search, paging);
		mav.addObject("paging", paging);
		mav.addObject("list", list);
		mav.setViewName("/board/home");
		return mav;
	}

	// 게시판 글 작성 뷰
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createView() {
		ModelAndView mav = new ModelAndView("board/write");

		return mav;
	}

	// 게시판 상세페이지 뷰
	@RequestMapping(value="/detail", method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public ModelAndView detailView() {
		ModelAndView mav = new ModelAndView("board/detail");

		return mav;
	}

	// 게시판 글 작성 로직(POST)

}
