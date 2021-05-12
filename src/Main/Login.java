package Main;

import java.util.ArrayList;
import java.util.Scanner;

import Admin.AdminMenu;
import User.User;
import User.UserMenu;

/**
 * 
 * ID, PW를 입력하여 로그인하는 클래스입니다.
 * 로그인시 관리자 or 사용자 구분하여 이동합니다.
 * 
 * @author Kimyounghyuk
 *
 */
public class Login {
	
	static Scanner scan = new Scanner(System.in);
	private ArrayList<User> userList = new ArrayList<User>();
	static User user;
	
		
	public Login(ArrayList<User> userList) {
		this.userList = userList;
		login();
	}
	
	/**
	 * 
	 * 로그인시 사용자인지, 관리자인지 확인 후 이동하는 메소드입니다.
	 * 
	 */
	public void login() {
		
		boolean flag = false;
		while(!flag) {
			
			System.out.print("ID: ");
			String id = scan.nextLine();
			
			System.out.print("PASSWORD: ");
			String password = scan.nextLine();
			
			for (User login : this.userList) {
				if(id.equals("admin") && password.equals("1234")) {
					flag = true;
					System.out.println("로그인 되었습니다.");
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					AdminMenu adminStart = new AdminMenu(userList);
					break;
				}
				if (login.getId().equals(id) && login.getPassword().equals(password)) {
					flag = true;
					System.out.println("로그인 되었습니다.");
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					UserMenu start = new UserMenu(login);
					break;
				}
			}
			if(!flag) {
				System.out.println("ID 또는 PASSWORD가 틀렸습니다. 다시 입력해주세요.");
			}
		}
	}
}
