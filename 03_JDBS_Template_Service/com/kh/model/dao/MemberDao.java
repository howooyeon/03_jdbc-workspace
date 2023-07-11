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
public class MemberDao {
	/*
	 * * Statement와 PreparedStatement의 특징
	 * - 둘 다 sql문 실행하고 결과를 받아내는 객체(둘 중 하나 쓰면 됨)
	 * 
	 * - Statement PreparedStatement의 차이점
	 * - Statement 같은 경우 sql문을 바로 전달하면서 실행시키는 객체
	 * 	 (즉, sql문을 완성 형태로 만들어 둬야됨!! 사용자가 입력한 값이 다 채워진 형태로!)
	 * 		  > 기존의 Statement 방식
	 * 		 1) Connection 객체를 통해 Statement 객체 생성:			stmt = conn.createStatement();
	 *		 2) Statement 객체를 통해 "완성된 sql문" 실행 및 결과 받기		결과 = stmt.excuteXXX(완성된 SQL);
	 *		
	 * - preparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 *   (즉, 사용자가 입력한 값들을 채워두지 않고 각각 들어갈 공간을 확보만 미리 해놓아도됨!)
	 *   단, 해당 sql문이 본격적으로 실행하기 전에는 빈 공간을 사용자가 입력한 값으로 채워서 실행하긴 해야됨
	 *   
	 *   		> PreparedStatement 방식
	 *   		1) Connection 객체를 통해 PreparedStatement 객체 생성 : pstmt = conn.preparedStatement(미완성된 sql문);
	 *   		2) pstmt에 담긴 sql문이 미완성 상태일 경우 우선 완성시켜야됨. pstmt.setXXX(1, "대체할 값");
	 *   		3) 해당 완성된 sql문 실행 결과 받기 					: 결과 = pstmt.excuteXXX();
	 *   
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 회원 추가하는 메소드
	 * @param m
	 * @return result : 처리된 행수
	 */
	
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태로 둘 수 있음)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE)
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간확보 (?== 홀더)만 해두면됨!!
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC" );
			
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
		
		
			// > 빈공간을 실제값 (사용자가 입력한 값)으로 채워준 후 실행
			// pstmt.setString(홀더 순번, 대체할 값);		=> '대체할 값' (홑따옴표 감싸준 상태로 알아서 들어감)
			// pstmt.setInt(홀더 순번, 대체할 값);		=> 대체할 값
			
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
		
		// 실행할 sql문 (미완성된 형태로 둘 수 있음)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE)
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간확보 (?== 홀더)만 해두면됨!!
		String sql = "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC" );
			
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
		
			// > 빈공간을 실제값 (사용자가 입력한 값)으로 채워준 후 실행
			// pstmt.setString(홀더 순번, 대체할 값);		=> '대체할 값' (홑따옴표 감싸준 상태로 알아서 들어감)
			// pstmt.setInt(홀더 순번, 대체할 값);		=> 대체할 값
			
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