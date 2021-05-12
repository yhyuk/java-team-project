package AdminSalary;

/**
 * 직급별 급여 정보를 담는 클래스
 * @author 유기호
 */
public class PositionSalary {
	//직급별 급여 데이터 저장용 클래스
	
	private String position;
	private int salary;
	
	/**
	 * 기본 생성자
	 */
	public PositionSalary() {
		
	}
	
	/**
	 * 직급, 급여를 매개변수로 받아 멤버를 초기화하는 생성자
	 * @param position 직급
	 * @param salary 급여(연봉)
	 */
	public PositionSalary(String position, int salary) {
		this.position = position;
		this.salary = salary;
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
	 * 급여 Getter
	 * @return 급여
	 */
	public int getSalary() {
		return salary;
	}
	
	/**
	 * 급여 Setter
	 * @param salary 급여
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * Override toString 자동 생성
	 */
	@Override
	public String toString() {
		return "PositionSalary [position=" + position + ", salary=" + salary + "]";
	}
	
}
