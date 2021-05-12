package AdminVacation;

/**
 * 달력 클래스<br/>
 * 휴가 일정표의 부모 클래스
 * @author 유기호
 */
public class MonthCalendar {
	//달력 생성용 클래스
	
	/**
	 * 매개변수로 받은 년, 월의 달력을 출력한다.
	 * @param year 년도
	 * @param month 월
	 */
	public void printCalendar(int year, int month) {
		//year년 month월 달력 출력
		
		int lastDay = 0; //마지막일
		int day_of_week = 0; //요일
		
		//마지막일?
		lastDay = getLastDay(year, month);
		
		//해당 월의 1일의 요일?
		day_of_week = getDayOfWeek(year, month);
		

		//달력 출력하기
		System.out.println();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.printf("                     %d년 %d월\n", year, month);
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("[일]\t[월]\t[화]\t[수]\t[목]\t[금]\t[토]");
		
		
		//1일의 요일을 맞추기 위해서..
		for (int i=0; i<day_of_week; i++) {
			System.out.print("\t");
		}
		
		
		//날짜 출력
		for (int i=1; i<=lastDay; i++) {
			
			System.out.printf("%3d\t", i);
			
			//토요일마다 개행 
			if ((i - 1) % 7 == (6 - day_of_week)) {
				System.out.println();
			}
			
		}
		
	}//output
	
	/**
	 * 서기 1년 1월 1일 ~ year년 month월 1일까지 총 일수를 계산
	 * @param year 년도
	 * @param month 월
	 * @return 총 일수
	 */
	public int getDayOfWeek(int year, int month) {
		
		//서기 1년 1월 1일 ~ year년 month월 1일까지 총 며칠?
		
		//누적 변수
		int totalDays = 0;
		
		//1.1.1 ~2021.4.1
		
		//1.1.1 ~ 2020.12.31 -> 1 ~ 2020 x 365
		
		for (int i=1; i<year; i++) { //전년도까지
			
			if (isLeafYear(i)) {
				totalDays += 366;
			} else {
				totalDays += 365;
			}
			
		}
		
		//2021.1.1 ~ 2021.3.31
		for (int i=1; i<month; i++) { //전월까지
			
			totalDays += getLastDay(year, i);
			
		}
		
		//2021.4.1 ~ 2021.4.1
		totalDays++;
		
		return totalDays % 7;
		
	}//getDayOfWeek
	
	/**
	 * 선택된 년/월의 마지막 날짜를 구한다.
	 * @param year 년도
	 * @param month 월
	 * @return 해당 월의 마지막 날짜
	 */
	public int getLastDay(int year, int month) {
		
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31; //return문: 메소드를 종료하는 역할(break 유사) + 값 반환
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return isLeafYear(year) ? 29 : 28;
			//default:
			//	return 0;
		}
		
		return 0;
		
	}//getLastDay
	
	/**
	 * 해당 년도가 윤년인지 확인한다.
	 * @param year 년도
	 * @return true:윤년/false:평년
	 */
	public boolean isLeafYear(int year) { //윤년: true, 평년: false
		
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					return true; //윤년
				} else {
					return false; //평년
				}
			} else {
				return true; //윤년
			}
		} else {
			return false; //평년
		}
		
	}//isLeafYear
	
}
