package UserSuggestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import User.User;

/**
 * 사용자가 건의사항을 작성하거나 작성 내역을 확인하는 클래스
 * @author 82106
 *
 */
public class UserOfferWrite extends OfferList {

	private static ArrayList<User> userInfo; //사용자 정보 데이터
	private static User userData; //로그인한 사용자 객체
 	private final static String USERDATA;
	private static String id = "";
 	

	static {

		USERDATA = "data\\사용자정보.txt";
		userInfo = new ArrayList<User>();
		userData = new User();
		
	}
	
	public UserOfferWrite(User login) {
		this.id = login.getId();
	}
	
	/**
	 * 
	 *사용자의 팀명, 직급을 받아와 객체로 만들고 user(ArrayLIst)에 담는 메소드 
	 *
	 */
	public static void userLoad() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(USERDATA));
			
			String line = "";
			
			while((line = reader.readLine()) != null) {

				User user = new User();
				
				String[] temp = line.split("■");
				
				user.setId(temp[0]); 	   //사용자 사번(id)
				user.setTeam(temp[3]);     //사용자 팀명
				user.setPosition(temp[4]); //사용자 직급
				
				userInfo.add(user);

			}
			
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e);		
		}
	}


	/**
	 * 로그인한 아이디와 일치하는 건의사항을 찾는 메소드
	 * @return 일치하면 true, 일치하지 않으면 false를 반환
	 */
	public static boolean setUserInfo(){
		for(User user : userInfo) {
			//로그인한 아이디가 사용자 리스트에 있는 지 확인(사용자 아이디 확인)
			if(user.getId().equals(id)) {
				userData.setId(user.getId()); //아이디를 로그인한 사용자 정보를 담는 객체에 담기
				update();
				return true;
			}

		}

		return false;
	}

	/**
	 * 로그인한 사용자의 사번과 일치하는 건의사항만 걸러주는 메소드
	 */
	public static void update() {
		
		//list2 선언
		ArrayList<OfferMemo> list2 = new ArrayList<OfferMemo>(); 

		for(int i=0; i<list.size(); i++){
		    if(list.get(i).getId().equals(id)){
		        list2.add(list.get(i));
		    }
		}
		
		// array list 교체, 내가 필요한 건의사항만 담아서.
		list = list2;
		
	}

	/**
	 * 건의사항 목록을 보고 기능을 선택할 수 있게 하는 메소드
	 */
	public static void offerList() {
		
		load();
		userLoad();
		
		//로그인한 사용자의 아이디가 데이터에 없으면 종료
		if (!setUserInfo()){
			return;
		};

		
		int pageNum = 0;
		int totalPage = (list.size()-1) / N + 1;

		boolean loop = true;

		while(loop) {

			int start = pageNum*N; // start index, 0 => 0 , 10, 20, 30, ...
			int end = Math.min((pageNum + 1) * N, list.size()); // end : last_index보다 1 큼. 0,,, -1


			printOffer(start, end);
				

			
			System.out.printf( "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t%d / %d page\n", pageNum+1, totalPage);
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			String sel = memu();

			if (sel.equals("0")) {
				//뒤로가기
				loop = false;

			} else if (sel.equals("1")) {
				//이전보기
				if (pageNum-1 < 0){

					System.out.println("\n이전 페이지가 없습니다.");
				}
				else {
					pageNum--;
				}

			} else if (sel.equals("2")) {
				//다음보기
				if ((pageNum+1)*N >= list.size()){

					System.out.println("\n다음 페이지가 없습니다.");
				}
				else {
					pageNum++;
				}

			} else if (sel.equals("3")) {
				//자세히보기
				offerReadDetail();
				scan.skip("\r\n");
				stop();

			}else if (sel.equals("4")) {
				//건의사항 작성하기
				offerWrite();

			} else if (sel.equals("5")) {
				//프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);

			} else {
				System.out.println("\n주어진 보기만에서 선택해주세요.");
				stop();
			}

		}
	}

	
	/**
	 * 사용자가 건의사항을 작성하는 메소드
	 */
	public static void offerWrite() {

		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("\n[ 건의사항 작성 ]\n");
		System.out.print("제목 : ");
		String offerTitle = scan.nextLine();
		
		System.out.print("내용(exit-입력종료): ");
		String offerContent = "";
		
		while(true) {
			String temp = scan.nextLine();
			if(temp.equals("exit")){
				break;
			}
			
			offerContent += temp + "\r\n";
		}
		
		OfferMemo offerMemo = new OfferMemo();
		
		offerMemo.setTitle(offerTitle); //제목
		offerMemo.setId(userData.getId()); //사용자 아이디
		offerMemo.setContent(offerContent); //내용
		
		Calendar today = Calendar.getInstance();
		offerMemo.setDate(String.format("%tF", today)); //오늘날짜
		
		offerMemo.setNum(num()); //번호
		
		System.out.print("\n0.뒤로가기\n");
		System.out.print("1.저장하기\t");
		System.out.print("\n번호 입력: ");
		String sel = scan.nextLine();
		
		if (sel .equals("0")) {
			//뒤로가기
			return;
			
		} else if (sel.equals("1")) {
			
			list.add(offerMemo);
			append(offerMemo);
			
			System.out.println("\n작성이 완료되었습니다.");
			stop();
			
		}
		
	}
	
	/**
	 * 데이터를 입력,저장하는 메소드 
	 * @param offerMemo 사용자가 작성한 데이터
	 */
	public static void append(OfferMemo offerMemo) {
		
		// 저장하는 메소드
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA,true));
				writer.write(String.format("\n%d■%s■%s■%s■\n%s==========\n"
					, num()
					, offerMemo.getId()
					, offerMemo.getTitle()
					, offerMemo.getDate()
					, offerMemo.getContent()));

			writer.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 건의사항 메뉴를 출력후 사용자의 선택을 입력받는 메소드
	 * @return 사용자가 선택한 번호
	 */
	public static String memu() {
		
		System.out.println();
		System.out.println("1. 이전 목록");
		System.out.println("2. 다음 목록");
		System.out.println("3. 자세히 보기");
		System.out.println("4. 건의사항 작성하기");
		System.out.println("5. 프로그램 종료");
		System.out.println("0. 뒤로 가기\n");

		System.out.print("번호 입력: ");
		String sel = scan.nextLine();
		System.out.println();
		return sel;
		
	}
}