package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import User.User;

/**
 * 
 * 회사 사원 관리 프로그램. 
 * 실행시 처음 등장하는 화면을 보여주는 Main클래스입니다.
 * 
 * @author 1조 팀원
 *
 */
public class MainClass {
	
	static Scanner scan = new Scanner(System.in);
	static ArrayList<User> userList = new ArrayList<User>();
	private static String userPath = "data\\사용자정보.txt";
	static User user;
	static Calendar now = Calendar.getInstance();

	public static void main(String[] args) {
		
		userInfo(); 
		initAttendanceRecord(now); 
		initEmpStatusData(now); 
		
		boolean flag = true;
		while(flag) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 회사 사원 관리 프로그램 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1. 로그인하기");
			System.out.println("2. PW찾기");
			System.out.println("0. 프로그램 종료");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("번호 입력: ");
			String sel = scan.nextLine();
			
			switch(sel) {
				case "1":
					Login login = new Login(userList); //관리자 or 사용자 로그인
					break;
				case "2":
					PwFind pwfind = new PwFind(userList); //비밀번호 찾기
					userSave();
					break;
				case "0":
					System.out.print("프로그램을 종료하시겠습니까? (y/n): ");
					String re = scan.nextLine();
					if (re.equals("y")) {
						System.out.println("프로그램을 종료합니다. 감사합니다.");
						flag=false;
					} else if (re.equals("n")) break;
			}//switch
		}//while
	}//main

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
	 * 사용자 정보 저장 
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
	
	/**
	 * 
     * 현재 날짜를 기준으로 근태기록을 초기화<br/>
     * 현재 날짜의 근태기록 파일이 없으면 빈 근태기록 파일 생성 
     * 
     * @param now 현재시각
     * 
     */
	public static void initAttendanceRecord(Calendar now) {
		
		try {
			String year = now.get(Calendar.YEAR) + "";
			String month = String.format("%02d", now.get(Calendar.MONTH)+1);
			
			//경로 폴더가 없는 경우 생성
			String dirPath = "data\\근태기록\\" + year + "년\\" + month + "월";
			File dir = new File(dirPath);
			dir.mkdirs();
			
			//파일 경로 지정
			String fileName = String.format("%tF.txt", now);
			String filePath = dirPath + "\\" + fileName;
			File file = new File(filePath);
			
			if (!file.exists()) { //파일이 없는 경우 빈 파일 생성
				BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
				writer.write("");
				writer.close();
			}
		} catch (Exception e) {
			//TODO 에러처리 어떻게 할지...
			e.printStackTrace();
		}
	}
	
    /**
     * 
     * 현재 날짜를 기준으로 사원 상태 초기화<br/>
     * 사원 상태 파일의 기록 날짜가 현재 날짜와 다르면 파일 내용 초기화
     * 
     * @param now 현재시각
     * 
     */
	public static void initEmpStatusData(Calendar now) {
		//로그인 시 수행...
		
		try {
			//사원 상태.txt 파일 날짜 확인
			String filePath = "data\\사원 상태.txt";
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String date = reader.readLine(); //파일 생성 날짜 (첫번째 줄)
			reader.close();
			
			if (!date.equals(String.format("%tF", now))) {
				//금일 날짜가 아닌 경우 초기화 (파일 업데이트)
				
				HashMap<String,String> empStatusMap = new HashMap<String, String>(); //Map-<key:사원번호, value:상태>
				
				for (User my : userList) {
					String id = my.getId();
					empStatusMap.put(id, "결근");
					
				}
				
				//2. 휴가리신청명단.txt -> 휴가자 사번 긁어오기
				filePath = "data\\휴가신청명단.txt";
				reader = new BufferedReader(new FileReader(filePath));
				String line = "";
				while ((line = reader.readLine()) != null) {
					String id = line.split("■")[1];
					String start = line.split("■")[2];
					String end = line.split("■")[3];
					String approval = line.split("■")[5];
					
					if (approval.equals("승인")) { //승인된 것만 거르기
						
						//휴가 시작 날짜 캘린더 변수 생성
						Calendar startDate = Calendar.getInstance();
						int startYear = Integer.parseInt(start.split("-")[0]);
						int startMonth = Integer.parseInt(start.split("-")[1]) - 1;
						int startDayOfMonth = Integer.parseInt(start.split("-")[2]);
						startDate.set(startYear, startMonth, startDayOfMonth, 0, 0, 0);
						startDate.set(Calendar.MILLISECOND, 0);
						
						//휴가 종료 날짜 캘린더 변수 생성
						Calendar endDate = Calendar.getInstance();
						int endYear = Integer.parseInt(end.split("-")[0]);
						int endMonth = Integer.parseInt(end.split("-")[1]) - 1;
						int endDayOfMonth = Integer.parseInt(end.split("-")[2]);
						endDate.set(endYear, endMonth, endDayOfMonth, 0, 0, 0);
						endDate.set(Calendar.MILLISECOND, 0);
						
						//오늘 날짜가 휴가 기간에 들어있는지 확인 -> 기간 내에 있으면 휴가 상태로 변경
						if (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0) {
							empStatusMap.put(id, "휴가");
						}
						
					}
				}
				reader.close();
				
				//파일 내용 생성
				String result = String.format("%tF", now) + "\r\n"; //첫줄에 날짜 입력
				Iterator<String> keys = empStatusMap.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					result += key + "■" + empStatusMap.get(key) + "\r\n";
				}
				
				//파일 쓰기 - 사원 상태.txt
				filePath = "data\\사원 상태.txt";
				BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
				writer.write(result);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
