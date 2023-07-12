package com.kh.view;

import java.util.Scanner;

import com.kh.controller.ProductController;

public class ProductMenu {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();

	public void mainMenu() {
		
		while(true) {
			System.out.println("1. 전체조회하기");
			System.out.println("2. 상품 추가 하기");
			System.out.println("3. 상품 수정 하기 (상품 id로 조회하고 수정)");
			System.out.println("4. 상품 삭제 하기 (상품 id로 조회해서 삭제)");
			System.out.println("5. 상품 검색 하기 (상품 이름으로 키워드 검색)");
			System.out.println("0. 프로그램 종료하기");
			
			System.out.print("메뉴 선택 >>");
			int menu = sc.nextInt();
			
			sc.nextLine();
			
			switch(menu) {
			case 1: pc.selectList(); break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			default : break;
			}
			
			
			
			
			
		}
	}

}
