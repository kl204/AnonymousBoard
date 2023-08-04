package com.oneJo.board.model.vo;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CommentVO {
	private int seq;
	private String title;
	private String writer;
	private String pw;
	private String content;
	private Timestamp created_at;
	private Timestamp updated_at;
	private int like;
	private int hate;
	private int board_seq;
	private int comment_seq;
	private ArrayList<CommentVO> cocomments;

	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getHate() {
		return hate;
	}
	public void setHate(int hate) {
		this.hate = hate;
	}
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public int getComment_seq() {
		return comment_seq;
	}
	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
	}
	@Override
	public String toString() {
		return "CommentVO [seq=" + seq + ", title=" + title + ", writer=" + writer + ", pw=" + pw + ", content="
				+ content + ", created_at=" + created_at + ", updated_at=" + updated_at + ", like=" + like + ", hate="
				+ hate + ", board_seq=" + board_seq + ", comment_seq=" + comment_seq + "]";
	}


}
