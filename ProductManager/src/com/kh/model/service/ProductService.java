package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;


public class ProductService {

	public ArrayList<Product> selectList() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Product> list = new ProductDao().selectList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
		
	}

}
