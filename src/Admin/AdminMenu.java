package Admin;

import java.util.ArrayList;
import java.util.Scanner;

import AdminManage.EmpManagePage;
import AdminSalary.UpdateSalaryPage;
import AdminVacation.VacationCalendarPage;
import User.User;
import UserNotice.AdminNotice;
import UserSuggestion.AdminOffer;

/**
 * 관리자 메인 페이지<br/>
 * 관리자 메인 페이지를 출력하고 메뉴 선택 시 해당 페이지 객체를 생성한다.
 * @author 유기호
 */
public class AdminMenu {
	
	private ArrayList<User> empList = new ArrayList<User>();
	
	/**
	 * 멤버 변수를 초기화하고 페이지를 출력한다.
	 * @param empList 사원 리스트(사용자정보.txt 파일의 내용)
	 */
	public AdminMenu(ArrayList<User> empList) {
		this.empList = empList;
		this.showPage();
	}
	
	/**
	 * 페이지를 출력한다.
	 */
	public void showPage() {
		System.out.println("==========================================================");
		System.out.println("[관리자 모드]");
		System.out.println("==========================================================");
	    System.out.println("1.직원 관리\n2.휴가 일정표\n3.급여 관리\n4.공지사항\n5.건의사항\n0.프로그램 종료");
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
	         System.out.println();
	         System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
	         
	         switch (sel) {
	            case "1":
	               loop = false;
	               EmpManagePage empManagePage = new EmpManagePage(empList); //직원 관리 페이지
	               this.showPage();
	               break;
	            case "2":
	               loop = false;
	               VacationCalendarPage vacationCalendarPage = new VacationCalendarPage(empList); //휴가 일정표 페이지
	               this.showPage();
	               break;
	            case "3":
	               loop = false;
	               UpdateSalaryPage updateSalaryPage = new UpdateSalaryPage(); //급여 관리 페이지
	               this.showPage();
	               break;
	            case "4":
	               loop = false;
	               //AdminNotice adminNoticePage = new AdminNotice();
	               //adminNoticePage.managerNotice();
	               AdminNotice.managerNotice(); //공지사항 페이지
	               this.showPage();
	               break;
	            case "5":
	            	loop = false;
	            	//AdminOffer adminSuggestionPage = new AdminOffer();
	            	//adminSuggestionPage.offerRead();
	            	AdminOffer.offerRead(); //건의사항 페이지
	            	this.showPage();
	            	break;
	            case "0":
	               loop = false;
	               System.out.println("프로그램이 종료됩니다.");
	               System.exit(0);
	               break;
	            default:
	               System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
	               break;
	         }
	      }
	   }
	
}
