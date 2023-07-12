package com.kh.view;

import java.util.Scanner;

import com.kh.controller.PhoneController;

public class PhoneMenu {
	// 전역변수 선언
	private Scanner sc = new Scanner(System.in);

	private PhoneController pc = new PhoneController();

	// 사용자가 보게 될 메인화면
	public void mainMenu() {
		while(true) {
			System.out.println("\n================");
			System.out.println("전화번호부 v1.0");
			System.out.println("-------------------");
			System.out.println("1. 전화번호등록");
			System.out.println("2. 전화번호검색");
			System.out.println("3. 전화번호모두보기");
			System.out.println("4. 종료");
			System.out.println("===================");
			System.out.print("메뉴 > ");
		
			int menu = sc.nextInt();
			
			sc.nextLine();
			
			switch(menu) {
			case 1: inputNumber(); break;
			case 2: break;
			case 3: break;
			case 4: break;
			default: break;
			}
		}
	}

	private void inputNumber() {
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		
		sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("전화번호 : ");
		String phone = sc.nextLine();
		
		pc.insertPhone(userName, age, address, phone);
	}

	// ------------------------------응답화면-------------------------------------------
	public void displaySuccess(String message) {
		System.out.println("\n 서비스 요청 성공" + message);
	}

	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패" + message);
	}

}
