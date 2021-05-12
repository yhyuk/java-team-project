package User;

/**
 * 
 * 사용자 정보를 저장하는 클래스입니다.
 * 사번(id), 비밀번호, 이름, 나이, 부서, 직급, 전화번호, 이메일, 주소, 입사날짜
 * 
 * @author 1조 팀원
 *
 */
public class User {
	
	private String id;
	private String password;
	private String name;
	private int age;
	private String team;
	private String position;
	private String phone;
	private String email;
	private String address;
	private String joinDate;
	
	
	public User(String id) {
		this.id = id;
	}
	
	public User() {
		
	}
	public User(String id, String password, String name, int age, 
				String team, String position, String phone, 
				String email, String address, String joinDate) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.team = team;
		this.position = position;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.joinDate = joinDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	
}
