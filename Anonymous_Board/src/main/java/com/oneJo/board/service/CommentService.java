package com.oneJo.board.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneJo.board.model.dto.CommentDTO;
import com.oneJo.board.model.dto.Paging;
import com.oneJo.board.model.vo.CommentVO;
import com.oneJo.board.repository.CommentDAO;

@Service
public class CommentService {

	private CommentDAO commentDAO;
	
	@Autowired
	public CommentService(CommentDAO commentDAO){
		this.commentDAO = commentDAO;
	}
	
	// �뙎湲� 異붽�
	public boolean addComment(CommentDTO commentVO) {
		if(commentVO.getComment_seq() != 0)	// 대댓글 등록
			return commentDAO.insertCocomment(commentVO);
		return commentDAO.insertComment(commentVO);	//댓글 등록
	}
	
	// �뙎湲� �궘�젣
	public boolean deleteComment(int seq) {
		return commentDAO.deleteComment(seq);
	}
	
	// �뙎湲� 醫뗭븘�슂 異붽�
	public boolean addLike(int seq) {
		return commentDAO.addLike(seq);
	}
	
	// �뙎湲� �떕�뼱�슂 異붽�
	public boolean addHate(int seq) {
		return commentDAO.addHate(seq);
	}
	
	// �빐�떦 湲��뿉 ���븳 �뙎湲� �닔 議고쉶
	public int selectAllCommentNum(int seq) {
		return commentDAO.selectAllCommentNum(seq);
	}
	
	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� 議고쉶
	public ArrayList<CommentDTO> selectAllByBoardSeq(int boardSeq,Paging paging ){
		int start = (paging.getPageNo() - 1) * paging.getPageSize();
		return commentDAO.selectAllByBoardSeq(boardSeq, start, paging.getPageSize());
	}
	// �뙎湲� �닔�젙
	public boolean updateComment(CommentDTO dto) {
		return commentDAO.updateComment(dto);
	}
	// �뙎湲� 鍮꾨�踰덊샇 �씪移� �뿬遺� �솗�씤
	public boolean checkPw(CommentDTO dto) {
		return commentDAO.checkPw(dto);
	}

	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� �닔 - ���뙎湲� �룷�븿
	public int countAllByBoard(int boardSeq) {
		return commentDAO.countAllByBoard(boardSeq);
	}
	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� �닔 - ���뙎湲� �젣�쇅
	public int countCommentByBoard(int boardSeq) {
		return commentDAO.countCommentByBoard(boardSeq);
	}
	
	public boolean likeIncreace(int seq) {
		return commentDAO.increase(seq, "like_count");
	}
	
	public boolean hateIncreace(int seq) {
		return commentDAO.increase(seq, "hate_count");
	}
}
