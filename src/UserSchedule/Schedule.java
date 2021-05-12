package UserSchedule;

/**
 * 부서별 일정 내용을 정리하기 위한 클래스입니다.
 * @author User 1조 경지윤
 *
 */
public class Schedule {
	
	/**
	 * 회사일정 정보<br/>
	 * 글번호, 날짜, 제목, 내용 
	 */

	private int num;
	private String date;
	private String title;
	private String txt;
	
	
	
	
	/**
	 * 생성자
	 * @param num
	 * @param date
	 * @param title
	 * @param txt
	 */
	public Schedule(int num, String date, String title, String txt) {
		this.num = num;
		this.date = date;
		this.title = title;
		this.txt = txt;
	}
	


	public Schedule() {

	}


	/**
	 * 
	 * @return 글번호
	 */
	public int getNum() {
		return num;
	}
	/**
	 * 
	 * @param num
	 * 글번호
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 
	 * @return 해당일정의 날짜
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 
	 * @param date
	 * 해당일정의 날짜
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 
	 * @return 해당일정의 제목
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 * 해당일정의 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return 해당일정의 내용
	 */
	public String getTxt() {
		return txt;
	}
	/**
	 * 
	 * @param txt
	 * 해당일정의 내용
	 */
	public void setTxt(String txt) {
		this.txt = txt;
	}
	
}
