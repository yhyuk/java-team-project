package User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import UserNotice.UserNoticeList;
import UserSchedule.UserSchedule;
import UserStatus.SetUserStatus;
import UserSuggestion.UserOfferWrite;
import UserVacation.UserVacation;

/**
 * 
 * 사용자가 로그인시 출력되는 Main 화면 입니다.
 * 
 * @author 1조 팀원
 * 
 */
public class UserMenu {
	
	private static Scanner scan;
	private static String userPath;
	private static String userStatePath;
	private static String userVacationPath;
	private static ArrayList<User> userList;
	static User user;
	
	static {
		scan = new Scanner(System.in);
		userPath = "data\\사용자정보.txt";
		userStatePath = "data\\사원 상태.txt";
		userVacationPath = "data\\휴가신청명단.txt";
		userList = new ArrayList<User>();
		user = new User();
	}
	
	public UserMenu(User login) {
		userSelect(login);
	}
	
	/**
	 * 
	 * 사용자가 원하는 카테고리를 선택할 수 있는 메소드입니다.
	 * 
	 * @param login 사용자의 정보입니다.
	 * 
	 */
	public void userSelect(User login) {
		userMain(login);
		
		boolean flag = true;
		while(flag) {

			String sel = scan.nextLine();
			switch(sel) {
				case "0": //프로그램 종료
					System.out.println("프로그램이 종료됩니다.");
					System.exit(0);
					break;
				case "1": //마이페이지
					UserMyPage.myPage(login);
					userMain(login);
					break;
				case "2": //부서원 현황
					UserMyPage.memberList(login);
					userMain(login);
					break;
				case "3": //회사 일정
					UserSchedule schedule = new UserSchedule(login);
					userMain(login);
					break;
				case "4": //근태 관리
					SetUserStatus status = new SetUserStatus(login.getId());
					userMain(login);
					break;
				case "5": //휴가 관리
					UserVacation vacation = new UserVacation(login);
					userMain(login);
					break;
				case "6": //건의 사항
					UserOfferWrite offer = new UserOfferWrite(login);
					offer.offerList();
					userMain(login);
					break;
				case "7": //공지 사항
					UserNoticeList notice = new UserNoticeList(login);
					notice.offerList();
					userMain(login);
					break;
				default:
					System.out.print("잘못 입력하셨습니다. 다시 입력해주세요: ");
					break;
			}
		}
	}
	
	/**
	 * 
	 * 사용자가 로그인 후 선택할 수 있는 카테고리를 보여줍니다.
	 * 사용자가 만약 처음으로 로그인 했다면, 개인정보를 등록할 수 있는 화면이 나옵니다.
	 * 
	 * @param login 사용자의 정보입니다.
	 * 
	 */
	private static void userMain(User login) {
		
		userInfo();
		if(login.getName() == "") {
			System.out.printf("사번 \"%s\" 님이 첫 로그인 했습니다.\n", login.getId());
			System.out.println("개인정보 등록을 위해 개인정보를 입력해주세요.");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			for (User my : userList) {
				if(my.getId().equals(login.getId())) {
					System.out.print("이름을 입력하세요: ");
					String name = scan.nextLine();
					System.out.print("변경할 비밀번호를 입력하세요: ");
					String password = scan.nextLine();
					System.out.print("나이를 입력하세요.(숫자만 입력): ");
					String age = scan.nextLine();
					System.out.print("전화번호를 입력하세요(010-0000-0000): ");
					String phone = scan.nextLine();
					System.out.print("이메일를 입력하세요(test@naver.com): ");
					String email = scan.nextLine();
					System.out.print("주소를 입력하세요: ");
					String address = scan.nextLine();
					
					my.setName(name);
					my.setPassword(password);
					my.setAge(Integer.parseInt(age));
					my.setPhone(phone);
					my.setEmail(email);
					my.setAddress(address);
					System.out.printf("\"%s\"님 개인정보 등록이 완료 되었습니다.\n", my.getName());
					System.out.println("추후 개인정보등록은 마이페이지에서 수정이 가능합니다.");
					userSave();
				}
			} 
		}
		
		for (User my : userList) {
			if(my.getId().equals(login.getId())) {
				System.out.printf("\"%s\"님 안녕하세요.^^ %d일째 출근 중입니다.\n", my.getName(), myEmploy(login));
				System.out.printf("휴가 D-Day: %d일\n", userVacation(login)); 
				System.out.printf("현재 상태: %s\n",userState(login));
			}
		}
		System.out.println("1. 마이페이지");
		System.out.println("2. 부서원 정보");
		System.out.println("3. 회사 일정");
		System.out.println("4. 근태 관리");
		System.out.println("5. 휴가 관리");
		System.out.println("6. 건의사항");
		System.out.println("7. 공지사항");
		System.out.println("0. 프로그램 종료");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("번호 입력: ");
	}
	
	/**
	 * 
	 *  사용자의 입사일 부터 현재까지 출근 일수를 보여주는 메소드 입니다.
	 *  
	 *  @param login 사용자의 정보입니다.
	 *  @return 현재시간과 입사시간의 차이값 입니다.
	 *  
	 */
	private static long myEmploy(User login) {
		
		Calendar now = Calendar.getInstance();
		Calendar join = Calendar.getInstance();
		
		for (User my : userList) {
			
			if (my.getId().equals(login.getId())) {
				String[] temp = my.getJoinDate().split("-");
				join.set(Integer.parseInt(temp[0])
						, Integer.parseInt(temp[1])
						, Integer.parseInt(temp[2]));
			}
		}
		
		return (now.getTimeInMillis() - join.getTimeInMillis()) / 1000 / 60 / 60 / 24;
	}
	
	/**
	 * 
	 * 사용자의 현재 상태(출근,퇴근)를 보여주는 메소드입니다.
	 * 
	 * @param login 사용자의 정보입니다.
	 * @return 사용자의 현재 상태를 반환합니다.
	 * 
	 */
	private static String userState(User login) {
		
		String result = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(userStatePath));
			
			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");
				if(login.getId().equals(data[0])) {
					result = data[1];
				}
			}
			reader.close();
		} catch (Exception e) {}
		return result;
	}
	
	/**
	 * 
	 * 사용자의 휴가까지의 D-Day를 보여주는 메소드입니다.
	 * 
	 * @param login 사용자의 정보입니다.
	 * @return 사용자의 휴가신청날짜와 현재시간을 뺀 D-Day 값입니다.
	 * 
	 */
	private static long userVacation(User login) {
		
		Calendar now = Calendar.getInstance();
		Calendar vacation = Calendar.getInstance();
		
		String result = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(userVacationPath));
			
			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");
				if(login.getId().equals(data[1])) {
					result = data[2];
				}
			}
			reader.close();
			String[] data2 = result.split("-");
			vacation.set(Integer.parseInt(data2[0])
					, Integer.parseInt(data2[1])
					, Integer.parseInt(data2[2]));
		} catch (Exception e) {}
		
		return (vacation.getTimeInMillis() - now.getTimeInMillis()) / 1000 / 60/ 60 / 24;
	}
	
	/**
	 * 
	 * 모든 사원 정보를 불러오는 메소드입니다.
	 * 사번(id), 비밀번호, 이름, 나이, 부서, 직급, 전화번호, 이메일, 주소, 입사날짜
	 * 
	 */
	private static void userInfo() {
		userList.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(userPath));
			
			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");
				
				userList.add(new User(data[0], data[1], data[2], Integer.parseInt(data[3])
									, data[4], data[5], data[6]
									, data[7], data[8], data[9]));
			}
			reader.close();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 * 사용자가 개인정보 변경시 필요한 저장 메소드입니다.
	 * 사번(id), 비밀번호, 이름, 나이, 부서, 직급, 전화번호, 이메일, 주소, 입사날짜
	 * 
	 */
	private static void userSave(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(userPath));
			
			for(User my : userList){
				writer.write(String.format("%s■%s■%s■%d■%s■%s■%s■%s■%s■%s"
										,my.getId()
										,my.getPassword()
										,my.getName()
										,my.getAge()
										,my.getTeam()
										,my.getPosition()
										,my.getPhone()
										,my.getEmail()
										,my.getAddress()
										,my.getJoinDate()));
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {}
	}
}
