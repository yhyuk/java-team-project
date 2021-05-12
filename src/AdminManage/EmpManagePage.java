package AdminManage;

import java.util.ArrayList;
import java.util.Scanner;

import User.User;

/**
 * 관리자 - 직원 관리 페이지<br/>
 * 직원 관리 페이지를 출력하고 메뉴 선택 시 해당 페이지 객체를 생성한다.
 * @author 유기호
 */
public class EmpManagePage {

	private ArrayList<User> empList = new ArrayList<User>();
	
	/**
	 * 멤버 변수를 초기화하고 페이지를 출력한다.
	 * @param empList 사원 리스트(사용자정보.txt 파일의 내용)
	 */
	public EmpManagePage(ArrayList<User> empList) {
		this.empList = empList;
		this.showPage();
	}
	
	/**
	 * 페이지를 출력한다.
	 */
	public void showPage() {
		System.out.println();
		System.out.println("[직원 관리]");
		System.out.println("==========================================================");
		System.out.println("원하는 메뉴 번호를 선택하세요.");
	    System.out.println("1.직원 목록 보기\n2.직원 휴가 승인\n0.뒤로가기");
		this.selectMenu();
	}

	/**
	 * 사용자로부터 메뉴를 입력 받고 선택된 메뉴 페이지 객체를 생성한다.
	 */
	public void selectMenu() {
	      //메뉴 선택 메소드
	      
	      Scanner scan = new Scanner(System.in);
	      
	      boolean loop = true;
	      while (loop) {
	    	 System.out.println("==========================================================");
	         System.out.print("번호 입력: ");
	         String sel = scan.nextLine();
	         
	         switch (sel) {
	            case "1":
	               loop = false;
	               UserList userListPage = new UserList(); //직원 목록 보기 페이지
	               this.showPage();
	               break;
	            case "2":
	               loop = false;
	               AdminOff adminOffPage = new AdminOff(empList); //직원 휴가 승인 페이지
	               this.showPage();
	               break;
	            case "0":
	               loop = false;
	               System.out.println("이전 작업으로 돌아갑니다.");
	               System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
	               System.out.println();
	               //뒤로가기
	               break;
	            default:
	               System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
	               break;
	         }
	      }
	   }
	
}
