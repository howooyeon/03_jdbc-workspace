package com.product.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.product.controller.ProductController;
import com.product.model.vo.Product;

public class ProductMenu {

	public Scanner sc = new Scanner(System.in);

	public ProductController pc = new ProductController();

	public void mainMenu() {

		boolean flag = true;

		while (flag) { // -- while start

			System.out.println("\n== 쓱(SSG) 상품관리 프로그램 ==");
			System.out.println("1) 상품 추가");
			System.out.println("2) 상품 전체 조회");
			System.out.println("3) 상품명(키워드)으로 검색 ");
			System.out.println("4) 상품 정보 변경");
			System.out.println("5) 상품 정보 삭제");
			System.out.println("6) 쓱배송 가능상품 조회");
			System.out.println("7) 가격대별 검색");
			System.out.println("0) 프로그램 종료");

			System.out.print(">>메뉴 선택 : ");
			int menu = sc.nextInt();

			sc.nextLine();

			switch (menu) {

			case 1:inputProduct();
				break;
			case 2:pc.selectProduct();
				break;
			case 3:pc.selectByPname(inputPname());
				break;
			case 4:updateProduct();
				break;
			case 5:pc.deleteProduct(inputPname());
				break;
			case 6:pc.selectSsgProduct();
				break;
			case 7:selectPriceProduct();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				flag = false;
			} // -- while end

		}

	}


	/**
	 * 추가할 상품 정보를 입력받는 메소드
	 */
	private void inputProduct() {
		System.out.println("\n == 상품추가 메뉴입니다 == ");
		
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		
		System.out.print("가격 : ");
		String price = sc.nextLine();
		
		System.out.print("원산지 : ");
		String national = sc.nextLine();
		
		System.out.print("제조회사 : ");
		String brand = sc.nextLine();
		
		System.out.print("쓱배송(Y/N) : ");
		String ssgAble = sc.nextLine();
		
		System.out.print("상품분류 : ");
		String category = sc.nextLine();
		
		pc.inputProduct(pName, price, national, brand, ssgAble, category);
	}
	
	/**
	 * 상품명 입력받는 메소드
	 * @return 입력받은 상품명
	 */
	private String inputPname() {
		
		System.out.print("\n 상품명 입력 : ");
		return sc.nextLine();
		
	}
	
	private void updateProduct() {
		
		String pName = inputPname();
		
		System.out.print("변경할 가격 : ");
		String price = sc.nextLine();
		
		System.out.print("변경할 쓱배송(Y/N) : ");
		String ssgAble = sc.nextLine();
		
		pc.updateProduct(pName, price, ssgAble);
		
	}
	
	private void selectPriceProduct() {
		
		System.out.print("최소 검색 가격 : ");
		String minPrice = sc.nextLine();
		
		System.out.print("최대 검색 가격 : ");
		String maxPrice = sc.nextLine();
		
		pc.selectPriceProduct(minPrice, maxPrice);
		
	}
	
	//------------------------ 출력화면 --------------------------
	

	/**
	 * 성공했을때 출력하는 화면
	 * @param message 	: 성공 문구
	 */
	public void DisplaySuccess(String message) {
		System.out.println(message);
	}

	/**
	 * 실패했을때 출력하는 화면
	 * @param message	: 실패 문구
	 */
	public void DisplayFail(String message) {
		System.out.println(message);
	}

	/**
	 * 출력하고자 하는 list가 여러행일때
	 * @param list		: 여러행 list
	 */
	public void DisplayProductList(ArrayList<Product> list) {
		for(Product p : list) {
			System.out.println(p);
		}
	}
}
