package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Product;

public class ProductDao {
private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> selectList(Connection conn) {
		// select문(여러행) => ResultSet객체
				ArrayList<Product> list = new ArrayList<Product>(); // 텅빈 리스트
				
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				
				String sql = prop.getProperty("selectList");
				
				try {
			
				pstmt = conn.prepareStatement(sql);
				
				rset = pstmt.executeQuery();
				while (rset.next()) {
					
					list.add(new Product(rset.getString("PRODUCT_ID"),
							rset.getString("P_NAME"),
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

}
