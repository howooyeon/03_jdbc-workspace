package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

// Dao(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과받기(JDBC)
// 							 결과를 Controller로 다시 리턴
public class MemberDao {
	
	
	/*
	 * 기존의 방식 : DAO 클래스가 사용자가 요청할 때마다 실행해야되는 SQL문을 자바소스코드 내에 명시적으로 작성 => 정적코딩방식
	 * 
	 * > 문제점 : SQL문을 수정해야 될 경우 자바소스코드를 수정해야됨 => 수정된 내용을 반영시키고자 한다면 프로그램을 재구동 해야됨
	 * 
	 * > 해결방식 : SQL문들을 별도로 관리하는 외부파일(.xml)을 만들어서 실시간으로 그 파일에 기록된 sql문을 읽어들여서 실행 => 동적코딩방식
	 * 			 여러줄 쓸 수 있도록 => xml로 하는게 좋음!!
	 * 
	 * 
	 */
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 회원 추가하는 메소드
	 * @param m
	 * @return result : 처리된 행수
	 */
	
	public int insertMember(Connection conn, Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		// conn 이미 서비스에서 생성돼있음!
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");
		
		try {
			
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
		
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
			result = pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(pstmt);
			// conn은 아직 반납하면 안됨!! (트랜젝션 처리 서비스가서 해야함!)
			
		}
		return result;
	}
	
	public ArrayList<Member> selectList(Connection conn) {
		// select문(여러행) => ResultSet객체
		ArrayList<Member> list = new ArrayList<Member>(); // 텅빈 리스트
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
	
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		}
		return list;
	}

	public Member selectByUserId(Connection conn, String userId) {
		// select문 => 한행문 => ResultSet 객체 => Member 객체
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectByUserId");
		
		try {
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}

	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		Member m = null;
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectByUserName");
		// 위의 sql문을 제시한 후 pstmt.setString(1, "관");
		
		// 해결방법 1.
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'"; // 하나의 문자열 됨
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 해결방법1의 sql문 일경우
			//pstmt.setString(1, keyword);
			
			pstmt.setString(1, "%" + keyword + "%");

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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태로 둘 수 있음)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE)
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간확보 (?== 홀더)만 해두면됨!!
		String sql = prop.getProperty("updateMember");
		
		try {
			
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
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			
		}
		return result;
	}
	
//	public int deleteMember(Connection conn, String userId) {
//		int result = 0;
//		preparedStatement pstmt = null;
//		
//		String sql = prop.getProperty("deleteMember");
	
	
//	}
	
	
	
}