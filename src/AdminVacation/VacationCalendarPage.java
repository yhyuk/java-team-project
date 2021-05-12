package AdminVacation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import User.User;

/**
 * 관리자 - 휴가 일정표 페이지<br/>
 * 직원들의 휴가 일정을 확인할 수 있다.
 * @author 유기호
 */
public class VacationCalendarPage extends MonthCalendar{
	//직원 일정표 메뉴 클래스

	private int year;
	private int month;
	private ArrayList<User> empList = new ArrayList<User>();
	private ArrayList<Vacation> vacationList = new ArrayList<Vacation>();

	
	/**
	 * 멤버 변수를 초기화하고 페이지를 출력한다.
	 * @param empList 사원 리스트(사용자정보.txt 파일의 내용)
	 */
	public VacationCalendarPage(ArrayList<User> empList) {
		//생성자
		
		this.empList = empList;
		
		Calendar now = Calendar.getInstance();
		this.year = now.get(Calendar.YEAR);
		this.month = now.get(Calendar.MONTH) + 1;
		
		//실행 순서 작성
		this.load();
		this.showPage();
	}
	
	/**
	 * 페이지를 출력한다.
	 */
	public void showPage() {
		//달력 출력 + 휴가자 리스트 출력 + 메뉴 선택
		this.printCalendar(this.year, this.month);
		this.showVacationList();
		this.selectMenu();
	}

	/**
	 * 휴가 목록을 날짜순으로 정렬하여 출력한다.
	 */
	public void showVacationList() {
		//해당 달의 휴가자 리스트 날짜순으로 정렬하여 출력
		
		System.out.printf("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■휴가자 목록■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n", this.year, this.month);
		System.out.println(" [사번]\t [이름] \t  [부서]\t    [직급]\t\t[날짜]\t\t\t[내용]");
		
		vacationList.sort(new Comparator<Vacation>() { //휴가 출발 날짜순 정렬
			@Override
			public int compare(Vacation o1, Vacation o2) {
				//양수 -> 리스트 뒤로
				//음수 -> 리스트 앞으로
				//0 -> 같음
				
				Calendar startDate1 = Calendar.getInstance(); //o1의 휴가 시작 날짜
				int startYear1 = Integer.parseInt(o1.getStartDate().split("-")[0]);
				int startMonth1 = Integer.parseInt(o1.getStartDate().split("-")[1]);
				int startDayOfMonth1 = Integer.parseInt(o1.getStartDate().split("-")[2]);
				startDate1.set(startYear1, startMonth1, startDayOfMonth1, 0, 0, 0);
				startDate1.set(Calendar.MILLISECOND, 0);
				
				Calendar endDate1 = Calendar.getInstance(); //o1의 휴가 종료 날짜
				int endYear1 = Integer.parseInt(o1.getEndDate().split("-")[0]);
				int endMonth1 = Integer.parseInt(o1.getEndDate().split("-")[1]);
				int endDayOfMonth1 = Integer.parseInt(o1.getEndDate().split("-")[2]);
				endDate1.set(endYear1, endMonth1, endDayOfMonth1, 0, 0, 0);
				endDate1.set(Calendar.MILLISECOND, 0);
				
				Calendar startDate2 = Calendar.getInstance(); //o2의 휴가 시작 날짜
				int startYear2 = Integer.parseInt(o2.getStartDate().split("-")[0]);
				int startMonth2 = Integer.parseInt(o2.getStartDate().split("-")[1]);
				int startDayOfMonth2 = Integer.parseInt(o2.getStartDate().split("-")[2]);
				startDate2.set(startYear2, startMonth2, startDayOfMonth2, 0, 0, 0);
				startDate2.set(Calendar.MILLISECOND, 0);

				Calendar endDate2 = Calendar.getInstance(); //o2의 휴가 종료 날짜
				int endYear2 = Integer.parseInt(o2.getEndDate().split("-")[0]);
				int endMonth2 = Integer.parseInt(o2.getEndDate().split("-")[1]);
				int endDayOfMonth2 = Integer.parseInt(o2.getEndDate().split("-")[2]);
				endDate2.set(endYear2, endMonth2, endDayOfMonth2, 0, 0, 0);
				endDate2.set(Calendar.MILLISECOND, 0);
				
				if (startDate1.compareTo(startDate2) < 0) {
					//시작 날짜가 앞선 경우 앞으로...
					return -1;
				} else if (startDate1.compareTo(startDate2) > 0) {
					//시작 날짜가 뒤진 경우 뒤로
					return 1;
				} else {
					//시작 날짜가 같은 경우 종료 날짜 비교
					if (endDate1.compareTo(endDate2) < 0) {
						//종료 날짜가 앞선 경우 앞으로
						return -1;
					} else if (endDate1.compareTo(endDate2) > 0) {
						//종료 날짜가 뒤진 경우 뒤로
						return 1;
					} else {
						//시작 날짜, 종료 날짜 모두 같은 경우
						return 0;
					}
				}
			}
		});
		
		for (Vacation vacation : vacationList) {
			//해당 달의 휴가자만 출력
			int startYear = Integer.parseInt(vacation.getStartDate().split("-")[0]);
			int endYear = Integer.parseInt(vacation.getEndDate().split("-")[0]);
			if (this.year >= startYear && this.year <= endYear) {
				//년도 비교 (휴가 날짜가 해당 년도에 포함되어있는지)
				int startMonth = Integer.parseInt(vacation.getStartDate().split("-")[1]);
				int endMonth = Integer.parseInt(vacation.getEndDate().split("-")[1]);
				if (this.month >= startMonth && this.month <= endMonth) {
					//월 비교 (휴가 날짜가 해당 월에 포함되어있는지)
					//휴가 데이터 출력
					System.out.printf("%s\t %s\t%s    %s  \t%s ~ %s\t\t%s\n", vacation.getId(), vacation.getName(), vacation.getTeam(), vacation.getPosition(), vacation.getStartDate(), vacation.getEndDate(), vacation.getContent());
				}
			}
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
	}

	/**
	 * 파일을 읽고 데이터를 저장한다.
	 */
	public void load() {
		//파일 읽고 데이터 저장
		try {
			//파일 내용 읽기 - 휴가신청명단.txt
			String filePath = "data\\휴가신청명단.txt";
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				//vacationList에 데이터 저장
				if (line.split("■")[5].equals("승인")) {
					//승인된 휴가만 저장
					String id = line.split("■")[1];
					String name = "";
					String team = "";
					String position = "";
					String startDate = line.split("■")[2];
					String endDate = line.split("■")[3];
					String content = line.split("■")[4];
					
					for (User emp : this.empList) {
						if (emp.getId().equals(id)) {
							name = emp.getName();
							team = emp.getTeam();
							position = emp.getPosition();
							break;
						}
					}
					
					Vacation vacation = new Vacation(id, name, team, position, startDate, endDate, content);
					this.vacationList.add(vacation);
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 사용자로부터 메뉴를 입력 받고 선택된 메뉴 페이지 객체를 생성한다.
	 */
	public void selectMenu() {
		//메뉴 선택 메소드
		//1. 이전 달	2. 다음 달	0. 뒤로가기
		
		Scanner scan = new Scanner(System.in);
		System.out.println("원하는 메뉴 번호를 선택하세요.");
		System.out.println("1. 이전 달\t2. 다음 달\t0. 뒤로가기");
		
		boolean loop = true;
		while (loop) {
			System.out.print("입력: ");
			String sel = scan.nextLine();
			
			if (sel.equals("1")) {
				//이전 달
				loop = false;
				if (this.month == 1) {
					//1월 달력인 경우 이전 년도 12월로 이동
					this.year--;
					this.month = 12;
				} else {
					this.month--;
				}
				this.showPage(); //바뀐 달력 페이지
			} else if(sel.equals("2")) {
				//다음 달
				loop = false;
				if (this.month == 1) {
					//12월 달력인 경우 다음 년도 1월로 이동
					this.year++;
					this.month = 1;
				} else {
					this.month++;
				}
				this.showPage(); //바뀐 달력 페이지
			} else if (sel.equals("0")) {
				//뒤로가기
				loop = false;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		
	}
	
}
