package UserNotice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import User.User;

/**
 * 사용자가 공지사항을 읽을 수 있는 클래스
 * @author 82106
 *
 */
public  class UserNoticeList extends Notice{
	
	private static ArrayList<User> userInfo;
	private static User userData;			  
 	private final static String USERDATA; // 사용자정보 데이터 파일 경로
	private static String id = "";
 	
	/**
	 * 사용자 공지사항 static 생성자
	 */
	static {

		USERDATA = "data\\사용자정보.txt";
		userInfo = new ArrayList<User>();
		userData = new User();
	}

	/**
	 * 
	 * @param login 사용자의 로그인 정보를 매개변수로 받아서 사용자 아이디를 가져오는 생성자
	 */
	public UserNoticeList(User login) {
		this.id = login.getId();
	}


	/**
	 * 사용자의 팀명을 받아와 객체로 만들고 ArrayList에 담는 메소드
	 */
	public static void userLoad() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(USERDATA));
			
			String line = "";
			
			while((line = reader.readLine()) != null) {

				User user = new User();
				
				String[] temp = line.split("■");
				
				user.setId(temp[0]); 	   //사용자 사번(id)
				user.setTeam(temp[4]);     //사용자 팀명
				user.setPosition(temp[5]); //사용자 직급
				
				userInfo.add(user);

			}
			
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e);		
		}
	}

	/**
	 * 로그인한 사용자의 아이디를 비교확인해서 boolean값을 리턴하는 메소드
	 * 사용자의 직업, 사용자의 직급을 객체에 저장한다. 
	 * @return 아이디 비교 후 true, false 결과
	 */
	public static boolean setUserInfo(){
		
		for(User user : userInfo) {
			//사용자 아이디 확인
			if(user.getId().equals(id)) {
				userData.setTeam(user.getTeam()); 		  //사용자 팀명
				userData.setPosition(user.getPosition()); //사용자 직급
				update();
				return true;
			}

		}

		return false;
	}

	/**
	 * 사용자의 팀, 직급과 일치하는 공지사항만 볼 수 있게 하는 메소드
	 */
	public static void update() {
		ArrayList<NoticeMemo> temp = new ArrayList<NoticeMemo>();
		String team = userData.getTeam();
		String position = userData.getPosition();
		
		for(NoticeMemo memo : list){
			if ((memo.getReadTargetTeam().equals("전체") || memo.getReadTargetTeam().equals(team))
					&& (memo.getReadTargetPosition().equals("전체") || memo.getReadTargetPosition().equals(position))){
				temp.add(memo);
			}
		}
		
		// array list 교체, 내가 필요한 공지사항만 담아서.
		list = temp;
		

	}

	/**
	 * 사용자의 공지사항을 출력하는 메소드
	 */
	public static void offerList() {
		
		load();
		userLoad();
		
		if (!setUserInfo()){
			return;
		};

		int pageNum = 0;
		int totalPage = ((list.size()-1) / N) + 1;

		boolean loop = true;

		while(loop) {

			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.println("\t\t\t\t\t\t\t[ 공지사항 목록 ]");
			System.out.println("\n[번호]\t\t     [제목]\t\t\t [날짜]\t\t     [부서]\t  [직급]\t\t[내용]\n");

			int start = pageNum*N; // start index, 0 => 0 , 10, 20, 30, ...
			int end = Math.min((pageNum + 1) * N, list.size()); // end : last_index보다 1 큼. 0,,, -1


			for(int i=start; i<end; i++) {
				NoticeMemo memo = list.get(i);
				String content = memo.getContent().replace("\r\n", " ");
				String title = memo.getTitle();

				if (content.length() > 13) {
					content = content.substring(0, 13) + "...";
					title = title.substring(0, 7) + "...";
				}


				//부서, 직급에 따라 보이는 공지사항이 다름.
				System.out.printf("%3d%28s\t\t%10s\t %7s\t   %s\t        %s\n"
						, list.get(i).getNum()
						, title
						, list.get(i).getDate()
						, list.get(i).getReadTargetTeam()
						, list.get(i).getReadTargetPosition()
						, content);

			}
			
			System.out.printf( "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%d / %d page ", pageNum+1, totalPage);
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			String sel = memu();

			if (sel.equals("0")) {
				loop = false;
				//뒤로가기

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
				noticeDetail();
				scan.skip("\r\n");
				stop();

			} else if (sel.equals("4")) {
				
				//프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);

			} else {
				
				//없는 번호를 입력했을 경우
				System.out.println("\n주어진 보기만에서 선택해주세요.");
				stop();
			}
			System.out.println("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		}
	}
	
	/**
	 * 공지사항 메뉴창을 사용자에게 출력 후 이용할 번호를 입력받아 리턴하는 메소드 
	 * @return 메뉴번호 
	 */
	public static String memu() {
		
		System.out.println();
		System.out.println("1. 이전 목록");
		System.out.println("2. 다음 목록");
		System.out.println("3. 자세히 보기");
		System.out.println("4. 프로그램 종료");
		System.out.println("0. 뒤로 가기");

		System.out.print("\n번호 입력: ");
		String sel = scan.nextLine();
		return sel;
	}

	
}
