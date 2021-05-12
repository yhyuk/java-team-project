package Main;

import java.util.ArrayList;
import java.util.Scanner;

import User.User;
/**
 * 
 * 사용자의 개인정보를 입력받아 비밀번호를 찾을 수 있는 메소드입니다.
 * 
 * @author Kimyounghyuk
 *
 */
public class PwFind {
	
	static Scanner scan = new Scanner(System.in);
	private ArrayList<User> userList = new ArrayList<User>();
	static User user;
	
	public PwFind(ArrayList<User> userList) {
		this.userList = userList;
		pwFind();
	}
	
	/**
	 * 
	 * 사용자의 개인정보(ID, 이름, Email)을 입력받아 일치하면 비밀번호를 초기화합니다.
	 * 초기화 이후, 경고메세지를 보여줍니다.
	 * 
	 */
	public void pwFind() {
		boolean flag = false;
		System.out.print("ID 입력: ");
		String id = scan.nextLine();
		
		System.out.print("이름 입력: ");
		String name = scan.nextLine();
		
		System.out.print("Email 입력: ");
		String eamil = scan.nextLine();
		
		for (User login : this.userList) {
			if (login.getId().equals(id) && login.getName().equals(name) && login.getEmail().equals(eamil)) {
				flag = true;
				login.setPassword(login.getPhone().substring(9));
				System.out.println("비밀번호가 초기화 되었습니다.");
				System.out.println("★★★초기화된 비밀번호는 사용자의 휴대폰 뒷번호 4자리입니다.★★★");
				System.out.println("★★★초기화된 후 반드시 개인정보수정에서 비밀번호 변경을 해야합니다.★★★");
			}
		}
		if(!flag) System.out.println("잘못된 입력입니다. 메인화면으로 돌아갑니다.");
	}
}
