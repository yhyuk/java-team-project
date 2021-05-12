package AdminManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import User.User;
/**
 * (관리자) 퇴사한 직원 목록을 출력하고, 퇴사직원을 삭제하는 클래스입니다.
 * @author 김예운
 */
public class UserLeaveApply {

	private static ArrayList<User> empList;
	private static ArrayList<Employee_del> delEmpList;
	
	private static String empPath; // 상대경로
	private static String delpath; // 퇴사한회원경로
	private static Scanner scan;

	static {
		empPath = "data\\사용자정보.txt";
		delpath = "data\\퇴사신청명단.txt";
		
		empList = new ArrayList<User>();
		delEmpList = new ArrayList<Employee_del>();
		scan = new Scanner(System.in);

	}
	
	/**
	 * 기본 생성자 입니다.
	 */
	public UserLeaveApply() {
		getEmpInfo();
		viewLeaveEmpMenu();
		save();

	}
	
	/**
	 * 퇴사직원의 명단을 출력합니다.
	 */
	public void viewLeaveEmpMenu() {
		//퇴사 신청자 명단을 띄우는 메소드
		
		for (int i = 0; i < (delEmpList.size() / 5) + 1;) {

			System.out.println();
			System.out.println("[퇴사 신청자 명단]");
			System.out.println("사번\t\t신청일\t\t사유");
			System.out.println("---------------------------------------------------");

			for (int j = i; j < delEmpList.size(); j++) {

				if (j >= delEmpList.size()) {
					break;
				}
				
				delEmpList.clear();
				try{
					BufferedReader reader = new BufferedReader(new FileReader(delpath));
					String txt = "";
					while ((txt = reader.readLine()) != null) {
						
						String[] temp = txt.split("■");
						delEmpList.add(new Employee_del(temp[0], temp[1], temp[2]));
						
						
					}
					reader.close();
					
				}catch (Exception e) {
					System.out.println(e.toString());
				}
				System.out.printf("%s\t\t%s\t%s\n",
						delEmpList.get(j).getId(),
					//	empList.get(j).getName(),
						delEmpList.get(j).getDate(),
						delEmpList.get(j).getReason());
				

			}
			
			System.out.println("---------------------------------------------------");
			System.out.println("1. 퇴사신청 직원 삭제하기");
			System.out.println("0. 뒤로가기");
			System.out.println("---------------------------------------------------");
			System.out.print("메뉴 번호입력 : ");
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				System.out.print("삭제할 직원의 사번입력:");
				String id = scan.nextLine();
				Userdel(id);

			} else if (sel.equals("0")) {
				break;

			}else {
				System.out.println("잘못 입력하셨습니다.");
				pause();
			}
		}
}
		


	/**
	 * 퇴사 신청자 목록 중 원하는 직원을 삭제하는 메소드
	 */
	public void Userdel(String id) {

			for (int i = 0; i < delEmpList.size(); i++) {

				for (int j = 0; j < empList.size(); j++) {
					if (empList.get(j).getId().contains(id)) {

						empList.remove(j);
						delEmpList.remove(i);
						break;
					}

				}

			}
			save();
			savedel();
			System.out.println("삭제했습니다.");
			return;


		}
		
		
		/**
		 * 삭제한 직원을 '퇴사신청명단.txt' 파일에 반영하여 저장하는 메소드
		 */
	public void savedel() {
			try {

				BufferedWriter saveWriter = new BufferedWriter(new FileWriter(delpath));
				for (Employee_del emp : delEmpList) {
					saveWriter.write(String.format("%s■%s■%s\r\n", emp.getId(), emp.getDate(), emp.getReason()));

				}
				saveWriter.close();

			} catch (Exception e) {
				System.out.println("직원 갱신에서 에러 발생");
				System.out.println(e);

			}

		}

		

	   /**
		* 삭제한 직원을 '사용자정보.txt' 파일에 반영하여 저장하는 메소드
		*/
	public void save() {
			try {
				
				BufferedWriter saveWriter = new BufferedWriter(new FileWriter(empPath));
				for(User emp : empList){
					saveWriter.write(String.format("%s■%s■%s■%d■%s■%s■%s■%s■%s■%s\r\n",
						emp.getId(),
						emp.getPassword(),
						emp.getName(), 
						emp.getAge(), 
						emp.getTeam(),
						emp.getPosition(), 
						emp.getPhone(), 
						emp.getEmail(),
						emp.getAddress(), 
						emp.getJoinDate()));
				
				}
				//saveWriter.newLine();
				saveWriter.close();
				
			}catch (Exception e) {
				System.out.println("직원 갱신에서 에러 발생");
				System.out.println(e);
				
			}

			
		} 

		
		
	public void pause() {
		System.out.println("계속하려면 엔터를 입력하세요.");
		scan.nextLine();
		
	}


	

	/**
	 * 퇴사 신청한 직원을 사번으로 조회하여 사용자정보txt에서 불러오는 메소드
	 */
	public void getEmpInfo() {
	//회원정보가져오기
		empList.clear();
		delEmpList.clear();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(empPath));
			BufferedReader delreader = new BufferedReader(new FileReader(delpath));

			String txt = "";
			while ((txt = reader.readLine()) != null) {
				String[] temp = txt.split("■");
				empList.add(new User(temp[0], temp[1], temp[2], Integer.parseInt(temp[3])
                        , temp[4], temp[5], temp[6]
                        , temp[7], temp[8], temp[9]));

			}
			reader.close();

			//퇴사신청자정보가져오기
			while ((txt = delreader.readLine()) != null) {
				
				String[] temp = txt.split("■");
				delEmpList.add(new Employee_del(temp[0], temp[1], temp[2]));
			}
			delreader.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}
