package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.kh.model.vo.Phone;

public class PhoneDao {
	
	public int insertPhone(Connection conn, Phone p) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO PHONEBOOK VALUES (SEQ_USER_NO.NEXTVAL, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getUserName());
			pstmt.setInt(2, p.getAge());
			pstmt.setString(3, p.getAddress());
			pstmt.setString(4, p.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
		
	}
	
	public ArrayList<Phone> selectList(Connection conn) {
		
		ArrayList<Phone> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM PHONEBOOK";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Phone(rset.getInt("user_no"), 
										rset.getString("username"), 
										rset.getInt("age"), 
										rset.getString("address"), 
										rset.getString("phone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}

}
