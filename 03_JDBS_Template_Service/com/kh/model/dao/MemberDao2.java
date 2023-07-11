package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// Dao(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과받기(JDBC)
// 							 결과를 Controller로 다시 리턴
public class MemberDao2 {
	/**
	 * 회원 추가하는 메소드
	 * @param m
	 * @return result : 처리된 행수
	 */
	
	public int insertMember(Member m) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC" );
			
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
		
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// 4,5) sql문 실행 및 결과받기
			result = pstmt.executeUpdate(); // 여기서는 sql문 전달하지 않고 그냥 실행해야됨!! 이미 pstmt에 sql들어가있음
			
			// 여기서 SQLException 날수도 있기 때무넹 뒤에 커밋과 롤백이 있으면 실행이 안됨!
			// 문제가 생기면 무조건 롤백해야함...
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		if(result > 0) {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} else {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();				
			}
		}
		finally {
			try {
				if(pstmt != null && !conn.isClosed()) {
					pstmt.close();
				}
				
				if(conn!= null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	/**
	 * 회원 전체 조회 메소드
	 * @return list
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행) => ResultSet객체
		ArrayList<Member> list = new ArrayList<Member>(); // 텅빈 리스트
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
	
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
				
		pstmt = conn.prepareStatement(sql);
		
		rset = pstmt.executeQuery();
		while (rset.next()) {
			
			list.add(new Member(rset.getInt("userno"),
					rset.getString("userid"),
					rset.getString("userpwd"),
					rset.getString("username"),
					rset.getString("gender"),
					rset.getInt("age"),
					rset.getString("email"),
					rset.getString("phone"),
					rset.getString("address"),
					rset.getString("hobby"),
					rset.getDate("enrolldate")
					));
		}	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}

	/**
	 * 회원 아이디를 받아 회원정보 검색해주는 메소드
	 * @param userId : 회원이 검색하고자 하는 아이디
	 * @return m : 그 아이디를 가진 회원
	 */
	public Member selectByUserId(String userId) {
		// select문 => 한행문 => ResultSet 객체 => Member 객체
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
					
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("userno"),
						rset.getString("userid"),
						rset.getString("userpwd"),
						rset.getString("username"),
						rset.getString("gender"),
						rset.getInt("age"),
						rset.getString("email"),
						rset.getString("phone"),
						rset.getString("address"),
						rset.getString("hobby"),
						rset.getDate("enrolldate"));
					
						
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}

	public Member selectByUserName(String keyword) {
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		// 위의 sql문을 제시한 후 pstmt.setString(1, "관");
		
		// 해결방법 1.
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'"; // 하나의 문자열 됨
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
					
			pstmt = conn.prepareStatement(sql);
			
			// 해결방법1의 sql문 일경우
			//pstmt.setString(1, keyword);
			
			pstmt.setString(1, "%" + keyword + "%");

			rset = pstmt.executeQuery();
			
			
			if(rset.next()) {
				m = new Member(rset.getInt("userno"),
						rset.getString("userid"),
						rset.getString("userpwd"),
						rset.getString("username"),
						rset.getString("gender"),
						rset.getInt("age"),
						rset.getString("email"),
						rset.getString("phone"),
						rset.getString("address"),
						rset.getString("hobby"),
						rset.getDate("enrolldate"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}

	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC" );
			
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
		
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			// 4,5) sql문 실행 및 결과받기
			result = pstmt.executeUpdate(); // 여기서는 sql문 전달하지 않고 그냥 실행해야됨!! 이미 pstmt에 sql들어가있음
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
}