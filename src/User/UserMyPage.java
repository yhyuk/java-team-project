package User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * 사용자의 마이페이지, 부서원 정보를 보여주는 클래스입니다.
 * 마이페이지에서는 사용자의 개인정보 확인 및 수정, 퇴사신청, 급여 확인하기를 제공합니다.
 * 
 * @author Kimyounghyuk
 *
 */
public class UserMyPage {
	
	private static Scanner scan;
	private static String userPath;
	private static String salaryPath;
	private static String leavePath;
	private static String annualPath;
	private static ArrayList<User> userList;
	static User user;
	
	static {
		scan = new Scanner(System.in);
		userPath = "data\\사용자정보.txt";
		salaryPath = "data\\근태기록\\2021년\\05월";
		leavePath = "data\\퇴사자신청명단.txt";
		annualPath = "data\\직급별 급여.txt";
		userList = new ArrayList<User>();
		user = new User();
	}
	
	
	public UserMyPage(User login) {
		myPage(login);
	}
	
	/**
	 * 
	 * 모든 사원 정보를 불러오는 메소드입니다.
	 * 사번(id), 비밀번호, 이름, 나이, 부서, 직급, 전화번호, 이메일, 주소, 입사날짜
	 * 
	 */
	public static void userInfo() {
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
		} catch (Exception e) {}
	}
	
	/**
	 * 
	 * 사용자가 개인정보 변경시 필요한 저장 메소드입니다.
	 * 사번(id), 비밀번호, 이름, 나이, 부서, 직급, 전화번호, 이메일, 주소, 입사날짜
	 * 
	 */
	public static void userSave(){
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
		} catch (Exception e) {
		
		}
	}
	
	/**
	 * 
	 * 직급별 연봉 파일에서 사용자의 해당 직급 연봉을 받아오는 메소드입니다.
	 * 
	 * @param login 사용자의 정보 입니다.
	 * @return 사용자의 해당직급에 맞는 연봉을 반환합니다.
	 * 
	 */
	public static int userSalary(User login) {
		
		int annualPay = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(annualPath));
			
			for (User my : userList) {
				if(my.getId().equals(login.getId())) {
					String temp;
					while ((temp = reader.readLine()) != null) {
						String[] data = temp.split("■");
						if(my.getPosition().equals(data[0])) {
							annualPay = Integer.parseInt(data[1]);
						}
					}
					reader.close();
				}
			}
		} catch (Exception e) {}
		
		return annualPay;
	}
	
	/**
	 * 
	 * 마이페이지 화면이며, 사용자의 개인정보를 출력해주는 메소드입니다.
	 * 사용자의 개인정보수정, 급여확인, 퇴사신청 선택메뉴를 제공합니다.
	 * 
	 * @param login 사용자의 정보입니다
	 * 
	 */
	public static void myPage(User login) {
		
		userInfo();
		userSalary(login);
		for(User my : userList) {
			if(my.getId().equals(login.getId())) {
				
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.printf("이름: %s\n", my.getName());
				System.out.printf("나이: %d\n", my.getAge());
				System.out.printf("부서/직급: %s %s\n", my.getTeam(), my.getPosition());
				System.out.printf("전화번호: %s\n", my.getPhone());
				System.out.printf("eamil: %s\n", my.getEmail());
				System.out.printf("주소: %s\n", my.getAddress());
				System.out.printf("입사일: %s\n", my.getJoinDate());
				System.out.printf("현재 급여: %,.0f원\n", userPay(login));
			}
		}
		
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1. 개인정보 수정하기");
		System.out.println("2. 급여 자세히 확인하기");
		System.out.println("3. 퇴사 신청하기");
		System.out.println("0. 메인메뉴로가기");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

		System.out.print("원하는 번호를 입력하세요: ");
		String sel = scan.nextLine();
		
		switch(sel) {
			case "1":
				userInfoModify(login);
				break;
			case "2":
				detailPay(login);
				break;
			case "3":
				System.out.print("퇴사 신청을 정말 하시겠습니까?(y/n): ");
				String leave = scan.nextLine();
				if (leave.equals("y")) leaveSign(login);
				else myPage(login);
				break;
			case "0": 
				break;
			default: 
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				myPage(login);
				break;
		}
	}
	
	/**
	 * 
	 *  사용자의 개인정보를 수정할 수 있는 메소드입니다.
	 *  이름, 나이, 전화번호, email, 주소, 비밀번호 수정이 가능합니다.
	 *  변경이 완료된다면, 자동 저장되며 이동합니다.
	 *  
	 *  @param login 사용자의 정보입니다.
	 *  
	 */
	public static void userInfoModify(User login) {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 개인 정보 수정 하기 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1. 이름");
		System.out.println("2. 나이");
		System.out.println("3. 전화번호");
		System.out.println("4. email");
		System.out.println("5. 주소");
		System.out.println("6. 비밀번호");
		System.out.println("0. 메인메뉴로 가기");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("수정하고 싶은 목록 번호를 입력하세요: ");
		String sel = scan.nextLine();
		
		switch(sel) {
			case "0": //뒤로 가기
				break;
			case "1": //이름 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 이름은 '%s' 입니다.\n", my.getName());
						System.out.print("변경할 이름을 입력하세요: ");
						String name = scan.nextLine();
						my.setName(name);
						System.out.printf("'%s' 이름 변경 완료 되었습니다.^^\n",my.getName());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;
			case "2": //나이 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 나이는 '%d' 입니다.\n", my.getAge());
						System.out.print("변경할 나이를 입력하세요(숫자만 가능): ");
						String age = scan.nextLine();
						my.setAge(Integer.parseInt(age));
						System.out.printf("'%d' 나이 변경 완료 되었습니다.^^\n",my.getAge());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;
			case "3": //전화번호 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 전화번호는 '%s' 입니다.\n", my.getPhone());
						System.out.print("변경할 전화번호를 입력하세요(010-0000-0000): ");
						String phone = scan.nextLine();
						my.setPhone(phone);
						System.out.printf("'%s' 전화번호 변경 완료 되었습니다.^^\n",my.getPhone());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;
			case "4": //이메일 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 이메일은 '%s' 입니다.\n", my.getEmail());
						System.out.print("변경할 이메일을 입력하세요(abc@xxx.com): ");
						String email = scan.nextLine();
						my.setEmail(email);
						System.out.printf("'%s' 이메일 변경 완료 되었습니다.^^\n",my.getEmail());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;
			case "5": //주소 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 주소는 '%s' 입니다.\n", my.getAddress());
						System.out.print("변경할 주소를 입력하세요: ");
						String address = scan.nextLine();
						my.setAddress(address);
						System.out.printf("'%s' 주소 변경 완료 되었습니다.^^\n",my.getAddress());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;
			case "6": //비밀번호 변경
				for(User my : userList) {
					if(my.getId().equals(login.getId())) {
						System.out.printf("현재 비밀번호는 '%s' 입니다.\n", my.getPassword());
						System.out.print("변경할 비밀번호를 입력하세요: ");
						String password = scan.nextLine();
						my.setPassword(password);
						System.out.printf("'%s' 비밀번호 변경 완료 되었습니다.^^\n",my.getPassword());
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						userSave();
					}
				}
				break;	
			default: 
				System.out.print("잘못된 입력입니다. 다시 입력해주세요: "); 
				userInfoModify(login);
				break;
		}
	}
	/**
	 * 
	 *  마이페이지 - 퇴사 신청하기
	 *  - 퇴사사유, 작성일 2가지를 사용자가 작성한다. 
	 *  - 작성이 완료되면 퇴사신청명단.txt에 저장이된다.
	 *  
	 *  @param im 사용자의 정보입니다.
	 * 
	 */
	public static void leaveSign(User im) {
		
		String temp = "";
		for (User my : userList) {
			if(my.getId().equals(im.getId())) {
				System.out.print("퇴사 사유를 입력해주세요: ");
				String leave = scan.nextLine();
				System.out.print("작성일을 입력해주세요(YYYY-MM-DD): ");
				String date = scan.nextLine();
				temp = my.getId() + "■" + date + "■" + leave; 
			}
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(leavePath, true));
			writer.write(temp);	
			writer.newLine();
			writer.close();
		} catch (Exception e) {}
		
		System.out.println("퇴사 신청이 완료 되었습니다.");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

		pause();
		
	} 
	
	/**
	 * 
	 * 사용자 야근 시간 구하는 메소드입니다.
	 * 해당 날짜의 폴더(salaryPath)에서 사용자의 출/퇴근 기록 확인합니다.
	 * 출근 9시 기준, 퇴근 18시 기준
	 * 정상출근 하면서, 퇴근시간 초과시 야근시간 증가
	 * 
	 * @return 사용자가 야근시간 값을 반환합니다.
	 * 
	 */
	public static double nightPay() {
		int overTime = 0;
		File dir = new File(salaryPath);
		File[] list = dir.listFiles();
		for (File sub : list) {
			if (sub.isFile()) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(salaryPath + "\\" + sub.getName()));
					
					for (User my : userList) {
						String temp;
						int go = 0;
						int out = 0;
						while ((temp = reader.readLine()) != null) {
							String[] data = temp.split("■");
							if(my.getId().equals(data[0])) {
								go = Integer.parseInt(data[1].substring(0, 2));
								out = Integer.parseInt(data[2].substring(0, 2));
								if (go <= 9 && out >= 18) {
									overTime += out - 18;
								}
							}
						}
						reader.close();
					}
				} catch (Exception e) {}
			}
		}
		return overTime;
	}
	
	/**
	 * 
	 * 사용자의 예상 월 월급을 확인할 수 있는 메소드입니다.
	 * 사용자의 연봉(userSalary)파일을 받아옵니다.
	 * 한달 세금과 보험료, 연봉을 보여줍니다.
	 * @param im 사용자의 정보입니다.
	 * 
	 */
	public static void detailPay(User im) {
		
		int monthPay = userSalary(im)/12; //월급
		int hourPay = monthPay/160; //시급
		double overPay = nightPay() * (hourPay + (hourPay*0.5)); 
		
		double pension = 0.045*monthPay; 
		double health = 0.0343*monthPay; 
		double employ = 0.008*monthPay; 
		double tax = 0.1*monthPay; 
		
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.printf("\"%s\"님의 현재 연봉 금액: %,d원\n", im.getName(), userSalary(im));
		System.out.printf("국민연금(4.5%%): %,.0f원\n", pension);
		System.out.printf("건강보험(3.43%%): %,.0f원\n", health);
		System.out.printf("고용보험(0.8%%): %,.0f원\n", employ);
		System.out.printf("근로소득세(10%%): %,.0f원\n", tax);
		System.out.printf("이번달 추가 야근수당: %,.0f원\n", overPay);
		System.out.printf("예상 월 급여: %,.0f원\n" , monthPay + overPay - (pension+health+employ+tax));
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		pause();
	}
	
	/**
	 * 
	 * 사용자의 예상 월 급여 계산하는 메소드입니다.
	 * 사용자의 현재 월급 상태를 보여줍니다.
	 * 
	 * @param im 사용자의 정보입니다.
	 * @return 사용자의 야근시간과 각 세금, 연봉을 계산하여 값을 반환합니다.
	 * 
	 */
	public static double userPay(User im) {
		int monthPay = userSalary(im)/12; //월급
		int hourPay = monthPay/160; //시급
		double overPay = nightPay() * (hourPay + (hourPay*0.5)); //야근수당1.5배
		double pension = 0.045*monthPay; //국민연금4.5%
		double health = 0.0343*monthPay; //건강보험3.43%
		double employ = 0.008*monthPay; //고용보험0.8%
		double tax = 0.1*monthPay; //근로소득세10%
		return monthPay - (pension + health + employ + tax) + overPay;
	}
	
	/**
	 * 
	 * 사용자가 속한 부서의 부서원 현황을 출력해주는 메소드입니다.
	 * 
	 * @param im 사용자의 정보입니다.
	 * 
	 */
	public static void memberList(User im) { 
		userInfo();
		System.out.println("이름\t나이  팀명   직급\t  전화번호\t\t\t이메일\t  주소");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		for (User my : userList) {
			if(my.getTeam().equals(im.getTeam())) {
				System.out.printf("%s  %s  %s  %3.2s\t%s \t%23s %s\n"
				, my.getName()
				, my.getAge()
				, my.getTeam()
				, my.getPosition()
				, my.getPhone()
				, my.getEmail()
				, my.getAddress());
			}
		}
		pause();
	}
	
	public static void pause() {
		System.out.println("메인으로 돌아가려면 엔터를 입력하세요.");
		scan.nextLine();
	}
}
