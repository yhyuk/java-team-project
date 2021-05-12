package AdminManage;

import java.util.ArrayList;
import java.util.Scanner;


import User.User;

/**
 * 직원관리에 들어가서 메뉴를 출력하는 클래스입니다.
 * @author 김예운
 *
 */
public class UserList {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<User> empList = new ArrayList<User>();
	static ArrayList<Employee_del> delEmpList = new ArrayList<Employee_del>();
	
	/**
	 * 메뉴를 선택하는 메소드
	 */
	public UserList() {
	
		boolean flag = true;
		
		while(flag) {  
			
			getMenu();
			System.out.print("선택: ");
			Scanner scan = new Scanner (System.in);
			String sel = scan.nextLine();
			
			if(sel.equals("1")) {
				UserManage usermanage = new UserManage(); //직원의 목록 보기(직급별, 부서별)
			}else if(sel.equals("2")) {
				UserLeaveApply userleaveapply = new UserLeaveApply(); //퇴사직원 삭제하기
				
			}else if(sel.equals("3")) {
				UserAdd useradd = new UserAdd();//직원등록
				
			}else if(sel.equals("0")){
				//뒤로가기
				break;
				
			}else {
				
				System.out.println("잘못 입력하셨습니다.");
				pause();
			}
			
		}
	

	}

	public static void pause() {
		System.out.println("계속하려면 엔터를 입력하세요.");
		scan.nextLine();
		
	}

	/**
	 * 직원관리 - 메뉴 출력
	 */
	public static void getMenu() {
		System.out.println("======================");
		System.out.println("      [직원관리]");
		System.out.println();
		System.out.println("1. 직원목록보기/수정");
		System.out.println("2. 퇴사 신청자 확인/삭제");
		System.out.println("3. 직원 등록하기");
		System.out.println("0. 뒤로가기 ");
		System.out.println("======================");
	
	}

	
	
	
}
	


