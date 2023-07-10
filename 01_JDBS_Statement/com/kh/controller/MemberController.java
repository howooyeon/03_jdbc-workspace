package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

//Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
// 			   해당 메소드로 전달된 데이터 [가공처리를 한 후] Dao로 전달하면서 호출
// 			   Dao로부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정 (View 메소드 호출)
public class MemberController {
	
	
	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String Pwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
		
		// 받은 값들을 데이터를 직접적으로 처리해주는 Dao로 넘기기!
		// => 어딘가에 주섬주섬 담아서 전달!
		// 어딘가? => Vo객체 => Member 객체!
		
		// 방법 1) 기본생성자 생성한 후 각 필드에 setter 메소드 통해서 일일히 담는 방법 => 이건 매개변수가 몇개 없을 때...
		// 방법 2) 아싸리 매개변수 생성자를 통해서 생성과 동시에 담는 방법
		
		Member m = new Member(userId, userId, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		// 여기서 나이를 반드시!! int 형으로 바꿔야함!!
		// String => int 로 변경하는 방법? parseInt();
		
		// new 부터 빨간줄 뜨는 경우 => 1000000% 확률로 그 데이터 타입을 받는 매개변수 생성자 없다는 거임!!
		// System.out.println(m);
		
		int result = new MemberDao().insertMember(m);
		
		// System.out.println(result);
		
		// 팝업창 뜨는 것처럼 사용자에게 문구 보여주기
		if(result > 0) { // 성공
			new MemberMenu().displaySuccess("성공적으로 회원 추가 되었습니다.");
		}else { // 실패
			new MemberMenu().displayFail("회원 추가 실패했습니다....");
		}
		
	}
	
	/**
	 * 사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		
		// 조회결과가 있는지 없는지 판단한 후 사용자가 보게될 응답화면 지정
		if (list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displayNoData("전체 조회결과가 없습니다.");
		} else { // 뭐라도 조회된 데이터가 있을 경우
			new MemberMenu().displayMemberList(list);
		}
	}
	
	public void selectByUserId(String userId) {
		Member m = new MemberDao().selectByUserId(userId);
		
		if(m == null) { // 검색 결과가 없을 경우(조회된 데이터 없음)
			new MemberMenu().displayNoData(userId + "에 해당하는 검색결과가 없습니다.");
		} else {
			new MemberMenu().displayMember(m);
		}
		
	}
	
	
}
