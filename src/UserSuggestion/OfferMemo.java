package UserSuggestion;

/**
 * 휴가신청명단 정보 받아오는 클래스입니다.
 * @author 1조 경지윤
 *
 */
public class OfferMemo {

	/**
	 * 건의사항 번호, 건의사항을 보낸 직원의 사번, 제목, 작성날짜, 내용
	 */
	private int num;
	private String id;
	private String title;
	private String date;
	private String content;

	/**
	 * private멤버 변수의 사용을 도와주는 생성자
	 * @param num 건의사항 번호
	 * @param id 건의사항을 작성한 직원의 사번
	 * @param title 건의사항 제목
	 * @param date 건의사항을 작성한 날짜 
	 * @param content 건의사항 내용
	 */
	public OfferMemo(int num, String id, String title, String date, String content) {
		this.num = num;
		this.id = id;
		this.title = title;
		this.date = date;
		this.content = content;

	}

	/**
	 * 기본 생성자
	 */
	public OfferMemo() {

	}

	/**
	 * toString을 오버라이드해서 개발자가 객체의 상태를 확인할 수 있게 도와준다.
	 */
	@Override
	public String toString() {
		return String.format("[번호: %d,\t사번: %s,\t제목: %s,\t날짜: %s,\t내용: %s]"
								, this.num
								, this.id
								, this.title
								, this.date
								, this.content);
	}

	/**
	 * 
	 * @return 건의사항 번호
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 
	 * @param num 건의사항 번호
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 
	 * @return 건의사항을 작성하는 사람의 사번
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id 건의사항을 작성하는 사람의 사번
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return 건의사항 제목
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title 건의사항 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return 건의사항을 작성한 날짜
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 
	 * @param date 건의사항을 작성한 날짜
	 */
	public void setDate(String date) {
		this.date = date;
	}


	/**
	 * 
	 * @return 건의사항 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content 건의사항 내용
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
