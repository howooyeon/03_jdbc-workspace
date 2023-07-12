package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {

	/**
	 * 상품 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Product> list = new ProductService().selectList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회된 결과가 없습니다.");
		} else {
			new ProductMenu().displayMemberList(list);
		}
		
	}

}
