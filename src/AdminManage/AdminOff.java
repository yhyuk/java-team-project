package AdminManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import User.User;
import UserVacation.OffMemo;

/**
 * 
 * 관리자모드에서 휴가승인을 관리하는 클래스입니다.
 * @author User 1조 경지윤
 *
 */
public class AdminOff {

	private static Scanner scan;
	private final static String DATA;
	/**
	 * 1페이지당 8개씩 뿌리기 위한 변수
	 */
	private static int onePageCnt = 8;
	private static ArrayList<OffMemo> list;
	
	static {
		scan = new Scanner(System.in);
		DATA = "data\\휴가신청명단.txt";
		list = new ArrayList<OffMemo>();
	}

	
	public AdminOff(ArrayList<User> loginList) {

		offListLoad();
		
		
		int pageNo = 0;
		int totalPage = totalPageCalc(list.size());
		
		/**
		 * 휴가신청명단 테이블 출력
		 */
		offListView(pageNo, totalPage,  loginList);
		
		
		boolean loop = true;
	
		while (loop) {
			
			/**
			 * 처음 시작 페이지 일때<br/>
			 * 2.다음페이지만 뜨도록 
			 */
			if(pageNo == 0) {
				System.out.println("\t                   2.다음페이지");
			} else if(pageNo+1 == totalPage){
				
				/**
				 * 해당페이지가 총 페이지 개수와 같을때 (마지막페이지)<br/>
				 * 1.이전페이지만 띄우기 
				 */
				System.out.println("\t                   1.이전페이지");
				
			}else {
				System.out.println("\t        1.이전페이지   2.다음페이지");
			}
			
			
			System.out.println("\t\t\t    3.자세히 보기");
			System.out.println("________________________________________");
			
			while(true) {
			System.out.print("번호입력(뒤로가기는 0) : ");
			String sel = scan.nextLine();
			System.out.println();
			
			
			
			if (sel.equals("1")) {
				
				/**
				 * 첫번쨰 페이지만 아니면 pageNo를 차감하기
				 */
				if(pageNo > 0) {
					pageNo--;
					offListView(pageNo,totalPage, loginList);
					break;
					
				} else if(pageNo>=0) {
					System.out.println();
					System.out.println("error! : 현재 화면은 첫번째 페이지 입니다.");
					
				}
				
				
				
				
			} else if (sel.equals("2")) {

				/**
				 * 마지막 전페이지 까지만 pageNo가감하기 
				 */
				if(pageNo+1 < totalPage) {
					pageNo++;
					offListView(pageNo,totalPage, loginList);
					break;
				} else if (pageNo+1==totalPage) {
					
					System.out.println();
					System.out.println("error! : 현재 화면이 마지막 페이지 입니다.");
				}
				
			} else if (sel.equals("3")) {
				
				/**
				 * 상세보기 메소드 호출
				 */
				detail();
				offListView(pageNo,totalPage, loginList);
				break;
				
			} else if (sel.equals("0")) {
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println();
				loop = false;
				break;
			} else {
				System.out.println();
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

			}
			}

			
		}
		
		
	}
	
	/**
	 * 실제로 8개씩 페이지를 나누었을때 총 몇페이지가 나오는지 계산하는 메소드입니다.
	 * @param size : 휴가신청명단의 총 개수 
	 * @return 계산된 총 페이지 개수 (계속 추가될시에 8개씩 나눠서 보이도록) 
	 */
	public int totalPageCalc (int size) {
		
		/**
		 * 리스트의 개수를 총 8로 나누기
		 */
		int cnt = size/onePageCnt;
		
		/**
		 * 나머지 1보다 크면(마지막페이지 8개 안채워졌을때) 1페이지 더 축적하기  
		 */
		cnt += size%onePageCnt > 0 ? 1 : 0;
		return cnt;
	}
	
	/**
	 * 게시물의 리스트를 출력하는 메소드입니다.<br/>
	 * @param nowPage : 위에서 변수선언한 pageNo
	 * @param totalPage : : 총페이지 개수 
	 * @param loginList 
	 */
	public void offListView ( int nowPage, int totalPage, ArrayList<User> loginList) {

	
		
		/**
		 * tmp는 list의 값의 index(방번호)를 의미합니다. 
		 */
		int tmp = (list.size()-1)-(nowPage*onePageCnt);
		/**
		 * 맨위에있는 글번호보다 8개 차이나는 글번호까지 끊기 위해
		 */
		int forTmp = (tmp - onePageCnt)+1;
		
		System.out.println();
		System.out.println("            [직원 휴가신청 목록]");
		System.out.println("========================================");
		System.out.println("[글번호]\t[이름(사번)]\t[사유]\t   [상태]");
		System.out.println("----------------------------------------");
		
		for (int i = tmp; i>=forTmp; i--) {
			
			/**
			 * 마지막 페이지에 첫 게시글의 index는 [0]
			 */
			if (i<0) {
				
				break;
			}
			
			/**
			 * 사용자 정보에 이름 있는 데이터 뒤지면서 사번이 똑같으면 해당 이름 출력하기
			 */
			for (int j= loginList.size()-1; j>=0; j--) {
				
				/**
				 * 테이블 리스트 띄우기 3글자 이상은 점점 처리하기
				 */
				String content = list.get(i).getContext();
				if (content.length() > 3) {
					content = content.substring(0, 3) + "..";
				}
				
				String result = "";
				
				
				/**
				 * 전체 사용자정보.txt명단과 휴가신청명단.txt의 사번으로 비교하기
				 * 사번이 동일하면 해당 정보 출력하기
				 */
				if(list.get(i).getMemberId().equals(loginList.get(j).getId())) {
					
					result = loginList.get(j).getName();
					
					System.out.printf("%03d\t%s(%s)\t%s\t    %3s\n"
									, list.get(i).getNum()
									, result
									, list.get(i).getMemberId()
									,content
									,list.get(i).getAdmission());
				} else {
					result = null;
				}
				
			}


		}




		

		System.out.println("              ["+(nowPage+1) + " / " + totalPage +"]          ");
		System.out.println("________________________________________");
	

	}

	/**
	 * 지정한 게시물을 상세보기하는 메소드입니다. <br/>
     * 해당글이 미승인상태라면 승인처리까지 묻는 메소드 
	 */
	public void detail() {

		
		while (true) {
			
			
		System.out.print("자세히 볼 게시물의 글번호를 입력해주세요 :");
		String seq = scan.nextLine();
		
		/**
		 * getOffMemo 해당 글번호의 메모를 받아오는 메소드 호출
		 */
		OffMemo memo = getOffMemo(seq);

		if (Integer.parseInt(seq) <= list.size() && Integer.parseInt(seq)>0) {
			
				System.out.println();
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println();
				System.out.println("[휴가신청 상세보기]");
				System.out.println("￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
				
	
				System.out.println("[작성자: " + memo.getMemberId() + "]" + "[상태: " + memo.getAdmission() + "]");
				System.out.println();
				System.out.println("휴가내용: " + memo.getContext());
				System.out.println("휴가기간: " + memo.getStartDate() + " ~ " + memo.getEndDate());
				System.out.println();
				
				/**
				 * 위에서 받아온 해당 글번호의 내용중 admission이 미승인이면 출력시키기
				 */
				if (!memo.getAdmission().equals("승인")) {
					
					
					System.out.print("\"휴가신청을 승인하시겠습니까?(y/n)\" : ");
					String okay = scan.nextLine();
					System.out.println();
					
					for (int i=0; i<list.size(); i++) {
						
						/**
						 * 해당 메모를 올린 사람의 사번과 list 속 사번을 비교<br/>
						 * 같으면 승인 미승인 세팅 시작
						 */
						if(Integer.parseInt(seq) == list.get(i).getNum()) {
							/**
							 * 승인 미승인을 반환받은뒤 admission변수에 세팅
							 */
							list.get(i).setAdmission(getIsOkay(okay));
							System.out.println();
							
						} 
					}
					save();
					pause();
					break;
					
				} else  {
					pause();
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					break;
				} 
			} else {
				System.out.println("글번호를 다시입력해주세요!");
			}
		}
		
		
	}



	/**
	 * 입력받은 문자로 승인 미승인 값을 반환하는 메소드입니다.
	 * @param okay 승인할지말지 입력받은 값
	 * @return 미승인 or 승인
	 */
	public String getIsOkay(String okay) {
		
		

			/**
			 *  y 라고하면 승인을 돌려주기<br/>
			 *  n 라고하면 미승인을 돌려주기
			 */
		while (true) {
			
			if (okay.equals("y")) {
				
				System.out.println("승인이 완료되었습니다.");
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				return "승인";
				
			} else if(okay.equals("n")){
				// 아니면 그대로....
				System.out.println("승인을 보류하였습니다.");
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				return "미승인";
			} else {
				System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
				System.out.print("\"휴가신청을 승인하시겠습니까?(y/n)\" : ");
				okay= scan.nextLine();
			}

		}

	}
	

	/**
	 * 입력받은 글번호의 내용을 받아오는 메소드입니다. 
	 * @param seq 입력받은 글번호
	 * @return 입력받은 글번호의 해당 내용을 반환합니다.
	 */
	public OffMemo getOffMemo(String seq) {
			
		
			/**
			 * 한번더 list읽어오기
			 */
			for (OffMemo memo : list) {
				
				if (memo.getNum() == Integer.parseInt(seq)) {
					return memo;
				}
			}
			return null;
		}
		

	/**
	 * 입력 Block 메소드입니다.
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();// Block
	}

	


	/**
	 * 휴가신청명단.txt 파일을 읽어오는메소드입니다.
	 */
	public void offListLoad() {
		
		list.clear();
		
		try {

			BufferedReader reader = new BufferedReader(new FileReader(DATA));
			String line = "";
			
			
			while ((line = reader.readLine()) != null) {

			
				
				
				OffMemo memo = new OffMemo();

				String[] temp = line.split("■");

				memo.setNum(Integer.parseInt(temp[0]));
				memo.setMemberId(temp[1]);
				memo.setStartDate(temp[2]);
				memo.setEndDate(temp[3]);
				memo.setContext(temp[4]);
				memo.setAdmission(temp[5]);

				/**
				 * 메모 1건 OffMemo 객체 1개에 옮겨 담기
				 */
				list.add(memo);

			} // while

			reader.close();


		} catch (Exception e) {
			
			System.out.println("load: " + e);
			
		}
		
	}
	
	/**
	 * 작업후 수정된 내용을 다시 저장하는 메소드입니다.
	 */
	public static void save() {
		
		try {
			
			 BufferedWriter writer = new BufferedWriter(new FileWriter(DATA));
			 String result = "";
			 for(OffMemo memo : list) {
				 
				 
				// 글번호■1000910■2021-07-09■2021-07-10■가족행사■1
				 result += String.format("%s■%s■%s■%s■%s■%s\r\n"
						 						,memo.getNum()
						 						,memo.getMemberId()
						 						,memo.getStartDate()
						 						,memo.getEndDate()
						 						,memo.getContext()
						 						,memo.getAdmission());
				 
				 
				 
				 
			 }
			 writer.write(result);
			 
			 
			 writer.close(); //(*************************)

		
		} catch (Exception e) {
			System.out.println(e);
		}
		 
		  
		  
	
	}

}

