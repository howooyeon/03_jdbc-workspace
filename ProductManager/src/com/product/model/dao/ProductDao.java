package com.product.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static com.product.common.JDBCTemplate.*;
import com.product.model.vo.Product;
import com.product.view.ProductMenu;

public class ProductDao {//
	
	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int inputProduct(Connection conn, Product p) {

		int result = 0;
		
		PreparedStatement pstmt = null;

		String sql = prop.getProperty("inputProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getpName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setString(3, p.getNational());
			pstmt.setString(4, p.getBrand());
			pstmt.setString(5, p.getSsgAble());
			pstmt.setString(6, p.getCategory());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
		
	}

	public ArrayList<Product> selectProduct(Connection conn) {
		
		ArrayList<Product> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Product(rset.getInt("pno"),
						 			rset.getString("pname"),
						 			rset.getInt("price"),
						 			rset.getString("national"),
						 			rset.getString("brand"),
						 			rset.getString("ssg_able"),
						 			rset.getString("category"),
						 			rset.getDate("reg_date")
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

	public ArrayList<Product> selectByPname(Connection conn, String pName) {
		
		ArrayList<Product> list = new ArrayList<Product>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectByPname");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+pName+"%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Product(rset.getInt("pno"),
			 			rset.getString("pname"),
			 			rset.getInt("price"),
			 			rset.getString("national"),
			 			rset.getString("brand"),
			 			rset.getString("ssg_able"),
			 			rset.getString("category"),
			 			rset.getDate("reg_date")
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

	public int updateProduct(Connection conn, Product p) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE TB_PRODUCT SET PRICE = ?, SSG_ABLE = ? WHERE PNAME = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, p.getPrice());
			pstmt.setString(2, p.getSsgAble());
			pstmt.setString(3, p.getpName());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteProduct(Connection conn, String pName) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pName);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ArrayList<Product> selectSsgProduct(Connection conn) {
		
		ArrayList<Product> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM TB_PRODUCT WHERE ssg_Able='Y'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				
				list.add(new Product(rset.getInt("pno"),
			 			rset.getString("pname"),
			 			rset.getInt("price"),
			 			rset.getString("national"),
			 			rset.getString("brand"),
			 			rset.getString("ssg_able"),
			 			rset.getString("category"),
			 			rset.getDate("reg_date")
						));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<Product> selectPriceProduct(Connection conn, int minPrice, int maxPrice) {

		ArrayList<Product> list = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectPriceProduct");

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, minPrice);
			pstmt.setInt(2, maxPrice);
			
			rset = pstmt.executeQuery();

			while (rset.next()) {

				list.add(new Product(rset.getInt("pno"),
			 			rset.getString("pname"),
			 			rset.getInt("price"),
			 			rset.getString("national"),
			 			rset.getString("brand"),
			 			rset.getString("ssg_able"),
			 			rset.getString("category"),
			 			rset.getDate("reg_date")
						));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
