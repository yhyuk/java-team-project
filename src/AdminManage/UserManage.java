
package AdminManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import User.User;
/**
 * 
 * (관리자) 직원 목록을 출력하고, 직원 정보를 수정하는 클래스입니다.
 * @author 김예운
 *
 */
public class UserManage {
	static Scanner scan = new Scanner(System.in);

	private static ArrayList<User> saveList;  // 전체 리스트 -> 나중에 직급/부서 수정부분에서 사용
	private static ArrayList<User> empList;	//직급/부서별리스트
	private static String empPath;


	static {
		empPath = "data\\사용자정보.txt";
		empList = new ArrayList<User>();
		saveList = new ArrayList<User>();

	}

	/**
	 * 기본 생성자 입니다.
	 */
	public UserManage() {
		viewManage();
		//getPostionUser();
		//getTeamUser();
		//viewPostionList();// 직급별리스트
		//viewTeamList();// 부서별리스트

	}

	
	/**
	 * 직원목록 직급별, 부서별
	 * 직원목록을 직급별 또는 부서별로 확인할건지 선택합니다.
	 */
	public void viewManage() {
		//직원목록 들어가면 나오는 페이지!
		String str = "";

		boolean flag = true;

		while (flag) {


			System.out.println("===================");
			System.out.println("1. 직급별 직원목록");
			System.out.println("2. 부서별 직원목록");
			System.out.println("0. 뒤로가기"); //수정
			System.out.println("===================");
			System.out.print("선택: ");

			Scanner scan = new Scanner(System.in);
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				
				//직급별 직원목록
				getPostionUser();
				viewPostionList();

			} else if (sel.equals("2")) {
				
				//부서별 직원목록
				getTeamUser();
				viewTeamList();


			} else if (sel.equals("0")) {
				// 뒤로가기

				break;

			} else {
				System.out.println("잘못 입력하셨습니다.");
				pause();
			}
		}

	}
	

	/**
	 * 직원 목록을 보기를 원하는 부서를 선택합니다.
	 * 선택한 부서의 직원목록을 불러옵니다.
	 */
	public void getTeamUser() {
		
		System.out.println("===================");
		System.out.println("1. 인사총무");
		System.out.println("2. 재무회계");
		System.out.println("3. 홍보");
		System.out.println("4. 국내사업");
		System.out.println("5. 해외사업");
		System.out.println("6. 고객지원");
		System.out.println("7. R&D");
		System.out.println("8. 시스템");
		System.out.println("===================");
		System.out.print("선택: ");
		
		String sel = scan.nextLine();

		String p = "";
		
			
			
			
		switch(sel) {
		
		case "1": p = "인사총무"; break;
		case "2": p = "재무회계"; break;
		case "3": p = "홍보"; break;
		case "4": p = "국내사업"; break;
		case "5": p = "해외사업"; break;
		case "6": p = "고객지원"; break;
		case "7": p = "R&D"; break; 
		case "8": p = "시스템"; break;
		
		}
		
			try {
				BufferedReader reader = new BufferedReader(new FileReader(empPath));

				String temp;
				while ((temp = reader.readLine()) != null) {
					String[] data = temp.split("■");

					if (data[4].contains(p)) {
						empList.add(new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5],
								data[6], data[7], data[8], data[9]));
					}
				}

			} catch (Exception e) {
			}
			
		
		}

	
	
	/**
	 * 직원 목록을 보기를 원하는 직급를 선택합니다.
	 * 선택한 직급의 직원목록을 불러옵니다.
	 */
	public void getPostionUser() {
		
		//직급별 직원목록 불러오는 메소드
		System.out.println("===================");
		System.out.println("1. 부장");
		System.out.println("2. 차장");
		System.out.println("3. 과장");
		System.out.println("4. 대리");
		System.out.println("5. 사원");
		System.out.println("6. 신입사원");
		System.out.println("===================");
		System.out.print("선택: ");
		String sel = scan.nextLine();
		
		String p = "";
		switch(sel) {
			case "1": p = "부장"; break;
			case "2": p = "차장"; break;
			case "3": p = "과장"; break;
			case "4": p = "대리"; break;
			case "5": p = "사원"; break;
			case "6": p = "신입사원"; break;
		}
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(empPath));

			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");

				if (data[5].contains(p)) {
					empList.add(new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5],
							data[6], data[7], data[8], data[9]));
				}

			}
		} catch (Exception e) {
		}
		

	}
		
	
	/**
	 * 부서별 직원 목록을 출력합니다.
	 */
	public void viewTeamList() {
		//부서별 직원목록
		for(int i=0;i<(empList.size()/10)+1;){
			System.out.println();
			System.out.printf("[부서별 직원 목록]\n");
			System.out.println("사번\t  이름\t나이\t부서\t\t직급\t연락처\t\t이메일\t\t\t주소\t입사일");
			System.out.println("----------------------------------------------------------------------------------------------------------");

			for(int j=i*10;j<10+i*10;j++){
				if(j>=empList.size()){
					break;
					
				}
				System.out.printf("%s\t %s\t %d\t%-10s\t%s\t%s\t%s\t%s\t%s\r\n", 
						empList.get(j).getId(),
						empList.get(j).getName(), 
						empList.get(j).getAge(), 
						empList.get(j).getTeam(),
						empList.get(j).getPosition(), 
						empList.get(j).getPhone(), 
						empList.get(j).getEmail(),
						empList.get(j).getAddress(), 
						empList.get(j).getJoinDate());
				
			}
			for( User user : saveList) {

					empList.add(user);

			}
			System.err.println();
			System.out.printf("현재 %d 페이지  최대 %d 페이지 입니다.\n", i+1, (empList.size()/10));
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("1. 이전 페이지\t2. 다음 페이지");
			System.out.println("3. 페이지 이동\t4. 부서수정");
			System.out.println("0. 뒤로가기");
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.print("메뉴 번호입력 : ");
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				System.out.println("");
				if (i != 0) {
					i--;
				} else {
					System.out.println("제일 첫번째 페이지입니다..");
					pause();
				}
			} else if (sel.equals("2")) {
				if (i >= empList.size() / 10-1) {
					System.out.println("더 이상 페이지를 넘길 수 없습니다.");
					pause();
				} else {
					i++;
				}
			} else if (sel.equals("3")) {
				System.out.print("이동할 페이지 숫자를 입력 : ");
				int pageNum = scan.nextInt();
				if (i > empList.size() / 10 && i < 0) {
					System.out.println("없는 페이지 번호입니다. 다시 입력하세요.");
					pause();

				} else {
					i = pageNum - 1;
				}

			} else if (sel.equals("4")) {
				// 직원정보수정
				System.out.println();
				System.out.print("수정하고싶은 직원 사번 입력: ");
				String editId = scan.next();

				UserTeamEdit(editId);

			} else if (sel.equals("0")) {
			
				break;

			} else {
				System.out.println("잘못 입력하셨습니다.");
				break;
				
			}
		}
	}
	
	
	/**
	 * 직급별 직원 목록을 출력합니다.
	 */
	public void viewPostionList() {
		// 직급별 직원목록
		
		for(int i=0;i<(empList.size()/10)+1;){
			System.out.println();
			System.out.printf("[직원 목록]\n");
			System.out.println("사번\t  이름\t나이\t부서\t\t직급\t연락처\t\t이메일\t\t\t주소\t입사일");
			System.out.println("----------------------------------------------------------------------------------------------------------");

			for(int j=i*10;j<10+i*10;j++){
				if(j>=empList.size()){
					break;
					
				}
				System.out.printf("%s\t %s\t %d\t%-10s\t%s\t%s\t%s\t%s\t%s\r\n", 
						empList.get(j).getId(),
						empList.get(j).getName(), 
						empList.get(j).getAge(), 
						empList.get(j).getTeam(),
						empList.get(j).getPosition(), 
						empList.get(j).getPhone(), 
						empList.get(j).getEmail(),
						empList.get(j).getAddress(), 
						empList.get(j).getJoinDate());
			}
			for( User user : saveList) {
				empList.add(user);
			}
			
			System.err.println();
			System.out.printf("현재 %d 페이지	최대 %d 페이지 입니다.\n", i+1, (empList.size()/10));
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("1. 이전 페이지\t2. 다음 페이지");
			System.out.println("3. 페이지 이동\t4. 직급 수정");
			System.out.println("0. 뒤로가기");
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.print("메뉴 번호입력 : ");
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				System.out.println("");
				if (i != 0) {
					i--;
				} else {
					System.out.println("제일 첫번째 페이지입니다..");
					pause();
				}
			} else if (sel.equals("2")) {
				if (i >= empList.size() / 10-1) {
					System.out.println("더 이상 페이지를 넘길 수 없습니다.");
					pause();
				} else {
					i++;
				}
			} else if (sel.equals("3")) {
				System.out.print("이동할 페이지 숫자를 입력 : ");
				int pageNum = scan.nextInt();
				if (i > empList.size() / 10 && i < 0) {
					System.out.println("없는 페이지 번호입니다. 다시 입력하세요.");
					pause();

				} else {
					i = pageNum - 1;
				}

			} else if (sel.equals("4")) {
				// 직원정보수정
				System.out.println();
				System.out.print("수정하고싶은 직원 사번 입력: ");
				String editId = scan.next();

				UserPositionEdit(editId);

			} else if (sel.equals("0")) {
				break;

			} else {
				System.out.println("잘못 입력하셨습니다.");
				pause();
			}
		}
		

	}// viewPostionList
	
	
	/**
	 * 해당 직원의 부서를 수정하는 메소드
	 */
	public void UserTeamEdit(String editId) {
		//부서를 수정하는 메소드
		for (User emp : empList) {

			if (emp.getId().equals(editId)) {

				System.out.print("수정하실 부서를 입력하세요: ");
				String changedTeam = scan.next();
				emp.setTeam(changedTeam);

			}
		}
		for(int i = 0; i <empList.size(); i++){
			if (empList.get(i).getId().contains(editId)) {
	
				empList.remove(i);			
				
				
			}
		}
		save();
		System.out.println("수정했습니다.");
		pause();

	}


	/**
	 * 해당 직원의 직급을 수정하는 메소드
	 */
	public void UserPositionEdit(String editId) {
		//직급을 수정하는 메소드
		//for (User emp : empList) {
		System.out.print("수정하실 직급를 입력하세요: ");
		String changedPosition = scan.next();
		
		for(int i = 0; i < saveList.size(); i++){
			if (saveList.get(i).getId().contains(editId)) {
			//if (emp.getId().equals(editId)) {

				//emp.setPosition(changedPosition);
				saveList.get(i).setPosition(changedPosition);			
				

			}
		}
		for(int i = 0; i <empList.size(); i++){
			if (empList.get(i).getId().contains(editId)) {
	
				empList.remove(i);			
				
				
			}
		}
		
		save();
		System.out.println("수정했습니다.");
		pause();
		
	}
	



	/**
	 * 수정한 정보를 저장하는 메소드
	 */
	public void save() {
		try {
		
			BufferedWriter saveWriter = new BufferedWriter(new FileWriter(empPath));
			//for(User emp : empList){
			//for(int i = 0; i < empList.size(); i++){
			String result ="";
				for(User user : saveList) {
				result += String.format("%s■%s■%s■%d■%s■%s■%s■%s■%s■%s\r\n",
						user.getId(),
						user.getPassword(),
						user.getName(), 
						user.getAge(), 
						user.getTeam(),
						user.getPosition(), 
						user.getPhone(), 
						user.getEmail(),
						user.getAddress(), 
						user.getJoinDate());
				}
				////
				saveWriter.write(result);
			
		
			//saveWriter.newLine();
			saveWriter.close();
			
		}catch (Exception e) {
			System.out.println("직원 갱신에서 에러 발생");
			System.out.println(e);
			
		

		}
	}

	
	/**
	 * 입력을 잠시 멈추는 메소드
	 */
	public void pause() {
		System.out.println("계속하려면 엔터를 입력하세요.");
		scan.nextLine();
	}
	
}
	
