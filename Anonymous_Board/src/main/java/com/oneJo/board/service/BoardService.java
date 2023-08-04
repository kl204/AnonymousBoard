package com.oneJo.board.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneJo.board.model.dto.Paging;
import com.oneJo.board.model.vo.BoardVO;
import com.oneJo.board.repository.BoardDAO;

@Service
public class BoardService {

	private BoardDAO boardDAO;

	@Autowired
	public BoardService(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	public ArrayList<BoardVO> selectAllBoard(String sortType, String category, String search, Paging paging) {
		
		// 쿼리 작성을 위한 StringBuilder 초기화
		StringBuilder sb = new StringBuilder("SELECT * FROM board");

		// category 또는 search가 비어 있지 않은 경우 조건 추가
		if (!category.equals("all") || !search.isEmpty()) {
			sb.append(" WHERE");

			// category에 따른 카테고리 조건 추가
			if (!category.equals("all")) {
				sb.append(" category = '").append(category).append("'");
			}

			// search에 따른 검색어 조건 추가
			if (!search.isEmpty()) {
				if (!category.equals("all")) {
					sb.append(" AND");
				}
				sb.append(" title LIKE '%").append(search).append("%'");
			}
		}
		sb.append(" ORDER BY CASE WHEN category ='공지' THEN 0 ELSE 1 END,");

		// sortType에 따른 정렬 조건 추가
		if (sortType.equals("latest")) {
			sb.append(" created_at DESC limit ?, ?");
		} else if (sortType.equals("views")) {
			sb.append(" views DESC limit ?, ?");
		} else if (sortType.equals("likes")) {
			sb.append(" like_count DESC limit ?, ?");
		}

		String sql = sb.toString();

		System.out.println("sql" + sql);

		int start = (paging.getPageNo() - 1) * paging.getPageSize();
		ArrayList<BoardVO> list = null;
		list = boardDAO.selectAll(sql, start, paging.getPageSize());
		return list;
	}
	
	public int boardCount() {
		int total = 0;
		total = boardDAO.boardCount();
		return total;
	}

	public boolean createBoard(BoardVO boardVO) {
		return boardDAO.insert(boardVO);
	}

	public boolean updateBoard(BoardVO boardVO) {
		Boolean pwResult = boardDAO.checkPw(boardVO.getSeq(), boardVO.getPw());
		if(!pwResult) {
			System.out.println("비번이 다름 ");
			return false;
		}
		return boardDAO.update(boardVO);
	}

	public boolean deleteBoard(int seq, String myPw) {
		Boolean pwResult = boardDAO.checkPw(seq, myPw);
		if(!pwResult) {
			return false;
		}
		return boardDAO.delete(seq);
	}

	public BoardVO searchBoard(int seq) {
		return boardDAO.selectById(seq);
	}

	public boolean likeIncreace(int seq) {
		return boardDAO.increase(seq, "like_count");
	}

	public boolean hateIncreace(int seq) {
		return boardDAO.increase(seq, "hate_count");
	}
}
