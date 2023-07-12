package com.kh.controller;

import com.kh.model.service.PhoneService;
import com.kh.model.vo.Phone;
import com.kh.view.PhoneMenu;

public class PhoneController {

	/**
	 * 전화번호 등록을 처리해주는 메소드
	 * @param userName ~ phone : 사용자가 입력했던 정보들이 담겨있는 매개변
	 */
	public void insertPhone(String userName, int age, String address, String phone) {
		Phone p = new Phone(userName, age, address, phone);
		int result = new PhoneService().insertPhone(p);
		
		if(result > 0) {
			new PhoneMenu().displaySuccess("전화번호가 등록되었습니다.");	
		} else {
			new PhoneMenu().displayFail("실패했습니다.");
		}
		
	}
	
}
