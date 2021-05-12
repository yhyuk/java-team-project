package UserVacation;

/**
 * 휴가신청명단의 정보를 얻기위한 클래스입니다.
 * @author User 1조 경지윤
 *
 */
public class OffMemo {

	private int Num;
	private String memberId;
	private String startDate;
	private String endDate;
	private String context;
	private String admission;
	
	/**
	 * 
	 * @return 휴가신청명단의 글번호
	 */
	public	int getNum() {
		return Num;
	}
	/**
	 * 
	 * @param num
	 * 휴가신청명단의 글번호
	 */
	public void setNum(int num) {
		Num = num;
	}
	/**
	 * 
	 * @return 휴가신청직원의 사번
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * 
	 * @param memberId
	 * 휴가신청명단의 글번호
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * 
	 * @return 휴가신청 시작날짜
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * 
	 * @param startDate
	 * 휴가신청 시작날짜
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 
	 * @return 휴가신청 종료날짜
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * 
	 * @param endDate
	 * 휴가신청 종료날짜
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return 휴가신청 내용
	 */
	public String getContext() {
		return context;
	}
	/**
	 * 
	 * @param context
	 * 휴가신청 내용
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
	/**
	 * 
	 * @return 해당 휴가신청의 처리상태(승인,미승인)
	 */
	public String getAdmission() {
		return admission;
	}
	/**
	 * 
	 * @param admission
	 * 해당 휴가신청의 처리상태(승인,미승인)
	 */
	public void setAdmission(String admission) {
		this.admission = admission;
	}
	
}
