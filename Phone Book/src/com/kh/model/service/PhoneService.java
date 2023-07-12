package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.PhoneDao;
import com.kh.model.vo.Phone;

public class PhoneService {

	public int insertPhone(Phone p) {
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		int result = new PhoneDao().insertPhone(conn, p); // pstmt 만들려면 conn 필요함
		
		// 6) 트랜젝션 처리
		if(result > 0) {
			/*JDBCTemplate.*/commit(conn);
		}else {
			/*JDBCTemplate.*/rollback(conn);
		}
		
		// 7) 다쓴자원 반납처리
		/*JDBCTemplate.*/close(conn);
		return result;
	}
	
	public ArrayList<Phone> selectList() {
		Connection conn = /*JDBCTemplate.*/getConnection();
		ArrayList<Phone> list = new PhoneDao().selectList(conn);
		
		close(conn);
		
		return list;
	}
	
	
}
	
	
