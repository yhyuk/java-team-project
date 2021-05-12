package UserNotice;

/**
 * 공지사항 정보를 객체로 관리하는 클래스
 * @author 82106
 *
 */
public class NoticeMemo {
	private int num;
	private String title;
	private String date;
	private String readTargetTeam;
	private String readTargetPosition;
	private String content;
	
	/**
	 * private멤버변수의 값을 사용할 수 있도록 도와주는 생성자
	 * 
	 * @param num 공지사항 번호
	 * @param title 공지사항 제목
	 * @param date 공지사항을 작성한 날짜
	 * @param readTargetTeam 공지사항을 읽을 팀명
	 * @param readTargetPosition 공지사항을 읽을 직급
	 * @param content 공지사항 내용
	 */
	public NoticeMemo(int num, String title, String date, String readTargetTeam, String readTargetPosition, String content) {
		this.num = num;
		this.title = title;
		this.date = date;
		this.readTargetTeam = readTargetTeam;
		this.readTargetPosition = readTargetPosition;
		this.content = content;
	}
	
	/**
	 * 기본 생성자
	 */
	public NoticeMemo() {
	
	}
	
	/**
	 * toString을 오버라이드해서 개발자가 객체의 상태를 확인할 수 있게 도와준다. 
	 */
	@Override
	public String toString() {
		return String.format("Notice [번호= %d,\t제목= %s,\t날짜= %s,\t배포대상팀= %s,\t배포대상팀= %s,\t내용= %s]"
				, this.title
				, this.date
				, this.readTargetTeam
				, this.readTargetPosition
				, this.content);
	}

	/**
	 * 
	 * @return 공지사항 번호
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 
	 * @param num 공지사항 번호
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 
	 * @return 공지사항 제목
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param title 공지사항 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return 공지사항 작성 날짜
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 
	 * @param date 공지사항 작성 날짜
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 
	 * @return 공지사항을 읽을 팀 이름
	 */
	public String getReadTargetTeam() {
		return readTargetTeam;
	}

	/**
	 * 
	 * @param readTargetTeam 공지사항을 읽는 팀이름
	 */
	public void setReadTargetTeam(String readTargetTeam) {
		this.readTargetTeam = readTargetTeam;
	}

	/**
	 * 
	 * @return 공지사항을 읽을 직급
	 */
	public String getReadTargetPosition() {
		return readTargetPosition;
	}

	/**
	 * 
	 * @param readTargetPosition 공지사항을 읽을 직급
	 */
	public void setReadTargetPosition(String readTargetPosition) {
		this.readTargetPosition = readTargetPosition;
	}

	/**
	 * 
	 * @return 공지사항 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 공지사항 내용
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}