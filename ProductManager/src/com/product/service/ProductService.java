package com.product.service;

import static com.product.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.product.model.dao.ProductDao;
import com.product.model.vo.Product;

public class ProductService {

	public int inputProduct(Product p) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().inputProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}
	
	public ArrayList<Product> selectProduct() {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectProduct(conn);
		
		return list;
	}

	public ArrayList<Product> selectByPname(String pName) {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectByPname(conn, pName);
		
		return list;
	}

	public int updateProduct(Product p) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().updateProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}

	public int deleteProduct(String pName) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().deleteProduct(conn, pName);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}

	public ArrayList<Product> selectSsgProduct() {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectSsgProduct(conn);
		
		return list;
	}

	public ArrayList<Product> selectPriceProduct(int minPrice, int maxPrice) {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectPriceProduct(conn, minPrice, maxPrice);
		
		return list;
	}

}
