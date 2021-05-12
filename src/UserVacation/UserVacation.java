package UserVacation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import User.User;

/**
 * 사용자가 휴가를 관리하기 위한 클래스입니다.
 * @author User 1조 경지윤
 *
 */
public class UserVacation {

	private static Scanner scan;
	private final static String DATA;
	private static ArrayList<OffMemo> list;

	static {

		scan = new Scanner(System.in);
		DATA = "data\\휴가신청명단.txt";
		list = new ArrayList<OffMemo>();

	}

	/**
	 * 
	 * @param user 사용자정보.txt 를 넣은 클래스(User) 객체
	 */
	public UserVacation(User user) {

		OffListLoad();

		boolean loop = true;
		while (loop) {

			
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
			System.out.println("                [휴가 관리하기]");
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
			System.out.println("1. 휴가 신청하기\n2. 남은휴가확인\n3. 휴가 승인확인");
			System.out.println("0. 뒤로가기");
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("번호 입력: ");
			String sel = scan.nextLine();
			System.out.println();
			System.out.println();
			if (sel.equals("1")) {

				writeVacation(user);
				save();

			} else if (sel.equals("2")) {

				remain(user);

			} else if (sel.equals("3")) {

				checkAdmission(user);

			} else if (sel.equals("0")) {
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				loop = false;
				save();
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}

		} // while

	}

	/**
	 * 휴가 승인상태를 출력하는 메소드입니다. 
	 * @param user
	 */
	public void checkAdmission(User user) {

		System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("                [휴가 승인 확인하기]");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("[작성날짜]     [휴가내용]    [휴가날짜]     [처리상태]");
		String admission = "";

		/**
		 * list(휴가신청명단) 뒤져서 로그인한 사람의 사번과 비교후 동일하면 출력시킵니다.
		 */
		for (int i = 0; i < list.size(); i++) {

			String txt = "";
			if (user.getId().equals(list.get(i).getMemberId())) {

				/**
				 * 4글자 넘어가면 생략하기
				 */
				if (list.get(i).getContext().length() > 4) {
					txt = list.get(i).getContext().substring(0, 4) + "..";
				} else {
					txt = list.get(i).getContext();
				}

				System.out.printf("%s  %s\t%s     %4s\n", list.get(i).getStartDate(), txt, list.get(i).getEndDate(),
						list.get(i).getAdmission());
			}
		}
		
		System.out.println();
		System.out.println();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		pause();
	}

	/**
	 * 남은 휴가일수를 확인하고 출력하는 메소드입니다. 
	 * @param user
	 */
	public void remain(User user) {

		/**
		 * reaminDay : 남은 휴가 날짜  
		 */
		int remainDay = 12;
		
 		for (int i = 0; i < list.size(); i++) {

			if (user.getId().equals(list.get(i).getMemberId())) {

				int startDay = Integer.parseInt(list.get(i).getStartDate().substring(8,10));
				int endDay = Integer.parseInt(list.get(i).getEndDate().substring(8,10));
				
				
				/**
				 * 휴가 시작날짜의 달과 휴가 종료날짜의 달이 다르면 해당 달만큼의 날짜를 더해준다.<br/>
				 * 예시] 2021-12-31 ~ 2021-01-02 
				 */
				if ( 
					(Integer.parseInt(list.get(i).getStartDate().substring(5, 7))) 
					!= (Integer.parseInt(list.get(i).getEndDate().substring(5, 7)))
					) {
					
					switch(list.get(i).getStartDate().substring(5,7)) {
						case "01" :
						case "03" :
						case "05" :
						case "07" :
						case "08" :
						case "10" :
						case "12" :
							endDay = endDay + 31;
							break;
						case "04" :
						case "06" : 
						case "09" :
						case "11" :
							endDay = endDay +30;
							break;
						case "02" :
							//시간남으면 윤년평년인지에 따라 값을 달리주는 메소드 만들어놓기 
							endDay = endDay + 28;
							break;
							
								
					}
					
					
				}
				
				/**
				 * 휴가 종료날짜의 일수에서 시작날짜의 일수를 빼고 1을 더해주면 총 사용휴가기간입니다.
				 */
				int result = endDay - startDay;
				remainDay = remainDay-(result+1);
				
				/**
				 *  음수일때, 0일때 남은횟수없다고 하기
				 */
				if (remainDay == 0 || remainDay < 0) {
					remainDay = 0;
				}

			} else {

			}
		}

		System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("                [남은 휴가 확인하기]");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
		System.out.printf("%s님 휴가가 총 %d일 남았습니다!", user.getName(), remainDay);
		System.out.println();
		System.out.println();
		System.out.println("                       (남은 휴가는 1년단위 입니다.)");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		pause();
	}

	
	/**
	 * 사용자가 휴가신청을 작성하기 위한 메소드입니다.<br/>
	 * 신청완료와 동시에 작성내용 상세보기를 출력합니다.
	 * @param user 글 작성시 이름을 얻기위해 필요합니다.
	 */
	public void writeVacation(User user) {
		
		while (true) {

			System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
			System.out.println("                [휴가 신청하기]");
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――");
			System.out.println("휴가내용 입력 (뒤로가기는 0 입력): ");
			String txt = scan.nextLine();

			if (!txt.equals("0")) {
				
				String startOff = "";
				String endOff = "";

				/**
				 * 유효성검사입니다.
				 */
				while (true) {

					System.out.print("휴가날짜를 입력해주세요.");
					System.out.println("휴가 시작 날짜를 입력해주세요 (YYYY MM DD): ");
					startOff = scan.nextLine();

					String value = startOff.replace("-", "").replace(" ", "");
					if (value.length() != 8) {
						System.out.println("날짜를 8글자로 입력해주세요.");
					} else {
						break;
					}
				}
				
				while (true) {
					System.out.println("휴가 마지막 날짜를 입력해주세요 (YYYY MM DD): ");
					endOff = scan.nextLine();

					String value2 = endOff.replace("-", "").replace(" ", "");
					if (value2.length() != 8) {
						System.out.println("날짜를 8글자로 입력해주세요.");
					} else {
						break;
					}

				}

				/**
				 * String value : 무엇을 입력하든 일정하게 출력받기위해
				 */
				String value = startOff.replace("-", "").replace(" ", "");
				String result = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);

				String value2 = endOff.replace("-", "").replace(" ", "");
				String result2 = value2.substring(0, 4) + "-" + value2.substring(4, 6) + "-" + value2.substring(6, 8);
				System.out.println("휴가작성이 완료되었습니다.");
				

				/**
				 * 작성내용 출력해주기
				 */
				Calendar now = Calendar.getInstance();
				/**
				 *  작성후 초기상태 무조건 미승인으로 하기
				 */
				System.out.println();
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println();
				System.out.println();
				System.out.println("      	[휴가신청 내역보기]");
				System.out.println("￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
				System.out.println("[작성자: " + user.getName() + "]" + "[상태: 미승인]");
				System.out.println("휴가내용: " + txt);
				System.out.println("신청날짜: " + result + " ~ " + result2);
				System.out.printf("[작성날짜 : %tF]", now);
				System.out.println();
				System.out.println();
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println();
				/**
				 * 방금 작성완료한 내용을 list에 저장하기 위한 메소드입니다. 휴가신청명단 추가
				 */

				// 내용 추가-저장하기!
				/**
				 * OffMemo라는 휴가관련 클래스를 담은 ArrayList에 추가하기
				 */
				OffMemo memo = new OffMemo();
				memo.setNum(list.size() + 1);
				memo.setContext(txt);
				memo.setAdmission("미승인");
				memo.setStartDate(result);
				memo.setEndDate(result2);
				memo.setMemberId(user.getId());

				list.add(memo);
				/**
				 * 방금 작성완료한 내용을 휴가신청명단.txt에 저장하기위한 메소드입니다.
				 */
				save();
				pause();
				break;

			} else if (txt.equals("0")) {
				System.out.println();
				System.out.println();
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				pause();
				break;
			}

		}

	}


	/**
	 * 입력 Block 메소드입니다.
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();// Block
	}

	/**
	 * 변경내용을 휴가신청명단.txt에 저장하기위한 메소드입니다.
	 */
	public void save() {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA));
			String result = "";
			for (OffMemo memo : list) {

				// 1000910■2021-07-09■2021-07-10■가족행사■승인
				result += String.format("%s■%s■%s■%s■%s■%s\r\n",memo.getNum() , memo.getMemberId(), memo.getStartDate(),
						memo.getEndDate(), memo.getContext(), memo.getAdmission());
			}
			writer.write(result);
			writer.close(); // (*************************)

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * 휴가신청명단.txt를 읽어오는 메소드입니다.
	 */
	public void OffListLoad() {

		/**
		 * clear()<br/>
		 * 안해주면 (같은 사람이 연속적으로 2개를 추가한다고 했을때) list가 중복으로 기록됩니다.
		 * 		 
		 */
		list.clear();

		try {

			BufferedReader reader = new BufferedReader(new FileReader(DATA));
			String line = "";
			int count = 1;

			while ((line = reader.readLine()) != null) {

				OffMemo offmemo = new OffMemo();

				// 1000910■2021-07-09■2021-07-10■가족행사■1
				String[] temp = line.split("■");

				offmemo.setNum(Integer.parseInt(temp[0]));
				offmemo.setMemberId(temp[1]);
				offmemo.setStartDate(temp[2]);
				offmemo.setEndDate(temp[3]);
				offmemo.setContext(temp[4]);
				offmemo.setAdmission(temp[5]);

				count++;
				/**
				 * 메모 1건 : OffMemo 객체 1개에 옮겨 담기
				 */
				list.add(offmemo);

			} // while

			reader.close();

		} catch (Exception e) {
			System.out.println("load: " + e);
		}

	}

}
