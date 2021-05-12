package AdminManage;

/**
 * 회원 삭제 Getter, Setter
 * @author 김예운
 *
 */

public class Employee_del {

	/**
	 * @param id 사번
	 * @param date 퇴사신청날짜
	 * @param reason 퇴사사유
	 */
	private String id;
	private String date;
	private String reason;

	
	/**
	 * 아이디를 받아오는 메소드
	 * @return 사번
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 아이디를 돌려주는 메소드
	 * @param id 사번
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 퇴사 날짜를 받아오는 메소드
	 * @return 퇴사날짜
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * 퇴사 날짜를 돌려주는 메소드
	 * @param date 퇴사날짜
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * 퇴사 사유를 받아오는 메소드
	 * @return 퇴사사유
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * 퇴사 사유를 돌려주는 메소드
	 * @param reason 퇴사사유
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * toString()메소드입니다.
	 */
	@Override
	public String toString() {
		return "Employee_del [" + id + ", date=" + date + ", reason=" + reason + "]";
	}
	
	/**
	 * 
	 * @param id 사번
	 * @param date 퇴사날짜
	 * @param reason 퇴사사유
	 */
	public Employee_del(String id, String date, String reason) {
		super();
		this.id = id;
		this.date = date;
		this.reason = reason;

	}
	
	
}
