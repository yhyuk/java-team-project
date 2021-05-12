package AdminVacation;

/**
 * 휴가 정보를 담는 클래스
 * @author 유기호
 */
public class Vacation {
	//휴가 데이터 담을 클래스
	
	private String id; //사번
	private String name; //이름
	private String team; //부서
	private String position; //직급
	private String startDate; //휴가 시작 날짜
	private String endDate; //휴가 종료 날
	private String content; //휴가 내용 -> 필요(?)
	
	/**
	 * 기본 생성자
	 */
	public Vacation() {
		
	}

	/**
	 * 매개변수를 받아 멤버를 초기화하는 생성자
	 * @param id 사번
	 * @param name 이름
	 * @param team 부서
	 * @param position 직급
	 * @param startDate 휴가 시작 날짜
	 * @param endDate 휴가 종료 날짜
	 * @param content 휴가 내용
	 */
	public Vacation(String id, String name, String team, String position, String startDate, String endDate,
			String content) {
		this.id = id;
		this.name = name;
		this.team = team;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
	}

	/**
	 * 사번 Getter
	 * @return 사번
	 */
	public String getId() {
		return id;
	}

	/**
	 * 사번 Setter
	 * @param id 사번
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 이름 Getter
	 * @return 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 이름 Setter
	 * @param name 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 부서 Getter
	 * @return 부서
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * 부서 Setter
	 * @param team 부서
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * 직급 Getter
	 * @return 직급
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * 직급 Setter
	 * @param position 직급
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * 휴가 시작 날짜 Getter
	 * @return 휴가 시작 날짜
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 휴가 시작 날짜 Setter
	 * @param startDate 휴가 시작 날짜
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 휴가 종료 날짜 Getter
	 * @return 휴가 종료 날짜
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 휴가 종료 날짜 Setter
	 * @param endDate 휴가 종료 날짜
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 휴가 내용 Getter
	 * @return 휴가 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 휴가 내용 Setter
	 * @param content 휴가 내용
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Override toString 자동 생성
	 */
	@Override
	public String toString() {
		return "Vacation [id=" + id + ", name=" + name + ", team=" + team + ", position=" + position + ", startDate="
				+ startDate + ", endDate=" + endDate + ", content=" + content + "]";
	}

}
