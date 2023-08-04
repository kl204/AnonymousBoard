package com.oneJo.board.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.oneJo.board.model.dto.CommentDTO;
import com.oneJo.board.model.vo.BoardVO;
import com.oneJo.board.utils.ConnectionManager;


@Repository
public class BoardDAO {
	
	public BoardDAO() {
	
	}
	
	public ArrayList<BoardVO> selectAll(String sql, int startSeq, int pagingSize) {
		  ConnectionManager cm = ConnectionManager.getInstance();
		    ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		   
		    Connection conn = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
	    try {
	        conn = cm.getConnection();
	        ps = conn.prepareStatement(sql);
	        
	        ps.setInt(1, startSeq); //페이징 시작 순서
	        ps.setInt(2, pagingSize); // 페이지당 가져올 갯수
	        
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            BoardVO boardVO = new BoardVO();
	            boardVO.setSeq(rs.getInt(1));
	            boardVO.setTitle(rs.getString(2));
	            boardVO.setContent(rs.getString(3));
	            boardVO.setWriter(rs.getString(4));
	            boardVO.setPw(rs.getString(5));
	            boardVO.setCreated_at(rs.getTimestamp(6));
	            boardVO.setUpdated_at(rs.getTimestamp(7));
	            boardVO.setLike_count(rs.getInt(8));
	            boardVO.setHate_count(rs.getInt(9));
	            boardVO.setCategory(rs.getString(10));
	            boardVO.setViews(rs.getInt(11));
	            list.add(boardVO);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return list;
	}

	//페이징total구하기
	public int boardCount() {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select count(*) as seq from board;";
		
		try {
			conn = cm.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("seq");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return count;
	}
	
	public boolean insert(BoardVO boardVO) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("insert into board (title, content, writer, pw, category) ");
			sb.append("values(?, ?, ?, ?, ?);");
			String insertSQL = sb.toString(); 
			
			ps = conn.prepareStatement(insertSQL);
			ps.setString(1, boardVO.getTitle());
			ps.setString(2, boardVO.getContent());
			ps.setString(3, boardVO.getWriter());
			ps.setString(4, boardVO.getPw());
			ps.setString(5, boardVO.getCategory());
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs > 0) {
			return true;
		}
		return false;
	}
	
	public boolean update(BoardVO boardVO) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update board set title=?, content=?, updated_at=CURRENT_TIMESTAMP, category=? where seq=?");
			String updateSQL = sb.toString(); 
			ps = conn.prepareStatement(updateSQL);
			ps.setString(1, boardVO.getTitle());
			ps.setString(2, boardVO.getContent());
			ps.setString(3, boardVO.getCategory());
			ps.setInt(4, boardVO.getSeq());
			
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs > 0) {
			return true;
		}
		return false;
	}
	
	public boolean delete(int seq) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from board where seq=?;");
			String deleteSQL = sb.toString(); 
			
			ps = conn.prepareStatement(deleteSQL);
			ps.setInt(1, seq);
			
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs > 0) {
			return true;
		}
		return false;
	}
	
	public BoardVO selectById(int seq) {
		
		System.out.print("seq : ");
		System.out.println(seq);
		
		
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BoardVO tempVO = new BoardVO();
		try {
			conn = cm.getConnection();
			System.out.println(conn);
			conn.setAutoCommit(false);
			
			StringBuilder updateSb = new StringBuilder();
			updateSb.append("update board set views=views+1 where seq=?;");
			String updateSQL = updateSb.toString();
			
			ps = conn.prepareStatement(updateSQL);
			ps.setInt(1, seq);
			
			int updateResult = ps.executeUpdate();
			
			System.out.print("updateResult : ");
			System.out.println(updateResult);
			
			
			if(updateResult < 1) {
				conn.rollback();
				return null;
			}
			
			StringBuilder selectSb = new StringBuilder();
			selectSb.append("select * from board where seq=?;");
			
			String selectSQL = selectSb.toString();
			
			ps = conn.prepareStatement(selectSQL);
			ps.setInt(1, seq);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				tempVO.setSeq(rs.getInt("seq"));
				tempVO.setTitle(rs.getString("title"));
				tempVO.setContent(rs.getString("content"));
				tempVO.setWriter(rs.getString("writer"));
				tempVO.setPw(rs.getString("pw"));
				tempVO.setCreated_at(rs.getTimestamp("created_at"));
				tempVO.setUpdated_at(rs.getTimestamp("updated_at"));
				tempVO.setLike_count(rs.getInt("like_count"));
				tempVO.setHate_count(rs.getInt("hate_count"));
				tempVO.setCategory(rs.getString("category"));
				tempVO.setViews(rs.getInt("views"));
				
				System.out.print("seq 1 : ");
				System.out.println(tempVO);
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tempVO;
	}
	
	public boolean increase(int seq, String kind) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int updateResult = 0;
		try {
			conn = cm.getConnection();
			
			StringBuilder updateSb = new StringBuilder();
			updateSb.append("update board set ");
			updateSb.append(kind).append("=").append(kind).append("+1");
			updateSb.append(" where seq=?;");
			String updateSQL = updateSb.toString();
			
			ps = conn.prepareStatement(updateSQL);
			ps.setInt(1, seq);
			
			updateResult = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(updateResult > 0) {
			return true;
		}
		return false;
	}
	
	public boolean checkPw(int seq, String myPw) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pw ="";
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT pw FROM board WHERE seq=?");
			String sql = sb.toString(); 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, seq);
			rs = ps.executeQuery();
			while(rs.next()) {
				pw = rs.getString(1);
			}
			if(pw.equals(myPw)) {
				return true;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
