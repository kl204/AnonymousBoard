package com.oneJo.board.model.vo;

import java.sql.Timestamp;

public class BoardVO {
	
	private int seq;
	private String title;
	private String content;
	private String writer;
	private String pw;
	private Timestamp created_at;
	private Timestamp updated_at;
	private int like_count;
	private int hate_count;
	private String category;
	private int views;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getHate_count() {
		return hate_count;
	}
	public void setHate_count(int hate_count) {
		this.hate_count = hate_count;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	@Override
	public String toString() {
		return "BoardVO [seq=" + seq + ", title=" + title + ", content=" + content + ", writer=" + writer + ", pw=" + pw
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + ", like_count=" + like_count
				+ ", hate_count=" + hate_count + ", category=" + category + ", views=" + views + "]";
	}
	
	
}
