package UserStatus;

/**
 * 직원의 상태를 담는 클래스
 * @author 유기호
 */
public class EmpStatus {
	
	private String id;
	private String status;
	
	/**
	 * 기본 생성자
	 */
	public EmpStatus() {
		
	}
	
	/**
	 * 사번, 상태를 매개변수로 받아 멤버를 초기화하는 생성자
	 * @param id 사번
	 * @param status 상태
	 */
	public EmpStatus(String id, String status) {
		this.id = id;
		this.status = status;
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
	 * 상태 Getter
	 * @return 상태
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 상태 Setter
	 * @param status 상태
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Override toString 자동 생성
	 */
	@Override
	public String toString() {
		return "EmpStatus [id=" + id + ", status=" + status + "]";
	}
	
}
