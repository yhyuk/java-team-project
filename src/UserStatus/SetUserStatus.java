package UserStatus;

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

/**
 * 사용자 - 근태 관리 페이지<br/>
 * 사용자의 상태를 변경할 수 있다.
 * @author 유기호
 */
public class SetUserStatus {
	//근태 관리 메뉴 클래스
	
	private ArrayList<EmpStatus> empStatusList = new ArrayList<EmpStatus>(); //파일에서 불러온 데이터 저장 객체리스트
	private String date = ""; //오늘 날짜
	private String id = ""; //사원 id - 받아오는 값
	private String status = ""; //사원 상태
	private int index = 0; //empStatusList에서 현재 사원 데이터가 들어있는 index
	
	/**
	 * 생성과 동시에 멤버 변수를 초기화하고 페이지를 출력한다.
	 * @param id 사번
	 */
	public SetUserStatus(String id) {
		
		this.id = id;
		this.load();
		this.getEmpStatus();
		this.showUserStatus();
		this.selectMenu();
	}
	
	/**
	 * 사용자의 현재 상태를 출력한다.
	 */
	public void showUserStatus() {
		//사용자의 현재 상태 출력
		System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■현재 상태: %s■■■■■■■■■■■■■■■■■■■■■■■\n", this.status);
	}
	
	/**
	 * 메뉴를 출력하고 번호를 입력받아 선택된 메뉴를 실행킨다.
	 */
	public void selectMenu() {
		//메뉴 선택 메소드
		//1. 출근	2. 퇴근	3. 외출/복귀	4. 외근/복귀	0. 뒤로가기
		
		Scanner scan = new Scanner(System.in);
		System.out.println("원하는 메뉴 번호를 선택하세요.");
		System.out.println("1.출근\t 2.퇴근\t3.외출/복귀\t4.외근/복귀\t0.뒤로가기");
		
		boolean loop = true;
		while (loop) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("입력: ");
			String sel = scan.nextLine();
			
			switch (sel) {
				case "1":
					loop = false;
					goWork(); //출근 메소드
					SetUserStatus userStatus = new SetUserStatus(this.id); //다시 근태 관리 메뉴로 돌아가기
					break;
				case "2":
					loop = false;
					leaveWork(); //퇴근 메소드
					userStatus = new SetUserStatus(this.id); //다시 근태 관리 메뉴로 돌아가기
					break;
				case "3":
					loop = false;
					goOut(); //외출/복귀 메소드
					userStatus = new SetUserStatus(this.id); //다시 근태 관리 메뉴로 돌아가기
					break;
				case "4":
					loop = false;
					workOut(); //외근/복귀 메소드
					userStatus = new SetUserStatus(this.id); //다시 근태 관리 메뉴로 돌아가기
					break;
				case "0":
					loop = false;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		}
	}
	
	public void getEmpStatus() {
		//사원 근태 상태 가져오기
		for (int i=0; i<this.empStatusList.size(); i++) {
			if (this.empStatusList.get(i).getId().equals(this.id)) {
				this.status = this.empStatusList.get(i).getStatus(); //상태 저장
				this.index = i; //사원의 인덱스 저장
				break;
			}
		}
	}
	
	/**
	 * 사원 상태 파일을 읽고 데이터를 멤버 변수에 저장한다.
	 */
	public void load() {
		//파일에서 상태 받아오기 - static empStatusList 에 저장
		
		//파일 읽기
		//파일명 - 사원 상태.txt
		try {
			String filePath = "data\\사원 상태.txt";
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			this.date = reader.readLine(); //파일 생성 날짜 (첫번째 줄) -> date 에 저장
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				String id = line.split("■")[0];
				String status = line.split("■")[1];
				EmpStatus empStatus = new EmpStatus(id, status);
				this.empStatusList.add(empStatus);
			}
			reader.close();
		} catch (Exception e) {
			//TODO 예외처리 필요
			e.printStackTrace();
		}
		
	}

	/**
	 * 외근/복귀를 처리한다.
	 */
	public void workOut() {

		//외근/복귀 메소드

		switch (this.status) {
			case "결근":
				System.out.println("출근 상태가 아닙니다.");
				break;
			case "출근":
				//외근
				//상태 변경 + 사원 상태 파일 수정
				
				//1. 상태 변경
				this.status = "외근";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
				
				System.out.println("외근이 완료되었습니다.");
				break;
			case "퇴근":
				System.out.println("현재 퇴근 상태입니다.");
				break;
			case "외출":
				System.out.println("현재 외출 상태입니다.");
				break;
			case "외근":
				//외근 복귀
				//상태 변경 + 사원 상태 파일 수정
				
				//1. 상태 변경
				this.status = "출근";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
				
				System.out.println("외근 복귀가 완료되었습니다.");
				break;
			case "휴가":
				System.out.println("현재 휴가 상태입니다.");
				break;
		}
		
	}

	/**
	 * 외출/복귀를 처리한다.
	 */
	public void goOut() {
		//외출/복귀 메소드

		switch (this.status) {
			case "결근":
				System.out.println("출근 상태가 아닙니다.");
				break;
			case "출근":
				//외출
				//상태 변경 + 사원 상태 파일 수정
				
				//1. 상태 변경
				this.status = "외출";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
				
				System.out.println("외출이 완료되었습니다.");
				break;
			case "퇴근":
				System.out.println("현재 퇴근 상태입니다.");
				break;
			case "외출":
				//외출 복귀
				//상태 변경 + 사원 상태 파일 수정
				
				//1. 상태 변경
				this.status = "출근";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
				
				System.out.println("외출 복귀가 완료되었습니다.");
				break;
			case "외근":
				System.out.println("현재 외근 상태입니다.");
				break;
			case "휴가":
				System.out.println("현재 휴가 상태입니다.");
				break;
		}
		
	}

	/**
	 * 퇴근 처리한다.
	 */
	public void leaveWork() {
		//퇴근 메소드
		
		//현재 시간 저장
		Calendar now = Calendar.getInstance();
		
		switch (this.status) {
			case "결근":
				System.out.println("출근 상태가 아닙니다.");
				break;
			case "출근":
				//상태 변경 + 사원 상태 파일 수정 + 근태 기록 파일 수정
				
				//1. 상태 변경
				this.status = "퇴근";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
	
				//3. 근태 기록 파일 수정
				//근태 기록 리스트 객체: ?
				//근태 기록 파일 수정 -> 퇴근 기록 추가
				try {
					//파일 경로 지정
					String year = this.date.split("-")[0];
					String month = this.date.split("-")[1];
					String dirPath = "data\\근태기록\\" + year + "년\\" + month + "월";
					String fileName = String.format("%s.txt", this.date);
					String filePath = dirPath + "\\" + fileName;
					
					//파일 읽기, 내용 수정 및 생성
					String result = "";
					BufferedReader reader = new BufferedReader(new FileReader(filePath));
					String line = "";
					while ((line = reader.readLine()) != null) {
						//모든 라인 result에 넣기!
						String id = line.split("■")[0];
						if (id.equals(this.id)) {
							//본인 사번 라인에 퇴근 시간 추가
							line = line + "■" + String.format("%tT", now);
						}
						result += line + "\r\n";
					}
					reader.close();
					
					BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
					writer.write(result);
					writer.close();
					
				} catch (Exception e) {
					//TODO 에러처리 어떻게 할지...
					e.printStackTrace();
				}
				
				System.out.println("퇴근이 완료되었습니다.");
				break;
			case "퇴근":
				System.out.println("현재 퇴근 상태입니다.");
				break;
			case "외출":
				System.out.println("현재 외출 상태입니다.");
				break;
			case "외근":
				System.out.println("현재 외근 상태입니다.");
				break;
			case "휴가":
				System.out.println("현재 휴가 상태입니다.");
				break;
				
		}
		
		
	}

	/**
	 * 출근을 처리한다.
	 */
	public void goWork() {
		//출근 메소드

		//현재 시간 저장
		Calendar now = Calendar.getInstance();
		
		switch (this.status) { //현재 상태에 따라 동작 방식 구분
			case "결근":
				//상태 변경 + 사원 상태 파일 수정 + 근태 기록 파일 수정
				
				//1. 상태 변경
				this.status = "출근";
				this.empStatusList.get(this.index).setStatus(this.status); //사원 상태 리스트(empStatusList) 수정
				
				//2. 사원 상태.txt 파일 수정
				save();
				
				//3. 근태 기록 파일 수정
				//근태 기록 파일 추가 -> 출근 기록 추가
				try {
					String year = this.date.split("-")[0];
					String month = this.date.split("-")[1];
					String dirPath = "data\\근태기록\\" + year + "년\\" + month + "월";
					String fileName = String.format("%s.txt", this.date);
					String filePath = dirPath + "\\" + fileName;
					
					//파일 쓰기 - 새로운 내용 추가
					BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)); //Append 모드
					String line = String.format("%s■%tT\r\n", this.id, now);
					writer.write(line);
					writer.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("출근이 완료되었습니다.");
				break;
				
			case "출근":
				System.out.println("현재 출근 상태입니다.");
				break;
			case "퇴근":
				System.out.println("현재 퇴근 상태입니다.");
				break;
			case "외출":
				System.out.println("현재 외출 상태입니다.");
				break;
			case "외근":
				System.out.println("현재 외근 상태입니다.");
				break;
			case "휴가":
				System.out.println("현재 휴가 상태입니다.");
				break;
				
		}
		
	}
	

	/**
	 * 데이터를 사원 상태 파일에 저장한다.
	 */
	public void save() {
		//사원 상태.txt 파일 수정
		try {
			//파일 내용 생성
			String result = this.date + "\r\n"; //첫줄에 날짜 입력
			for (EmpStatus empStatus : this.empStatusList) {
				result += empStatus.getId() + "■" + empStatus.getStatus() + "\r\n";
			}
			
			//파일 쓰기
			String filePath = "data\\사원 상태.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(result);
			writer.close();
		} catch (Exception e) {
			//TODO 예외처리
			e.printStackTrace();
		}
	}
	
}
