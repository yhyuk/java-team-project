package AdminManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import User.User;

public class UserAdd {

	static Scanner scan = new Scanner(System.in);

	private static ArrayList<User> empList;
	private static String empPath; // 상대경로
	private static String choice;

	static {
		empPath = "data\\사용자정보.txt";
		empList = new ArrayList<User>();

	}

	public UserAdd() {
		getEmpInfo();
		addEmp();

	}

	public void getEmpInfo() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(empPath));
			String txt = "";

			while ((txt = reader.readLine()) != null) {
				String[] temp = txt.split("■");

				empList.add(new User(temp[0], temp[1], temp[2], 
						Integer.parseInt(temp[3]), 
						temp[4], temp[5], temp[6],
						temp[7], temp[8], temp[9]));

			}

			reader.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void addEmp() {

		System.out.println();
		System.out.println("      [직원 등록하기]");
		
		String id = checkId();

		String team = checkTeam();

		String position = checkPosition();
		
//		System.out.print("직급입력: ");
//		String position = scan.nextLine();
		

		Date n = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-M-d");
		String now = transFormat.format(n);



		
		empList.add(new User(id,"","", 0, team, position, "", "", "", now));
		
	
	
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(empPath, true));

			writer.write(String.format("%s■%s■%s■%d■%s■%s■%s■%s■%s■%s\r\n", 
					empList.get(empList.size()-1).getId(), 
					empList.get(empList.size()-1).getPassword(),
					empList.get(empList.size()-1).getName(), 
					empList.get(empList.size()-1).getAge(), 
					empList.get(empList.size()-1).getTeam(), 
					empList.get(empList.size()-1).getPosition(), 
					empList.get(empList.size()-1).getPhone(), 
					empList.get(empList.size()-1).getEmail(),
					empList.get(empList.size()-1).getAddress(), 
					empList.get(empList.size()-1).getJoinDate()));
			writer.close();


		} catch (Exception e) {
			System.out.println("직원 갱신에서 에러 발생");
			System.out.println(e);

		}
		System.out.println("직원을 추가했습니다.");
		System.out.println();
	}
		
	public String checkPosition() {
		
		System.out.print("직급입력: ");
		String position = scan.nextLine();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(empPath));
			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");
				
				if (data[5].equals(position)) {
					new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5], data[6], data[7],
							data[8], data[9]);
					return position;
					
				} else {

				}
			}
			System.out.println("해당하는 직급이 없습니다. 다시입력하세요.");
			checkTeam();
			reader.close();
		} catch (Exception e) {
		}
		return position;
		
	}

	public String checkTeam() {

		System.out.print("부서입력: ");
		String team = scan.nextLine();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(empPath));
			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");
			
				if (data[4].equals(team)) {
					
					new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5], data[6], data[7],
							data[8], data[9]);
					return team;
					
				} else {

				}
			}
			System.out.println("해당하는 부서가 없습니다. 다시입력하세요.");
			checkTeam();
			reader.close();
		} catch (Exception e) {
		}
		return team;
	}
	

	public String checkId() {

		// System.out.println(addId);

		// String id = scan.nextLine();
		// 아이디 유효성 검사
		System.out.print("등록할 사번(숫자 7개)입력 : ");
		String id = scan.nextLine();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(empPath));

			String temp;
			while ((temp = reader.readLine()) != null) {
				String[] data = temp.split("■");

				if (data[0].contains(id)) {
					new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5],
							data[6], data[7], data[8], data[9]);
				System.out.println("동일한 사번이 존재합니다. 다시입력하세요.");
					checkId();
				}
			}

		} catch (Exception e) {
		}
		return id;

	}
	

	public void pause() {
		System.out.println("계속하려면 엔터를 입력하세요.");
		scan.nextLine();
	}

}
