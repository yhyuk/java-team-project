package UserSuggestion;

/**
 * 관리자가 건의사항을 확인하는 클래스 
 * @author 82106
 *
 */
public class AdminOffer extends OfferList{
	
	/**
	 * 건의사항을 읽어오는 메소드를 호출
	 * 건의사항 프로그램의 메뉴 메소드를 호출하는 실행 메소드
	 */
	public static void offerRead() {
			
			load();
			memu();
			
	}
	
	/**
	 * 
	 * @param number 건의사항 번호
	 * @return 일치하는 건의사항이 없으면 -1을 반환
	 */
	public static int numToIndex(int number) {
		for(int i=0; i<list.size(); i++) {
			OfferMemo memo = list.get(i);
			if (memo.getNum() == number) return i;
		}
		
		return -1;
	}
	
	/**
	 * 관리자가 목록에서 삭제할 번호를 입력받아 번호가 일치하는 건의사항을 삭제하는 메소드
	 */
	public static void delete() {
		
		// 삭제할 숫자 입력 받기
		while (true) {
			System.out.print("삭제할 번호 입력(-1 입력시 뒤로감) : ");
			int number;

			// 숫자 아닌거 입력되는 경우?
			try {
				number = scan.nextInt();
				scan.skip("\r\n");
				
			} catch (Exception e) {
				
				System.out.println("\n숫자를 입력해주세요.");
				scan.next();
				continue;

			}
			
			// -1을 입력했을 메소드 종료
			if (number == -1) {
				System.out.println("\n취소되었습니다.");
				return;
			}

			// 숫자 넘겨서 삭제하기, 에러 난 경우 없다고 출력
			int index = numToIndex(number);
			if (index == -1) {
				System.out.println("\n목록에 있는 숫자를 입력해주세요.");
			} else {
				System.out.print("정말 삭제하시겠습니까? (y/n) : ");
				String answer = scan.nextLine();
				
				if (answer.toUpperCase().equals("Y")) { // 대소문자 상관없이 입력 받음.
					list.remove(index);
					System.out.printf("\n%d번 건의사항이 삭제 되었습니다.\n", number);

					save();
					break;

				} else if(answer.toUpperCase().equals("N")) {
					System.out.println("삭제가 취소 되었습니다.");
					stop();
				}

			}
		}

	}
	
	
	/**
	 * 관리자에게 메뉴를 보여주고 번호선택을 입력받는 메소드
	 */
	public static void memu() {

		int pageNum = 0;
		int totalPage = ((list.size() - 1) / N) + 1;

		boolean loop = true;

		while (loop) {
			int start = pageNum * N; // start index, 0 => 0 , 10, 20, 30, ...
			int end = Math.min((pageNum + 1) * N, list.size()); // end : last_index보다 1 큼. 0,,, -1

			printOffer(start, end);
			System.out.printf("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%d / %d page\n", pageNum + 1, totalPage);
			System.out.println();
			System.out.println(
					"■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			System.out.print("\n0. 뒤로가기\n");
			System.out.print("1. 이전 목록\n");
			System.out.print("2. 다음 목록\n");
			System.out.print("3. 자세히 보기\n");
			System.out.print("4. 삭제하기\n");
			System.out.print("5. 프로그램 종료");

			System.out.print("\n\n번호 선택 : ");
			String sel = scan.nextLine();

			if (sel.equals("0")) {
				
				// 뒤로가기
				loop = false;

			} else if (sel.equals("1")) {
				
				// 이전 페이지
				if (pageNum - 1 < 0) {

					System.out.println("이전 페이지가 없습니다.");
				} else {
					pageNum--;
				}
			} else if (sel.equals("2")) {
				
				// 다음 페이지
				if ((pageNum + 1) * N >= list.size()) {

					System.out.println("다음 페이지가 없습니다.");
				} else {
					pageNum++;
				}

			} else if (sel.equals("3")) {
				
				// 자세히보기
				offerReadDetail();
				stop();
				scan.skip("\r\n");

			} else if (sel.equals("4")) {
				
				// 삭제하기
				delete();
				stop();

			} else if (sel.equals("5")) {
				
				// 프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				
			} else {
				
				//보기에 없는 번호를 입력했을 경우
				System.out.println("\n주어진 보기만에서만 선택해주세요.");
				stop();
				scan.skip("\r\n");
				memu();
			}

		}

	}
}
