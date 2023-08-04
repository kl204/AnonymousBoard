package com.oneJo.board.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oneJo.board.model.dto.CommentDTO;
import com.oneJo.board.model.vo.BoardVO;
import com.oneJo.board.model.vo.CommentVO;
import com.oneJo.board.utils.ConnectionManager;

@Repository
public class CommentDAO {
	
	private ConnectionManager cm = ConnectionManager.getInstance();

	// �빐�떦 湲��뿉 ���븳 �뙎湲� �닔 議고쉶
	public int selectAllCommentNum(int seq) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = cm.getConnection();
			String sql = "select count(*) as total from comment where board_seq = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, seq);
			rs = ps.executeQuery();

			while (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	// �뙎湲� �옉�꽦
	public boolean insertComment(CommentDTO commentVO) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			String sql = "insert into comment(writer,pw, content, board_seq) values(?, ?, ?, ?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, commentVO.getWriter());
			ps.setString(2, commentVO.getPw());
			ps.setString(3, commentVO.getContent());
			ps.setInt(4, commentVO.getBoard_seq());
			
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs > 0;
	}
	
	public boolean insertCocomment(CommentDTO commentVO) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			String sql = "insert into comment(writer,pw, content, board_seq, comment_seq) values(?, ?, ?, ?,?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, commentVO.getWriter());
			ps.setString(2, commentVO.getPw());
			ps.setString(3, commentVO.getContent());
			ps.setInt(4, commentVO.getBoard_seq());
			ps.setInt(5, commentVO.getComment_seq());
			
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs > 0;
	}

	// �뙎湲� �궘�젣
	public boolean deleteComment(int seq) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			String sql = "delete from comment where seq = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, seq);

			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs > 0;
	}

	// �뙎湲� 醫뗭븘�슂 異붽�
	public boolean addLike(int seq) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			String sql = "update comment set like_count = like_count + 1 where seq = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, seq);

			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs > 0;
	}

	// �뙎湲� �떕�뼱�슂 異붽�
	public boolean addHate(int seq) {
//		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			String sql = "update comment set hate_count = hate_count + 1 where seq = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, seq);

			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs > 0;
	}
	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� �닔 - ���뙎湲� �룷�븿
		public int countAllByBoard(int boardSeq) {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int count = 0;
			try {
				conn = cm.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT count(*) FROM comment  WHERE board_seq = ?");
				String sql = sb.toString();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, boardSeq);	// 寃뚯떆湲� �떆���뒪 踰덊샇
				rs = ps.executeQuery();
				while (rs.next()) {
					count = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return count;
		}
		
	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� �닔 - ���뙎湲� �젣�쇅
	public int countCommentByBoard(int boardSeq) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT count(*) FROM comment  WHERE comment_seq IS NULL AND board_seq = ?");
			String sql = sb.toString();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardSeq);	// 寃뚯떆湲� �떆���뒪 踰덊샇
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	// 寃뚯떆湲��쓽 �쟾泥� �뙎湲� 議고쉶
	public ArrayList<CommentDTO> selectAllByBoardSeq(int boardSeq,int startSeq, int pagingSize ) {   
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			
			// �뙎湲� 湲곗� �럹�씠吏� 議고쉶
			sb.append("SELECT * FROM comment  WHERE comment_seq IS NULL AND board_seq = ? ORDER BY created_at DESC LIMIT ?,?");
			String sql = sb.toString();

			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardSeq);	// 寃뚯떆湲� �떆���뒪 踰덊샇
			ps.setInt(2, startSeq);	// �럹�씠吏� �떆�옉 �닚�꽌
			ps.setInt(3, pagingSize);	// �븳 �럹�씠吏��떦 媛��졇�삱 媛��닔
			rs = ps.executeQuery();
			while (rs.next()) {
				CommentDTO comment = new CommentDTO();
				comment.setSeq(rs.getInt("seq"));
				comment.setWriter(rs.getString("writer"));
				comment.setPw(rs.getString("pw"));
				comment.setContent(rs.getString("content"));
				comment.setLike(rs.getInt("like_count"));
				comment.setHate(rs.getInt("hate_count"));
				comment.setCreated_at(rs.getTimestamp("created_at"));
				comment.setUpdated_at(rs.getTimestamp("updated_at"));
				comment.setBoard_seq(rs.getInt("board_seq"));
				comment.setComment_seq(rs.getInt("comment_seq"));
				list.add(comment);
			}
			// �뙎湲��뿉 �빐�떦�븯�뒗 ���뙎湲� 議고쉶 
			for(CommentDTO comment : list) {
				ArrayList<CommentDTO> cocomments = new ArrayList<CommentDTO>();
				String sub_sql = "SELECT * FROM comment  WHERE comment_seq = ?" ;
				ps = conn.prepareStatement(sub_sql);
				ps.setInt(1, comment.getSeq());	
				rs = ps.executeQuery();
				while (rs.next()) {
					CommentDTO cocomment = new CommentDTO();
					cocomment.setSeq(rs.getInt("seq"));
					cocomment.setWriter(rs.getString("writer"));
					cocomment.setPw(rs.getString("pw"));
					cocomment.setContent(rs.getString("content"));
					cocomment.setLike(rs.getInt("like_count"));
					cocomment.setHate(rs.getInt("hate_count"));
					cocomment.setCreated_at(rs.getTimestamp("created_at"));
					cocomment.setUpdated_at(rs.getTimestamp("updated_at"));
					cocomment.setBoard_seq(rs.getInt("board_seq"));
					cocomment.setComment_seq(rs.getInt("comment_seq"));
					cocomments.add(cocomment);
					
					
				}
				// ���뙎湲� 紐⑸줉 異붽�
				if(cocomments.size()>0)comment.setCocomments(cocomments);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	// �뙎湲� �닔�젙
	public boolean updateComment(CommentDTO comment) {
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update comment set content = ? , writer = ?, updated_at = NOW() where seq = ?");
			String sql = sb.toString();

			ps = conn.prepareStatement(sql);
			ps.setString(1, comment.getContent());
			ps.setString(2, comment.getWriter());
			ps.setInt(3, comment.getSeq());

			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rs > 0) {
			return true;
		}
		return false;
	}

	// �뙎湲� 鍮꾨�踰덊샇 �씪移� �뿬遺� �솗�씤
	public boolean checkPw(CommentDTO dto) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pw ="";
		try {
			conn = cm.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT pw FROM comment WHERE seq=?");
			String sql = sb.toString(); 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto.getSeq());
			rs = ps.executeQuery();
			while(rs.next()) {
				pw = rs.getString(1);
				System.out.println("pw ====> "+pw);
			}
			if(pw.equals(dto.getPw())) {// �씪移� �떆 true 諛섑솚
				return true;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean increase(int seq, String kind) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		int updateResult = 0;
		try {
			conn = cm.getConnection();
			
			StringBuilder updateSb = new StringBuilder();
			updateSb.append("update comment set ");
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
}
