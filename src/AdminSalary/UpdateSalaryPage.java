package AdminSalary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 관리자 - 급여 관리 페이지<br/>
 * 직급별 급여를 수정할 수 있다.
 * @author 유기호
 *
 */
public class UpdateSalaryPage {
	//직원 급여 관리 메뉴 클래스

	private ArrayList<PositionSalary> salaryList = new ArrayList<PositionSalary>();
	
	/**
	 * 생성과 동시에 직급별 급여 파일을 읽고 화면을 출력한다.
	 */
	public UpdateSalaryPage() { //생성자
		//생성과 동시에 파일 읽고 데이터 저장
		this.load();
		this.showSalary();
		this.selectMenu();
	}
	
	/**
	 * 직급별 급여 파일을 읽고 멤버 변수에 저장한다.
	 */
	public void load() {
		//파일 읽고 데이터 저장
		try {
			//파일 내용 읽기 - 직급별 급여.txt
			String filePath = "data\\직급별 급여.txt";
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				//salaryList에 데이터 저장
				String position = line.split("■")[0];
				int salary = Integer.parseInt(line.split("■")[1]);
				PositionSalary posSalary = new PositionSalary(position, salary);
				this.salaryList.add(posSalary);
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 현재 직급별 급여를 출력한다.
	 */
	public void showSalary() {
		//현재 직급별 급여 보여주는 메소드
		System.out.println("■■■■■■■■■■■■■■■■■■■■직급별 급여(연봉)■■■■■■■■■■■■■■■■■■■■");
		System.out.println("\t[직급]\t[급여]");
		for (PositionSalary posSalary: this.salaryList) {
			System.out.printf("\t%s\t%,d원\n", posSalary.getPosition(), posSalary.getSalary());
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
	}
	
	/**
	 * 메뉴를 출력하고 번호를 입력받아 선택된 메뉴를 실행킨다.
	 */
	public void selectMenu() {
		//메뉴 선택 메소드
		//1. 급여 수정	0. 뒤로가기
		
		Scanner scan = new Scanner(System.in);
		System.out.println("원하는 메뉴 번호를 선택하세요.\t1. 급여 수정\t0. 뒤로가기");
		
		boolean loop = true;
		while (loop) {
			System.out.print("입력: ");
			String sel = scan.nextLine();
			
			if (sel.equals("1")) {
				//급여 수정
				loop = false;
				selectPosition();
			} else if (sel.equals("0")) {
				//뒤로가기
				loop = false;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	/**
	 * 급여를 수정할 직급을 선택한다.
	 */
	public void selectPosition() {
		//급여를 수정할 직급 선택
		Scanner scan = new Scanner(System.in);
		System.out.println("급여 수정을 원하는 직급을 선택하세요.");
		for (int i=0; i<this.salaryList.size(); i++) {
			System.out.printf("%d. %s\t", i+1, this.salaryList.get(i).getPosition());
		}
		System.out.println();
		
		boolean loop = true;
		while (loop) { //올바른 입력을 할때까지 무한루프
			System.out.print("입력(번호): ");
			try {
				int sel = scan.nextInt();  //사용자 입력값(직급 선택(번호))
				
				if (sel > 0 && sel <= this.salaryList.size()) {
					//직급 선택(1 ~ 직급 개수)
					loop = false; //올바른 입력
					System.out.printf("%s 직급을 선택하였습니다.\n", this.salaryList.get(sel-1).getPosition());
					//급여 변경 메소드 실행
					updatePosSalary(this.salaryList.get(sel-1));
				} else {
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				}
			} catch (Exception e) {
				//숫자를 입력하지 않은 경우
				scan.nextLine(); //버퍼 비우기(?)
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	/**
	 * 선택된 직급의 급여를 수정한다.
	 * @param positionSalary 선택된 직급의 급여
	 */
	public void updatePosSalary(PositionSalary positionSalary) {
		//급여 변경 메소드
		Scanner scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) { //올바른 입력을 할때까지 무한루프
			System.out.print("급여를 입력하세요: ");
			try {
				int salary = scan.nextInt(); //사용자 입력값(급여)
				loop = false; //올바른 입력
				positionSalary.setSalary(salary); //급여 수정
				save(); //파일에 저장
				System.out.println("급여 수정이 완료되었습니다.");
				//TODO 완료하면 페이지 어디로 이동할지... 이 위치에 작성해야함
				//일단은 다시 직급별 급여 수정 페이지로...
				UpdateSalaryPage updateSalary = new UpdateSalaryPage();
			} catch (Exception e) {
				//숫자를 입력하지 않은 경우
				scan.nextLine(); //버퍼 비우기(?)
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		
	}

	/**
	 * 직급별 급여 리스트 멤버를 파일에 저장한다.
	 */
	public void save() {
		//변경된 데이터 파일에 저장
		try {
			String filePath = "data\\직급별 급여.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			String txt = "";
			for (PositionSalary posSalary : this.salaryList) {
				txt += posSalary.getPosition() + "■" + posSalary.getSalary() + "\r\n";
			}
			writer.write(txt);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
