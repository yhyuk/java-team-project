package UserNotice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;

/**
 * 관리자가 공지사항을 작성, 확인, 삭제, 관리하는 클래스
 * 
 */
public class AdminNotice extends Notice{
	
	/**
	 * 공지사항을 관리할 수 있는 메뉴창을 호출해 관리자의 선택에 따라 화면을 이동시키는 메소드.
	 * 
	 */
	public static void managerNotice() {
		
		load();
		
		boolean loop = true;
		
		while(loop) {
			
			String sel = menu();
			
			if (sel.equals("0")) {
				//뒤로가기
				loop = false;
				
			} else if (sel.equals("1")) {
				//공지사항 작성하기
				noticeWrite();
				
			} else if (sel.equals("2")) {
				//공지사항 목록보기
				noticeList();
				
			} else if (sel.equals("3")){
				//프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				loop = false;
				
			} else {
				System.out.println("주어진 보기에서 선택해주세요.");
				
			} 
			
		}//while

	}
	
	/**
	 *  공지사항 목록을 관리하는 메소드
	 */
	public static void noticeList() {
		
		/**
		 * 공지사항 목록 페이지 이동을 입력받는 변수
		 */
		int pageNum = 0;
		
		/**
		 * 공지사항 총 페이지 번호를 관리하는 변수
		 */
		int totalPage = ((list.size() - 1) / N) + 1;

		boolean loop = true;

		while(loop) {
			System.out.println(
					"\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.println("                                    [ 공지사항 목록 ]\n");
			System.out.println("\n[번호]\t\t\t[제목]\t\t\t   [날짜]\t\t\t\t[내용]\n");

			/**
			 * 시작하는 페이지 변수. 10개 단위로 이동한다. 
			 */
			int start = pageNum * N; 
			
			/**
			 * 마지막 페이지 변수 
			 */
			int end = Math.min((pageNum + 1) * N, list.size());

			for (int i = start; i < end; i++) {

				NoticeMemo memo = list.get(i);
				String content = memo.getContent().replace("\r\n", " ");
				String title = memo.getTitle();

				if (content.length() > 13) {
					content = content.substring(0, 13) + "...";
					title = title.substring(0, 7) + "...";
				}

				System.out.printf("%3d\t%23s\t\t%11s\t%20s\n", memo.getNum(), title, memo.getDate(), content);
			}

			// menu
			System.out.printf("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%d / %d page", pageNum + 1, totalPage);
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.println("1. 이전 목록");
			System.out.println("2. 다음 목록");
			System.out.println("3. 자세히 보기");
			System.out.println("4. 삭제하기");
			System.out.println("0. 뒤로가기");
			System.out.print("메뉴 번호입력 : ");

			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// 이전페이지
				if (pageNum - 1 < 0) {

					System.out.println("이전 페이지가 없습니다.");
				} else {
					pageNum--;
				}

			} else if (sel.equals("2")) {
				// 다음페이지
				if ((pageNum + 1) * N >= list.size()) {

					System.out.println("다음 페이지가 없습니다.");
				} else {
					pageNum++;
				}

			} else if (sel.equals("3")) {

				// 자세히보기
				noticeDetail();
				scan.skip("\r\n");
				stop();
				choose();

			} else if (sel.equals("4")) {

				// 삭제하기
				delete();

			}

			else if (sel.equals("0")) {
				// 뒤로가기
				loop = false;
				
			} else {
				System.out.println("보기에 있는 숫자를 입력해주세요.");
			}
		}

	}

	/**
	 * 공지사항을 삭제하는 메서드
	 */
	public static void delete() {
		
		while (true) {
			System.out.print("삭제할 번호 입력(-1 입력시 뒤로감) :");
			/**
			 * 삭제할 번호를 입력받는 변수
			 */
			int number;

			try {
				number = scan.nextInt();
				scan.skip("\r\n");
				
			} catch (Exception e) {

				//숫자가 아닌 정보가 입력되는 경우 처리
				System.out.println("\n숫자를 입력해주세요.");
				scan.skip("\r\n");
				continue;

			}
			
			// -1을 입력했을 경우 메소드 종료
			if (number == -1) {
				System.out.println("\n취소되었습니다.");
				return;
			}

			/**
			 * 데이터의 번호를 저장하는 변수 
			 * -1을 입력받으면 삭제를 취소한다. 
			 */
			int index = numToIndex(number);
			
			if (index == -1) {
				System.out.println("\n목록에 있는 숫자를 입력해주세요.");
				
			} else {
				System.out.print("정말 삭제하시겠습니까? (y/n) :");
				
				/**
				 * 공지사항을 삭제하기전 사용자의 의견을 저장하는 변수
				 */
				String answer = scan.nextLine();
				
				if (answer.toUpperCase().equals("Y")) {
					list.remove(index);
					System.out.printf("\n%d번 공지사항이 삭제 되었습니다.\n", number);

					save();
					break;

				}

			}
		}

	}

	/**
	 * 
	 * @param number 사용자가 입력한 번호
	 * @return 입력한 번호가 목록에 없으면 -1 , 목록에 있으면 인덱스
	 */
	public static int numToIndex(int number) {
		for(int i=0; i<list.size(); i++) {
			NoticeMemo memo = list.get(i);
			if (memo.getNum() == number) return i;
		}
		
		
		return -1;
	}
	
	/**
	 * 프로그램 진행을 선택하는 메소드
	 */
	public static void choose() {
		String sel;
		System.out.print("\n0. 뒤로가기\t");
		System.out.print("1. 프로그램 종료\n");
		System.out.print("\n번호 입력 : ");
		sel = scan.nextLine();

		if (sel.equals("0")) {
			// 뒤로가기
			return;

		} else if (sel.equals("1")) {

			// 프로그램 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		} else {
			// 다른 번호를 입력했을 경우 예외처리
			System.out.println("주어진 보기에서 선택해주세요.");
		}
	}
	

	/**
	 * 관리자가 공지사항을 작성하는 메소드
	 */
	public static void noticeWrite() {

		System.out.println(
				"\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		System.out.println("       [ 공지사항 작성 ]");
		System.out.println();
		System.out.print("제목 : ");
		String noticeTitle = scan.nextLine();

		System.out.print("배포 대상(부서명 or 전체 입력) : ");
		String noticeTarget = scan.nextLine();

		System.out.print("내용(exit-입력종료): ");
		String noticeContent = "";

		while (true) {
			String temp = scan.nextLine();
			if (temp.equals("exit")) {
				break;
			}

			noticeContent += temp + "\r\n";
		}

		NoticeMemo noticeMemo = new NoticeMemo();

		noticeMemo.setTitle(noticeTitle); // 제목
		noticeMemo.setReadTargetTeam(noticeTarget); // 배포대상팀
		noticeMemo.setReadTargetPosition("전체"); // 배포대상직급
		noticeMemo.setContent(noticeContent); // 내용

		Calendar today = Calendar.getInstance();
		noticeMemo.setDate(String.format("%tF", today)); // 오늘날짜

		noticeMemo.setNum(num()); // 번호

		System.out.print("\n0.뒤로가기\n");
		System.out.print("1.저장\t");
		System.out.print("\n번호 입력: ");
		String sel = scan.nextLine();

		if (sel.equals("0")) {
			// 뒤로가기

		} else if (sel.equals("1")) {

			list.add(noticeMemo);
			save();

			System.out.println("작성이 완료되었습니다.");
			stop();

		}

	}

	/**
	 * 공지사항을 데이터 파일에 저장하는 메소드 
	 */
	public static void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA));
			for (NoticeMemo memo : list) {
				writer.write(String.format("%d■%s■%s■%s■%s\n%s==========\n"
						, memo.getNum()
						, memo.getTitle()
						, memo.getDate()
						, memo.getReadTargetTeam()
						, memo.getReadTargetPosition()
						, memo.getContent()));
			}

			writer.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * 공지사항 번호를 관리하는 메소드
	 * @return 입력된 공지사항 중 가장 마지막에 저장된 번호를 + 1
	 */
	public static int num() {
		int max = 0;

		for (NoticeMemo memo : list) {
			if (memo.getNum() > max) {
				max = memo.getNum();
			}
		}
		return max + 1;
	}
	
	/**
	 * 공지사항 메뉴 선택을 위한 메소드
	 * @return 사용자가 선택한 번호를 리턴
	 */
	public static String menu() {

		System.out.println(
				"\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		System.out.println("       [ 공지사항 ]\n");
		System.out.println("      1. 공지사항 작성하기");
		System.out.println("      2. 공지사항 목록보기");
		System.out.println("      3. 프로그램 종료");
		System.out.println("      0. 뒤로가기");
		System.out.println(
				"\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("\n번호 입력: ");

		String sel = scan.nextLine();

		return sel;
	}

}// Notice
